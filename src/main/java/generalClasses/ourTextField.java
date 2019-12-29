package generalClasses;

import javax.swing.*;

public class ourTextField extends JTextField {

    private String message = "something's not quite right";

    public ourTextField(int collumns){
        super(collumns);

    }

    //returns different value regarding the scenario detected
    public int checkInput(){        //returns the message to be added to the label
        String input = this.getText();
        int check = 1;
        int asciVal;
        //checking for appropriate characters
        for(int i=0; i<input.length(); i++){
            asciVal = (int)(input.charAt(i));
            /*System.out.println(check);
            System.out.println(input.charAt(i));*/
            if( ((asciVal<65)||(asciVal>90))&&((asciVal<97)||(asciVal>122)) ){// if not a letter
                /*System.out.println(check);
                System.out.println(asciVal);
                System.out.println(input.charAt(i));*/
                if(input.charAt(i) != ' '){ //if not a space
                    check = 0;
                    /*System.out.println(check);
                    System.out.println(asciVal);
                    System.out.println(input.charAt(i));*/
                }
            }
            //System.out.println("next");
        }

        if(input.length() == 0){    //checking if no input has been given
            message = "Please fill in all information before submitting.";
            return 0;
        }
        else if(check == 0){ //if not an appropriate character has been input
            message = "Please only valid characters.";
            return 1;
        }
        else {   //we gucci
            message = "Information submitted successfully!"; //solid effort
            return 2;
        }

    }

    public String getMessage(){
        return message;

    }

}
