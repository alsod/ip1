
import javax.swing.JTextArea;

/**
 * Uppgift 2.1 (Äpplen) i kursen Internetprogrammering I
 * Klassen implementerar Runnable och lägger till text i en JTextArea varje sekund.
 *
 * @author André Karlsson
 * 
 */
public class T2 implements Runnable {

    private boolean alive = true; //för att hålla koll på om tråden lever
    private boolean active = true; //för att hålla koll på om tråden är pausad
    private JTextArea textArea;

    public T2(JTextArea textArea) { //konstruktor som tar emot en textarea
        this.textArea = textArea;
    }

    public void run() {
        while (alive) {
            while (active) {
                textArea.insert("Tråd 2\n", textArea.getCaretPosition()); //lägger till text vid markör
                textArea.setCaretPosition(textArea.getText().length()); //flyttar fram markören

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void kill() {
        this.active = false;
        this.alive = false;
    }
}
