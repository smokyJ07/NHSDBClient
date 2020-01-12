package menus.adminActions;

import clientClasses.CustomJson;
import clientClasses.Request;
import com.google.gson.JsonObject;
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
    private JScrollPane patientSpane = new JScrollPane(); //used for patient list display
    private JLabel inputLabel = new JLabel("GP Name: ");
    private JLabel listLabel = new JLabel("Patients found: ");
    private JTextField gpName = new JTextField();
    private JButton searchButt = new JButton("Search");
    private JLabel errorMessage = new JLabel("");
    JList<String> patientMatches;

    public GPSearchTab(){
        //Setting the error message to invisible before error is achieved
        errorMessage.setForeground(Color.red);
        errorMessage.setVisible(false);

        //Adding input action listeners
        //Searching for given input surname in our database (for now does not search)
        searchButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(gpName.getText().length() != 0){
                    executeGetPatientsRequest();
                }
            }
        });

        createLayout();
        this.setVisible(true);
    }

    //Searches through database for entered patient name
    private void executeGetPatientsRequest(){
        //reset everything
        Vector<String> patients = new Vector(); //vector holding information about patients
        patientMatches = new JList<>(patients);
        //first obtain gp's id with getDoctor
        String name = gpName.getText();
        String gpID = "";
        JSONObject getDoctorData = new JSONObject();
        try {
            getDoctorData.put("name", name);

            CustomJson instruction = new CustomJson("getDoctor", getDoctorData);
            Request post = new Request();
            String resp = post.makePostRequest(instruction.toString());
            JSONObject response = new JSONObject(resp);
            JSONObject responseData = response.getJSONObject("data");
            gpID = responseData.getString("gpid");
        }catch(Exception e){
            e.printStackTrace();
        }
        if(gpID.equals("")){
            errorMessage.setText("Unable to find GP.");
            errorMessage.setVisible(true);
        }
        else {
            //second obtain patients of gp with getPatients
            JSONObject data = new JSONObject();
            try {
                data.put("gpid", gpID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //creating the request
            CustomJson instruction = new CustomJson("getAllPatients", data);
            String instructionString = instruction.toString();
            //sending request and obtaining the response
            String response = "";
            Request post = new Request();
            try {
                response = post.makePostRequest(instructionString);
                JSONObject results = new JSONObject(response);
                JSONArray result_json_array = (JSONArray) results.get("data");

                //loop through resulting array and extract patient info
                for (int i = 0, size = result_json_array.length(); i < size; i++) {
                    JSONObject patient = (JSONObject) result_json_array.get(i);
                    String patientInfo = "ID: " + patient.getInt("id") + ", " + "Name: " + patient.getString("name") +
                            ", " + "DOB: " + patient.getString("dob");
                    patients.add(patientInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(patients.size()<1){
                errorMessage.setText("GP has no patients yet.");
                errorMessage.setVisible(true);
            }
            else {
                patientMatches = new JList<>(patients);
                patientMatches.setVisible(true);
                patientSpane.getViewport().setView(patientMatches);
            }
        }
    }

    //Sets the layout of this frame with absolute layout
    private void createLayout(){
        this.setLayout(null);

        //Getting ideal dimensions for elements
        Dimension listLabelDim = listLabel.getPreferredSize();
        Dimension inputLabelDim = inputLabel.getPreferredSize();
        Dimension patientNameDim = gpName.getPreferredSize();
        Dimension searchButtDim = searchButt.getPreferredSize();
        Dimension errorMessageDim = errorMessage.getPreferredSize();
        Dimension patientSpaneDim = patientSpane.getPreferredSize();

        //Setting bounds
        listLabel.setBounds(30, 100, listLabelDim.width + 100, listLabelDim.height);
        inputLabel.setBounds(30, 60, inputLabelDim.width, inputLabelDim.height);
        patientSpane.setBounds(30, 150, 530, 200);
        gpName.setBounds(30 + inputLabelDim.width, 56, 530 - inputLabelDim.width - searchButtDim.width, patientNameDim.height);
        searchButt.setBounds(490, 56, searchButtDim.width, searchButtDim.height);
        errorMessage.setBounds(30, 350, errorMessageDim.width, errorMessageDim.height);

        //Adding components to main panel
        this.add(listLabel);
        this.add(inputLabel);
        this.add(patientSpane);
        this.add(gpName);
        this.add(searchButt);
        this.add(errorMessage);

    }

}
