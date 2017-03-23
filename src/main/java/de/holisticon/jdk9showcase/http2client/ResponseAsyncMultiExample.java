package de.holisticon.jdk9showcase.http2client;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import de.holisticon.jdk9showcase.http2client.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;
import jdk.incubator.http.HttpResponse.MultiProcessor;
import jdk.incubator.http.MultiMapResult;

/**
 * 
 * 
 * @author janweinschenker
 *
 * @see HttpResponse
 * @see HttpRequest
 */
public class ResponseAsyncMultiExample {

	public static void main(String[] args) {

		try {
			startRequest();

		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static void startRequest() throws URISyntaxException, InterruptedException, ExecutionException {
		// URI uri = new URI("https://www.google.de/");
		// URI uri = new URI("https://www.smashingmagazine.com/");
		// URI uri = new URI("https://de.wikipedia.org/");
		// URI uri = new URI("https://localhost:8443/greeting?name=Javaland");
		// URI uri = new URI("https://blog.cloudflare.com/");
		// URI uri = new URI("https://www.example.com/#/");
		System.out.println();
		System.out.println();
		System.out.println("##################################################");
		System.out.println("The following resources were pushed by the server:");
		
		
		URI uri = new URI("https://blog.cloudflare.com/announcing-support-for-http-2-server-push-2/");
		HttpRequest request = ExampleUtils.createHttpRequest(uri);
		HttpClient client = ExampleUtils.createHttpClient(Version.HTTP_2);
		
		MultiMapResult<String> multiMapResult = client.sendAsync(request, MultiProcessor.asMap((req) -> {
					Optional<BodyHandler<String>> optional = Optional.of(HttpResponse.BodyHandler.asString());
					//if (optional.isPresent()) {
						System.out.println(" - " + req.uri());
					//}
					return optional;
				}, false))
				
				.orTimeout(2, TimeUnit.SECONDS)
				.join();

		if (multiMapResult != null) {
			for (HttpRequest key : multiMapResult.keySet()) {
				CompletableFuture<HttpResponse<String>> completableFuture = multiMapResult.get(key);
				HttpResponse<String> response = completableFuture.get();
				ExampleUtils.printResponse(response);
			}
		}
	}

	public static Path getDownloadPath(URI target) {
		return Paths.get("downloads", ResponseAsyncMultiExample.class.getSimpleName(), target.getHost(),
				target.getHost(), target.getPath());
	}
}
