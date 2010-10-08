
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author Andre Karlsson
 *
 * The model part of application, all static data goes here.
 * 
 */
public class AppModel {

    String address;
    String port;
    String username;
    String password;
    FTPClient client = new FTPClient();
    File local;
    String remotePath;
    String localPath;
    final String defaultPath = "/";
    InputStream in = null;

    /**
     * Address to ftp server
     * @return adress entered by user
     */
    public String getAdress() {
        return address;
    }

    /**
     * Set adress to use when connecting to ftp server
     * @param adress to use in connection
     */
    public void setAdress(String adress) {
        this.address = adress;
    }

    /**
     * Get the password used at login
     * @return password entered by user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password to use at login
     * @param password used at login
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Port to ftp server
     * @return port entered by user
     */
    public String getPort() {
        return port;
    }

    /**
     * Set port to use when connecting to ftp server
     * @param port to use in connection
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Username used at login
     * @return username entered by user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username to use at login
     * @param username to use at login
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Check if connected to server
     * @return true if connected, false otherwise
     */
    public boolean isConnected() {
        if (client.isConnected()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Connect to ftp server
     * @return true if connection is made, false otherwise
     */
    public boolean connectFTP() {

        if (username.length() == 0) {
            return false;
        }

        try {
            System.out.println("Connecting to host " + address);

            /* Connect */
            client.connect(address, Integer.parseInt(port));
            /* Send credentials */
            client.login(username, password);

            System.out.println("User " + username + " login OK");
            System.out.println(client.getStatus());

            /* Make reply understandable */
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Connected Success");
            } else {
                System.out.println("Connection Failed");
                client.disconnect();
                return false; // Connection failed
            }
            return true; // Connection made
        } catch (Exception ex) {
            System.out.println("FTP connection error: " + ex.toString());
            return false;
        }
    }

    /**
     * Disconnect from ftp server
     * @throws IOException
     */
    public void disconnectFTP() throws IOException {
        if (client.isConnected()) {
            client.disconnect();
        }
    }

    /**
     * Get list of files in working directory
     * on server.
     * @return Array of FTPFiles
     * @throws IOException
     */
    public FTPFile[] getRemoteFileList() throws IOException {
        return client.listFiles();

    }

    /**
     * Get list of files in working directory
     * on computer.
     * @return Array of Files
     */
    public File[] getLocalFileList() {
        if (localPath == null) {
            localPath = defaultPath;
        }
        local = new File(localPath);
        return local.listFiles();
    }

    /**
     * Send file to ftp server uses ProgressMonitorInputStream
     * to display progress
     * @param file file to be sent to server
     */
    public void sendLocalFile(final File file) {

        try {

            /* Create streams */
            in = new FileInputStream(file);
            in = new ProgressMonitorInputStream(null, "Upload", in);

            /* The actual sending */
            client.storeFile(client.printWorkingDirectory() + "/" + file.getName(), in);


        } catch (IOException e) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, e);
            connectFTP();
        }
    }

    /**
     * Download file from ftp server, uses a ProgressMonitor
     * to display progress
     * @param file file to be downloaded
     */
    public void getRemoteFile(final FTPFile file) {
        try {
            final InputStream stO = new BufferedInputStream(
                    client.retrieveFileStream(file.getName()),
                    client.getBufferSize());

            final OutputStream stD = new FileOutputStream(localPath + file.getName());

            final ProgressMonitor pm = new ProgressMonitor(null, "Downloading",
                    "", 0, (int) file.getSize());

            /* Overwrite the CopyStreamAdapter so its possible to find
            how many bytes that have been transferred */
            org.apache.commons.net.io.Util.copyStream(
                    stO,
                    stD,
                    client.getBufferSize(),
                    file.getSize(),
                    new org.apache.commons.net.io.CopyStreamAdapter() {

                        @Override
                        public void bytesTransferred(long totalBytesTransferred,
                                int bytesTransferred,
                                long streamSize) {

                            /* Update ProgressMonitor */
                            pm.setProgress((int) totalBytesTransferred);

                            if (pm.isCanceled()) { // Cancel button is pressed
                                System.out.println("Task canceled.\n");
                                try {
                                    /* Close streams */
                                    stD.close();
                                    stO.close();
                                    return;
                                } catch (IOException ex) {
                                    Logger.getLogger(AppModel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }
                    });

            /* Do retrieve */
            client.completePendingCommand();

            /* Close streams */
            stD.close();
            stO.close();

        } catch (IOException e) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, e);
            connectFTP();
        }


    }

    /**
     * Change working directory on computer
     * @param localPath new working directory
     */
    public void changeLocalDir(String localPath) {

        if (localPath.equals("..") && !localPath.equals("/")) {

            /* Go to parent by removing last folder from path string */
            String[] folders = localPath.split("/");
            localPath = "/";
            for (int i = 0; i < folders.length - 1; i++) {
                localPath = folders[i] + "/";
            }
            this.localPath = localPath;

        } else {
            this.localPath = localPath;

        }
    }

    /**
     * Change working directory on ftp server
     * @param remotePath new working directory
     * @throws IOException
     */
    public void changeRemoteDir(String remotePath) throws IOException {
        if (remotePath.equals("..")) {
            client.changeToParentDirectory();
        } else {
            client.changeWorkingDirectory(client.printWorkingDirectory() + "/" + remotePath);
        }

    }

    /**
     * Delete a local file
     * @param file file to be deleted
     */
    public void deleteLocalFile(File file) {
        file.delete();
    }

    /**
     * Delete a remote file
     * @param file remote file to be deleted
     * @throws IOException
     */
    public void deleteRemoteFile(FTPFile file) throws IOException {
        if(client.deleteFile(file.getName())){
            System.out.println("delete success");
        }else{
            System.out.println("delete error");
        }
    }

    /**
     * Change the name of a local file
     * @param file file to be renamed
     * @param newName the new name to be given
     */
    public void renameLocalFile(File file, String newName) {
        file.renameTo(new File(localPath + "/" + newName));
    }

    /**
     * Change the name of a remote file
     * @param file file to be renamed
     * @param newName the name to be given
     * @throws IOException
     */
    public void renameRemoteFile(FTPFile file, String newName) throws IOException {
        client.rename(file.getName(), newName);
    }
}
