/**
 * 
 */
package com.holisticon.jdk9.http2.util;

import java.io.File;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.holisticon.jdk9.http2.strategy.AbstractResponseStrategy;

/**
 * @author janweinschenker
 *
 */
public class ExampleUtils {

	private static final Logger LOG = Logger.getLogger(ExampleUtils.class.getName());

	public static void printFuturesReport(List<CompletableFuture<File>> futures) {
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
	public static List<CompletableFuture<File>> getCompletableFutures(AbstractResponseStrategy strategy,
			List<URI> targets) {

		LOG.info(strategy.getClass().getName());
		return strategy.getCompletableFutures(targets);
	}

}
