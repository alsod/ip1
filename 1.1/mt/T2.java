package mt;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Uppgift 1.1 (Multitrådning) i kursen Internetprogrammering I
 * Klassen implementerar Runnable och skriver ut på stdout varje sekund.
 *
 * @author André Karlsson
 * 
 */
public class T2 implements Runnable {

    private boolean alive = true; //för att hålla koll på om tråden lever
    private boolean active = true; //för att hålla koll på om tråden är pausad

    public void run() {
        while (alive) {
            while (active) {
                System.out.println("Tråd 2");
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
