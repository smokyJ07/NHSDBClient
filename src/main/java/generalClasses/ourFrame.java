package generalClasses;


import menus.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//All JFrames used in the GUI will follow the format of the ourFrame class
//This will prevent me from having to repeat the same code for every new frame
public class ourFrame extends JFrame {

    protected JMenuBar menuBar;
    protected JMenu navigation;

    public ourFrame(){
        //Initialises every one of these frame types
        this.setResizable(false);
        createMenuBar();

    }

    private void createMenuBar(){
        menuBar = new JMenuBar();
        JMenu unison = new JMenu("UnIson");

        JMenuItem quitButton = new JMenuItem("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        unison.add(quitButton);
        menuBar.add(unison);

        //adding navigation JMenu
        navigation = new JMenu("Navigation");

        JMenuItem backButton = new JMenuItem("Back to log in");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainMenu mainMenu = new MainMenu();
                dispose();
            }
        });

        navigation.add(backButton);
        menuBar.add(navigation);
        this.setJMenuBar(menuBar);
    }

}
