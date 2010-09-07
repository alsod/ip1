
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuBarController implements ActionListener{

    private AppView view;
    private AppModel model;

    public MenuBarController(AppView view, AppModel model) {
        this.view = view;
        this.model = model;

        model.setAdress("ctrlr");

        view.addMenuBarActionListeners(this);
    }

    public void actionPerformed(ActionEvent e) {
        view.createConnectionDialog();
    }

    

}
