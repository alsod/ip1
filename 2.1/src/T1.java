
import javax.swing.JTextArea;


/**
 * Uppgift 2.1 (Äpplen) i kursen Internetprogrammering I
 * Klassen ärver av Thread och lägger till text på en JTextArea varje sekund.
 *
 * @author André Karlsson
 * 
 */
public class T1 extends Thread {

    private boolean alive = true; //för att hålla koll på om tråden lever
    private boolean active = true; //för att hålla koll på om tråden är pausad
    private JTextArea textArea;

    public T1(JTextArea textArea){
        this.textArea = textArea;
    }

    @Override
    public void run() {
        while (alive) {
            while (active) {
                textArea.insert("Tråd 1\n", textArea.getCaretPosition()); //lägger till text vid markör
                textArea.setCaretPosition(textArea.getText().length()); //flyttar fram markören
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            try {
                sleep(25);
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Sätt active till falsk om tråden ska pausas annars sann
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Dödar tråden
     */
    public void kill() {
        this.active = false;
        this.alive = false;
    }
}
