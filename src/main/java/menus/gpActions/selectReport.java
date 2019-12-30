package menus.gpActions;

import clientClasses.Request;
import generalClasses.ourFrame;
import menus.GPMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class selectReport extends ourFrame {

    //UI elements
    private JLabel label = new JLabel();
    private JLabel guideMessage = new JLabel("Please choose the date of the case report.");
    private JList<String>caseReportList;
    private JLabel listLabel = new JLabel("Case report dates: ");
    private JButton viewButton = new JButton("View/Edit Selected");
    private JScrollPane spane;

    //data
    private String gpName;
    private int gpID;
    private int patientID;
    private String response;
    private Vector<String> caseDatetimes = new Vector(); //this is what is put in the JList for display
    private JSONObject selectedReport;
    private JSONArray selectedMedis; //this is a JSONArray containing the medications for the selected report
    private JSONArray data; //all the rest of the data that was sent from the server

    public selectReport(int idNum, int gpID, String gpName){
        //initialize frame
        this.setTitle("View case records");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //store data
        patientID = idNum;
        this.gpID = gpID;
        this.gpName = gpName;

        //changing guide message font
        guideMessage.setFont(guideMessage.getFont().deriveFont(18f));

        //make request to get all case reports
        getCaseReports();

        //Adding case report list to scroll panel
        //caseReportList = new JList<String>(caseDatetimes);
        caseReportList = new JList<String>(caseDatetimes);
        spane = new JScrollPane(caseReportList);

        extendMenu();
        createLayout();

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int idx = caseReportList.getSelectedIndex();
                try {
                    selectedReport = (JSONObject) ((JSONObject) data.get(idx)).get("casereport");
                    selectedMedis = (JSONArray)((JSONObject) data.get(idx)).get("medications");
//                    //now do with the data within selectedReport and selectedMedis what you like
//                    //e.g.: print them out so visible, edit them by making textfield editable, etc.
                    JFrame viewedit = new vieweditReport(patientID, selectedReport, selectedMedis, gpID, gpName);
                    dispose();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCaseReports(){
        JSONObject instruction = new JSONObject();
        try {
            instruction.put("function", "getCaseReports");
            JSONObject data = new JSONObject();
            data.put("patient_id", patientID);
            instruction.put("data", data);
        }catch(Exception e){
            e.printStackTrace();
        }
        String instruction_string = instruction.toString();
        Request post = new Request();
        response = post.makePostRequest(instruction_string);
        System.out.println("response: " + response);

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
    }

    private void extendMenu(){
        JMenuItem selectPatient = new JMenuItem("Select new patient");
        selectPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GPMenu gpMenu = new GPMenu(gpName, gpID);
                dispose();
            }
        });
        navigation.add(selectPatient);
    }
    private void createLayout(){

        JPanel pane = new JPanel();
        pane.setLayout(null);

        //getting appropriate dimensions for various list items
        Dimension guideMessageDim = guideMessage.getPreferredSize();
        Dimension listLabelDim = listLabel.getPreferredSize();
        Dimension spaneDim = spane.getPreferredSize();
        Dimension viewButtonDim = viewButton.getPreferredSize();

        //setting the bounds for each JComponent
        guideMessage.setBounds(30, 20, guideMessageDim.width, guideMessageDim.height);
        listLabel.setBounds(30, 80, listLabelDim.width, listLabelDim.height);
        spane.setBounds(30, 110, spaneDim.width + 400, spaneDim.height);
        viewButton.setBounds(30, spaneDim.height + 140, viewButtonDim.width, viewButtonDim.height);

        //Adding buttons to main panel to be added to frame
        pane.add(guideMessage);
        pane.add(listLabel);
        pane.add(spane);
        pane.add(viewButton);

        this.setContentPane(pane);


    }

}
