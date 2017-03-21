package de.holisticon.jdk9.http2.strategy;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import de.holisticon.jdk9.http2.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse.BodyHandler;

/**
 * This class will use an http2 request to fetch an html-file from a URI. This
 * request will not receive files pushed by the server.
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
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	@Override
	public List<CompletableFuture<File>> getCompletableFutures(List<URI> targets) {
		HttpClient client = ExampleUtils.createHttpClient();

		List<CompletableFuture<File>> futures = targets.stream()
				.map(target -> client
						.sendAsync(ExampleUtils.createHttpRequest(target),
								BodyHandler.asFile(prepareDownloadPathFor(target)))
						.thenApply(response -> response.body()).thenApply(path -> path.toFile()))
				.collect(Collectors.toList());
		return futures;
	}

}
