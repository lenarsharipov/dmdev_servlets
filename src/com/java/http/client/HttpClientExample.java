package com.java.http.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpResponse<String> response;
        try (HttpClient httpClient = HttpClient.newBuilder()
                // если не передавать версию, то будет установлена версия 2
                .version(HttpClient.Version.HTTP_1_1)
                .build()) {
        /*
        Можно отправить запрос синхронно и асинхронно, получив CompletableFuture.
        CompletableFuture позволяет не останавливаться на выполнении нашего метода
        ждать его ответа, а получить какой-то объект, который получит какое-то значение в будущем.
        httpClient.sendAsync()
         */
            HttpRequest request = HttpRequest.newBuilder(URI.create("https://www.google.com"))
                    .GET()
                    .build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        System.out.println(response.headers());

        System.out.println("*".repeat(100));
        System.out.println(response.body());


    }
}
