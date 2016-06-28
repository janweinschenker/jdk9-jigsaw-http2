package com.holisticon.jdk9.http2;

import java.io.File;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.holisticon.jdk9.http2.strategy.ResponseAsync;
import com.holisticon.jdk9.http2.strategy.ResponseAsyncMulti;
import com.holisticon.jdk9.http2.strategy.ResponseStrategy;

/**
 * 
 * 
 * @author janweinschenker
 *
 * @see HttpResponse
 * @see HttpRequest
 */
public class Session {

	private static final Logger LOG = Logger.getLogger(Session.class.getName());

	public static void main(String[] args) {
		Session session = new Session();
		session.go();

	}

	public void go() {
		List<URI> targets = UriProvider.getInstance().getUriList();

		List<CompletableFuture<File>> futures = getCompletableFutures(new ResponseAsync(), targets);

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

		printFuturesReport(futures);

		// ------
		List<CompletableFuture<File>> futuresMulti = getCompletableFutures(new ResponseAsyncMulti(), targets);

		CompletableFuture.allOf(futuresMulti.toArray(new CompletableFuture[0])).join();

		printFuturesReport(futuresMulti);

	}

	private void printFuturesReport(List<CompletableFuture<File>> futures) {
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

	/**
	 * 
	 * fetch a list of target URIs asynchronously and store them in Files.
	 * 
	 * @see HttpRequest
	 * 
	 * @param targets
	 * @return
	 */
	private List<CompletableFuture<File>> getCompletableFutures(ResponseStrategy strategy, List<URI> targets) {

		LOG.info(strategy.getClass().getName());
		return strategy.getCompletableFutures(targets);
	}

}
