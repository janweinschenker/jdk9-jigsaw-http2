/**
 * 
 */
package de.holisticon.jdk9showcase.http2client.util;

import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

/**
 * @author janweinschenker
 *
 */
public class ExampleUtils {

	private static final Logger LOG = Logger.getLogger(ExampleUtils.class.getName());

	public static HttpClient createHttpClient(Version version) {
		HttpClient client = HttpClient.newBuilder()
				.version(version)
				.followRedirects(HttpClient.Redirect.ALWAYS)
				//.executor(Executors.newCachedThreadPool())
				.build();
		return client;
	}

	public static HttpRequest createHttpRequest(String uriString) {
		try {
			URI uri = new URI(uriString);
			return createHttpRequest(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			LOG.log(Level.SEVERE, "URISyntaxException", e);
		}
		return null;
	}

	public static HttpRequest createHttpRequest(URI uri) {
		return HttpRequest.newBuilder(uri)
				.version(Version.HTTP_2)
				.timeout(Duration.ofSeconds(2))
				.GET()
				.build();
	}

	public static void printResponse(HttpResponse<String> response) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("##################################################");
		System.out.println();

		if (response.body() != null && response.body().length() <= 200) {
			System.out.println("Response:     " + response.body());
		} else if (response.body() != null && response.body().length() > 200) {
			System.out.println("Response:     " + response.body().substring(0, 199));
		}
		System.out.println();
		System.out.println("HTTP-Version: " + response.version());
		response.headers().map().forEach((header, values) -> System.out.println("Header: " + header + " / value: "
				+ values.stream().map(value -> value.trim()).reduce(String::concat).get()));

		System.out.println();
		System.out.println("##################################################");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}

}
