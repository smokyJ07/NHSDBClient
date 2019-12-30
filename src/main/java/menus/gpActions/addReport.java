package menus.gpActions;

import clientClasses.CustomJson;
import clientClasses.Request;
import generalClasses.ourFrame;
import generalClasses.ourTextField;
import menus.GPMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class addReport extends ourFrame {
    //Labels
    private JLabel reportLabel = new JLabel("Case Report: ");
    private JLabel titleLabel = new JLabel("Title: ");
    //Inputs
    protected JTextArea reportInput = new JTextArea(8, 61);
    protected ourTextField titleInput = new ourTextField(58);
    protected JRadioButton chronicButt = new JRadioButton("Chronic");
    protected JRadioButton tempButt = new JRadioButton("Temporary");
    protected ButtonGroup group = new ButtonGroup();
    protected JButton addMedButt = new JButton("Add new medication");
    protected JButton submit = new JButton("Submit");
    //ArrayList of added components related to the medicine(s) added
    protected ArrayList<JTextField> addMedInput = new ArrayList<JTextField>();    //contains medication inputs
    private ArrayList<JLabel> addMedLabel = new ArrayList<JLabel>();            //contains medication input labels
    protected ArrayList<ArrayList<JComboBox>> startDates = new ArrayList<ArrayList<JComboBox>>();
    protected ArrayList<ArrayList<JComboBox>> endDates = new ArrayList<ArrayList<JComboBox>>();   //an array list with each entry having a input date 'bundle'
    private ArrayList<JLabel> startLabel = new ArrayList<JLabel>();
    private ArrayList<JLabel> endLabel = new ArrayList<JLabel>();
    //Date and time variables
    private String[] days = new String[31];     //string type used so it can be the input of JCombo boxes
    private String[] months = new String[12];
    private String[] years = new String[24];
    private String[] hours = new String[24];
    private String[] minutes = new String[60];
    private ArrayList<JComboBox> recordDate = new ArrayList<JComboBox>();
    //Other important fields
    private JPanel pane = new JPanel();
    private int medicationNumber = 0;
    protected int patientID;
    protected JLabel guideMessage = new JLabel("Please enter the relevant details of today's session.");
    protected JLabel errorsuccessMessage = new JLabel("  ");

    public addReport(int patientID_in){

        //initialize frame
        this.setTitle("New case record");
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //Initialising button group
        group.add(chronicButt);
        chronicButt.setActionCommand("Chronic");
        group.add(tempButt);
        tempButt.setActionCommand("Temporary");

        //Setting size and sorting out messages
        guideMessage.setFont(guideMessage.getFont().deriveFont(18f));
        //errorsuccessMessage.setVisible(false);

        //setting patient id that is currently treated
        patientID = patientID_in;

        //Initialising date variables
        for(int i = 1; i <= 31; i++){

            //conditional statements to add 0 before single digits
            if(i<10){
                days[i-1] = '0' + Integer.toString(i);
                if(i<=12){
                    months[i-1] = '0' + Integer.toString(i);
                    years[2*(i-1)] = Integer.toString(2021 - 2*(i-1));
                    years[2*i - 1] = Integer.toString(2020 - 2*i);
                }
            }
            else{
                days[i-1] = Integer.toString(i);
                if(i<=12){
                    months[i-1] = Integer.toString(i);
                    years[i-1] = Integer.toString(2021 - i);
                }
            }

        }

        //Creating the main layout of the window in the main panel
        extendMenu();
        createLayout();
        addingNewMedicine();
        this.getContentPane().add(pane);

        submitCall();
    }

    //adds menu options to menu bar
    private void extendMenu(){
        JMenuItem selectPatient = new JMenuItem("Select new patient");
        selectPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GPMenu gpmenu = new GPMenu();
                dispose();
            }
        });
        navigation.add(selectPatient);
    }

    //Called if add new medicine button is called
    private void addingNewMedicine(){
        //'Add medicine' button
        addMedButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { //adds new inputs lower and lower
                int minPosX;
                int yPosTrack = (int) (130 * Math.floor(medicationNumber / 2));  //tracks by how much lower the medical inputs should be placed
                pane.revalidate();

                if (medicationNumber % 2 == 0) {    //even number of medicationNumber are placed on left
                    minPosX = 30;
                } else {       //odd number of medicationNumber are placed on the right
                    minPosX = 430;
                }
                if (medicationNumber < 4) { //only add new inputs if less than 4 medicines have already been added
                    //adding new inputs
                    startLabel.add(new JLabel("Start date: "));
                    Dimension startLabelDim = startLabel.get(medicationNumber).getPreferredSize();
                    startLabel.get(medicationNumber).setBounds(minPosX, 400 + yPosTrack, startLabelDim.width, startLabelDim.height);
                    startDates.add(addDateInput(minPosX + startLabelDim.width, 400 + yPosTrack));

                    endLabel.add(new JLabel("End date: "));
                    Dimension endLabelDim = endLabel.get(medicationNumber).getPreferredSize();
                    endLabel.get(medicationNumber).setBounds(minPosX, 430 + yPosTrack, endLabelDim.width, endLabelDim.height);
                    endDates.add(addDateInput(minPosX + endLabelDim.width, 430 + yPosTrack));

                    addMedLabel.add(new JLabel("Medication name: "));
                    addMedInput.add(new JTextField(25));
                    Dimension addMedLabelDim = addMedLabel.get(medicationNumber).getPreferredSize();
                    Dimension addMedInputDim = addMedInput.get(medicationNumber).getPreferredSize();
                    addMedLabel.get(medicationNumber).setBounds(minPosX, 340 + yPosTrack, addMedLabelDim.width, addMedLabelDim.height);
                    addMedInput.get(medicationNumber).setBounds(minPosX, 370 + yPosTrack, addMedInputDim.width, addMedInputDim.height);

                    Dimension submitDim = submit.getPreferredSize();
                    submit.setBounds(30, 460 + yPosTrack, submitDim.width, submitDim.height);

                    //adding new inputs to main panel
                    pane.add(addMedLabel.get(medicationNumber));
                    pane.add(addMedInput.get(medicationNumber));
                    pane.add(startLabel.get(medicationNumber));
                    pane.add(endLabel.get(medicationNumber));
                    pane.add(submit);

                    pane.revalidate();  //refreshes panel
                    pane.repaint();
                    medicationNumber = medicationNumber + 1;

                }
            }
        });
    }

    //Setting layout of the JFrame (absolute layout)
    private void createLayout(){
        //creating main panel to be added to frame
        pane.setLayout(null);

        //Getting preferred dimensions of JComponents
        Dimension titleInputDim = titleInput.getPreferredSize();
        Dimension titleLabelDim = titleLabel.getPreferredSize();
        Dimension recordLabelDim = reportLabel.getPreferredSize();
        Dimension recordInputDim = reportInput.getPreferredSize();
        Dimension chronicButtDim = chronicButt.getPreferredSize();
        Dimension tempButtDim = tempButt.getPreferredSize();
        Dimension addMedButtDim = addMedButt.getPreferredSize();
        Dimension guideMessageDim = guideMessage.getPreferredSize();
        Dimension errorsuccessMessageDim = errorsuccessMessage.getPreferredSize();

        //Setting bounds for the layout
        titleInput.setBounds(30 + titleLabelDim.width, 76, titleInputDim.width, titleInputDim.height);
        titleLabel.setBounds(30, 80, titleLabelDim.width, titleLabelDim.height);
        reportLabel.setBounds(30, 110, recordLabelDim.width, recordLabelDim.height);
        reportInput.setBounds(30, 130, recordInputDim.width, recordInputDim.height);
        chronicButt.setBounds(30, 280, chronicButtDim.width, chronicButtDim.height);
        tempButt.setBounds(30 + chronicButtDim.width, 280, tempButtDim.width, tempButtDim.height);
        addMedButt.setBounds(30, 310, addMedButtDim.width, addMedButtDim.height);
        guideMessage.setBounds(30, 20, guideMessageDim.width +220, guideMessageDim.height);
        errorsuccessMessage.setBounds(30, 620, errorsuccessMessageDim.width + 400, errorsuccessMessageDim.height);

        //Adding components to the scrollable JPanel
        pane.add(errorsuccessMessage);
        pane.add(titleLabel);
        pane.add(titleInput);
        pane.add(reportLabel);
        pane.add(reportInput);
        pane.add(chronicButt);
        pane.add(tempButt);
        pane.add(addMedButt);
        pane.add(guideMessage);

    }

    //Adds a date input bundle with the three variables all together in an array of inputs
    private ArrayList<JComboBox> addDateInput(int posX, int posY){    //easier to keep three inputs in a bundle
        //Initialising Combo boxes
        JComboBox day = new JComboBox(days);
        JComboBox month = new JComboBox(months);
        JComboBox year = new JComboBox(years);
        ArrayList<JComboBox> date = new ArrayList<JComboBox>();

        //adding combo boxes
        date.add(day);
        date.add(month);
        date.add(year);

        //Getting preferred dimensions
        Dimension dayDim = day.getPreferredSize();
        Dimension monthDim = month.getPreferredSize();
        Dimension yearDim = year.getPreferredSize();

        //Setting bounds for new inputs created
        day.setBounds(posX, posY, dayDim.width, dayDim.height);
        month.setBounds(posX + dayDim.width, posY, monthDim.width, monthDim.height);
        year.setBounds(posX + 2*dayDim.width, posY, yearDim.width, yearDim.height);

        //Adding to main panel
        pane.add(day);
        pane.add(month);
        pane.add(year);

        return date;

    }

    //Hey guys! Use this function to add all the code to take the inputs to the server
    protected JSONObject gettingDataForServer(){
        //These are the days of the case record
        //recordDates.get(0).getSelectedItem().toString() = day; recordDates.get(1).getSelectedValue().toString() = month;
        // recordDates.get(2).getSelectedItem().toString();    ==> strings

        //This is the time:
        // hour = hourInput.getSelectedItem().toString() and minute = minuteInput.getSelectedItem().toString()

        //This is the case record
        //case record string = recordInput.getText();       ==> string

        //This is for the disease type
        //disease type = group.getSelection().getActionCommand();       ==>string

        //Number of medications = medicationNumber  ==>int

        //startDates is an "array of arrays". Each subcomponent is a "date" array
        //so to get the starting day of the second medication, the command would be startDates.get(1).get(0).getSelectedValue().toString()
        // the command to get the year of the third medication is startDates.get(2).get(2).getSelectedValue().toString(), and so on
        //        //endDates WORKS IN THE EXACT SAME WAY
        //these commands return strings

        //Medication names
        //medication1 = addMedInput.get(0).getText(), medication2 = addMedInput.get(1).getText()
        //medication3 = addMedInput.get(2).getText(), medication4 = addMedInput.get(3).getText()

        //getting data for casereport
        JSONObject casereport = new JSONObject();
        try {
            casereport.put("casenotes", reportInput.getText());
            casereport.put("patient_id", patientID);
            String choice = group.getSelection().getActionCommand();
            if (choice.equals("Chronic")){
                casereport.put("chronic_condition", true);
            }
            else{
                casereport.put("chronic_condition", false);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        //getting data for medications
        JSONArray medications = new JSONArray();
        try{
            for (int i = 0; i<medicationNumber; i++){
                //SQL timestamp format: '2016-06-22 19:10:25'
                JSONObject medication = new JSONObject();
                String startYear = startDates.get(i).get(2).getSelectedItem().toString();
                String startMonth = startDates.get(i).get(1).getSelectedItem().toString();
                String startDay = startDates.get(i).get(0).getSelectedItem().toString();
                String startDate = startYear + "-" + startMonth + "-" + startDay + " 00:00:00";
                String endYear = endDates.get(i).get(2).getSelectedItem().toString();
                String endMonth = endDates.get(i).get(1).getSelectedItem().toString();
                String endDay = endDates.get(i).get(0).getSelectedItem().toString();
                String endDate = endYear + "-" + endMonth + "-" + endDay + " 00:00:00";
                String type = addMedInput.get(i).getText();
                medication.put("starttime", startDate);
                medication.put("endtime", endDate);
                medication.put("type", type);
                medications.put(medication);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        //constructing custom json
        JSONObject data = new JSONObject();
        try {
            data.put("casereport", casereport);
            data.put("medications", medications);
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    //send data to server when submit button clicked
    private void submitCall() {
        errorsuccessMessage.setVisible(true);

            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JSONObject data = gettingDataForServer();
                    CustomJson instruction = new CustomJson("addCaseReport", data);
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


    //If patient accidently added a new medicine,
    private void removeMedication(){


    }

}
