package menus.gpActions;

import javax.swing.*;

public class addRecord extends JFrame {
    //Labels
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel emailLabel;
    private JLabel pagerNumLabel;
    private JLabel medicalCentreLabel;
    //Inputs
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField emailInput;
    private JTextField pagerNumInput;
    private JButton submit = new JButton("Submit");
    private JScrollPane spane = new JScrollPane();
    private JComboBox medicalCentreInput;

    public addRecord(){

        //initialize frame
        this.setTitle("Add case record");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

}
