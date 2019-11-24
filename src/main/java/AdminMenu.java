import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {
    private JButton backButton;
    private JTabbedPane tabbedPane;
    private JPanel medicalCentrePanel;
    private JPanel patientPanel;
    private JPanel gpPanel;

    public AdminMenu(){
        //initialize frame
        this.setTitle("Admin Menu");
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        //button to get back to the main menu
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainMenu mainMenu = new MainMenu();
                dispose();
            }
        });
        this.getContentPane().add(backButton);

        //tabbed pane with all the actions
        tabbedPane = new JTabbedPane();
        //three panes on the tabbed pane for adding either medical centres,
        //GPs or patients
        medicalCentrePanel = new JPanel();
        patientPanel = new JPanel();
        gpPanel = new JPanel();
        tabbedPane.addTab("New Medical Centre", medicalCentrePanel);
        tabbedPane.addTab("New Patient", patientPanel);
        tabbedPane.addTab("New GP", gpPanel);
        this.getContentPane().add(tabbedPane);

        this.setVisible(true);

    }
}