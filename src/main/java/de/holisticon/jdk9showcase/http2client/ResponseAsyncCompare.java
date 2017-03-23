package de.holisticon.jdk9showcase.http2client;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import de.holisticon.jdk9showcase.http2client.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpRequest.Builder;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpClient.Version;

/**
 * 
 * 
 * @author janweinschenker
 *
 * @see HttpResponse
 * @see HttpRequest
 */
public class ResponseAsyncCompare {

	public static void main(String[] args) {

		try {
			startRequest(args);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	private static void startRequest(String[] args)
			throws URISyntaxException, InterruptedException, ExecutionException {
		Version httpVersion = Version.valueOf(args[0]);
		String port = Version.HTTP_2.equals(httpVersion)?"8443": "8080";
		URI uri = new URI("https://localhost:"+port+"/greeting?name=JavaLand");

		Date start = new Date();
		HttpClient client = ExampleUtils.createHttpClient(httpVersion);
		for (int i = 0; i < Integer.valueOf(args[1]); i++) {
			Builder newBuilder = HttpRequest.newBuilder();
			HttpRequest request = newBuilder.uri(uri).version(httpVersion).GET()
					// .DELETE()
					// .POST(body)
					// .PUT(body)
					// .timeout(Duration.ofSeconds(30))
					// .expectContinue(true)
					.timeout(Duration.ofSeconds(1))
					.build();
			client.sendAsync(request, HttpResponse.BodyHandler.asString()).get();
		}
		Date end = new Date();
		long diff = end.getTime() - start.getTime();
		System.out.println("Milliseconds: " + diff);
		System.out.println(args[0]);
	}

}
