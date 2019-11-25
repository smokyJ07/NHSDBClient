import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {
    private JMenuBar menuBar;
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
        gpPanel = new JPanel();
        tabbedPane.addTab("New Medical Centre", medicalCentrePanel);
        tabbedPane.addTab("New Patient", patientPanel);
        tabbedPane.addTab("New GP", gpPanel);
    }
}