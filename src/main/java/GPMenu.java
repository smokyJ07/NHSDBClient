import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GPMenu extends JFrame {
    private JMenuBar menuBar;
    private JScrollPane spane;
    private JLabel message;

    public GPMenu(){
        //initialize frame
        this.setTitle("GP Menu");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //button to go back to the Main Menu
        createMenuBar();

        //create patientList
        createPatientList();

        JLabel listLabel = new JLabel("Select patient: ");

        //creates the layout of the main frame
        createLayout( listLabel, spane, message);

        this.setVisible(true);
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

    private void createPatientList(){
        message = new JLabel();
        String[] patients = {"Alex", "Chloe", "Jonas"};
        JList patientList = new JList(patients);
        patientList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                message.setText((String)patientList.getSelectedValue());
            }
        });
        spane = new JScrollPane();
        spane.getViewport().add(patientList);
    }

    private void createLayout(JComponent... arg){
        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(2, 2);
        panel.setLayout(gl);

        panel.add(arg[0]);
        panel.add(arg[1]);
        panel.add(arg[2]);

        this.getContentPane().add(panel);
    }
}
