
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Andre Karlsson
 *
 * Class is used by the View to create the components and layout
 * of the connection dialog. Provides getters for textfields.
 */
public class ConnectionDialog {

    private JPanel textPanel;
    private JLabel addressLabel;
    private JLabel portLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel panelForTextFields;
    private JTextField addressField;
    private JTextField portField;
    private JTextField usernameField;
    private JButton connectButton;
    private JButton closeButton;
    private JTextField passwordField;

    /**
     * Create all components and lay them out on a panel. Return this so it
     * can be used on a frame.
     * @return JPanel Connection dialog panel
     */
    public JPanel createContentPane() {

        // Create a bottom JPanel to place everything on.
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(null);


        /* Labels */

        // Creation of a Panel to contain the JLabels
        textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setLocation(20, 15);
        textPanel.setSize(70, 160);
        dialogPanel.add(textPanel);

        // Address Label
        addressLabel = new JLabel("Address:");
        addressLabel.setLocation(0, 0);
        addressLabel.setSize(70, 40);
        addressLabel.setHorizontalAlignment(4);
        textPanel.add(addressLabel);

        // Port Label
        portLabel = new JLabel("Port:");
        portLabel.setLocation(0, 40);
        portLabel.setSize(70, 40);
        portLabel.setHorizontalAlignment(4);
        textPanel.add(portLabel);

        // Username Label
        usernameLabel = new JLabel("Username:");
        usernameLabel.setLocation(0, 80);
        usernameLabel.setSize(70, 40);
        usernameLabel.setHorizontalAlignment(4);
        textPanel.add(usernameLabel);

        // Login Label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setLocation(0, 120);
        passwordLabel.setSize(70, 40);
        passwordLabel.setHorizontalAlignment(4);
        textPanel.add(passwordLabel);


        /* TextFields */

        // TextFields Panel Container
        panelForTextFields = new JPanel();
        panelForTextFields.setLayout(null);
        panelForTextFields.setLocation(115, 22);
        panelForTextFields.setSize(100, 160);
        dialogPanel.add(panelForTextFields);

        // Address Textfield
        addressField = new JTextField(8);
        addressField.setLocation(0, 0);
        addressField.setSize(100, 30);
        panelForTextFields.add(addressField);

        // Port Textfield
        portField = new JTextField(8);
        portField.setLocation(0, 40);
        portField.setSize(100, 30);
        portField.setText("21");
        panelForTextFields.add(portField);

        // Username Textfield
        usernameField = new JTextField(8);
        usernameField.setLocation(0, 80);
        usernameField.setSize(100, 30);
        panelForTextFields.add(usernameField);

        // Password Textfield
        passwordField = new JPasswordField(8);
        passwordField.setLocation(0, 120);
        passwordField.setSize(100, 30);
        panelForTextFields.add(passwordField);


        /* Buttons */

        // Button for Logging in
        connectButton = new JButton("Connect");
        connectButton.setLocation(40, 210);
        connectButton.setSize(80, 30);
        dialogPanel.add(connectButton);

        // Button for closing dialog
        closeButton = new JButton("Close");
        closeButton.setLocation(130, 210);
        closeButton.setSize(80, 30);
        dialogPanel.add(closeButton);

        dialogPanel.setOpaque(true);
        return dialogPanel;
    }

    /**
     * Send String with field contents to be used to make a connection
     * @return String connection adress
     */
    public String getAddressField() {
        return addressField.getText();
    }

    /**
     * Send String with field contents to be used to make a connection
     * @return String connection password
     */
    public String getPasswordField() {
        return passwordField.getText();
    }

    /**
     * Send String with field contents to be used to make a connection
     * @return String connection port
     */
    public String getPortField() {
        return portField.getText();
    }

    /**
     * Send String with field contents to be used to make a connection
     * @return String connection username
     */
    public String getUsernameField() {
        return usernameField.getText();
    }



    /**
     * Add Actionlistener to components in Dialog
     * @param al actionlistener given by controller
     */
    public void addConnectionDialogActionListeners(ActionListener al) {
        // Add listener to Connect Button
        connectButton.setActionCommand("connectbutton");
        connectButton.addActionListener(al);

        // Add listener to close button
        closeButton.setActionCommand("closebutton");
        closeButton.addActionListener(al);

    }
}
