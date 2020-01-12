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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/*CLASS DESCRIPTION: This class extends from the customized "ourFrame" class. It provides the appropriate container
 * for a GP to search and select a case report from our database. It allows the user to scroll through
 * and find the case report based on its date and title */


public class selectReport extends ourFrame {

    //UI elements
    private JLabel label = new JLabel();
    private JLabel guideMessage = new JLabel("Please choose the date of the case report.");
    private JList<String>caseReportList;
    private JLabel listLabel = new JLabel("Case report dates: ");
    private JButton viewButton = new JButton("View/Edit Selected");
    private JScrollPane spane;
    private JButton viewMedicines = new JButton("List of current Medicines");

    //data
    private String gpName;
    private int gpID;
    private int patientID;
    private String response;
    private Vector<String> caseDatetimes = new Vector(); //this is what is put in the JList for display
    private JSONObject selectedReport;
    private JSONArray selectedMedis; //this is a JSONArray containing the medications for the selected report
    private JSONArray data; //all the rest of the data that was sent from the server
    Vector<String> medicines = new Vector();
    Vector<String> startDates = new Vector();
    Vector<String> endDates = new Vector();

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
        caseReportList = new JList<String>(caseDatetimes);
        spane = new JScrollPane(caseReportList);

        extendMenu();
        createLayout();

        viewMedicines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getCurrentMedicines();
                currentMedicines medWindow = new currentMedicines(medicines, startDates, endDates);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int idx = caseReportList.getSelectedIndex();
                try {
                    selectedReport = (JSONObject) ((JSONObject) data.get(idx)).get("casereport");
                    selectedMedis = (JSONArray)((JSONObject) data.get(idx)).get("medications");
                    JFrame viewedit = new vieweditReport(patientID, selectedReport, selectedMedis, gpID, gpName);
                    dispose();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //Searches through our database for the case reports available for the patient selected
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

    //extends menu to allow option to return to the main gp menu
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

    //make list of all current medicines taken
    private void getCurrentMedicines(){
        medicines.clear();
        startDates.clear();
        endDates.clear();
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            for (int i = 0, size = data.length(); i < size; i++) {
                JSONObject casereport = (JSONObject)data.get(i);
                JSONArray medications = casereport.getJSONArray("medications");
                for (int j = 0, size1 = medications.length(); j<size1; j++){
                    JSONObject medication = (JSONObject)medications.get(j);
                    String start = medication.getString("starttime");
                    String end = medication.getString("endtime");

                    Date startDate = formatter.parse(start);
                    Date endDate = formatter.parse(end);
                    Timestamp startStamp = new Timestamp(startDate.getTime());
                    Timestamp endStamp = new Timestamp(endDate.getTime());
                    if (startStamp.before(now) && endStamp.after(now)){
                        medicines.add(medication.getString("type"));
                        startDates.add(start);
                        endDates.add(end);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Creates the layout of this frame using absolute layout
    private void createLayout(){

        JPanel pane = new JPanel();
        pane.setLayout(null);

        //getting appropriate dimensions for various list items
        Dimension guideMessageDim = guideMessage.getPreferredSize();
        Dimension listLabelDim = listLabel.getPreferredSize();
        Dimension spaneDim = spane.getPreferredSize();
        Dimension viewButtonDim = viewButton.getPreferredSize();
        Dimension viewMedicinesDim = viewMedicines.getPreferredSize();

        //setting the bounds for each JComponent
        guideMessage.setBounds(30, 20, guideMessageDim.width, guideMessageDim.height);
        listLabel.setBounds(30, 80, listLabelDim.width, listLabelDim.height);
        spane.setBounds(30, 110, spaneDim.width + 400, spaneDim.height);
        viewButton.setBounds(30, spaneDim.height + 140, viewButtonDim.width, viewButtonDim.height);
        viewMedicines.setBounds(300, spaneDim.height + 140, viewMedicinesDim.width, viewMedicinesDim.height);

        //Adding buttons to main panel to be added to frame
        pane.add(guideMessage);
        pane.add(listLabel);
        pane.add(spane);
        pane.add(viewButton);
        pane.add(viewMedicines);

        this.setContentPane(pane);


    }

}
