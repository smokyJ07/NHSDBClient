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
    private JLabel medicalCentresLabel;
    //Inputs
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField emailInput;
    private JTextField pagerNumInput;
    private JButton submit = new JButton("Submit");
    private JScrollPane spane = new JScrollPane();
    //data
    private String firstName;
    private String lastName;
    private String email;
    private String pagerNum;
    //Output GP list added
    private DefaultListModel<String> GPAdded = new DefaultListModel<String>();
    private JScrollPane outspane = new JScrollPane();
    private JList<String> GPList = new JList<String>();
    private JLabel medicalCentreGP = new JLabel();

    public GPTab() {

        //Adding fake medical centres for now
        String[] medicalCentres =  {"Roehampton","Kensington and Chelsea", "Chelsea and Westminster", "Teddington"};
        JList medicalCentreList = new JList(medicalCentres);

        //Initialising JLabels
        firstNameLabel = new JLabel("Please enter the GP's first name:");
        lastNameLabel = new JLabel("Please enter the GP's last name:");
        emailLabel = new JLabel("Please enter the GP's email address:");
        pagerNumLabel = new JLabel("Please enter the GP's pager number:");
        medicalCentresLabel = new JLabel("Please choose a medical centre:");

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

                //constructing JSON to send to servlet
                JSONObject data = new JSONObject(gpMap);
                CustomJson instruction = new CustomJson("addDoctor", data);
                String instructionString = instruction.toString();
                Request post = new Request();
                post.makePostRequest(instructionString);
            }
        });

        //Adding medical centres to the scroll panel
        spane.getViewport().add(medicalCentreList);

        //Adding GP List
        GPList = new JList(GPAdded);
        createGPList();

        //Function that sets layout appropriately
        settingLayout();

    }

    public void createGPList() {        //Making it so by selecting GP, outputs medical centre of GP

        GPList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                String medicalCentreChosen = gpMap.get(GPList.getSelectedValue());
                medicalCentreGP.setText(medicalCentreChosen);    //Outputs medical centre of selected GP
            }
        });
        outspane.getViewport().add(GPList);

    }

    public void settingLayout(){
        //Adding the textfield input, list and their labels as one flow panel
        JPanel firstPane = new JPanel(new FlowLayout()); //first panel will allow default grid
        firstPane.add(firstNameLabel);
        firstPane.add(firstNameInput);
        firstPane.add(lastNameLabel);
        firstPane.add(lastNameInput);
        firstPane.add(emailLabel);
        firstPane.add(emailInput);
        firstPane.add(pagerNumLabel);
        firstPane.add(pagerNumInput);
        firstPane.add(medicalCentresLabel);
        firstPane.add(spane);

        //Adding submit button and GP info display
        JPanel secondPane = new JPanel(new FlowLayout());
        secondPane.add(submit);
        secondPane.add(outspane);
        secondPane.add(medicalCentreGP);

        //Adding created panels to the main panel
        this.setLayout(new GridLayout(2, 1));
        this.add(firstPane);
        this.add(secondPane);

    }



}
