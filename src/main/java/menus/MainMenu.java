package menus;

import clientClasses.CustomJson;
import clientClasses.Request;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*CLASS DESCRIPTION: This class is extends from the JFrame class. It is an appropriate container
* for our user to choose whether they would like to log in as an admin or a GP and
* enter their credentials, which are verified through our database */


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
    JLabel logo = new JLabel(new ImageIcon("nhs.png"));

    //gp data
    private int gpID;
    private String gpName;

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
        usernameField = new JTextField(40);

        //password field
        passwordField = new JPasswordField(40);

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

        //creating layout from custom method
        createLayout();

        //Adding image to main window

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
                GPMenu gpMenu = new GPMenu(gpName, gpID);
                dispose();
            } else {
                message.setText("Credentials could not be verified.");
                message.setForeground(Color.red);
                passwordField.setText("");
            }
        }

        //checks the login credentials
        private int checkCredentials(String username, String password) {
            String validAdmin = "Jonas";
            String validAdminPassword = "jonas";
            if (adminButton.isSelected()) {
                if (username.equals(validAdmin) && password.equals(validAdminPassword)) {
                    return 1;
                }
            } else if (gpButton.isSelected()) {
                //Check whether log in data for gp is in db
                JSONObject data = new JSONObject();
                try {
                    data.put("username", username);
                    data.put("password", password);
                    CustomJson instruction = new CustomJson("checkLogIn", data);
                    String instruction_string = instruction.toString();
                    Request post = new Request();
                    String resp = post.makePostRequest(instruction_string);
                    //unpack response
                    JSONObject response = new JSONObject(resp);
                    JSONObject logInData = response.getJSONObject("data");
                    Boolean result = logInData.getBoolean("login");
                    if (result){
                        gpID = logInData.getInt("id");
                        gpName = logInData.getString("name");
                        return 2;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return 0;
        }
    }

    //Connects client to the server
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

    //Layout of the main menu, created using absolute layout
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
        Dimension logoDim = logo.getPreferredSize();

        //Setting bounds of each element
        logo.setBounds(130, 50, logoDim.width, logoDim.height);

        adminButton.setBounds(10, 200, adminButtonDim.width, adminButtonDim.height);
        gpButton.setBounds(10 + adminButtonDim.width + 40, 200, gpButtonDim.width, gpButtonDim.height);

        usernameLabel.setBounds(10, 242, usernameLabelDim.width, usernameLabelDim.height);
        usernameField.setBounds(10 + usernameLabelDim.width, 240, usernameFieldDim.width + 30, usernameFieldDim.height);

        passwordLabel.setBounds(10, 282, passwordLabelDim.width, passwordLabelDim.height);
        passwordField.setBounds(10 + passwordLabelDim.width, 280, passwordFieldDim.width + 30, passwordFieldDim.height);

        loginButton.setBounds(205, 320, loginButtonDim.width, loginButtonDim.height);
        quitButton.setBounds(205 + loginButtonDim.width + 15, 320, quitButtonDim.width, quitButtonDim.height);

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
        this.setResizable(false);

    }

}
