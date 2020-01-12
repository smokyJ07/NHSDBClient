package generalClasses;


import menus.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*CLASS DESCRIPTION: This class extends from the class JFrame. It is essentially a common JFrame,
* except it includes our standard menu bar. Since the layout was done without a layout manager,
* these frames are not resizable, which is ensured through this class. */


public class ourFrame extends JFrame {

    protected JMenuBar menuBar;
    protected JMenu navigation;

    public ourFrame(){
        //Initialises every one of these frame types
        this.setResizable(false);
        createMenuBar();
    }

    //Creates the standard menu bar for the frames in our GUI with functional options
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
