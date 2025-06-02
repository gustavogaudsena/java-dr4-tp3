package TP3_12;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class TP3_12 {
    public static void main(String[] args) throws IOException, URISyntaxException {

        try {
            String urlBase = "https://apichallenges.eviltester.com/simpleapi/";
            String urlItems = "https://apichallenges.eviltester.com/simpleapi/items";
            String urlISBN = "https://apichallenges.eviltester.com/simpleapi/randomisbn";
            String responseA = getData(urlItems);
            System.out.printf("RESPOSTA A: %s\n", responseA);

            String responseB = getData(urlISBN);
            System.out.printf("RESPOSTA B: %s\n", responseB);


            String data = String.format("""
                    {
                        "type": "dvd",
                         "isbn13": "%s",
                         "price": 97.99,
                         "numberinstock": 0
                    }
                    """, responseB);

            String responseC = postData(data, urlItems);
            System.out.printf("RESPOSTA C: %s\n", responseC);

            String dataPut = String.format("""
                    {
                        "type": "cd",
                         "isbn13": "%s",
                         "price": 97.99,
                         "numberinstock": 0
                    }
                    """, responseB);
            Gson gson = new Gson();
            ResponseSimpleApi responseObj = gson.fromJson(responseC, ResponseSimpleApi.class);
            int id = responseObj.getId();
            String responseD = putData(dataPut, urlItems + "/" + id);
            System.out.printf("RESPOSTA D: %s\n", responseD);


            String responseE = deleteData(urlItems + "/" + id);
            System.out.printf("RESPOSTA E: %s\n", responseE);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getData(String urlStr) throws IOException, URISyntaxException {
        URL url = new URI(urlStr).toURL();

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
            content.append(inputLine);
        }

        if (status == HttpURLConnection.HTTP_NOT_FOUND) {
            return String.format("não encontrada (Erro 404)");
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String getDataById(int id) throws IOException, URISyntaxException {
        URL url = new URI(" https://apichallenges.eviltester.com/simpleapi/items" + id).toURL();

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
            content.append(inputLine);
        }

        if (status == HttpURLConnection.HTTP_NOT_FOUND) {
            return String.format("Entidade com ID %d não encontrada (Erro 404).", id);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String postData(String data, String urlStr) throws IOException, URISyntaxException {
        URL url = new URI(urlStr).toURL();
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
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String putData(String data, String urlStr) throws IOException, URISyntaxException {
        URL url = new URI(urlStr).toURL();
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
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    public static String deleteData(String urlStr) throws IOException, URISyntaxException {
        URL url = new URI(urlStr).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");

        int status = conn.getResponseCode();
        InputStreamReader streamReader;


        System.out.println("DELETE STATUS: " + status);


        if (status == HttpURLConnection.HTTP_NO_CONTENT || status == HttpURLConnection.HTTP_OK) {
            streamReader = new InputStreamReader(conn.getInputStream());
        } else {
            streamReader = new InputStreamReader(conn.getErrorStream());
        }
        BufferedReader in = new BufferedReader(streamReader);

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }


        in.close();
        conn.disconnect();

        if (status == HttpURLConnection.HTTP_FORBIDDEN || status == HttpURLConnection.HTTP_BAD_METHOD) {
            return String.format("Entidade não pode ser deletada");
        }
        return content.toString();
    }

    public static String getOptions() throws IOException, URISyntaxException {
        URL url = new URI(" https://apichallenges.eviltester.com/simpleapi/items").toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("OPTIONS");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");

        int status = conn.getResponseCode();
        InputStreamReader streamReader;
        System.out.println("OPTIONS Status: " + status);
        streamReader = new InputStreamReader(conn.getInputStream());
        BufferedReader in = new BufferedReader(streamReader);

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

}
