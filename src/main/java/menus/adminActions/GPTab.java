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
    //Inputs
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField emailInput;
    private JTextField pagerNumInput;
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

        //Initialising GP_name text field
        firstNameInput = new JTextField(30);
        lastNameInput = new JTextField(30);
        emailInput = new JTextField(30);
        pagerNumInput = new JTextField(30);

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

                //Adding input data to Map and resetting the text field
                gpMap.put("firstName", firstName);
                gpMap.put("lastName", lastName);
                gpMap.put("pagerNumber", pagerNum);
                gpMap.put("email", email);
                GPAdded.addElement(firstNameInput.getText());
                firstNameInput.setText("");    //Resets textfield so that user knows it's submitted
                lastNameInput.setText("");
                pagerNumInput.setText("");
                emailInput.setText("");

                //Setting the success message to visble
                successMessage.setVisible(true);

                //constructing JSON to send to servlet
                JSONObject data = new JSONObject(gpMap);
                CustomJson instruction = new CustomJson("addDoctor", data);
                String instructionString = instruction.toString();
                Request post = new Request();
                post.makePostRequest(instructionString);
            }
        });

        //Adding medical centres to the scroll panel
        //spane.getViewport().add(medicalCentreList);

        //Adding GP List
        //GPList = new JList(GPAdded);
        //createGPList();

        //Function that sets absolute-style layout appropriately
        settingAbsLayout();

    }

//    public void createGPList() {        //Making it so by selecting GP, outputs medical centre of GP
//
//        GPList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent listSelectionEvent) {
//                String medicalCentreChosen = gpMap.get(GPList.getSelectedValue());
//                medicalCentreGP.setText(medicalCentreChosen);    //Outputs medical centre of selected GP
//            }
//        });
//        outspane.getViewport().add(GPList);
//
//    }

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
       componentArray.add(medicalCentreLabel);
       componentArray.add(medicalCentreInput);

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
        successMessage.setBounds(10, successY, successDim.width, successDim.height);
        this.add(successMessage);

    }


}

