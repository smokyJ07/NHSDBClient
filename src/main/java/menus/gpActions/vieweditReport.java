package menus.gpActions;


//This class extends addReport as it has all the same interfaces, but only needs first to be initialised with relevant data


import clientClasses.CustomJson;
import clientClasses.Request;
import menus.GPMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vieweditReport extends addReport {
    private JSONObject report;
    private JSONArray medications;

    //all the extracted casereport and medication data
    private String datetime;
    private String casenotes;
    private Boolean chronicCondition;
    private int caseID;
    //medication data
    private int medCount;

    public vieweditReport(int patientID, JSONObject report, JSONArray medications){
        super(patientID);
        this.report = report;
        this.medications = medications;

        //Small edits to change the context of the JFrame
        super.submit.setText("Apply Changes");
        super.guideMessage.setText("If needed, edit the information below and and click on 'Apply Changes'.");

        getCaseReportData();

        for(int i =0; i<medCount; i++){
            super.addMedButt.doClick(); //Ensures that the correct number of medications is automatically displayed by
            //virtually clicking the add medication button
        }
        extendMenuBar();

        initialiseInput();

        submitChanges();
    }

    //Here you guys can call the methods to extract the data from the case report so that I can add it to the inputs.
    private void getCaseReportData(){
        try {
            //report
            casenotes = report.getString("casenotes");
            datetime = report.getString("datetime");
            chronicCondition = report.getBoolean("chronic_condition");
            caseID = report.getInt("id");
            //medications
            medCount = medications.length();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //adds important things to the MenuBar
    private void extendMenuBar(){
        JMenuItem selectPatient = new JMenuItem("Select new patient");
        selectPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GPMenu gpMenu = new GPMenu();
                dispose();
            }
        });
        navigation.add(selectPatient);
    }

    //Here I'm going to take the data variables you guys added to initialise the UI with the values of our case report
    private void initialiseInput(){
        //set report info
        reportInput.setText(casenotes);
        titleInput.setText("Change the following details");
        if (chronicCondition){
            chronicButt.setSelected(true);
        }
        else{
            tempButt.setSelected(true);
        }
        //set medication info
        for (int i = 0; i<medCount; i++){
            try{
                JSONObject medication = (JSONObject) medications.get(i);

                //extract date data contents
                String startDate = medication.getString("starttime").split(" ")[0]; //gets only the date part of timestamp
                String startDay = startDate.split("-")[2];
                String startMonth = startDate.split("-")[1];
                String startYear = startDate.split("-")[0];
                String endDate = medication.getString("endtime").split(" ")[0];
                String endDay = endDate.split("-")[2];
                String endMonth = endDate.split("-")[1];
                String endYear = endDate.split("-")[0];
                //put data into combo box
                startDates.get(i).get(0).setSelectedItem(startDay);
                startDates.get(i).get(1).setSelectedItem(startMonth);
                startDates.get(i).get(2).setSelectedItem(startYear);
                endDates.get(i).get(0).setSelectedItem(endDay);
                endDates.get(i).get(1).setSelectedItem(endMonth);
                endDates.get(i).get(2).setSelectedItem(endYear);

                addMedInput.get(i).setText(medication.getString("type"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    private void submitChanges(){
        errorsuccessMessage.setVisible(true);

        //change action listener
        for (ActionListener al : submit.getActionListeners()) {
            submit.removeActionListener(al);
        }
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject data = gettingDataForServer();

                //insert casereport ID into data
                try {
                    JSONObject reportData = data.getJSONObject("casereport");
                    reportData.put("id", caseID);
                    data.remove("casereport");
                    data.put("casereport", reportData);
                }catch(Exception e){
                    e.printStackTrace();
                }
                CustomJson instruction = new CustomJson("changeCaseReport", data);
                String instruction_string = instruction.toString();
                Request post = new Request();
                post.makePostRequest(instruction_string);
                int check = titleInput.checkInput();
                //System.out.println(check);

                if (check == 2) { //If input is correct
                    //gettingDataForServer();
                    errorsuccessMessage.setText(titleInput.getMessage());
                    errorsuccessMessage.setForeground(Color.green);
                }
                else if (check == 1){
                    errorsuccessMessage.setText(titleInput.getMessage());
                    errorsuccessMessage.setForeground(Color.red);
                }
                else if (check == 0){
                    errorsuccessMessage.setText(titleInput.getMessage());
                    errorsuccessMessage.setForeground(Color.red);
                }

            }
        });
    }
}
