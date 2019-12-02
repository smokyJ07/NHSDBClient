package menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clientClasses.CustomJson;
import clientClasses.Request;
import jdk.tools.jaotc.collect.jar.JarSourceProvider;
import clientClasses.Request;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminMenu extends JFrame {
    private JMenuBar menuBar;
    private JTabbedPane tabbedPane;
    private JPanel medicalCentrePanel;
    private JPanel gpPanel;
    private JPanel patientPanel;

    public AdminMenu(){
        //initialize frame
        this.setTitle("Admin Menu");
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //menu for the admin (only back button now)
        createMenuBar();

        //tabbed pane with all the actions
        createTabbedPane();

        //create layout of main frame
        createMainLayout(tabbedPane);

        this.setVisible(true);
    }

    private void createMainLayout(JComponent... arg){
        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(1, 1);
        panel.setLayout(gl);
        panel.add(arg[0]);
        this.getContentPane().add(panel);
    }

    private void createMenuBar(){
        menuBar = new JMenuBar();
        JMenu general = new JMenu("General");
        JMenuItem backButton = new JMenuItem("Back to log in");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainMenu mainMenu = new MainMenu();
                dispose();
            }
        });

        general.add(backButton);
        menuBar.add(general);
        this.setJMenuBar(menuBar);
    }

    private void createTabbedPane(){
        tabbedPane = new JTabbedPane();
        //three panes on the tabbed pane for adding either medical centres,
        //GPs or patients
        medicalCentrePanel = new JPanel();

        patientPanel = new JPanel();
        setupPatientPanel();

        gpPanel = new JPanel();

        tabbedPane.addTab("New Medical Centre", medicalCentrePanel);
        tabbedPane.addTab("New Patient", patientPanel);
        tabbedPane.addTab("New GP", gpPanel);
    }

    private void setupPatientPanel(){
        patientPanel.setLayout(new GridLayout(4, 2));

        patientPanel.add(new JLabel("First name: "));
        JTextField firstNameField = new JTextField(15);
        patientPanel.add(firstNameField);

        patientPanel.add(new JLabel("Last name: "));
        JTextField lastNameField = new JTextField(15);
        patientPanel.add(lastNameField);

        patientPanel.add(new JLabel("Phone number: "));
        JTextField phoneNumField = new JTextField(15);
        patientPanel.add(phoneNumField);

        //submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //get info from the text fields when button clicked and save in patient class
                String firstName = firstNameField.getText();
                firstNameField.setText("");
                String lastName = lastNameField.getText();
                lastNameField.setText("");
                String phoneNum = phoneNumField.getText();
                phoneNumField.setText("");
                //p = new Patient(firstName,lastName,phoneNum);

                //create instruction json object containing data and function to execute by server
                JSONObject patient = new JSONObject();
                try {
                    patient.put("firstName", firstName);
                    patient.put("lastName", lastName);
                    patient.put("phoneNum", phoneNum);
                } catch(JSONException e){
                    System.out.println("error during patient json creation");
                }

                JSONObject data = new JSONObject();
                try{
                    data.put("patient", patient);
                }catch(JSONException e){
                    System.out.println("Error during json data body creation");
                }

                CustomJson instruction = new CustomJson("addPatient", data);
                String instruction_json_string = instruction.toString();
                Request post = new Request();
                post.makePostRequest(instruction_json_string);

            }
        });
        patientPanel.add(submitButton);

    }




}