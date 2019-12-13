package menus.gpActions;

import clientClasses.CustomJson;
import clientClasses.Request;
import com.google.gson.Gson;
import menus.ourFrame;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class viewReport extends ourFrame {

    private JLabel label = new JLabel();
    private JList<String>caseReportList;
    private JLabel listLabel = new JLabel("Select casereport:");
    private JButton viewButton = new JButton("View selected");

    //data
    private int patientID;
    private String response;
    private Vector<String> caseDatetimes; //this is what is put in the JList for display
    private JSONObject selectedReport;
    private JSONArray selectedMedis; //this is a JSONArray containing the medications for the selected report
    private JSONArray data; //all the rest of the data that was sent from the server

    public viewReport(int idNum){
        //initialize frame
        this.setTitle("View case records");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //store patient id
        patientID = idNum;

        //make request to get all caserepords
        getCaseReports();

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int idx = caseReportList.getSelectedIndex();
                try {
                    selectedReport = (JSONObject) ((JSONObject) data.get(idx)).get("casereport");
                    selectedMedis = (JSONArray)((JSONObject) data.get(idx)).get("medications");
                    //now do with the data within selectedReport and selectedMedis what you like
                    //e.g.: print them out so visible, edit them by making textfield editable, etc.
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
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

        //unpacking the data
        try {
            JSONObject resp = new JSONObject(response);
            data = (JSONArray) resp.get("data");
            //loop through all the case records
            for(int i = 0, size=data.length(); i<size; i++){
                JSONObject report = (JSONObject) ((JSONObject) data.get(i)).get("casereport");
                caseDatetimes.add(report.getString("datetime"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        caseReportList = new JList<String>(caseDatetimes);
    }
}
