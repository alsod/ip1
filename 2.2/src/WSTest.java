
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Andre Karlsson
 * A little program to test Web Start
 * Assignment 2.2 in Internetprogramming 1
 *
 */
public class WSTest extends JFrame{

    public WSTest(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final JTextArea text = new JTextArea();
        JScrollPane sp = new JScrollPane(text);

        JButton button = new JButton("Tryck här!");

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                text.append("bravo!\n");
            }
        });

        sp.setPreferredSize(new Dimension(640, 460));
        //button.setPreferredSize(new Dimension(640, 20));

        panel.add(sp);
        panel.add(button);


                getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(panel);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WSTest().setVisible(true);
    }

}
