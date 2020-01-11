package menus.adminActions;

import clientClasses.CustomJson;
import clientClasses.Request;
import menus.gpActions.addReport;
import menus.gpActions.selectReport;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GPSearchTab extends JPanel {
    private JScrollPane spane;  //used for patient list display
    private JLabel inputLabel = new JLabel("GP Name: ");
    private JLabel listLabel = new JLabel("Matches Found: ");
    private JTextField gpName = new JTextField();
    private JButton addButt= new JButton("?");        //leads to case record add JFrame
    private JButton viewButt = new JButton("?");//leads to case record view JFrame
    private JButton searchButt = new JButton("Search");
    //private JLabel errorMessage = new JLabel("Please select a patient.");
    JList<String> patientMatches;
    //GP data


    public GPSearchTab(){

        //Adding an automatic element just to move onto add record page
        String[] names = {"Test GP"};
        patientMatches = new JList(names);
        spane = new JScrollPane(patientMatches);


        //Setting the error message to invisible before error is achieved
//        errorMessage.setForeground(Color.red);
//        errorMessage.setVisible(false);

        //Adding input action listeners

        //Searching for given input surname in our database (for now does not search)
//        searchButt.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//
//                if(gpName.getText().length() != 0){
//                    executeGetPatientsRequest();
//                }
//            }
//        });

//        //Adding patient case record
//        addButt.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                if(spane.getComponentCount() != 0) {
//                    String patientInfo = patientMatches.getSelectedValue();
//                    String idString = patientInfo.split(",")[0];
//                    int idNum = Integer.parseInt(idString.split(" ")[1]);
//                    System.out.println(idNum);
//                    //int idNum = 3;
//                    JFrame addRecord = new addReport(idNum, gpID, gpName);
//                    dispose();
//                }
//                else{
//                    errorMessage.setVisible(true);
//                }
//            }
//        });

//        //View patient case records button
//        viewButt.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                if(spane.getComponentCount() !=0) {
//                    String patientInfo = patientMatches.getSelectedValue();
//                    String idString = patientInfo.split(",")[0];
//                    int idNum = Integer.parseInt(idString.split(" ")[1]);
//                    //int idNum = 3;  //arbitrary ID for now to simply work on the viewing case record aspect
//                    JFrame viewRecord = new selectReport(idNum, gpID, gpName);
//                    dispose();
//                }
//                else{
//                    errorMessage.setVisible(true);
//                }
//            }
//        });


        //creates the layout of the main frame
        createLayout();
        //createTabbedPane();

        this.setVisible(true);
    }

//    //Searches through database for entered patient name
//    private void executeGetPatientsRequest(){
//        String name = gpName.getText();
//        Vector<String> patients = new Vector(); //vector holding information about patients
//        JSONObject data = new JSONObject();
//        try {
//            data.put("name", name);
//            data.put("gpid", gpID);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //creating the request
//        CustomJson instruction = new CustomJson("getPatients", data);
//        String instructionString = instruction.toString();
//        //System.out.println("Instruction: " + instructionString);
//
//        //sending request and obtaining the response
//        String response = "";
//        Request post = new Request();
//        try {
//            response = post.makePostRequest(instructionString);
//            System.out.println("response: " + response);
//            JSONObject results = new JSONObject(response);
//            JSONArray result_json_array = (JSONArray) results.get("data");
//
//            //loop through resulting array and extract patient info
//            for(int i = 0, size=result_json_array.length(); i<size; i++){
//                JSONObject patient = (JSONObject) result_json_array.get(i);
//                String patientInfo = "ID: " + patient.getInt("id") + ", " + "Name: " + patient.getString("name") +
//                        ", " + "DOB: " + patient.getString("dob");
//                patients.add(patientInfo);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        patientMatches = new JList<>(patients);
//        patientMatches.setVisible(true);
//        spane.getViewport().setView(patientMatches);
//    }

    //Sets the layout of this frame with absolute layout
    private void createLayout(){
        this.setLayout(null);

        //Getting ideal dimensions for elements
        Dimension listLabelDim = listLabel.getPreferredSize();
        Dimension inputLabelDim = inputLabel.getPreferredSize();
        Dimension spaneDim = spane.getPreferredSize();
        Dimension patientNameDim = gpName.getPreferredSize();
        Dimension viewButtDim = viewButt.getPreferredSize();
        Dimension addButtDim = addButt.getPreferredSize();
        //Dimension welcomeMessageDim = welcomeMessage.getPreferredSize();
        Dimension searchButtDim = searchButt.getPreferredSize();
        //Dimension errorMessageDim = errorMessage.getPreferredSize();

        //Setting bounds
        listLabel.setBounds(30, 100, listLabelDim.width, listLabelDim.height);
        inputLabel.setBounds(30, 60, inputLabelDim.width, inputLabelDim.height);
        spane.setBounds(30, 150, 530, 200);
        gpName.setBounds(30 + inputLabelDim.width, 56, 530 - inputLabelDim.width - searchButtDim.width, patientNameDim.height);
        viewButt.setBounds(30, 360, viewButtDim.width, viewButtDim.height);
        addButt.setBounds(220, 360, addButtDim.width, addButtDim.height);
        //welcomeMessage.setBounds(30, 20, welcomeMessageDim.width, welcomeMessageDim.height);
        searchButt.setBounds(480, 56, searchButtDim.width, searchButtDim.height);
        //errorMessage.setBounds(30, 415, errorMessageDim.width, errorMessageDim.height);

        //Adding components to main panel
        this.add(listLabel);
        this.add(inputLabel);
        this.add(spane);
        this.add(gpName);
        this.add(viewButt);
        this.add(addButt);
        //this.add(welcomeMessage);
        this.add(searchButt);
        //this.add(errorMessage);

    }

}
