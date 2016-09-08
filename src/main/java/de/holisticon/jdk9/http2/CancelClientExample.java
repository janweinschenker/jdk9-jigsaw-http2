package de.holisticon.jdk9.http2;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CancelClientExample {

	private static final Logger LOG = Logger.getLogger(CancelClientExample.class.getName());

	public static void main(String[] args) {
		CancelClientExample client = new CancelClientExample();
		try {
			client.send();
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			LOG.log(Level.SEVERE, e.getClass().getSimpleName());
		} catch (CancellationException e) {
			LOG.log(Level.SEVERE, "The request has been cancelled: " + e.getClass().getSimpleName());
			System.exit(0);
		}
	}

	public HttpResponse send() throws InterruptedException, ExecutionException, URISyntaxException {

		HttpRequest request = HttpRequest.create(new URI("http://www.holisticon.de")).body(HttpRequest.noBody())
				.version(Version.HTTP_2).GET();
		CompletableFuture<HttpResponse> future = request.responseAsync();

		Thread.sleep(10);
		if (!future.isDone()) {
			future.cancel(true);
			LOG.info("Sorry, timeout!");
		}
		LOG.info("Request finished without timeout.");
		HttpResponse response = future.get();
		LOG.info(response.uri().toASCIIString());
		return response;
	}
}
