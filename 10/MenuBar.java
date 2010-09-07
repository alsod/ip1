
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Andre Karlsson
 *
 * Class creates a menu bar and it's components to be used in main window of GUI
 */
public class MenuBar {
    private JMenuItem menuConnect;
    private JMenuItem menuDownload;
    private JMenuItem menuUpload;
    private JMenuItem menuDisconnect;
    private JMenuItem menuQuit;

/**
 * Create components and menu
 * @return JMenuBar
 */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menuFile, menuEdit;

        // Create the menu bar.
        menuBar = new JMenuBar();

        // Build the file menu in the menu bar.
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_A);
        menuFile.getAccessibleContext().setAccessibleDescription(
                "Menu for starting new connection, getting and sending files etc.");
        menuBar.add(menuFile);

        // Create menu items
        menuConnect = new JMenuItem("Connect", KeyEvent.VK_N);
        menuDownload = new JMenuItem("Download file", KeyEvent.VK_G);
        menuUpload = new JMenuItem("Upload file", KeyEvent.VK_P);
        menuDisconnect = new JMenuItem("Disconnect", KeyEvent.VK_D);
        menuQuit = new JMenuItem("Quit", KeyEvent.VK_Q);


        // Add items to file menu
        menuFile.add(menuConnect);
        menuFile.add(menuDownload);
        menuFile.add(menuUpload);
        menuFile.add(menuDisconnect);
        menuFile.add(menuQuit);


        // Build edit menu in the menu bar.
        menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);

        return menuBar;
    }

    /**
     * Add actionlistener to components in menu.
     * @param al
     */
    public void addMenuBarActionListeners(ActionListener al) {
        menuConnect.setActionCommand("connect");
        menuConnect.addActionListener(al);

        menuDownload.setActionCommand("download");
        menuDownload.addActionListener(al);

        menuUpload.setActionCommand("upload");
        menuUpload.addActionListener(al);

        menuDisconnect.setActionCommand("disconnect");
        menuDisconnect.addActionListener(al);

        menuQuit.setActionCommand("quit");
        menuQuit.addActionListener(al);

    }
}
