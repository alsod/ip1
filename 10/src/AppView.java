
import java.awt.event.*;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.commons.net.ftp.FTPFile;

/**
 * @author Andre Karlsson
 *
 * View part of the application, everything graphical goes here
 *
 */
public class AppView {

    JFrame frame, cdFrame;
    JTextArea left, right;
    JScrollPane scrollPaneLeft, scrollPaneRight;
    ConnectionDialog cd = new ConnectionDialog();
    MenuBar mb = new MenuBar();
    ContentPane c = new ContentPane();
    ActionListener al;
    MouseListener ml;
    JPopupMenu localPopup;

    /**
     * Create the main application gui
     */
    public AppView() {
        createAndShowGUI();

    }

    /**
     * Get adress from connection dialog
     * @return adress to server
     */
    public String getAddress() {
        return cd.getAddressField();
    }

    /**
     * Get port from connection dialog
     * @return port to server
     */
    public String getPort() {
        return cd.getPortField();
    }

    /**
     * Get username from connection dialog
     * @return username
     */
    public String getUsername() {
        return cd.getUsernameField();
    }

    /**
     * Get password from connection dialog
     * @return password
     */
    public String getPassword() {
        return cd.getPasswordField();
    }

    /**
     * Create a Frame to be used as a dialog.
     */
    public void createConnectionDialog() {
        //Create and set up the window.
        cdFrame = new JFrame("Connection");

        cdFrame.setContentPane(cd.createContentPane()); // Get pane from ConnectionDialog class

        //Display the window.
        cdFrame.setSize(250, 290);
        cdFrame.setVisible(true);
        addConnectionDialogActionListeners();
    }

    /**
     * Close the connection dialog window
     */
    public void closeConnectionDialog() {
        cdFrame.dispose();
    }

    /**
     * Create a new alert dialog
     * @param msg the message to display
     */
    public void createAlert(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", 0);
    }

    /**
     * Create a Frame, add a JMenuBar and a Container to it and set the size.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("aFTP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(mb.createMenuBar()); // Get pane from MenuBar class
        frame.setContentPane(c.createContentPane()); // Get pane from ContentPane class

        //Display the window.
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Used by Controller to set the ActionListener used by components in the application
     * @param al ActionListener given by Controller
     */
    public void setActionListener(ActionListener al) {
        this.al = al;
        mb.addMenuBarActionListeners(al);
        c.addActionListeners(al);
    }

    /**
     * Used by Controller to set the MouseListener used by components in the application
     * @param ml MouseListener given by Controller
     */
    public void setMouseListener(MouseListener ml) {
        this.ml = ml;
        c.setMouseListener(ml);

    }

    /**
     * Add actionlisteners to connection dialog
     */
    public void addConnectionDialogActionListeners() {
        cd.addConnectionDialogActionListeners(this.al);
    }

    /**
     * Tell remotelist in contentpane to update
     * @param files list of files from server
     */
    public void populateRemoteList(FTPFile[] files) {
        c.populateRemoteList(files);
    }

    /**
     * Tell locallist in contentpane to update
     * @param files list of local files
     */
    public void populateLocalList(File[] files) {
        c.populateLocalList(files);
    }

    /**
     * Get file that is choosen in localfilelist
     * @return choosen file
     */
    public File getLocalFile() {
        return c.getLocalFile();
    }

    /**
     * Get file that is choosen in remotelist
     * @return choosen file
     */
    public FTPFile getRemoteFile() {
        return c.getRemoteFile();
    }

    /**
     * Close program
     */
    public void quit() {
        frame.dispose();
    }

    /**
     * Enable or disable interaction with the gui
     * @param bool true if interactions enabled, false otherwise
     */
    public void enableGUI(boolean bool) {
        c.enableGUI(bool);
    }

    /**
     *  Create a menu to show on rightclick in locallist
     * @return rightclick menu
     */
    public JPopupMenu getLocalRightClickMenu() {
        localPopup = new JPopupMenu();
        JMenuItem menuRename = new JMenuItem("Rename");
        JMenuItem menuDelete = new JMenuItem("Delete");
        JMenuItem menuUpload = new JMenuItem("Upload");

        localPopup.add(menuRename);
        localPopup.add(menuDelete);
        localPopup.add(menuUpload);

        menuRename.setActionCommand("renameLocal");
        menuDelete.setActionCommand("deleteLocal");
        menuUpload.setActionCommand("upload");

        menuRename.addActionListener(al);
        menuDelete.addActionListener(al);
        menuUpload.addActionListener(al);

        return localPopup;
    }

    /**
     *  Create a menu to show on rightclick in remotelist
     * @return rightclick menu
     */
    public JPopupMenu getRemoteRightClickMenu() {
        localPopup = new JPopupMenu();
        JMenuItem menuRename = new JMenuItem("Rename");
        JMenuItem menuDelete = new JMenuItem("Delete");
        JMenuItem menuDownload = new JMenuItem("Download");

        localPopup.add(menuRename);
        localPopup.add(menuDelete);
        localPopup.add(menuDownload);

        menuRename.setActionCommand("renameRemote");
        menuDelete.setActionCommand("deleteRemote");
        menuDownload.setActionCommand("download");

        menuRename.addActionListener(al);
        menuDelete.addActionListener(al);
        menuDownload.addActionListener(al);

        return localPopup;
    }

    /**
     * Create a popup to show when delete is choosen
     * @param file the name of the file to be deleted
     * @return true if yes is choosen false otherwise
     */
    public boolean createDeletePopup(String file) {
        int n = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to delete the file \"" + file + "\"?",
                "Delete",
                JOptionPane.YES_NO_OPTION);
        return (n == 0) ? true : false;
    }

    /**
     * Create popup to show when rename is choosen
     * @param file name of file to be renamed
     * @return new name of file or null if no new name is given
     */
    public String createRenamePopup(String file){

        String name = (String) JOptionPane.showInputDialog(
                    frame,
                    "Rename the file \" " + file + "\" to...",
                    "Rename",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");
        return name;
    }
}
