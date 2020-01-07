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
    //Adding message showing the success of adding an item to the database successfully
    private JLabel successMessage = new JLabel ("Medical centre added successfully!");
    //Output Medical Centre list added
    private DefaultListModel<String> MCAdded = new DefaultListModel<String>();
    private JScrollPane outspane = new JScrollPane();
    private JList<String> MCList = new JList<String>();
    private JLabel addressMC = new JLabel();

    public MedicalCentreTab() {

        //Initialising JLabels
        MCNameLabel = new JLabel("Name:");
        addressLabel = new JLabel("Address:");

        //Initialising GP_name text field
        inputMC = new JTextField(30);
        inputAddress = new JTextField(30);

        //Setting success message to be invisible
        successMessage.setForeground(Color.green);
        successMessage.setVisible(false);

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

                //Setting the success message to be visible after submission!
                successMessage.setVisible(true);

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
        settingAbsLayout();

    }

    //Makes a list of medical centres and adds it to scroll panel
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

    //Sets the layout this panel's JComponents using absolute layout
    public void settingAbsLayout(){
        //Adding all different layouts via an absolute layout
        this.setLayout(null);   //size is (600, 500)

        //Dimensions of each element
        Dimension MCNameLabelDim = MCNameLabel.getPreferredSize();
        Dimension inputMCDim = inputMC.getPreferredSize();
        Dimension addressLabelDim = addressLabel.getPreferredSize();
        Dimension inputAddressDim = inputAddress.getPreferredSize();
        Dimension submitDim = submit.getPreferredSize();
        Dimension successMessageDim = successMessage.getPreferredSize();
//        Dimension outspaneDim = outspane.getPreferredSize();
//        Dimension addressMCDim = addressMC.getPreferredSize();

        //Setting bounds of each component individually
        MCNameLabel.setBounds(10, 12, MCNameLabelDim.width, MCNameLabelDim.height); //label must be slightly lower to be aligned with text field
        inputMC.setBounds(205, 10, inputMCDim.width, inputMCDim.height);

        addressLabel.setBounds(10, 52, addressLabelDim.width, addressLabelDim.height);
        inputAddress.setBounds(205, 50, inputAddressDim.width, inputAddressDim.height);

        submit.setBounds(5, 100, submitDim.width, submitDim.height);
        successMessage.setBounds(10, 140, successMessageDim.width, successMessageDim.height);

        //Adding groups of components to JPanel
        this.add(MCNameLabel);
        this.add(inputMC);

        this.add(addressLabel);
        this.add(inputAddress);

        this.add(submit);
        this.add(successMessage);

    }


}
