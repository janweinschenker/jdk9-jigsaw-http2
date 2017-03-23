/**
 * 
 */
package de.holisticon.jdk9showcase.http2client.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
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
		HttpClient client = HttpClient.newBuilder().version(version).followRedirects(HttpClient.Redirect.ALWAYS)
				.build();
		return client;
	}

	public static HttpRequest createHttpRequest(String uriString) {
		try {
			URI uri = new URI(uriString);
			return createHttpRequest(uri);
		} catch (URISyntaxException e) {
			LOG.log(Level.SEVERE, "URISyntaxException", e);
		}
		return null;
	}

	public static HttpRequest createHttpRequest(URI uri) {
		return HttpRequest.newBuilder(uri).version(Version.HTTP_2).timeout(Duration.ofSeconds(2)).GET().build();
	}

	public static void printResponse(HttpResponse<String> response) {
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "##################################################");
		LOG.log(Level.INFO, "");

		if (response.body() != null && response.body().length() <= 200) {
			LOG.log(Level.INFO, "Response:     " + response.body());
		} else if (response.body() != null && response.body().length() > 200) {
			LOG.log(Level.INFO, "Response:     " + response.body().substring(0, 199));
		}
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "HTTP-Version: " + response.version());
		response.headers().map().forEach((header, values) -> LOG.log(Level.INFO, "Header: " + header + " / value: "
				+ values.stream().map(value -> value.trim()).reduce(String::concat).get()));

		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "##################################################");
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "");
		LOG.log(Level.INFO, "");
	}

}
