package com.holisticon.jdk9.http2.strategy;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import com.holisticon.jdk9.http2.util.SSLContextCreator;

/**
 * 
 * @author janweinschenker
 *
 */
public class ResponseAsync extends AbstractResponseStrategy {

	private static final Logger LOG = Logger.getLogger(ResponseAsync.class.getName());

	/**
	 * 
	 * Fetch a list of target URIs asynchronously and store them in Files.
	 * 
	 * @see HttpRequest
	 * 
	 * @param targets
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@Override
	public List<CompletableFuture<File>> getCompletableFutures(List<URI> targets) {
		SSLContext context = SSLContextCreator.getContext();
		HttpClient client = HttpClient.create().sslContext(context).build();

		List<CompletableFuture<File>> futures = targets.stream().map(target -> {
			HttpRequest request = client.request(target).version(Version.HTTP_2).GET();
			return request.responseAsync().thenCompose(response -> {
				Path dest = prepareDownloadPathFor(target);
				if (response.statusCode() == 200) {
					// LOG.log(Level.INFO, dest.toString() + ": 200");
					return response.bodyAsync(HttpResponse.asFile(dest));
				} else {
					// LOG.log(Level.INFO, dest.toString() + ": else");
					return CompletableFuture.completedFuture(dest);
				}
			}).whenCompleteAsync((it, err) -> {
				if (it != null) {
					LOG.log(Level.INFO, "saved to disk: " + it.toString());
				} else {
					LOG.log(Level.SEVERE, err.getClass().getSimpleName());
				}

			}).thenApply((Path dest) -> {
				// convert Path -> File
				return dest.toFile();
			});
		}).collect(Collectors.toList());
		return futures;
	}
}
