package com.rob.restweatherapifunction;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<SNSEvent, Void> {

    public Void handleRequest(final SNSEvent snsEvent, final Context context) {
        context.getLogger().log(snsEvent.getRecords().get(0).getSNS().getMessage());

        try {

            ExecutorService executor = Executors.newFixedThreadPool(3);

            HttpClient httpClient = HttpClient.newBuilder()
                    .executor(executor)  // custom executor
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://m97rr6-8080.preview.csb.app/?country=uk"))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .build();
            CompletableFuture<Void> voidCompletableFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        System.out.println("Thread is: " + Thread.currentThread().getName());
                        // do something when the response is received
                    });
            CompletableFuture<Void> voidCompletableFuture2 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        System.out.println("Thread is: " + Thread.currentThread().getName());
                        // do something when the response is received
                    });
            CompletableFuture<Void> voidCompletableFuture3 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        System.out.println("Thread is: " + Thread.currentThread().getName());
                        // do something when the response is received
                    });

            List<CompletableFuture<Void>> completableFutures = Arrays.asList(voidCompletableFuture, voidCompletableFuture2, voidCompletableFuture3);

            completableFutures.forEach(CompletableFuture::join);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return null;

    }

}
