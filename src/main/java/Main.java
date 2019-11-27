//import drawingUI.DrawingUIController;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws Exception{
        //connect to servlet
        URL myURL = new URL("https://nhsdbservlet.herokuapp.com/patients");
        HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/html");
        conn.setRequestProperty("charset", "utf-8");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(myURL.openStream()));
        String inputLine;
        // Read the body of the response
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equals("Hello, World!")){
                System.out.println("Client successfully connected to server.");
            }
        }
        in.close();


        //open GUI
        GUI gui = new GUI();
        }
}
