
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class AppModel {

    String address;
    String port;
    String username;
    String password;
    FTPClient client = null;
    File local;
    String remotePath;
    String localPath;
    final String defaultPath = "/";
    InputStream in = null;

    public AppModel() {
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = "ftp.andrek.se";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = "<zaxs987";
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = "21";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = "andrek.se";
    }

    public boolean connectFTP() {

        if (username.length() == 0) {
            return false;
        }

        try {
            System.out.println("Connecting to host " + address);
            client = new FTPClient();
            client.connect(address, Integer.parseInt(port));
            client.login(username, password);
            System.out.println("User " + username + " login OK");
            System.out.println(client.getStatus());

            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Connected Success");
            } else {
                System.out.println("Connection Failed");
                client.disconnect();
            }


            return true;
        } catch (Exception ex) {
            System.out.println("FTP connection error: " + ex.toString());
            return false;
        }
    }

    public void disconnectFTP() throws IOException {
        if (client.isConnected()) {
            client.disconnect();
        }
    }

    public FTPFile[] getRemoteFileList() throws IOException {
        return client.listFiles();

    }

    public File[] getLocalFileList() {
        if (localPath == null) {
            localPath = defaultPath;
        }
        local = new File(localPath);
        return local.listFiles();
    }

    public void sendLocalFile(final File file) {

        try {

            in = new FileInputStream(file);
            in = new ProgressMonitorInputStream(null, "Upload", in);
            System.out.println(file.getAbsoluteFile());
            client.storeFile(remotePath + file.getName(), in);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void getRemoteFile(final FTPFile file) {


        try {
//            File localFile = new File(localPath + file.getName());
//            if (localFile.exists()) {
//                System.out.println("File already exists closing...");
//                System.exit(1);
//            }
//            OutputStream dfile = new FileOutputStream(localFile);
//
//            client.retrieveFile(file.getName(), dfile);

            InputStream stO = new BufferedInputStream(
                    client.retrieveFileStream(file.getName()),
                    client.getBufferSize());

            OutputStream stD = new FileOutputStream(localPath + file.getName());

//            JFrame progress = new JFrame();
//            progress.setVisible(true);
            final ProgressMonitor pm = new ProgressMonitor(null, "Downloading",
                    "", 0, (int) file.getSize());

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

                            pm.setProgress((int) totalBytesTransferred);


                        }
                    });
            client.completePendingCommand();
            stD.close();
            stO.close();

        } catch (IOException e) {
            System.out.println(e);
        }


    }
}
