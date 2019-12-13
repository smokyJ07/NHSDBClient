package menus.gpActions;

import java.util.ArrayList;

//class that contains all the casereports and medication info of one patient
public class PatientReports {
    public int patientID;
    public ArrayList<CaseReport> caseReports;
    public ArrayList<Medication> medications;

    public  PatientReports(int patientIDin){
        patientID = patientIDin;
    }
}
