package TP3_09;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class TP3_09 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int currentId = 9;

        try {
            String responseDelete = deleteById(currentId);
            String responseGetAfter = getDataById(currentId);
            System.out.printf("DELETE: %s", responseDelete);
            System.out.printf("GET: %s", responseGetAfter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDataById(int id) throws IOException, URISyntaxException {
        URL url = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");

        int status = conn.getResponseCode();
        InputStreamReader streamReader;
        System.out.println("GET Status: " + status);

        if (status == HttpURLConnection.HTTP_OK) {
            streamReader = new InputStreamReader(conn.getInputStream());
        } else {
            streamReader = new InputStreamReader(conn.getErrorStream());
        }

        BufferedReader in = new BufferedReader(streamReader);

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }

        if (status == HttpURLConnection.HTTP_NOT_FOUND) {
            return String.format("Entidade com ID %d não encontrada (Erro 404).", id);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String postData(String data, int id) throws IOException, URISyntaxException {
        URL url = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
            out.writeBytes(data);
            out.flush();
        }

        int status = conn.getResponseCode();
        InputStreamReader streamReader;

        streamReader = new InputStreamReader(conn.getInputStream());
        BufferedReader in = new BufferedReader(streamReader);

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String putData(String data, int id) throws IOException, URISyntaxException {
        URL url = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
            out.writeBytes(data);
            out.flush();
        }

        int status = conn.getResponseCode();
        InputStreamReader streamReader;

        streamReader = new InputStreamReader(conn.getInputStream());
        BufferedReader in = new BufferedReader(streamReader);

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String deleteById(int id) throws IOException, URISyntaxException {
        URL url = new URI("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");

        int status = conn.getResponseCode();
        InputStreamReader streamReader;
        System.out.println("DELETE STATUS: " + status);


        streamReader = new InputStreamReader(conn.getInputStream());

        BufferedReader in = new BufferedReader(streamReader);

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }


        in.close();
        conn.disconnect();

        return content.toString();
    }

}
