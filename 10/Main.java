
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        AppView view = new AppView();

        AppModel model = new AppModel();
        
        //AppController appController = new AppController(view, model);
        MenuBarController menuBarController = new MenuBarController(view, model);

    }

}
