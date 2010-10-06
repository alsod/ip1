
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * NotATrojanThread.java
 * @author Andre Karlsson
 * 
 * Assignment 9.5.2 Internetprogrammering 1
 *
 */
public class NotATrojanClient {

    String msg;
    BufferedReader in;
    PrintWriter out;

    public NotATrojanClient(String adress, String port, String password) throws UnknownHostException, IOException {

        Socket s = new Socket(adress, Integer.parseInt(port));
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream(), true);

        /* Send password */
        out.println(password);

        /* Read input from trojan */
        while ((msg = in.readLine()) != null) {
            System.out.println(msg);
        }

    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        NotATrojanClient client = new NotATrojanClient(args[0], args[1], args[2]);
    }
}
