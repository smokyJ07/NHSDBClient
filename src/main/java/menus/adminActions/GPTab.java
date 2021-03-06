package menus.adminActions;

import clientClasses.CustomJson;
import clientClasses.Request;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//IMPORTANT: Added a list of pseudo medical centres

/*CLASS DESCRIPTION: The class addGP extends from the JPanel class. It provides the appropriate container
* for adding a GP to our database: a text field with a label, a list to choose a medical centre from our database
* a submit button to add the new GP to our database alongside a display section to ensure that the program is working
* correctly. It includes a CreateGPList function in order for the display to work properly as well as a setting layout function  */



public class GPTab extends JPanel {

    //Information to collect
    private Map<String, String> gpMap = new HashMap<String, String>();    //will map GP data keys to its inputted values
    //Labels
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel emailLabel;
    private JLabel pagerNumLabel;
    private JLabel medicalCentreLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    //Inputs
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField emailInput;
    private JTextField pagerNumInput;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JButton submit = new JButton("Submit");
    private JScrollPane spane = new JScrollPane();
    private JComboBox medicalCentreInput;
    //Adding a success message after submission
    private JLabel successMessage = new JLabel("GP added successfully!");
    //data
    private String firstName;
    private String lastName;
    private String email;
    private String pagerNum;
    private String medicalCentre;
    private String username;
    private String password;

    //Output GP list added
    private DefaultListModel<String> GPAdded = new DefaultListModel<String>();
    private JList<String> GPList = new JList<String>();

    public GPTab() {

        //Adding fake medical centres for now
        String[] medicalCentres =  {"Roehampton","Kensington and Chelsea", "Chelsea and Westminster", "Teddington"};
        //JList medicalCentreList = new JList(medicalCentres);
        medicalCentreInput = new JComboBox(medicalCentres);

        //Initialising success message
        successMessage.setForeground(Color.green);
        successMessage.setVisible(false);

        //Initialising JLabels
        firstNameLabel = new JLabel("First name:");
        lastNameLabel = new JLabel("Last name:");
        emailLabel = new JLabel("Email address:");
        pagerNumLabel = new JLabel("Pager number:");
        medicalCentreLabel = new JLabel("Attending medical centre:");
        usernameLabel = new JLabel("Create username:");
        passwordLabel = new JLabel("Create password:");

        //Initialising GP_name text field
        firstNameInput = new JTextField(30);
        lastNameInput = new JTextField(30);
        emailInput = new JTextField(30);
        pagerNumInput = new JTextField(30);
        usernameInput = new JTextField(30);
        passwordInput = new JPasswordField(30);

        //Adding ActionListeners to button
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //extracting variables from textfields
                firstName = firstNameInput.getText();
                lastName = lastNameInput.getText();
                pagerNum = pagerNumInput.getText();
                email = emailInput.getText();
                medicalCentre = (String)medicalCentreInput.getSelectedItem();
                username = usernameInput.getText();
                char[] password_array = passwordInput.getPassword();
                password = String.valueOf(password_array); //get value of array into string
                if(firstName.length()<1 || lastName.length()<1 || pagerNum.length()<1 || email.length()<1 ||
                        medicalCentre.length()<1 || username.length()<1 || password.length()<1){
                    successMessage.setText("Please fill out the form completely.");
                    successMessage.setForeground(Color.red);
                    successMessage.setVisible(true);
                }
                else {
                    //Adding input data to Map and resetting the text field
                    gpMap.put("name", firstName + " " + lastName);
                    gpMap.put("pagerNum", pagerNum);
                    gpMap.put("email", email);
                    gpMap.put("username", username);
                    gpMap.put("password", password);
                    GPAdded.addElement(firstNameInput.getText());

                    //constructing JSON to send to servlet
                    JSONObject data = new JSONObject(gpMap);
                    CustomJson instruction = new CustomJson("addDoctor", data);
                    String instructionString = instruction.toString();
                    Request post = new Request();
                    String resp = post.makePostRequest(instructionString);
                    boolean nameExists = false;
                    boolean usernameExists = false;
                    try {
                        JSONObject response = new JSONObject(resp);
                        JSONObject responseData = response.getJSONObject("data");
                        nameExists = responseData.getBoolean("name_exists");
                        usernameExists = responseData.getBoolean("username_exists");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (!usernameExists && !nameExists) {
                        //Setting the success message to visble
                        successMessage.setText("GP successfully added");
                        successMessage.setForeground(Color.green);
                        successMessage.setVisible(true);

                        firstNameInput.setText("");    //Resets textfield so that user knows it's submitted
                        lastNameInput.setText("");
                        pagerNumInput.setText("");
                        emailInput.setText("");
                        usernameInput.setText("");
                        passwordInput.setText("");
                    } else if (usernameExists) {
                        successMessage.setText("Username already taken");
                        successMessage.setForeground(Color.red);
                        successMessage.setVisible(true);
                    } else if (nameExists) {
                        successMessage.setText("Name already exists");
                        successMessage.setForeground(Color.red);
                        successMessage.setVisible(true);
                    } else {
                        successMessage.setText("Unknown error occured");
                        successMessage.setForeground(Color.red);
                        successMessage.setVisible(true);
                    }
                }
            }
        });

        //Function that sets absolute-style layout appropriately
        settingAbsLayout();

    }


    //Sets the layout this panel's JComponents using absolute layout
    public void settingAbsLayout(){
        //Adding all different layouts via an absolute layout
        this.setLayout(null);   //size is (600, 500)

        //Dimensions of each element
        ArrayList<JComponent> componentArray = new ArrayList<JComponent>();

       componentArray.add(firstNameLabel);
       componentArray.add(firstNameInput);
       componentArray.add(lastNameLabel);
       componentArray.add(lastNameInput);
       componentArray.add(emailLabel);
       componentArray.add(emailInput);
       componentArray.add(pagerNumLabel);
       componentArray.add(pagerNumInput);
       //componentArray.add(medicalCentreLabel);
       //componentArray.add(medicalCentreInput);
       componentArray.add(usernameLabel);
       componentArray.add(usernameInput);
       componentArray.add(passwordLabel);
       componentArray.add(passwordInput);

        //Adding the desired coordinates to be implemented or iterated in the layout
        int xLabel = 10;
        int xInput = 205;
        int yInit = 10;
        int yCurrent;

        for (int i = 0; i < componentArray.size(); i++){        //Do not include the submit button

            //Extracting dimensions from components
            Dimension d = componentArray.get(i).getPreferredSize();
            yCurrent = yInit + Math.round(i/2)*40;

            //If a label, put on the left, if text, put on the right, thus different x
            if(i % 2 ==0){  //Label

                //setting bounds of component and updating y to move it further down symmetrically
                yCurrent = yCurrent + 2; //moving it down by 2 points to make it look more symmetrical
                componentArray.get(i).setBounds(xLabel, yCurrent, d.width, d.height);

            }
            else{   //Input

                //setting bounds of component and updating y to move it further down symmetrically
                componentArray.get(i).setBounds(xInput, yCurrent, 350, d.height);

            }

            this.add(componentArray.get(i));

        }
        //Adding the submit button
        Dimension submitDim = submit.getPreferredSize();
        int submitY = yInit + 40*Math.round(componentArray.size()/2);  //rounding in case it's an odd number
        submit.setBounds(5, submitY, submitDim.width, submitDim.height);
        this.add(submit);

        //Adding success message
        Dimension successDim = successMessage.getPreferredSize();
        int successY = submitY + 40;
        successMessage.setBounds(10, successY, 200, successDim.height);
        this.add(successMessage);

    }


}

