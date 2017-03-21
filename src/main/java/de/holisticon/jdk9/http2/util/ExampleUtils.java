/**
 * 
 */
package de.holisticon.jdk9.http2.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpClient.Version;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import de.holisticon.jdk9.http2.strategy.AbstractResponseStrategy;

/**
 * @author janweinschenker
 *
 */
public class ExampleUtils {

	private static final Logger LOG = Logger.getLogger(ExampleUtils.class.getName());

	public static void printFuturesReport(List<CompletableFuture<File>> futures) {
		for (CompletableFuture<File> completableFuture : futures) {
			if (completableFuture.isDone()) {
				try {
					LOG.info(completableFuture.get().getAbsolutePath());
				} catch (InterruptedException e) {
					LOG.log(Level.SEVERE, "InterruptedException", e);
				} catch (ExecutionException e) {
					LOG.log(Level.SEVERE, "ExecutionException", e);
				}
			}
		}
	}
	
	public static HttpClient createHttpClient() {
		// get the ssl context and use it to create an http client.
		SSLContext context = SSLContextCreator.getContextInstance();
		HttpClient client = HttpClient.newBuilder().sslContext(context).version(Version.HTTP_2).build();
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
			return HttpRequest.newBuilder(uri).version(Version.HTTP_2).GET().build();
	}

	/**
	 * 
	 * fetch a list of target URIs asynchronously and store them in Files.
	 * 
	 * @see HttpRequest
	 * 
	 * @param targets
	 * @return
	 */
	public static List<CompletableFuture<File>> getCompletableFutures(AbstractResponseStrategy strategy,
			List<URI> targets) {

		LOG.info(strategy.getClass().getName());
		return strategy.getCompletableFutures(targets);
	}

}
