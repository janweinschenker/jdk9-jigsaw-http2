package de.holisticon.jdk9.http2;

import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import de.holisticon.jdk9.http2.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
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

	public static void main(String[] args) {

		try {
			HttpClient client = ExampleUtils.createHttpClient();
			URI uri = new URI("https://localhost:8443/greeting?name=JavaLand");
			HttpRequest request = HttpRequest.newBuilder().uri(uri).version(HttpClient.Version.HTTP_2).GET()
					// .DELETE()
					// .POST(body)
					// .PUT(body)
					// .timeout(Duration.ofSeconds(30))
					// .expectContinue(true)
					.build();

			HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandler.asString()).get();

			ExampleUtils.printResponse(response);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}


}
