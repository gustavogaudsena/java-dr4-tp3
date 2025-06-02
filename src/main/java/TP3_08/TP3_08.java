package TP3_08;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TP3_08 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int currentId = 10;
        String data = """
                {
                    "name": "atualizado"
                }
                """;
        try {
            String responseGet = getDataById(currentId);
            String responsePut = putData(data, currentId);
            System.out.printf("PUT: %s", responsePut);
            System.out.printf("GET: %s", responseGet);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
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
        System.out.println("Status: " + status);

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
}
