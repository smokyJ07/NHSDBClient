package menus.adminActions;

import clientClasses.CustomJson;
import clientClasses.Request;
import org.json.JSONException;
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

/*CLASS DESCRIPTION: The class addPatient extends from the JPanel class. It provides the appropriate container
 * for adding a patients to our database: four text fields with labels,
 * a submit button to add the new patient to our database alongside a display section to ensure that the program is working
 * correctly. It includes a CreatePatientList function in order for the display to work properly as well as a setting layout function  */


//Once information can be fetched from database, also give medical centre info attached to GP name

//For now, display list shows a concatenation of first and last name and outputs in the JLabel the GP name and phone number


public class PatientTab extends JPanel {

    //Information to collect
    private Map map = new HashMap();
    private String patientFirstName = new String();
    private String patientLastName = new String();
    private String patientNumber = new String();
    private String GPName = new String();
    private String patientAddress;
    private String patientEmail;
    private String patientDob;
    //Labels
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel GPLabel;
    private JLabel phoneNumberLabel;
    private JLabel emailLabel;
    private JLabel dobLabel;
    private JLabel addressLabel;
    //Inputs
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField GPInput;
    private JTextField phoneNumberInput;
    private JTextField emailInput;
    private JTextField dobInput;
    private JTextField addressInput;
    private JButton submit = new JButton("Submit");
    //Adding a success message for once patient is added
    private JLabel successMessage = new JLabel("Patient added successfully.");
    //Output GP list added
    private DefaultListModel<String> patientAdded = new DefaultListModel<String>();
    private JScrollPane outspane = new JScrollPane();
    private JList<String> patientList = new JList();
    private JLabel infoPatient = new JLabel();
    //Extra info
    private String fullName = new String();
    private String otherInfo = new String();

    public PatientTab() {

        //Initialising JLabels
        firstNameLabel = new JLabel("First name:");
        lastNameLabel = new JLabel("Last name:");
        phoneNumberLabel = new JLabel("Mobile phone number:");
        GPLabel = new JLabel("Full name of the patient's GP:");
        emailLabel = new JLabel("Email:");
        dobLabel = new JLabel("Date of birth:");
        addressLabel = new JLabel("Address:");

        //Initialising relevant text field
        firstNameInput = new JTextField(30);
        lastNameInput = new JTextField(30);
        phoneNumberInput = new JTextField(30);
        GPInput = new JTextField(30);
        emailInput = new JTextField(30);
        dobInput = new JTextField(30);
        addressInput = new JTextField(30);

        //Initialising success message
        successMessage.setForeground(Color.green);
        successMessage.setVisible(false);

        //Adding ActionListeners to button
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Adding input to database
                patientFirstName = firstNameInput.getText();
                patientLastName = lastNameInput.getText();
                patientNumber = phoneNumberInput.getText();
                GPName = GPInput.getText();
                patientAddress = addressInput.getText();
                patientDob = dobInput.getText();
                patientEmail = emailInput.getText();

                //Adding input data to Map
                fullName = (firstNameInput.getText() + " " + lastNameInput.getText());
                otherInfo  = (phoneNumberInput.getText() + ", " + GPInput.getText());
                map.put(fullName, otherInfo);
                patientAdded.addElement(fullName);

                //Resetting the text field
                firstNameInput.setText("");
                lastNameInput.setText("");
                phoneNumberInput.setText("");
                GPInput.setText("");
                addressInput.setText("");
                emailInput.setText("");
                dobInput.setText("");

                //create instruction json object containing data and function to execute by server
                JSONObject patient = new JSONObject();
                try {
                    patient.put("name", patientFirstName + " " + patientLastName);
                    patient.put("phoneNum", patientNumber);
                    patient.put("address", patientAddress);
                    patient.put("email", patientEmail);
                    patient.put("dob", patientDob);
                    patient.put("gp", GPName);
                } catch(JSONException e){
                    System.out.println("error during patient json creation");
                }

                CustomJson instruction = new CustomJson("addPatient", patient);
                String instruction_json_string = instruction.toString();
                Request post = new Request();
                String resp = post.makePostRequest(instruction_json_string);
                //unpack response
                boolean patientIDFound = false;
                boolean gpIDFound = false;
                try {
                    JSONObject response = new JSONObject(resp);
                    JSONObject data = response.getJSONObject("data");
                    patientIDFound = data.getBoolean("patientid_found");
                    gpIDFound = data.getBoolean("gpid_found");
                }catch(Exception e){
                    e.printStackTrace();
                }
                //execute response
                if (patientIDFound && gpIDFound){
                    successMessage.setText("Patient added successfully.");
                    successMessage.setForeground(Color.green);
                    successMessage.setVisible(true);
                }
                else if (!patientIDFound && gpIDFound){
                    successMessage.setText("Error while retrieving the patientID.");
                    successMessage.setForeground(Color.red);
                    successMessage.setVisible(true);
                }
                else if(!gpIDFound && patientIDFound){
                    successMessage.setText("Unable to find GP with the specified name.");
                    successMessage.setForeground(Color.red);
                    successMessage.setVisible(true);
                }
                else{
                    successMessage.setText("An unknown error occured.");
                    successMessage.setForeground(Color.red);
                    successMessage.setVisible(true);
                }
            }
        });

//        //Adding GP List
//        patientList = new JList(patientAdded);
//        createPatientList();

        //Function that sets layout appropriately
        settingAbsLayout();

    }

//    public void createPatientList() {        //Making it so by selecting GP, outputs medical centre of GP
//
//        patientList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent listSelectionEvent) {
//                System.out.println(patientList.getSelectedValue().getClass());
//                String patientInfo = (String)map.get(patientList.getSelectedValue());
//                infoPatient.setText(patientInfo);    //Outputs medical centre of selected GP
//            }
//        });
//        outspane.getViewport().add(patientList);
//
//    }

    //Sets the layout of the elements of this panel using absolute layout
    public void settingAbsLayout(){
        //Adding all different layouts via an absolute layout
        this.setLayout(null);   //size is (600, 500)

        //Setting a component container for iterating similar actions
        ArrayList<JComponent> componentArray = new ArrayList<JComponent>();
        componentArray.add(firstNameLabel);         //labels are in even indexes and inputs are in odd
        componentArray.add(firstNameInput);
        componentArray.add(lastNameLabel);
        componentArray.add(lastNameInput);
        componentArray.add(emailLabel);
        componentArray.add(emailInput);
        componentArray.add(dobLabel);
        componentArray.add(dobInput);
        componentArray.add(addressLabel);
        componentArray.add(addressInput);
        componentArray.add(phoneNumberLabel);
        componentArray.add(phoneNumberInput);
        componentArray.add(GPLabel);
        componentArray.add(GPInput);
        componentArray.add(submit);

        //Loop is in charge of extracting dimensions, setting bounds for each component and adding them to the JPanel

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
                componentArray.get(i).setBounds(xInput, yCurrent, d.width, d.height);

            }

            this.add(componentArray.get(i));

        }
        //Adding the submit button
        Dimension submitDim = submit.getPreferredSize();
        int submitY = yInit + 40*Math.round(componentArray.size()/2);  //rounding in case it's an odd number
        submit.setBounds(5, submitY, submitDim.width, submitDim.height);
        this.add(submit);

        Dimension successDim = successMessage.getPreferredSize();
        int successY = submitY + 40;
        successMessage.setBounds(10, successY, successDim.width, successDim.height);
        this.add(successMessage);


    }

}
