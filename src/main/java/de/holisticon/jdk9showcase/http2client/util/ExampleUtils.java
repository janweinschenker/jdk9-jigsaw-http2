/**
 * 
 */
package de.holisticon.jdk9showcase.http2client.util;

import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.time.Duration;

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

	public static HttpClient createHttpClient() {
		CookieManager cookieManager = new CookieManager();
		HttpClient client = HttpClient.newBuilder().cookieManager(cookieManager).version(Version.HTTP_2).build();
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
		return HttpRequest.newBuilder(uri).version(Version.HTTP_2).timeout(Duration.ofSeconds(2)).GET().build();
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
