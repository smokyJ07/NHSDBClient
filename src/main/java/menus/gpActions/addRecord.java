package menus.gpActions;

import menus.ourFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

public class addRecord extends ourFrame {
    //Labels
    private JLabel dateLabel = new JLabel("Date: ");
    private JLabel timeLabel = new JLabel("Time: ");
    private JLabel recordLabel = new JLabel("Case Record: ");
    private JLabel day = new JLabel("Day");
    private JLabel month = new JLabel("Month");
    private JLabel year = new JLabel("Year");
    private JLabel hour = new JLabel("Hour");
    private JLabel minute = new JLabel("Minute");
    //Inputs
    private JComboBox hourInput;
    private JComboBox minuteInput;
    private JTextArea recordInput = new JTextArea(8, 45);
    private JRadioButton chronicButt = new JRadioButton("Chronic");
    private JRadioButton tempButt = new JRadioButton("Temporary");
    private ButtonGroup group = new ButtonGroup();
    private JButton addMedButt = new JButton("Add new medication");
    private JButton submit = new JButton("Submit");
    //ArrayList of added components related to the medicine(s) added
    private ArrayList<JTextField> addMedInput = new ArrayList<JTextField>();    //contains medication inputs
    private ArrayList<JLabel> addMedLabel = new ArrayList<JLabel>();            //contains medication input labels
    private ArrayList<ArrayList<JComboBox>> startDates = new ArrayList<ArrayList<JComboBox>>();
    private ArrayList<ArrayList<JComboBox>> endDates = new ArrayList<ArrayList<JComboBox>>();   //an array list with each entry having a input date 'bundle'
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

    public addRecord(){

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

        //Initialising time variables
        for(int i = 0; i<=59; i++){

            //conditional statements to add 0 before single digits
            if(i<10){
                if(i<24){
                    hours[i] = '0' + Integer.toString(i);
                }
                minutes[i] = '0' + Integer.toString(i);

            }
            else{
                if(i<24) {
                    hours[i] = Integer.toString(i);
                }
                minutes[i] = Integer.toString(i);
            }
        }
        hourInput = new JComboBox(hours);
        minuteInput = new JComboBox(minutes);

        //Creating the main layout of the window in the main panel
        createLayout();
        addingNewMedicine();
        this.getContentPane().add(pane);
        gettingDataForServer();
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
        Dimension dateLabelDim = dateLabel.getPreferredSize();
        Dimension timeLabelDim = timeLabel.getPreferredSize();
        Dimension hourInputDim = hourInput.getPreferredSize();
        Dimension minuteInputDim = minuteInput.getPreferredSize();
        Dimension recordLabelDim = recordLabel.getPreferredSize();
        Dimension recordInputDim = recordInput.getPreferredSize();
        Dimension chronicButtDim = chronicButt.getPreferredSize();
        Dimension tempButtDim = tempButt.getPreferredSize();
        Dimension addMedButtDim = addMedButt.getPreferredSize();
        Dimension dayDim = day.getPreferredSize();
        Dimension monthDim = month.getPreferredSize();
        Dimension yearDim = year.getPreferredSize();
        Dimension hourDim = hour.getPreferredSize();
        Dimension minuteDim = minute.getPreferredSize();

        //Setting bounds for the layout
        dateLabel.setBounds(30, 50, dateLabelDim.width, dateLabelDim.height);
        timeLabel.setBounds(350, 50, timeLabelDim.width, timeLabelDim.height);
        hourInput.setBounds(350 + timeLabelDim.width,46,  hourInputDim.width, hourInputDim.height);
        hour.setBounds(360 + timeLabelDim.width, 20, hourDim.width, hourDim.height);
        minuteInput.setBounds(350 + timeLabelDim.width + hourInputDim.width, 46, minuteInputDim.width, minuteInputDim.height);
        minute.setBounds(350 + timeLabelDim.width + 85, 20, minuteDim.width, minuteDim.height);
        recordLabel.setBounds(30, 110, recordLabelDim.width, recordLabelDim.height);
        recordInput.setBounds(30, 130, recordInputDim.width, recordInputDim.height);
        chronicButt.setBounds(30, 280, chronicButtDim.width, chronicButtDim.height);
        tempButt.setBounds(30 + chronicButtDim.width, 280, tempButtDim.width, tempButtDim.height);
        addMedButt.setBounds(30, 310, addMedButtDim.width, addMedButtDim.height);


        //Adding main date input
        addDateInput(30 + dateLabelDim.width, 46);
        day.setBounds(40 + dateLabelDim.width, 20, dayDim.width, dayDim.height);
        month.setBounds(40 + dateLabelDim.width + 70, 20, monthDim.width, monthDim.height);
        year.setBounds(40 + dateLabelDim.width + 160, 20, yearDim.width, yearDim.height);


        //Adding components to the scrollable JPanel
        pane.add(dateLabel);
        pane.add(timeLabel);
        pane.add(hourInput);
        pane.add(minuteInput);
        pane.add(recordLabel);
        pane.add(recordInput);
        pane.add(chronicButt);
        pane.add(tempButt);
        pane.add(addMedButt);
        pane.add(day);
        pane.add(month);
        pane.add(year);
        pane.add(minute);
        pane.add(hour);

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
    private void gettingDataForServer(){
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
        //so to get the starting day of the second medication, the command would be startDates.get(1).get(0)
        // the command to get the year of the third medication is startDates.get(2).get(2), and so on
        //endDates WORKS IN THE EXACT SAME WAY
        //these commands return strings

        //Medication names
        //medication1 = addMedInput.get(0).getText(), medication2 = addMedInput.get(1).getText()
        //medication3 = addMedInput.get(2).getText(), medication4 = addMedInput.get(3).getText()

    }

    //If patient accidently added a new medicine,
    private void removeMedication(){


    }

}