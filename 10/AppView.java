
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Andre Karlsson
 *
 * View part of the application, everything graphical goes here
 *
 */
public class AppView {

    JTextArea left, right;
    JScrollPane scrollPaneLeft, scrollPaneRight;
    ConnectionDialog cd = new ConnectionDialog();
    MenuBar mb = new MenuBar();
    ContentPane c = new ContentPane();
    ActionListener al;
    

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

    /**
     * Create a Frame to be used as a dialog.
     */
    public void createConnectionDialog() {
        //Create and set up the window.
        JFrame frame = new JFrame("Connection");

        frame.setContentPane(cd.createContentPane()); // Get pane from ConnectionDialog class

        //Display the window.
        frame.setSize(250, 290);
        frame.setVisible(true);
        addConnectionDialogActionListeners();
    }

    /**
     * Create a Frame, add a JMenuBar and a Container to it and set the size.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("aFTP");
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
        addMenuBarActionListeners();
    }

    /**
     * Add actionlisteners to components in the menu bar
     */
    public void addMenuBarActionListeners() {
        mb.addMenuBarActionListeners(this.al);
    }

    public void addConnectionDialogActionListeners(){
        cd.addConnectionDialogActionListeners(this.al);
    }
}
