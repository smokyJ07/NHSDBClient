package clientClasses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/*CLASS DESCRIPTION: This class deals with the connection to the server. It has two methods, one for making a get request
* and one for making a post request. JSON data (in form of a CustomJson) is transmitted as strings back and forth to let servlet and client
* communicate.*/

public class Request {
    public void Request() throws Exception {}

    public String makeGetRequest(String jsonString) {
        String response = "";
        try {
            System.out.println(jsonString);
            // Set up the body data
            byte[] body = jsonString.getBytes(StandardCharsets.UTF_8);
            URL myURL = new URL("http://localhost:8080/NHSDBServlet/patients");
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) myURL.openConnection();
            // Set up the header
            conn.setRequestMethod("GET");
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
                response += inputLine;
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            System.out.println("Something went wrong with the connection!");
            e.printStackTrace();
        }
        return response;
    }

    public String makePostRequest(String jsonString) {
        String response = "";
        try {
            System.out.println(jsonString);
            // Set up the body data
            byte[] body = jsonString.getBytes(StandardCharsets.UTF_8);
            //online URL: https://nhsdbservlet.herokuapp.com/patients
            //local URL:http://localhost:8080/NHSDBServlet/patients
            URL myURL = new URL("https://nhsdbservlet.herokuapp.com/patients");
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
                response += inputLine;
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            System.out.println("Something went wrong with the connection!");
            e.printStackTrace();
        }
        System.out.println("Response: " + response);
        return response;
    }
}
