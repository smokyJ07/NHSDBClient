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
    //Labels
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel GPLabel;
    private JLabel numberLabel;
    //Inputs
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField GPInput;
    private JTextField numberInput;
    private JButton submit = new JButton("Submit");
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
        firstNameLabel = new JLabel("Please enter the patient's first name:");
        lastNameLabel = new JLabel("Please enter the patient's last name:");
        numberLabel = new JLabel("Please enter the patient's mobile phone number:");
        GPLabel = new JLabel("Please enter the full name of the patient's GP:");

        //Initialising relevant text field
        firstNameInput = new JTextField(30);
        lastNameInput = new JTextField(30);
        numberInput = new JTextField(30);
        GPInput = new JTextField(30);


        //Adding ActionListeners to button
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Adding input to database
                patientFirstName = firstNameInput.getText();
                patientLastName = lastNameInput.getText();
                patientNumber = numberInput.getText();
                GPName = GPInput.getText();

                //Adding input data to Map
                fullName = (firstNameInput.getText() + " " + lastNameInput.getText());
                otherInfo  = (numberInput.getText() + ", " + GPInput.getText());
                map.put(fullName, otherInfo);
                patientAdded.addElement(fullName);

                //Resetting the text field
                firstNameInput.setText("");
                lastNameInput.setText("");
                numberInput.setText("");
                GPInput.setText("");

                //create instruction json object containing data and function to execute by server
                JSONObject patient = new JSONObject();
                try {
                    patient.put("firstName", patientFirstName);
                    patient.put("lastName", patientLastName);
                    patient.put("phoneNum", patientNumber);
                } catch(JSONException e){
                    System.out.println("error during patient json creation");
                }

                JSONObject data = new JSONObject();
                try{
                    data.put("patient", patient);
                }catch(JSONException e){
                    System.out.println("Error during json data body creation");
                }

                CustomJson instruction = new CustomJson("addPatient", data);
                String instruction_json_string = instruction.toString();
                Request post = new Request();
                post.makePostRequest(instruction_json_string);
            }
        });

        //Adding GP List
        patientList = new JList(patientAdded);
        createPatientList();

        //Function that sets layout appropriately
        settingLayout();

    }

    public void createPatientList() {        //Making it so by selecting GP, outputs medical centre of GP

        patientList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                System.out.println(patientList.getSelectedValue().getClass());
                String patientInfo = (String)map.get(patientList.getSelectedValue());
                infoPatient.setText(patientInfo);    //Outputs medical centre of selected GP
            }
        });
        outspane.getViewport().add(patientList);

    }

    public void settingLayout(){
        //Adding the textfield inputs and their labels as one flow panel
        JPanel firstPane = new JPanel(new FlowLayout()); //first panel will allow default grid
        firstPane.add(firstNameLabel);
        firstPane.add(firstNameInput);
        firstPane.add(lastNameLabel);
        firstPane.add(lastNameInput);
        firstPane.add(numberLabel);
        firstPane.add(numberInput);
        firstPane.add(GPLabel);
        firstPane.add(GPInput);

        //Adding submit button and GP info display
        JPanel secondPane = new JPanel(new FlowLayout());
        secondPane.add(submit);
        secondPane.add(outspane);
        secondPane.add(infoPatient);

        //Adding created panels to the main panel
        this.setLayout(new GridLayout(2, 1));
        this.add(firstPane);
        this.add(secondPane);

    }


}
