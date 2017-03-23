package de.holisticon.jdk9showcase.http2client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.holisticon.jdk9showcase.http2client.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

/**
 * 
 * 
 * @author janweinschenker
 *
 * @see HttpResponse
 * @see HttpRequest
 */
public class ResponseAsyncExample {

	private static final Logger LOG = Logger.getLogger(ResponseAsyncExample.class.getName());

	public static void main(String[] args) {

		try {
			startRequest();
		} catch (URISyntaxException e) {
			LOG.log(Level.SEVERE, "URISyntaxException", e);
		} catch (InterruptedException e) {
			LOG.log(Level.SEVERE, "InterruptedException", e);
		} catch (ExecutionException e) {
			LOG.log(Level.SEVERE, "ExecutionException", e);
		}

	}

	private static void startRequest() throws URISyntaxException, InterruptedException, ExecutionException {
		HttpClient client = ExampleUtils.createHttpClient(Version.HTTP_2);
		URI uri = new URI("https://localhost:8443/greeting?name=JavaLand");
		// URI uri = new URI("https://www.example.com/#/");

		HttpRequest request = HttpRequest.newBuilder().uri(uri).version(HttpClient.Version.HTTP_2).GET()
				// .DELETE()
				// .POST(body)
				// .PUT(body)
				// .timeout(Duration.ofSeconds(30))
				// .expectContinue(true)
				.build();

		HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandler.asString()).get();

		ExampleUtils.printResponse(response);
	}

}
