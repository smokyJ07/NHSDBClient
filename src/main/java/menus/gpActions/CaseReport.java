package menus.gpActions;

import java.sql.Timestamp;
import java.util.Date;

public class CaseReport {


    public int patient_id;
    public int doctorid;
    public String casenotes;
    public String datetime;
    public boolean chronic_condition;

    public CaseReport(int patientid, int doctorid, String casenotes, boolean chronic_condition, String datetime){
        this.patient_id=patientid;
        this.doctorid=doctorid;
        this.casenotes=casenotes;
        this.chronic_condition=chronic_condition;
        this.datetime=datetime;
    }


}

