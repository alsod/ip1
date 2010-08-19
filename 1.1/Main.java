

/**
 * Uppgift 1.1 (Multitrådning) i kursen Internetprogrammering I
 * Klass med main metod för att starta, pausa och stoppa två trådar av klasserna
 * T1 och T2.
 *
 * @author André Karlsson
 *
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        
        T1 t1 = new T1(); //skapa tråd t1
        t1.start(); //starta t1

        Thread.sleep(5000); //sov fem sekunder

        T2 r2 = new T2(); //skapa en T2 instans
        Thread t2 = new Thread(r2); //skapa tråd t2
        t2.start(); // starta t2

        Thread.sleep(5000);

        r2.setActive(false); //pausa t2

        Thread.sleep(5000);

        r2.setActive(true); //återstarta t2

        Thread.sleep(5000);

        t1.kill(); //stäng av t1

        Thread.sleep(5000);

        r2.kill(); //stäng av t2

    }
}
