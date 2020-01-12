package menus.gpActions;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class currentMedicines extends JFrame {

    //data
    JList<String> medicationList;
    JScrollPane spane;
    JLabel guideMessage = new JLabel("Patient currently takes the following medications: ");

    public currentMedicines(Vector<String> medicines, Vector<String> startDates, Vector<String> endDates){
        //setup window
        this.setSize(600, 250);
        this.setTitle("Current Medications");
        this.setVisible(true);

        //set the location of the window
        //Source: https://stackoverflow.com/questions/3480102/java-jframe-setlocationrelativetonull-not-centering-the-window-on-ubuntu-10-0
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x + (this.getWidth()/3),
                middle.y - (this.getHeight() / 2));
        this.setLocation(newLocation);

        //setup data for display
        Vector<String> medications = new Vector();
        for (int i = 0, size = medicines.size(); i<size; i++){
            medications.add("Medication: " + medicines.get(i) + "\t Started: " + startDates.get(i) + "\t Ending: " + endDates.get(i));
        }
        medicationList = new JList(medications);
        spane = new JScrollPane();
        spane.getViewport().add(medicationList);

        //setup layout
        JPanel pane = new JPanel();
        pane.setLayout(null);
        //getting appropriate dimensions for various list items
        Dimension guideMessageDim = guideMessage.getPreferredSize();
        Dimension spaneDim = spane.getPreferredSize();
        //setting the bounds for each JComponent
        guideMessage.setBounds(30, 20, guideMessageDim.width, guideMessageDim.height);
        spane.setBounds(30, 50, spaneDim.width, spaneDim.height);
        //Adding components to main panel to be added to frame
        pane.add(guideMessage);
        pane.add(spane);
        this.setContentPane(pane);
    }
}
