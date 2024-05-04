package org.projectx.javafeatures.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * Old way using http url
 * new way using HttpClient, HttpRequest, HttpResponse
 * RestTemplate
 * WebClient - reactive - spring
 * RestClient
 * HttpInterfaces
 * FeignClient
 */
public class Client {

    private static final String URL = "https://jsonplaceholder.typicode.com/users";

    public static void getCall() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(URL))
                .timeout(Duration.ofSeconds(45))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public static void postCall() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(URL))
                .timeout(Duration.ofSeconds(45))
                .POST(HttpRequest.BodyPublishers.ofString("abc"))
                .setHeader("Content-Type", "application/json")
                .build();

        HttpClient.newHttpClient()
                   .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofInputStream())
                   .thenApply(res -> res.body())
                   .thenAccept(s -> {
                       try {
                           System.out.println(new String(s.readAllBytes()));
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
                   }).get();
    }


    public static void getCallOldWay() throws URISyntaxException, IOException, InterruptedException {
        URL url = new URL(URL);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == 200) {
            System.out.println(new String(httpURLConnection.getInputStream().readAllBytes()));
        } else {
            System.out.println("oops!!!");
        }
    }

    public static void postCallOldWay() throws URISyntaxException, IOException, InterruptedException {
        URL url = new URL(URL);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(45000);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");

        httpURLConnection.setDoOutput(true);
        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            outputStream.write("abc".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
        int responseCode = httpURLConnection.getResponseCode();

        System.out.println("Response code: " + responseCode);
        System.out.println("body " + new String(httpURLConnection.getInputStream().readAllBytes()));
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        //getCall();
        getCallOldWay();
    }
}
