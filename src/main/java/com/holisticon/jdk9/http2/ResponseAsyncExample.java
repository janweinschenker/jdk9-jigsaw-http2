package com.holisticon.jdk9.http2;

import java.io.File;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.holisticon.jdk9.http2.strategy.ResponseAsync;
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
public class ResponseAsyncExample {

	public static void main(String[] args) {
		ResponseAsyncExample session = new ResponseAsyncExample();
		session.go();

	}

	public void go() {
		List<URI> targets = UriProvider.getInstance().getUriList();

		List<CompletableFuture<File>> futures = ExampleUtils.getCompletableFutures(new ResponseAsync(), targets);

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

		ExampleUtils.printFuturesReport(futures);

	}

}
