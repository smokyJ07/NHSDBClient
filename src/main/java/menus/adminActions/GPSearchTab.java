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
    private JScrollPane GPSpane;  //used for GP list display
    private JScrollPane patientSpane; //used for patient list display
    private JLabel inputLabel = new JLabel("GP Name: ");
    private JLabel listLabel = new JLabel("GPs Found: ");
    private JTextField gpName = new JTextField();
    private JButton viewButt = new JButton("View patients");//leads to case record view JFrame
    private JButton searchButt = new JButton("Search");
    private JLabel errorMessage = new JLabel("Please select a GP.");
    JList<String>GPMatches;
    JList<String> patientMatches;
    //GP data

    public GPSearchTab(){

        //Adding automatic elements just to move onto add record page
        String[] names = {"Test GP"};
        String[] names2 = {"Test Patient"};
        GPMatches = new JList(names);
        patientMatches = new JList(names2);
        GPSpane = new JScrollPane(GPMatches);
        patientSpane = new JScrollPane(patientMatches);

        patientSpane.setVisible(false); // Making the patient one invisible for now

        /*HERE ADD CODE TO ADD PATIENTS OF THAT GP TO THE PATIENT LIST*/



        //Setting the error message to invisible before error is achieved
        errorMessage.setForeground(Color.red);
        errorMessage.setVisible(false);

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

        //View patients of that GP
        viewButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(GPSpane.getComponentCount() !=0) {
//                    String patientInfo = patientMatches.getSelectedValue();
//                    String idString = patientInfo.split(",")[0];
//                    int idNum = Integer.parseInt(idString.split(" ")[1]);
//                    //int idNum = 3;  //arbitrary ID for now to simply work on the viewing case record aspect
//                    JFrame viewRecord = new selectReport(idNum, gpID, gpName);
//                    dispose();
                    listLabel.setText("Patients of TestGP");      //Here you can add the GPs name once you find it from the database
                    GPSpane.setVisible(false);
                    viewButt.setVisible(false);
                    searchButt.setVisible(false);
                    gpName.setEditable(false);
                    patientSpane.setVisible(true);


                }
                else{
                    errorMessage.setVisible(true);
                }
            }
        });


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
        Dimension GPSpaneDim = GPSpane.getPreferredSize();
        Dimension patientNameDim = gpName.getPreferredSize();
        Dimension viewButtDim = viewButt.getPreferredSize();
        //Dimension welcomeMessageDim = welcomeMessage.getPreferredSize();
        Dimension searchButtDim = searchButt.getPreferredSize();
        //Dimension errorMessageDim = errorMessage.getPreferredSize();

        //Setting bounds
        listLabel.setBounds(30, 100, listLabelDim.width + 100, listLabelDim.height);
        inputLabel.setBounds(30, 60, inputLabelDim.width, inputLabelDim.height);
        GPSpane.setBounds(30, 150, 530, 200);
        patientSpane.setBounds(30, 150, 530, 200);
        gpName.setBounds(30 + inputLabelDim.width, 56, 530 - inputLabelDim.width - searchButtDim.width, patientNameDim.height);
        viewButt.setBounds(30, 360, viewButtDim.width, viewButtDim.height);
        searchButt.setBounds(480, 56, searchButtDim.width, searchButtDim.height);
        //errorMessage.setBounds(30, 415, errorMessageDim.width, errorMessageDim.height);

        //Adding components to main panel
        this.add(listLabel);
        this.add(inputLabel);
        this.add(GPSpane);
        this.add(patientSpane);
        this.add(gpName);
        this.add(viewButt);
        //this.add(welcomeMessage);
        this.add(searchButt);
        //this.add(errorMessage);

    }

}
