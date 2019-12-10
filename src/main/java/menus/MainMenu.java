package menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private JLabel logo = new JLabel("INSERT LOGO HERE:");

    public MainMenu() {
        initMainMenu();
    }

    private void initMainMenu() {
        //initialize frame
        this.setTitle("Main Menu");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //button to the right menu
        loginButton = new JButton("Sign In");
        loginButton.addActionListener(new SubmitAction());

        //username label and password label
        usernameLabel = new JLabel("Username:");
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
        adminButton.setSelected(true);

        //quit button
        quitButton = new JButton("Quit");
        quitButton.addActionListener((event) -> System.exit(0));

        //creating layout from custom method
        createLayout();
        //createLayout(usernameLabel, usernameField, passwordLabel, passwordField, adminButton,
        //    gpButton, loginButton, message, quitButton);

        this.setVisible(true);
    }

    //custom action handler for login button pressed
    private class SubmitAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            doSubmit();
        }

        // action performed when log in button clicked
        private void doSubmit() {
            username = usernameField.getText();
            char[] password_array = passwordField.getPassword();
            password = String.valueOf(password_array); //get value of array into string

            int res = checkCredentials(username, password);
            if (res == 1) {
                AdminMenu adminMenu = new AdminMenu();
                dispose();
            } else if (res == 2) {
                GPMenu gpMenu = new GPMenu();
                dispose();
            } else {
                message.setText("Credentials could not be verified.");
                message.setForeground(Color.red);
                passwordField.setText("");
            }
        }

        //check the login credentials
        private int checkCredentials(String username, String password) {
            String validAdmin = "Jonas";
            String validAdminPassword = "jonas";
            String validGP = "Joao";
            String validGPPassword = "joao";
            if (adminButton.isSelected()) {
                if (username.equals(validAdmin) && password.equals(validAdminPassword)) {
                    return 1;
                }
            } else if (gpButton.isSelected()) {
                if (username.equals(validGP) && password.equals(validGPPassword)) {
                    return 2;
                }
            }
            return 0;
        }
    }

    public void doGetRequest() throws Exception {
        //connect to servlet
        URL myURL = new URL("https://nhsdbservlet.herokuapp.com/patients");
        HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/html");
        conn.setRequestProperty("charset", "utf-8");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(myURL.openStream()));
        String inputLine;
        // Read the body of the response
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equals("Hello, World!")) {
                System.out.println("Client successfully connected to server.");
            }
        }
        in.close();
    }

    private void createLayout() {

        JPanel pane = new JPanel();
        pane.setLayout(null);

        //Getting preferred dimensions for each element
        //Dimension logoDim = logo.getPreferredSize();
        Dimension adminButtonDim = adminButton.getPreferredSize();
        Dimension gpButtonDim = gpButton.getPreferredSize();
        Dimension usernameLabelDim = usernameLabel.getPreferredSize();
        Dimension usernameFieldDim = usernameField.getPreferredSize();
        Dimension passwordLabelDim = passwordLabel.getPreferredSize();
        Dimension passwordFieldDim = passwordField.getPreferredSize();
        Dimension loginButtonDim = loginButton.getPreferredSize();
        Dimension quitButtonDim = quitButton.getPreferredSize();

        //Setting bounds of each element
        logo.setBounds(100, 100, 400, 100);

        adminButton.setBounds(10, 200, adminButtonDim.width, adminButtonDim.height);
        gpButton.setBounds(10 + adminButtonDim.width + 40, 200, gpButtonDim.width, gpButtonDim.height);

        usernameLabel.setBounds(10, 242, usernameLabelDim.width, usernameLabelDim.height);
        usernameField.setBounds(10 + usernameLabelDim.width, 240, usernameFieldDim.width + 30, usernameFieldDim.height);

        passwordLabel.setBounds(10, 282, passwordLabelDim.width, passwordLabelDim.height);
        passwordField.setBounds(10 + passwordLabelDim.width, 280, passwordFieldDim.width + 30, passwordFieldDim.height);

        loginButton.setBounds(5, 320, loginButtonDim.width, loginButtonDim.height);
        quitButton.setBounds(5 + loginButtonDim.width + 5, 320, quitButtonDim.width, quitButtonDim.height);

        pane.add(logo);
        pane.add(adminButton);
        pane.add(gpButton);
        pane.add(usernameLabel);
        pane.add(usernameField);
        pane.add(passwordLabel);
        pane.add(passwordField);
        pane.add(loginButton);
        pane.add(quitButton);

        this.getContentPane().add(pane);

    }

}
