package de.holisticon.jdk9.http2;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.holisticon.jdk9.http2.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
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
			// URI uri = new URI("https://www.google.de/");
			// URI uri = new URI("https://www.smashingmagazine.com/");
			// URI uri = new URI("https://de.wikipedia.org/");
			// URI uri = new
			// URI("https://localhost:8443/greeting?name=Javaland");
//			URI uri = new URI("https://blog.cloudflare.com/");
			URI uri = new URI("https://blog.cloudflare.com/announcing-support-for-http-2-server-push-2/");
			HttpRequest request = ExampleUtils.createHttpRequest(uri);
			HttpClient client = ExampleUtils.createHttpClient();

			MultiMapResult<String> multiMapResult = client.sendAsync(request, MultiProcessor.asMap((req) -> {
				Optional<BodyHandler<String>> optional = Optional.of(HttpResponse.BodyHandler.asString());
				System.out.println("## " + optional.get().toString() + " " + optional.isPresent());
				if (optional.isPresent()) {
					System.out.println(req.bodyProcessor().get());
					System.out.println(optional.get().toString());
				}
				return optional;
			}, false))
					
					.orTimeout(2, TimeUnit.SECONDS)
					.join();

			System.out.println("timeout");
			// MultiMapResult<String> multiMapResult = future.get();
			if (multiMapResult == null) {
				System.out.println("ende");
				return;
			}

			System.out.println(multiMapResult.entrySet().size());
			System.out.println(multiMapResult.keySet().size());

			for (HttpRequest key : multiMapResult.keySet()) {
				System.out.println("Key: " + key.toString());
				CompletableFuture<HttpResponse<String>> completableFuture = multiMapResult.get(key);
				HttpResponse<String> response = completableFuture.get();
				ExampleUtils.printResponse(response);
			}

		} catch (URISyntaxException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Path getDownloadPath(URI target) {
		return Paths.get("downloads", ResponseAsyncMultiExample.class.getSimpleName(), target.getHost(),
				target.getHost(), target.getPath());
	}

}
