package de.holisticon.jdk9showcase.http2client;

import java.net.URISyntaxException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.holisticon.jdk9showcase.http2client.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse.BodyHandler;

public class CancelClientExample {

	private static final Logger LOG = Logger.getLogger(CancelClientExample.class.getName());

	public static void main(String[] args) {
		CancelClientExample clientExample = new CancelClientExample();
		try {
			clientExample.send();
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			LOG.log(Level.SEVERE, e.getClass().getSimpleName());
		} catch (CancellationException e) {
			LOG.log(Level.SEVERE, "The request has been cancelled: " + e.getClass().getSimpleName());
			System.exit(0);
		}
	}

	public String send() throws InterruptedException, ExecutionException, URISyntaxException {
		HttpClient client = ExampleUtils.createHttpClient();
		HttpRequest request = ExampleUtils.createHttpRequest("http://www.holisticon.de");
		CompletableFuture<String> future = client.sendAsync(request, BodyHandler.asString()).thenApply(response -> response.body());

		Thread.sleep(10);
		if (!future.isDone()) {
			future.cancel(true);
			LOG.info("Sorry, timeout!");
		}
		LOG.info("Request finished without timeout.");
		String response = future.get();
		LOG.info(response);
		return response;
	}
}
