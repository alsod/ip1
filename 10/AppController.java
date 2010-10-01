
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.SwingUtilities;

/**
 * @author Andre Karlsson
 *
 * Controls all actions made by components in view, updates view and model if
 * necessary.
 */
public class AppController implements ActionListener, MouseListener {

    private AppView view;
    private AppModel model;

    /**
     * Constructor used to get the view and model instances.
     * @param view AppView
     * @param model AppModel
     */
    public AppController(final AppView view, final AppModel model) {
        this.view = view;
        this.model = model;

        view.setActionListener(this); // Give actionlistener to view
        view.setMouseListener(this); // Give actionlistener to view

        populateLocalList();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        /* Connection dialog */

        if (command.equals("connectbutton")) {
            model.setUsername(view.getUsername());
            model.setPassword(view.getPassword());
            model.setAdress(view.getAddress());
            model.setPort(view.getPort());

            if (!model.connectFTP()) {
                view.createAlert("Cannot connect, try again.");
            } else {
                populateRemoteList();
            }
            view.closeConnectionDialog();
        }



        if (command.equals("closebutton")) {
            view.closeConnectionDialog();
        }

        /* Menu bar - File menu */

        if (command.equals("connect")) {
            view.createConnectionDialog();
        }

        if (command.equals("upload")) {
            upload();
        }

        if (command.equals("download")) {
            download();
        }

        if (command.equals("disconnect")) {
            try {
                model.disconnectFTP();
            } catch (IOException ex) {
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (command.equals("quit")) {
            try {
                model.disconnectFTP();
            } catch (IOException ex) {
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            }
            view.quit();
        }

        if (command.equals("rename")) {
            System.out.println("rename");
        }

        if (command.equals("delete")) {
            System.out.println("delete");
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() > 1) {

            /* Doubleclick on file or folder in the localList */
            if (e.getComponent().getName().equals("localList")) {

                /* Upload if file, else change working directory */
                if (view.getLocalFile().isFile()) {
                    upload();
                } else if (view.getLocalFile().getPath().equals("..")) {
                    model.changeLocalDir("..");
                    populateLocalList();
                } else {
                    System.out.println(view.getLocalFile().getPath());
                    model.changeLocalDir(view.getLocalFile().getPath());
                    populateLocalList();
                }

            }

            /* Doubleclick on file or folder in the localList */
            if (e.getComponent().getName().equals("remoteList")) {

                /* Download if file, else change directory */
                if (view.getRemoteFile().isFile()) {
                    download();
                } else {
                    try {
                        model.changeRemoteDir(view.getRemoteFile().getName());
                        populateRemoteList();
                    } catch (IOException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


            }


        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (SwingUtilities.isRightMouseButton(e)) {
            try {
                Robot robot = new java.awt.Robot();
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            } catch (AWTException ae) {
                System.out.println(ae);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            JList list = (JList) e.getSource();
            System.out.println(list.getSelectedValue() + " selected");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Start upload of file to ftp server
     *
     */
    private void upload() {
        if (!model.isConnected()) {
            view.createAlert("Not connected, connect in file menu.");
        } else {

            /* Disable GUI when uploading, enable when finished */
            view.enableGUI(false);
            Thread uploadThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    model.sendLocalFile(view.getLocalFile());
                    populateRemoteList(); // Refresh file list.
                    view.enableGUI(true);

                }
            });

            uploadThread.start();

        }
    }

    /**
     * Start download from ftp server
     *
     */
    private void download() {
        if (!model.isConnected()) {
            view.createAlert("Not connected, connect in file menu.");
        } else {
            /* Disable GUI when download, enable when finished */
            view.enableGUI(false);
            Thread downloadThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    model.getRemoteFile(view.getRemoteFile());
                    populateLocalList();
                    view.enableGUI(true);

                }
            });

            downloadThread.start();

        }
    }

    /**
     * Update remote file list, uses swing utilities to avoid problems
     * with concurrency
     *
     */
    private void populateRemoteList() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    view.populateRemoteList(model.getRemoteFileList());
                } catch (IOException ex) {
                    Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    /**
     * Update local file list, uses swing utilities to avoid problems
     * with concurrency
     *
     */
    private void populateLocalList() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                view.populateLocalList(model.getLocalFileList());
            }
        });
    }
}
