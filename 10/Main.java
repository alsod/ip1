
/**
 * @author André Karlsson
 *
 * Class includes the main function. Creates instances of MVC (Model-View-Controller) components
 * Compile: javac main.java
 * Run: java Main or on mac java -Dapple.laf.useScreenMenuBar=true Main
 */
public class Main {

    public static void main(String[] args) {

        // Create a new view for the application
        AppView view = new AppView();

        // Create a new model
        AppModel model = new AppModel();

        // Create a controller
        AppController appController = new AppController(view, model);
        

    }

}
