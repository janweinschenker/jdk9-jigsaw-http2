/**
 * 
 */
package com.holisticon.jdk9.http2.strategy;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.MultiProcessor;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import com.holisticon.jdk9.http2.util.SSLContextCreator;

/**
 * I create an instance of an http2 client. This client will asynchronously
 * fetch content from a list of URIs.
 * 
 * @author janweinschenker
 *
 */
public class ResponseAsyncMulti extends AbstractResponseStrategy {

	private static final Logger LOG = Logger.getLogger(ResponseAsyncMulti.class.getName());

	/**
	 * 
	 * Fetch a list of target URIs asynchronously and store them in Files.
	 * Process files pushed by the server.
	 * 
	 * @see HttpResponse#multiFile(Path)
	 * @see HttpRequest#multiResponseAsync(java.net.http.HttpResponse.MultiProcessor)
	 * @param targets
	 * @return
	 */
	@Override
	public List<CompletableFuture<File>> getCompletableFutures(List<URI> targets) {
		List<CompletableFuture<File>> futures = new ArrayList<CompletableFuture<File>>();

		// get the ssl context and use it to create an http client.
		SSLContext context = SSLContextCreator.getContextInstance();
		HttpClient client = HttpClient.create().sslContext(context).build();

		for (URI target : targets) {

			createDownloadDir(target);

			Path downloadDirectory = getDownloadPath(target);
			MultiProcessor<Map<URI, Path>> multiFileProcessor = HttpResponse.multiFile(downloadDirectory);

			client.request(target).version(Version.HTTP_2).GET().multiResponseAsync(multiFileProcessor).whenCompleteAsync((it, err) -> {
				LOG.log(Level.INFO, it.keySet().toString());
			}).thenApplyAsync((Map<URI, Path> mp) -> {

				Map<URI, File> downloadedFiles = new HashMap<URI, File>();

				for (Iterator<URI> i = mp.keySet().iterator(); i.hasNext();) {
					URI eachUri = i.next();
					Path hostSpecificDirectory = mp.get(eachUri);

					downloadedFiles.put(eachUri, hostSpecificDirectory.toFile());

					futures.add(CompletableFuture.completedFuture(hostSpecificDirectory.toFile()).whenCompleteAsync((it, err) -> {
						if (it != null) {
							LOG.log(Level.INFO, "saved to disk: " + it.getAbsolutePath());
						} else {
							LOG.log(Level.SEVERE, err.getClass().getSimpleName());
						}
					}));
				}
				return CompletableFuture.completedFuture(downloadedFiles);
			}, Executors.newCachedThreadPool());
		}
		return futures;
	}
}
