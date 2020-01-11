package menus;

import generalClasses.ourFrame;
import menus.adminActions.*;

import javax.swing.*;
import java.awt.*;

/*CLASS DESCRIPTION: This class extends from the customized "ourFrame" class. It provides an appropriate container
* for the different actions an admin can take (adding patients, medical centres and GPs, which can be navigated via different tabs.  */

public class AdminMenu extends ourFrame {
    private JTabbedPane tabbedPane;
    private JPanel medicalCentrePanel;
    private JPanel patientPanel;
    private JPanel gpPanel;
    private JPanel gpSearchPanel;

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

    //Creates the frame layout and adds its one element (one tabbed panel)
    private void createMainLayout(JComponent... arg){
        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(1, 1);
        panel.setLayout(gl);
        panel.add(arg[0]);
        this.getContentPane().add(panel);
    }

    //Creates the tabbed panel and adds appropriate elements to each tab
    private void createTabbedPane(){
        tabbedPane = new JTabbedPane();
        //three panes on the tabbed pane for adding either medical centres,
        //GPs or patients
        medicalCentrePanel = new MedicalCentreTab();  //Here we can add the new classes that extend from JPanel
        patientPanel = new PatientTab();
        gpSearchPanel = new GPSearchTab();
        gpPanel = new GPTab();      //new code
        tabbedPane.addTab("New Medical Centre", medicalCentrePanel);
        tabbedPane.addTab("New Patient", patientPanel);
        tabbedPane.addTab("New GP", gpPanel);
        tabbedPane.addTab("GP Search", gpSearchPanel);
    }
}