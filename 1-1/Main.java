
/**
 * Uppgift 1.1 (Multitrådning) i kursen Internetprogrammering I
 * Klass med main metod för att starta, pausa och stoppa två trådar av klasserna
 * T1 och T2.
 *
 * @author André Karlsson
 *
 */
public class Main {

    T1 a = new T1();
    

    Thread t2 = new Thread(new T2());
    t2.start();

}
