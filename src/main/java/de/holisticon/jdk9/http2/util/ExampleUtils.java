/**
 * 
 */
package de.holisticon.jdk9.http2.util;

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

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;

/**
 * @author janweinschenker
 *
 */
public class ExampleUtils {

	private static final Logger LOG = Logger.getLogger(ExampleUtils.class.getName());

	public static HttpClient createHttpClient() {
		SSLContext context;
		try {
			context = SSLContext.getDefault();
			HttpClient client = HttpClient.newBuilder().sslContext(context).version(Version.HTTP_2).build();
			return client;
		} catch (NoSuchAlgorithmException e) {
			LOG.log(Level.SEVERE, "Could not create default SSLContext", e);
		} 
		return null;
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
		return HttpRequest.newBuilder(uri).version(Version.HTTP_2).GET().build();
	}
}
