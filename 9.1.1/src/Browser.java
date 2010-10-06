
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * Browser.java
 * Uppgift 3 (URL-Klassen) i kursen Internetprogrammering 1
 *
 * @author André Karlsson
 *
 */
public class Browser extends JApplet {

    private JPanel top;
    private JPanel bottom;
    private JScrollPane scroll;
    private JTextField address;
    private JButton go;
    private JTextArea content;
    private InputStream is;
    private String result;

    public static void main(String[] args) throws MalformedURLException, IOException {
        new Browser().setVisible(true);
    }

    public Browser() throws MalformedURLException, IOException {
        
        setSize(640, 480);
        

        /* Skapa paneler för att lägga i JFrame */
        top = new JPanel();
        bottom = new JPanel();

        /* Skapa komponenter till panelerna */
        address = new JTextField();
        go = new JButton();
        content = new JTextArea();
        scroll = new JScrollPane(content);



        /* Sätter önskad storlek på komponenter */
        scroll.setPreferredSize(new Dimension(640, 420));
        top.setMaximumSize(new Dimension(640, 25));

        /* Sätter ursprungsvärde */
        address.setText("http://www.google.com");
        go.setText("GO!");

        /* Hämtar startsida */
        is = new URL(address.getText()).openStream();
        result = new Scanner(is).useDelimiter("\\A").next();
        content.setText(result);

        /* Lägger till lyssnare vid tryck på Go!-knappen och enter i adressrad */
        go.addActionListener(new GoListener());
        address.addActionListener(new GoListener());

        /* Sätter layout för knapp och adressruta */
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.add(address);
        top.add(go);

        /* Lägger till resultatrutan */
        bottom.add(scroll);

        /* Lägger paneler med innehåll i container */
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(top);
        getContentPane().add(bottom);


    }

    class GoListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                /* Hämtar sida som är angiven i adressrutan och sätter resultatet i resultatrutan. */
                is = new URL(address.getText()).openStream();
                result = new Scanner(is).useDelimiter("\\A").next();
                content.setText(result);
            } catch (IOException ex) {
                Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}


