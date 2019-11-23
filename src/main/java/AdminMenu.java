import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {
    private JButton backButton;

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

        this.setVisible(true);

    }
}