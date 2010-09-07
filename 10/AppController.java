
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Andre Karlsson
 *
 * Controls all actions made by components in view, updates view and model if
 * necessary.
 */
public class AppController implements ActionListener {

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
    }

    public void actionPerformed(ActionEvent e) {
        view.createConnectionDialog();
        System.out.println("lol");
    }
}
