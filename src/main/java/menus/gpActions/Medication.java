package menus.gpActions;

public class Medication {
    public int casereportid;
    public String type;
    public String starttime;
    public String endtime;

    public Medication(int id,String type, String starttime, String endtime){
        this.casereportid = id;
        this.type = type;
        this.starttime=starttime;
        this.endtime=endtime;
    }
}
