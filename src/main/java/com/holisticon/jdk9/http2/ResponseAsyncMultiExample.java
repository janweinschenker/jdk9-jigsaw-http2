package com.holisticon.jdk9.http2;

import java.io.File;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.holisticon.jdk9.http2.strategy.ResponseAsyncMulti;
import com.holisticon.jdk9.http2.util.ExampleUtils;
import com.holisticon.jdk9.http2.util.UriProvider;

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
