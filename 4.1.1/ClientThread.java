
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author André Karlsson
 *
 */
public class ClientThread extends Thread {

    BufferedReader in;
    JTextArea chatBox;
    String msg;

    public ClientThread(BufferedReader in, JTextArea chatBox) {
        this.in = in;
        this.chatBox = chatBox;
    }

    @Override
    public void run() {

        try {
            while ((msg = in.readLine()) != null) {
                /* Använder swing*/
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        chatBox.insert(msg + "\n", chatBox.getCaretPosition());
                        chatBox.setCaretPosition(chatBox.getText().length());
                    }
                });
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
