package TP3_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class TP3_03 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int currentId = 13;
        try {
            String response = getDataById(currentId);
            System.out.printf("Id: %d\nBody: %s", currentId, response);
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
}
