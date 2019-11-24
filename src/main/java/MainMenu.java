import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

public class MainMenu extends JFrame {
    private JLabel usernameLabel;
    private JTextField usernameField;
    private String username;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private String password;
    private JButton loginButton;
    private JButton quitButton;
    private JLabel message = new JLabel("");
    private JRadioButton adminButton;
    private JRadioButton gpButton;

    public MainMenu(){
        initMainMenu();
    }

    private void initMainMenu(){
        //initialize frame
        this.setTitle("Main Menu");
        this.setSize(250, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //button to the right menu
        loginButton = new JButton("Sign In");
        loginButton.addActionListener(new SubmitAction());

        //username label and password label
        usernameLabel = new JLabel ("Username:");
        passwordLabel = new JLabel("Password:");

        //username text field
        usernameField = new JTextField(15);

        //password field
        passwordField = new JPasswordField(15);

        //initialize radio buttons
        adminButton = new JRadioButton("Log in as admin");
        gpButton = new JRadioButton("Log in as GP");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(adminButton);
        buttonGroup.add(gpButton);
        gpButton.setSelected(true);

        //quit button
        quitButton = new JButton("Quit");
        quitButton.addActionListener((event) -> System.exit(0));

        createLayout(gpButton, adminButton, usernameField, passwordLabel, passwordField,
                loginButton, message, quitButton);
        this.setVisible(true);
    }

    private class SubmitAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e){
            doSubmit();
        }

        // action performed when log in button clicked
        private void doSubmit(){
            username = usernameField.getText();
            char[] password_array = passwordField.getPassword();
            password = String.valueOf(password_array); //get value of array into string

            int res = checkCredentials(username, password);
            if(res == 1){
                AdminMenu adminMenu = new AdminMenu();
                dispose();
            }
            else if(res == 2){
                GPMenu gpMenu = new GPMenu();
                dispose();
            }
            else{
                message.setText("Credentials could not be verified.");
                message.setForeground(Color.red);
                passwordField.setText("");
            }
        }

        //check the login credentials
        private int checkCredentials(String username, String password){
            String validAdmin = "Jonas";
            String validAdminPassword = "jonas";
            String validGP = "Joao";
            String validGPPassword = "joao";
            if (adminButton.isSelected()) {
                if (username.equals(validAdmin) && password.equals(validAdminPassword)) {
                    return 1;
                }
            }
            else if (gpButton.isSelected()){
                if (username.equals(validGP) && password.equals(validGPPassword)){
                    return 2;
                }
            }
            return 0;
        }

    }

    private void createLayout(JComponent... arg){
        JPanel pane = new JPanel();
        GroupLayout gl = new GroupLayout(pane);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(
                gl.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addComponent(arg[3])
                        .addComponent(arg[4])
                        .addComponent(arg[5])
                        .addComponent(arg[6])
                        .addComponent(arg[7])
        );

        gl.setVerticalGroup(
                gl.createSequentialGroup()
                    .addComponent(arg[0])
                    .addPreferredGap(RELATED)
                    .addComponent(arg[1])
                    .addPreferredGap(RELATED)
                    .addComponent(arg[2])
                    .addComponent(arg[3])
                    .addComponent(arg[4])
                    .addComponent(arg[5])
                    .addComponent(arg[6])
                    .addComponent(arg[7])
        );
        this.getContentPane().add(pane);

    }
}
