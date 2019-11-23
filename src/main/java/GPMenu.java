import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GPMenu extends JFrame {
    private JButton backButton;

    public GPMenu(){
        //initialize frame
        this.setTitle("GP Menu");
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //button to go back to the Main Menu
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainMenu mainMenu = new MainMenu();
                dispose();
            }
        });
        this.getContentPane().add(backButton);

        this.setVisible(true);
    }
}
