package clientClasses;

import com.google.gson.Gson;
import objects.Patient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Request {
    public void Request() throws Exception {

    }

    public void makeGetRequest() throws Exception{
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
    }

    public void makePostRequest(String jsonString) {
        try {
            System.out.println(jsonString);
            // Set up the body data
            byte[] body = jsonString.getBytes(StandardCharsets.UTF_8);
            URL myURL = new URL("http://localhost:8080/FirstServlet/patients");
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) myURL.openConnection();
            // Set up the header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "text/html");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(body.length));
            conn.setDoOutput(true);
            // Write the body of the request
            try (OutputStream outputStream = conn.getOutputStream()) {
                outputStream.write(body, 0, body.length);
            }

            // Read the body of the response
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream(), "utf-8"));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                System.out.println(inputLine);
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            System.out.println("Something went wrong with the connection!");
        }
    }
}
