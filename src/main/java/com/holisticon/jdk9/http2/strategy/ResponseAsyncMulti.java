/**
 * 
 */
package com.holisticon.jdk9.http2.strategy;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author janweinschenker
 *
 */
public class ResponseAsyncMulti extends ResponseStrategy {

	private static final Logger LOG = Logger.getLogger(ResponseAsyncMulti.class.getName());

	/**
	 * 
	 * Fetch a list of target URIs asynchronously and store them in Files.
	 * 
	 * @see HttpResponse#multiFile(Path)
	 * @see HttpRequest#multiResponseAsync(java.net.http.HttpResponse.MultiProcessor)
	 * @param targets
	 * @return
	 */
	@Override
	public List<CompletableFuture<File>> getCompletableFutures(List<URI> targets) {
		List<CompletableFuture<File>> futures = new ArrayList<CompletableFuture<File>>();
		for (URI target : targets) {
			// LOG.info(target.toString());
			createDownloadDir(target);
			Path d = getDownloadPath(target);
			// LOG.info(d.toString());
			HttpRequest.create(target).version(Version.HTTP_1_1).GET().multiResponseAsync(HttpResponse.multiFile(d))
					.thenComposeAsync((Map<URI, Path> mp) -> {
						Map<URI, File> files = new HashMap<URI, File>();
						for (Iterator<URI> i = mp.keySet().iterator(); i.hasNext();) {
							URI eachUri = i.next();
							Path path = mp.get(eachUri);
							LOG.info("eachURI: " + path.toString());
							files.put(eachUri, path.toFile());
							futures.add(CompletableFuture.completedFuture(path.toFile()));
						}
						return CompletableFuture.completedFuture(files);
					}, Executors.newFixedThreadPool(5));
		}
		return futures;
	}
}
