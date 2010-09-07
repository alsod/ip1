
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alsod
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
    private JTextField loginField;
    private JButton loginButton;

  public JPanel createContentPane() {

        // We create a bottom JPanel to place everything on.
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(null);

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

        // Username Label
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
        panelForTextFields.add(portField);

        // Username Textfield
        usernameField = new JTextField(8);
        usernameField.setLocation(0, 80);
        usernameField.setSize(100, 30);
        panelForTextFields.add(usernameField);

        // Login Textfield
        loginField = new JTextField(8);
        loginField.setLocation(0, 120);
        loginField.setSize(100, 30);
        panelForTextFields.add(loginField);


        // Button for Logging in
        loginButton = new JButton("Login");
        loginButton.setLocation(40, 210);
        loginButton.setSize(80, 30);
        //loginButton.addActionListener(this);
        dialogPanel.add(loginButton);

        // Button for closing dialog
        loginButton = new JButton("Close");
        loginButton.setLocation(130, 210);
        loginButton.setSize(80, 30);
        //loginButton.addActionListener(this);
        dialogPanel.add(loginButton);

        dialogPanel.setOpaque(true);
        return dialogPanel;
    }
}
