package TP3_05;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TP3_05 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String data = """
                {"name": "aluno"}
                """;
        try {
            String response = postData(data);
            System.out.printf("Body: %s", response);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }

    }

    public static String postData(String data) throws IOException, URISyntaxException {
        URL url = new URI("https://apichallenges.eviltester.com/sim/entities").toURL();
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
}
