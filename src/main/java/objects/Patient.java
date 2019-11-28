package objects;

import com.google.gson.Gson;

public class Patient {
    public String firstName;
    public String lastName;
    public String phoneNum;

    public Patient(String firstName, String lastName, String phoneNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
    }

    public Patient(){
        this.firstName = "";
        this.lastName = "";
        this.phoneNum = "";
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
