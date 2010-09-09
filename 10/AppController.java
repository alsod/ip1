
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public AppController(AppView view, AppModel model) {
        this.view = view;
        this.model = model;

        view.setActionListener(this); // Give actionlistener to view
        view.setMouseListener(this); // Give actionlistener to view

        view.populateLocalList(model.getLocalFileList());
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

            model.connectFTP();
            try {
                view.populateRemoteList(model.getRemoteFileList());
            } catch (IOException ex) {
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            }
            view.closeConnectionDialog();

        }

        if (command.equals("closebutton")) {
            view.closeConnectionDialog();
        }

        /* Menu bar - File menu*/

        if (command.equals("connect")) {
            view.createConnectionDialog();
        }

        if (command.equals("upload")) {
            model.sendLocalFile(view.getLocalFile());
        }

        if (command.equals("download")) {
            model.getRemoteFile(view.getRemoteFile());
        }

        if (command.equals("quit")) {
            try {
                model.disconnectFTP();
            } catch (IOException ex) {
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            }
            view.quit();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1) {

            if (e.getComponent().getName().equals("localList")) {

                Thread uploadThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        model.sendLocalFile(view.getLocalFile());
                    }
                });

                uploadThread.start();

            }

            if (e.getComponent().getName().equals("remoteList")) {

                Thread downloadThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        model.getRemoteFile(view.getRemoteFile());
                    }
                });

                downloadThread.start();
            }


        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
