package de.holisticon.jdk9.http2;

import java.io.File;
import java.net.URI;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import de.holisticon.jdk9.http2.strategy.ResponseAsyncMulti;
import de.holisticon.jdk9.http2.util.ExampleUtils;
import de.holisticon.jdk9.http2.util.UriProvider;

/**
 * 
 * 
 * @author janweinschenker
 *
 * @see HttpResponse
 * @see HttpRequest
 */
public class ResponseAsyncMultiExample {

	public static void main(String[] args) {
		ResponseAsyncMultiExample session = new ResponseAsyncMultiExample();
		session.go();

	}

	public void go() {
		List<URI> targets = UriProvider.getInstance().getUriList();

		List<CompletableFuture<File>> futuresMulti = ExampleUtils.getCompletableFutures(new ResponseAsyncMulti(),
				targets);

		CompletableFuture.allOf(futuresMulti.toArray(new CompletableFuture[0])).join();

		ExampleUtils.printFuturesReport(futuresMulti);

	}

}
