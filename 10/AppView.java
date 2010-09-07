
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AppView {

    JTextArea left, right;
    JScrollPane scrollPaneLeft, scrollPaneRight;

    MenuBar mb = new MenuBar();


    public AppView() {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//
//            public void run() {
        createAndShowGUI();
//            }
//        });
    }



    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create file
        left = new JTextArea(5, 30);
        right = new JTextArea(5, 30);
        left.setEditable(true);
        right.setEditable(true);

        scrollPaneLeft = new JScrollPane(left);
        scrollPaneRight = new JScrollPane(right);

        scrollPaneLeft.setPreferredSize(new Dimension(340, 460));
        scrollPaneRight.setPreferredSize(new Dimension(340, 460));

        //Add the text area to the content pane.
        contentPane.add(left, BorderLayout.WEST);
        contentPane.add(right, BorderLayout.EAST);
        return contentPane;
    }

  
    public void addMenuBarActionListeners(ActionListener al){
        mb.addMenuBarActionListeners(al);
    }

    public void createConnectionDialog() {
        //Create and set up the window.
        JFrame frame = new JFrame("Connection");
        ConnectionDialog cd = new ConnectionDialog();

        frame.setContentPane(cd.createContentPane());

        //Display the window.
        frame.setSize(250, 290);
        frame.setVisible(true);
    }

    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("aFTP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(mb.createMenuBar());
        frame.setContentPane(createContentPane());

        //Display the window.
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
