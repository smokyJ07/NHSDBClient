package menus;

import generalClasses.ourFrame;
import menus.adminActions.*;

import javax.swing.*;
import java.awt.*;

public class AdminMenu extends ourFrame {
    private JTabbedPane tabbedPane;
    private JPanel medicalCentrePanel;
    private JPanel patientPanel;
    private JPanel gpPanel;

    public AdminMenu(){
        //initialize frame
        this.setTitle("Admin Menu");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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

    private void createTabbedPane(){
        tabbedPane = new JTabbedPane();
        //three panes on the tabbed pane for adding either medical centres,
        //GPs or patients
        medicalCentrePanel = new MedicalCentreTab();  //Here we can add the new classes that extend from JPanel
        patientPanel = new PatientTab();
        //gpPanel = new JPanel();   original code
        gpPanel = new GPTab();      //new code
        tabbedPane.addTab("New Medical Centre", medicalCentrePanel);
        tabbedPane.addTab("New Patient", patientPanel);
        tabbedPane.addTab("New GP", gpPanel);
    }
}