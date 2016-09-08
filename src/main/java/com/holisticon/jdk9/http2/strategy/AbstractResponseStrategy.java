/**
 * 
 */
package com.holisticon.jdk9.http2.strategy;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Super class for all strategies of this project. Contains some convenience
 * methods.
 * 
 * @author janweinschenker
 *
 */
public abstract class AbstractResponseStrategy {

	/**
	 * Get a list of {@link CompletableFuture} objects. How this is done must be
	 * implemented in the subclass.
	 * 
	 * @param targets
	 * @return
	 */
	public abstract List<CompletableFuture<File>> getCompletableFutures(List<URI> targets);

	/**
	 * Prepare a path for the HTTP download in directory "download".
	 * 
	 * @param target
	 * @return
	 */
	protected Path prepareDownloadPathFor(URI target) {
		createDownloadDir(target);
		Path dest = getDownloadPath(target);
		return dest;
	}

	protected Path getDownloadPath(URI target) {
		return Paths.get("downloads", getClass().getSimpleName(), target.getHost(), target.getHost(), target.getPath());
	}

	protected Path getDownloadDir(URI target) {
		return Paths.get("downloads", getClass().getSimpleName(), target.getHost());
	}

	protected void createDownloadDir(URI target) {
		Paths.get("downloads", getClass().getSimpleName(), target.getHost()).toFile().mkdir();
	}

}
