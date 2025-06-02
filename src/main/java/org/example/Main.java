package org.example;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {

        URL url = new URI("https://apichallenges.eviltester.com/sim/entities").toURL();


    }
}