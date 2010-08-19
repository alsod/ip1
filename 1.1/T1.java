

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Uppgift 1.1 (Multitrådning) i kursen Internetprogrammering I
 * Klassen ärver av Thread och skriver ut på stdout varje sekund.
 *
 * @author André Karlsson
 * 
 */
public class T1 extends Thread {

    private boolean alive = true; //för att hålla koll på om tråden lever
    private boolean active = true; //för att hålla koll på om tråden är pausad

    @Override
    public void run() {
        while (alive) {
            while (active) {
                System.out.println("Tråd 1");
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

    public void setActive(boolean active) {
        this.active = active;
    }

    public void kill() {
        this.active = false;
        this.alive = false;
    }
}
