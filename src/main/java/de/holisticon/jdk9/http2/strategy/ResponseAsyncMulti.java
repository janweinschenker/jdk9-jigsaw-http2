/**
 * 
 */
package de.holisticon.jdk9.http2.strategy;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import de.holisticon.jdk9.http2.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;
import jdk.incubator.http.HttpResponse.MultiProcessor;
import jdk.incubator.http.MultiMapResult;

/**
 * @author janweinschenker
 *
 */
public class ResponseAsyncMulti extends AbstractResponseStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.holisticon.jdk9.http2.strategy.AbstractResponseStrategy#
	 * getCompletableFutures(java.util.List)
	 */
	@Override
	public List<CompletableFuture<File>> getCompletableFutures(List<URI> targets) {

		HttpClient client = ExampleUtils.createHttpClient();

		for (URI target : targets) {
			HttpRequest request = ExampleUtils.createHttpRequest(target);
			Path downloadDirectory = getDownloadPath(target);
			
			MultiMapResult<Path> multiMapResult = client.sendAsync(request, MultiProcessor.asMap((req) -> {
				Optional<BodyHandler<Path>> optional = Optional.of(HttpResponse.BodyHandler.asFileDownload(downloadDirectory));
				return optional;
			})).join();
		}

		// TODO Auto-generated method stub
		return null;
	}

}
