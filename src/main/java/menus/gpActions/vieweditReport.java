package menus.gpActions;


//This class extends addReport as it has all the same interfaces, but only needs first to be initialised with relevant data


public class vieweditReport extends addReport {


    public vieweditReport(int idNum){

        super(idNum);

        //Small edits to change the context of the JFrame
        super.submit.setText("Apply Changes");
        super.guideMessage.setText("If needed, edit the information below and and click on 'Apply Changes'.");
        super.addMedButt.setVisible(false);
        int medCount = 3;  //number of medications added when case record was first submitted, 3 is just a random number to test it

        for(int i =0; i<medCount; i++){
            super.addMedButt.doClick(); //Ensures that the correct number of medications is automatically displayed by
            //virtually clicking the add medication button
        }

    }

    //Here you guys can call the methods to extract the data from the case report so that I can add it to the inputs.
    private void getCaseReportData(){


    }


    //Here I'm going to take the data variables you guys added to initialise the UI with the values of our case report
    private void initialiseInput(){


    }
}
