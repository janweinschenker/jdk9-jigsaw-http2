package de.holisticon.jdk9showcase.http2client;

import java.net.URISyntaxException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.holisticon.jdk9showcase.http2client.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse.BodyHandler;

public class CancelClientExample {

	private static final Logger LOG = Logger.getLogger(CancelClientExample.class.getName());

	public static final Version VERSION = Version.HTTP_2;

	public static void main(String[] args) {
		CancelClientExample clientExample = new CancelClientExample();
		try {
            LOG.log(Level.INFO, "");
            LOG.log(Level.INFO, "");
            LOG.log(Level.INFO, "##################################################");

            clientExample.send();


        } catch (InterruptedException | ExecutionException | URISyntaxException e) {
            LOG.log(Level.INFO, e.getClass().getSimpleName());
        } catch (CancellationException e) {
            LOG.log(Level.INFO, "The request has been cancelled: " + e.getClass().getSimpleName());
            LOG.log(Level.INFO, "");
            LOG.log(Level.INFO, "");
            LOG.log(Level.INFO, "##################################################");
            System.exit(0);
        }
    }

	public String send() throws InterruptedException, ExecutionException, URISyntaxException {
		HttpClient client = ExampleUtils.createHttpClient(VERSION);
		HttpRequest request = ExampleUtils.createHttpRequest("http://www.holisticon.de", Version.HTTP_2);
		CompletableFuture<String> future = client.sendAsync(request, BodyHandler.asString()).thenApply(response -> response.body());

		Thread.sleep(10);
        String response;
		if (!future.isDone()) {
			future.cancel(true);
			LOG.info("Sorry, timeout!");
		} else {
            LOG.info("Request finished without timeout.");
        }
        response = future.get();
        LOG.info("Response: " + response);
		return response;
	}
}
