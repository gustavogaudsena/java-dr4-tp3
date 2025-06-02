package TP3_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TP3_04 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            String response = getData();
            System.out.printf("Body: %s", response);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }

    }

    public static String getData() throws IOException, URISyntaxException {
        String rawName = "entity number 1";
        String encodedName = URLEncoder.encode(rawName, StandardCharsets.UTF_8);
        String finalURL = "https://apichallenges.eviltester.com/sim/entities?name=" + encodedName + "&limite=5";
        System.out.println("URL: " + finalURL);

        URL url = new URI(finalURL).toURL();

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
            return String.format("Entidade com ID %d não encontrada (Erro 404).");
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }
}
