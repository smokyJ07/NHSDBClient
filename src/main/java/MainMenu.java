import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private JButton gpLogin;
    private JButton adminLogin;
    private JButton quitButton;

    public MainMenu(){
        initMainMenu();
    }

    private void initMainMenu(){
        //initialize frame
        this.setTitle("Main Menu");
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create contents of the main menu panel and display them on frame
        JPanel mainMenuPanel = new JPanel();

        //button to the GP menu
        gpLogin = new JButton("Login as GP");
        gpLogin.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                  GPMenu gpMenu = new GPMenu();
                  dispose();
              }
          });
        mainMenuPanel.add(gpLogin);

        //button to the Admin menu
        adminLogin = new JButton("Login as admin");
        adminLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AdminMenu adminMenu = new AdminMenu();
                dispose();
            }
        });
        mainMenuPanel.add(adminLogin);

        //quit button
        quitButton = new JButton("Quit");
        quitButton.addActionListener((event) -> System.exit(0));
        mainMenuPanel.add(quitButton);

        this.getContentPane().add(mainMenuPanel);
        this.setVisible(true);
    }
}
