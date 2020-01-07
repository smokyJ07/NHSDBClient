package menus;

import menus.MainMenu;

import java.awt.*;

/*CLASS DESCRIPTION: This class simply acts to create a "MainMenu" object in our main class */

public class GUI {
    private MainMenu mainMenu;
    static GraphicsConfiguration gc; // Class field containing config info

    public GUI(){
        //create main menu
        mainMenu = new MainMenu();


    }

}
