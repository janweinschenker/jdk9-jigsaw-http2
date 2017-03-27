package de.holisticon.jdk9showcase.http2client;

import de.holisticon.jdk9showcase.http2client.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;
import jdk.incubator.http.HttpResponse.MultiProcessor;
import jdk.incubator.http.MultiMapResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author janweinschenker
 * @see HttpResponse
 * @see HttpRequest
 */
public class ResponseAsyncMultiExample {

    private static final Logger LOG = Logger
            .getLogger(ResponseAsyncMultiExample.class.getName());

    public static void main(String[] args) {

        try {
            // URI uri = new URI("https://www.google.de/");
            // URI uri = new URI("https://www.smashingmagazine.com/");
            // URI uri = new URI("https://de.wikipedia.org/");
            // URI uri = new URI("https://localhost:8443/greeting?name=Javaland");
            // URI uri = new URI("https://blog.cloudflare.com/");
            // URI uri = new URI("https://www.example.com/#/");
            LOG.log(Level.INFO, "");
            LOG.log(Level.INFO, "");
            LOG.log(Level.INFO,
                    "##################################################");
            LOG.log(Level.INFO,
                    "The following resources were pushed by the server:");
            ResponseAsyncMultiExample example = new ResponseAsyncMultiExample();
            example.startRequest();
            System.exit(0);
        } catch (URISyntaxException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see MultiProcessor#asMap(java.util.function.Function)
     */
    private void startRequest() throws URISyntaxException, InterruptedException,
            ExecutionException {

        URI uri = new URI(
                "https://blog.cloudflare.com/announcing-support-for-http-2-server-push-2/");
        HttpRequest request = ExampleUtils
                .createHttpRequest(uri, Version.HTTP_2);
        HttpClient client = ExampleUtils.createHttpClient(Version.HTTP_2);

        CompletableFuture<MultiMapResult<String>> sendAsync = client
                .sendAsync(request, MultiProcessor.asMap((req) -> {
                    Optional<BodyHandler<String>> optional = Optional
                            .of(HttpResponse.BodyHandler.asString());
                    String msg = " - " + req.uri();
                    LOG.log(Level.INFO, msg);
                    return optional;
                }, true)).orTimeout(30, TimeUnit.SECONDS);

        int numberOfDependents = sendAsync.getNumberOfDependents();
        LOG.log(Level.INFO, "Number of dependents: " + numberOfDependents);

        Map<HttpRequest, CompletableFuture<HttpResponse<String>>> multiMapResult = sendAsync
                .join();

        if (multiMapResult != null) {
            for (HttpRequest key : multiMapResult.keySet()) {
                CompletableFuture<HttpResponse<String>> completableFuture = multiMapResult
                        .get(key);
                HttpResponse<String> response = completableFuture.get();
                ExampleUtils.printResponse(response);
            }
        }
        System.exit(0);
    }

}
