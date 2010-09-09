
import java.awt.event.*;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    

    public AppView() {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//
//            public void run() {
        createAndShowGUI();
//            }
//        });
    }


    public String getAddress(){
        return cd.getAddressField();
    }

    public String getPort(){
        return cd.getPortField();
    }

    public String getUsername(){
        return cd.getUsernameField();
    }

    public String getPassword(){
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

    public void closeConnectionDialog(){
        cdFrame.dispose();
    }

    public void createAlert(String msg){
        JOptionPane.showMessageDialog(frame,"Eggs are not supposed to be green.", "Message", 0);
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
    public void setActionListener(ActionListener al){
        this.al = al;
        mb.addMenuBarActionListeners(al);
    }

        /**
     * Used by Controller to set the MouseListener used by components in the application
     * @param al MouseListener given by Controller
     */
    public void setMouseListener(MouseListener ml){
        this.ml = ml;
        c.setMouseListener(ml);

    }



    public void addConnectionDialogActionListeners(){
        cd.addConnectionDialogActionListeners(this.al);
    }

    public void populateRemoteList(FTPFile[] files){
        c.populateRemoteList(files);
    }

    public void populateLocalList(File[] files){
        c.populateLocalList(files);
    }

    public File getLocalFile(){
        return c.getLocalFile();
    }

    public FTPFile getRemoteFile(){
        return c.getRemoteFile();
    }

    public void quit(){
        frame.dispose();
    }
}
