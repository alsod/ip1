
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Andre Karlsson
 *
 * Class is used to create a pane to be used in the main window of the application
 */

public class ContentPane {
    private JTextArea left;
    private JTextArea right;
    private JScrollPane scrollPaneLeft;
    private JScrollPane scrollPaneRight;

    /**
     * Create components and add them to a Container which is then returned to caller
     * @return Container
     */
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
}
