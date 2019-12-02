package menus;

import menus.AdminMenu;
import menus.GPMenu;

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
        adminButton.setSelected(true);

        //quit button
        quitButton = new JButton("Quit");
        quitButton.addActionListener((event) -> System.exit(0));

        createLayout(usernameLabel, usernameField, passwordLabel, passwordField, adminButton,
                gpButton, loginButton, message, quitButton);

        this.setVisible(true);
    }

    //custom action handler for login button pressed
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

    public void doGetRequest() throws Exception{
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
            if (inputLine.equals("Hello, World!")){
                System.out.println("Client successfully connected to server.");
            }
        }
        in.close();
    };
    //method that handles complete layout of components
    private void createLayout(JComponent... arg){
        JPanel pane = new JPanel();
        GridLayout gl = new GridLayout(5, 2);

        pane.setLayout(gl);
        pane.add(arg[0]);
        pane.add(arg[1]);
        pane.add(arg[2]);
        pane.add(arg[3]);
        pane.add(arg[4]);
        pane.add(arg[5]);
        pane.add(arg[6]);
        pane.add(arg[7]);
        pane.add(arg[8]);

        this.getContentPane().add(pane);
    }
}
