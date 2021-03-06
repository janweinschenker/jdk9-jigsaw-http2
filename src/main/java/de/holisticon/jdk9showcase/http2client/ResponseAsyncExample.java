package de.holisticon.jdk9showcase.http2client;

import de.holisticon.jdk9showcase.http2client.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author janweinschenker
 * @see HttpResponse
 * @see HttpRequest
 */
public class ResponseAsyncExample {

  private static final Logger LOG = Logger
      .getLogger(ResponseAsyncExample.class.getName());
  private static final Version VERSION = Version.HTTP_2;

  public static void main(String[] args) {

    try {
      LOG.log(Level.INFO,
          "##################################################");
      LOG.log(Level.INFO, "");
      ResponseAsyncExample example = new ResponseAsyncExample();
      example.startRequest();
      System.exit(0);
    } catch (URISyntaxException e) {
      LOG.log(Level.SEVERE, "URISyntaxException", e);
    } catch (InterruptedException | TimeoutException e) {
      LOG.log(Level.SEVERE, "InterruptedException", e);
    } catch (ExecutionException e) {
      LOG.log(Level.SEVERE, "ExecutionException", e);
    }

  }

  private void startRequest() throws URISyntaxException,
      InterruptedException, ExecutionException, TimeoutException {

    URI uri = new URI("https://localhost:8443/greeting?name=JavaLand");
    String msg = "Sending request to: " + uri.toString();
    LOG.log(Level.INFO, msg);

    HttpClient client = ExampleUtils
        .createHttpClient(VERSION);
    HttpRequest request = ExampleUtils.createHttpRequest(uri, VERSION);

    CompletableFuture<HttpResponse<String>> completableFuture = client
        .sendAsync(request, HttpResponse.BodyHandler.asString());
    LOG.log(Level.SEVERE, "request created");

    String result = completableFuture
        .thenApplyAsync(stringHttpResponse -> stringHttpResponse.body())
        .completeOnTimeout("a timout has occured", 300, TimeUnit.MILLISECONDS)
        .get();

    LOG.log(Level.INFO, result);

    //ExampleUtils.printResponse(result);
  }


}
