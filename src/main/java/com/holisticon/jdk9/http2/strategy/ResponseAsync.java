package com.holisticon.jdk9.http2.strategy;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 
 * @author janweinschenker
 *
 */
public class ResponseAsync extends ResponseStrategy {

	private static final Logger LOG = Logger.getLogger(ResponseAsync.class.getName());

	/**
	 * 
	 * fetch a list of target URIs asynchronously and store them in Files.
	 * 
	 * @see HttpRequest
	 * 
	 * @param targets
	 * @return
	 */
	@Override
	public List<CompletableFuture<File>> getCompletableFutures(List<URI> targets) {

		List<CompletableFuture<File>> futures = targets.stream().map(target -> {
			return HttpRequest.create(target).version(Version.HTTP_2).GET().responseAsync().thenCompose(response -> {
				Path dest = prepareDownloadPathFor(target);
				if (response.statusCode() == 200) {
					// LOG.log(Level.INFO, dest.toString() + ": 200");
					return response.bodyAsync(HttpResponse.asFile(dest));
				} else {
					// LOG.log(Level.INFO, dest.toString() + ": else");
					return CompletableFuture.completedFuture(dest);
				}
			})
					// convert Path -> File
					.thenApply((Path dest) -> {
						return dest.toFile();
					});
		}).collect(Collectors.toList());
		return futures;
	}
}
