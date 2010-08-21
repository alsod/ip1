
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Uppgift 2.1 (Äpplen) i kursen Internetprogrammering I
 * Klassen ska köras som en applet och den är trådad.
 *
 * @author André Karlsson
 *
 */

public class Main extends JApplet implements Runnable {

    private JTextArea textArea = new JTextArea();
    private Thread thread = new Thread(this);

    @Override
    public void init() {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        thread.start();
    }

    public void run() {

        try {
            T1 t1 = new T1(textArea); //skapa tråd t1
            t1.start(); //starta t1

            Thread.sleep(5000); //sov fem sekunder

            T2 r2 = new T2(textArea); //skapa en T2 instans
            Thread t2 = new Thread(r2); //skapa tråd t2
            t2.start(); // starta t2

            Thread.sleep(5000);

            r2.setActive(false); //stäng av t2

            Thread.sleep(5000);

            r2.setActive(true); //återstarta t2

            Thread.sleep(5000);

            t1.kill(); //stäng av t1

            Thread.sleep(5000);

            r2.kill(); //stäng av t2
        } catch (InterruptedException ex) {
        }
    }
}
    
