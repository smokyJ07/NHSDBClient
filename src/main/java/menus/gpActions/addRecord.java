package menus.gpActions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class addRecord extends JFrame {
    //Labels
    private JLabel dateLabel = new JLabel("Date: ");
    private JLabel timeLabel = new JLabel("Time: ");
    private JLabel recordLabel = new JLabel("Case Record: ");
    //Inputs
    private JTextField timeInput = new JTextField(30);
    private JTextArea recordInput = new JTextArea(8, 45);
    private JRadioButton chronicButt = new JRadioButton("Chronic");
    private JRadioButton tempButt = new JRadioButton("Temporary");
    private JButton addMedButt = new JButton("Add new medication");
    private JButton submit = new JButton("Submit");
    //ArrayList of added components related to the medicine(s) added
    private ArrayList<JTextField> addMedInput = new ArrayList<JTextField>();    //contains medication inputs
    private ArrayList<JLabel> addMedLabel = new ArrayList<JLabel>();            //contains medication input labels
    private ArrayList<ArrayList<JComboBox>> startDates = new ArrayList<ArrayList<JComboBox>>();
    private ArrayList<ArrayList<JComboBox>> endDates = new ArrayList<ArrayList<JComboBox>>();   //an array list with each entry having a input date 'bundle'
    private ArrayList<JLabel> startLabel = new ArrayList<JLabel>();
    private ArrayList<JLabel> endLabel = new ArrayList<JLabel>();
    //Date variables
    private String[] days = new String[31];     //string type used so it can be the input of JCombo boxes
    private String[] months = new String[12];
    private String[] years = new String[24];
    private ArrayList<JComboBox> recordDate = new ArrayList<JComboBox>();
    //Other important fields
    private JPanel pane = new JPanel();
    private int medicationNumber = 0;

    public addRecord(){

        //initialize frame
        this.setTitle("New case record");
        this.setSize(600, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

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
        createLayout();
        addingNewMedicine();
        this.getContentPane().add(pane);
    }

    //Called if add new medicine button is called
    private void addingNewMedicine(){
        //'Add medicine' button
        addMedButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { //adds new inputs lower and lower
                //adding new inputs
                startLabel.add(new JLabel("Start date: "));
                Dimension startLabelDim = startLabel.get(medicationNumber).getPreferredSize();
                startLabel.get(medicationNumber).setBounds(30, 370 + medicationNumber*100, startLabelDim.width, startLabelDim.height);
                startDates.add(addDateInput(30 + startLabelDim.width, 370 + medicationNumber*100));

                endLabel.add(new JLabel("End date: "));
                Dimension endLabelDim = endLabel.get(medicationNumber).getPreferredSize();
                endLabel.get(medicationNumber).setBounds(30, 400 + medicationNumber*100, endLabelDim.width, endLabelDim.height);
                endDates.add(addDateInput(30 + endLabelDim.width,400 + medicationNumber*100));

                addMedLabel.add(new JLabel("Medication name: "));
                addMedInput.add(new JTextField());
                Dimension addMedLabelDim = addMedLabel.get(medicationNumber).getPreferredSize();
                Dimension addMedInputDim = addMedInput.get(medicationNumber).getPreferredSize();
                addMedLabel.get(medicationNumber).setBounds(30, 340 + medicationNumber*100, addMedLabelDim.width, addMedLabelDim.height);
                addMedInput.get(medicationNumber).setBounds(30 + addMedLabelDim.width, 340 + medicationNumber * 100, 100, addMedInputDim.height);

                Dimension submitDim = submit.getPreferredSize();
                submit.setBounds(30, 430 + medicationNumber*100 , submitDim.width, submitDim.height);

                //adding new inputs to main panel
                pane.add(startLabel.get(medicationNumber));
                pane.add(endLabel.get(medicationNumber));
                pane.add(addMedLabel.get(medicationNumber));
                pane.add(addMedInput.get(medicationNumber));
                pane.add(submit);

                medicationNumber = medicationNumber + 1;

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
        Dimension timeInputDim = timeInput.getPreferredSize();
        Dimension recordLabelDim = recordLabel.getPreferredSize();
        Dimension recordInputDim = recordInput.getPreferredSize();
        Dimension chronicButtDim = chronicButt.getPreferredSize();
        Dimension tempButtDim = tempButt.getPreferredSize();
        Dimension addMedButtDim = addMedButt.getPreferredSize();

        //Setting bounds for the layout
        dateLabel.setBounds(30, 50, dateLabelDim.width, dateLabelDim.height);
        timeLabel.setBounds(30, 80, timeLabelDim.width, timeLabelDim.height);
        timeInput.setBounds(30 + timeLabelDim.width,76,  timeInputDim.width, timeInputDim.height);
        recordLabel.setBounds(30, 110, recordLabelDim.width, recordLabelDim.height);
        recordInput.setBounds(30, 130, recordInputDim.width, recordInputDim.height);
        chronicButt.setBounds(30, 280, chronicButtDim.width, chronicButtDim.height);
        tempButt.setBounds(30 + chronicButtDim.width, 280, tempButtDim.width, tempButtDim.height);
        addMedButt.setBounds(30, 310, addMedButtDim.width, addMedButtDim.height);

        //Adding main date input
        addDateInput(30 + dateLabelDim.width, 46);

        //Adding components to the scrollable JPanel
        pane.add(dateLabel);
        pane.add(timeLabel);
        pane.add(timeInput);
        pane.add(recordLabel);
        pane.add(recordInput);
        pane.add(chronicButt);
        pane.add(tempButt);
        pane.add(addMedButt);

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

    //Used so that new inputs appear if more than one medicine is prescribed
    private void something (){     //takes in panel to add to and returns distances for boundary setting
        //Adding the submit button after medication inputs
        Dimension submitDim = submit.getPreferredSize();
        submit.setBounds(30, 430, submitDim.width, submitDim.height);
        pane.add(submit);
    }

    //If patient accidently added a new medicine,
    private void removeMedication(){


    }

}
