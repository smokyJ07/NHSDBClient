package menus.gpActions;

import clientClasses.CustomJson;
import clientClasses.Request;
import menus.ourFrame;
import org.json.JSONObject;

import javax.swing.*;

public class viewReport extends ourFrame {

    private JLabel label = new JLabel();
    private int patientID;
    private JList<String>caseReportList;
    private JLabel listLabel = new JLabel("Select casereport:");
    private JButton editButton = new JButton("Edit selected");
    private String response;

    public viewReport(int idNum){
        //initialize frame
        this.setTitle("View case records");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //store patient id
        patientID = idNum;

        //make request to get all caserecords
        getCaseReports();
    }

    private void getCaseReports(){
        JSONObject instruction = new JSONObject();
        try {
            instruction.put("function", "getCaseReport");
            instruction.put("data", patientID);
        }catch(Exception e){
            e.printStackTrace();
        }
        String instruction_string = instruction.toString();
        Request post = new Request();
        response = post.makePostRequest(instruction_string);
        System.out.println(response);
    }
}
