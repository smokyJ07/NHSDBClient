package menus;

import clientClasses.CustomJson;
import clientClasses.Request;
import generalClasses.ourFrame;
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

public class GPMenu extends ourFrame {
    //private JMenuBar menuBar;
    private JScrollPane spane;  //used for patient list display
    //private JLabel message;
    private JLabel inputLabel = new JLabel("Patient Name: ");
    private JLabel listLabel = new JLabel("Matches Found: ");
    private JTextField patientName = new JTextField();
    private JButton addButt= new JButton("Add Case Record");        //leads to case record add JFrame
    private JButton viewButt = new JButton("View/Edit Case Records");//leads to case record view JFrame
    private JButton searchButt = new JButton("Search");
    private JLabel welcomeMessage = new JLabel("Welcome back, Joao.");
    private JLabel errorMessage = new JLabel("Please select a patient.");
    JList<String> patientMatches;

    public GPMenu(){
        //initialize frame
        this.setVisible(true);
        this.setTitle("GP Menu");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Setting the font size of the welcome message
        welcomeMessage.setFont(welcomeMessage.getFont().deriveFont(18f));

        //Adding an automatic element just to move onto add record page
        String[] names = {"Test Patient"};
        patientMatches = new JList(names);
        spane = new JScrollPane(patientMatches);


        //Setting the error message to invisible before error is achieved
        errorMessage.setForeground(Color.red);
        errorMessage.setVisible(false);

        //Adding input action listeners

        //Searching for given input surname in our database (for now does not search)
        searchButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(patientName.getText().length() != 0){
                    executeGetPatientsRequest();
                }
            }
        });

        //Adding patient case record
        addButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(spane.getComponentCount() != 0) {
                    String patientInfo = patientMatches.getSelectedValue();
                    String idString = patientInfo.split(",")[0];
                    int idNum = Integer.parseInt(idString.split(" ")[1]);
                    System.out.println(idNum);
                    //int idNum = 3;
                    JFrame addRecord = new addReport(idNum);
                    dispose();
                }
                else{
                    errorMessage.setVisible(true);
                }
            }
        });

        //View patient case records button
        viewButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(spane.getComponentCount() !=0) {
                    String patientInfo = patientMatches.getSelectedValue();
                    String idString = patientInfo.split(",")[0];
                    int idNum = Integer.parseInt(idString.split(" ")[1]);
                    //int idNum = 3;  //arbitrary ID for now to simply work on the viewing case record aspect
                    JFrame viewRecord = new selectReport(idNum);
                    dispose();
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

    private void executeGetPatientsRequest(){
        String name = patientName.getText();
        Vector<String>patients = new Vector(); //vector holding information about patients
        JSONObject data = new JSONObject();
        try {
            data.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //creating the request
        CustomJson instruction = new CustomJson("getPatients", data);
        String instructionString = instruction.toString();
        //System.out.println("Instruction: " + instructionString);

        //sending request and obtaining the response
        String response = "";
        Request post = new Request();
        try {
            response = post.makePostRequest(instructionString);
            System.out.println("response: " + response);
            JSONObject results = new JSONObject(response);
            JSONArray result_json_array = (JSONArray) results.get("data");

            //loop through resulting array and extract patient info
            for(int i = 0, size=result_json_array.length(); i<size; i++){
                JSONObject patient = (JSONObject) result_json_array.get(i);
                String patientInfo = "ID: " + patient.getInt("id") + ", " + "Name: " + patient.getString("name") +
                        ", " + "DOB: " + patient.getString("dob");
                patients.add(patientInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        patientMatches = new JList<>(patients);
        patientMatches.setVisible(true);
        spane.getViewport().setView(patientMatches);
    }
    //Edited to work with absolute layout instead
    private void createLayout(){
        JPanel pane = new JPanel();
        pane.setLayout(null);

        //Getting ideal dimensions for elements
        Dimension listLabelDim = listLabel.getPreferredSize();
        Dimension inputLabelDim = inputLabel.getPreferredSize();
        Dimension spaneDim = spane.getPreferredSize();
        Dimension patientNameDim = patientName.getPreferredSize();
        Dimension viewButtDim = viewButt.getPreferredSize();
        Dimension addButtDim = addButt.getPreferredSize();
        Dimension welcomeMessageDim = welcomeMessage.getPreferredSize();
        Dimension searchButtDim = searchButt.getPreferredSize();
        Dimension errorMessageDim = errorMessage.getPreferredSize();

        //Setting bounds
        listLabel.setBounds(30, 120, listLabelDim.width, listLabelDim.height);
        inputLabel.setBounds(30, 80, inputLabelDim.width, inputLabelDim.height);
        spane.setBounds(30, 170, 530, 200);
        patientName.setBounds(30 + inputLabelDim.width, 76, 530 - inputLabelDim.width - searchButtDim.width, patientNameDim.height);
        viewButt.setBounds(30, 380, viewButtDim.width, viewButtDim.height);
        addButt.setBounds(220, 380, addButtDim.width, addButtDim.height);
        welcomeMessage.setBounds(30, 20, welcomeMessageDim.width, welcomeMessageDim.height);
        searchButt.setBounds(490, 76, searchButtDim.width, searchButtDim.height);
        errorMessage.setBounds(30, 415, errorMessageDim.width, errorMessageDim.height);

        //Adding components to main panel
        pane.add(listLabel);
        pane.add(inputLabel);
        pane.add(spane);
        pane.add(patientName);
        pane.add(viewButt);
        pane.add(addButt);
        pane.add(welcomeMessage);
        pane.add(searchButt);
        pane.add(errorMessage);

        //Adding main panel to JFrame
        this.getContentPane().add(pane);
    }
}
