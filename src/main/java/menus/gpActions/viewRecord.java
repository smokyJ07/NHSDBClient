package menus.gpActions;

import javax.swing.*;

public class viewRecord extends JFrame {

    private JLabel label = new JLabel();

    public viewRecord(){
        //initialize frame
        this.setTitle("View case records");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
