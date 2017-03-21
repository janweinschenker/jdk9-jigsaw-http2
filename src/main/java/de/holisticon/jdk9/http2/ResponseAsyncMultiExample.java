package de.holisticon.jdk9.http2;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
			URI uri = new URI("https://blog.cloudflare.com/announcing-support-for-http-2-server-push-2/");
			HttpRequest request = ExampleUtils.createHttpRequest(uri);

			HttpClient client = HttpClient.newHttpClient();
			MultiMapResult<String> multiMapResult = client
					.sendAsync(request, MultiProcessor.asMap((req) -> {
							Optional<BodyHandler<String>> optional = Optional.of(HttpResponse.BodyHandler.asString());
							return optional;
						}))
					.join();

			System.out.println(multiMapResult.entrySet().size());
			System.out.println(multiMapResult.keySet().size());

			for (HttpRequest key : multiMapResult.keySet()) {
				System.out.println("Key: " + key.toString());
				CompletableFuture<HttpResponse<String>> completableFuture = multiMapResult.get(key);
				HttpResponse<String> httpResponse = completableFuture.get();
				System.out.println("Value: " + httpResponse.body());
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
