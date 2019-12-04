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

/*CLASS DESCRIPTION: The class addMedicalCentre extends from the JPanel class. It provides the appropriate container
 * for adding a medical centre to our database: two text fields with labels,
 * a submit button to add the new medical centre to our database alongside a display section to ensure that the program is working
 * correctly. It includes a CreateMCList function in order for the display to work properly as well as a setting layout function  */


public class MedicalCentreTab extends JPanel {

    //Information to collect
    private Map<String, String> mcMap = new HashMap<String, String>();    //will map medical centre keys to data
    //Labels
    private JLabel MCNameLabel;
    private JLabel addressLabel;
    //Inputs
    private JTextField inputMC;
    private JButton submit = new JButton("Submit");
    private JTextField inputAddress;
    //Output Medical Centre list added
    private DefaultListModel<String> MCAdded = new DefaultListModel<String>();
    private JScrollPane outspane = new JScrollPane();
    private JList<String> MCList = new JList<String>();
    private JLabel addressMC = new JLabel();

    public MedicalCentreTab() {

        //Initialising JLabels
        MCNameLabel = new JLabel("Please enter the name of the medical centre:");
        addressLabel = new JLabel("Please enter the address of the medical centre:");

        //Initialising GP_name text field
        inputMC = new JTextField(30);
        inputAddress = new JTextField(30);

        //Adding ActionListeners to button
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Adding input data to Map and resetting the text field
                String mcName = inputMC.getText();
                String mcAddress = inputAddress.getText();
                mcMap.put("name", mcName);
                mcMap.put("address", mcAddress);

                MCAdded.addElement(inputMC.getText());
                inputMC.setText("");    //Resets textfield so that user knows it's submitted
                inputAddress.setText("");

                //create JSON and send it
                JSONObject mc = new JSONObject(mcMap);
                System.out.println(mc.toString());

                CustomJson instruction = new CustomJson("addMC", mc);
                String instructionString = instruction.toString();
                Request post = new Request();
                post.makePostRequest(instructionString);

            }
        });

        //Adding GP List
        MCList = new JList(MCAdded);
        createMCList();

        //Function that sets layout appropriately
        settingLayout();

    }

    public void createMCList() {        //Making it so by selecting GP, outputs medical centre of GP

        MCList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                String medicalCentreChosen = mcMap.get(MCList.getSelectedValue());
                addressMC.setText(medicalCentreChosen);    //Outputs medical centre of selected GP
            }
        });
        outspane.getViewport().add(MCList);

    }

    public void settingLayout(){
        //Adding the textfield input, list and their labels as one flow panel
        JPanel firstPane = new JPanel(new FlowLayout()); //first panel will allow default grid
        firstPane.add(MCNameLabel);
        firstPane.add(inputMC);
        firstPane.add(addressLabel);
        firstPane.add(inputAddress);

        //Adding submit button and GP info display
        JPanel secondPane = new JPanel(new FlowLayout());
        secondPane.add(submit);
        secondPane.add(outspane);
        secondPane.add(addressMC);

        //Adding created panels to the main panel
        this.setLayout(new GridLayout(2, 1));
        this.add(firstPane);
        this.add(secondPane);

    }



}
