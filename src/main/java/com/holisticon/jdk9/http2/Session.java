package com.holisticon.jdk9.http2;

import java.io.File;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 
 * 
 * @author janweinschenker
 *
 * @see HttpResponse
 * @see HttpRequest
 */
public class Session {

	public static void main(String[] args) {
		// fetch a list of target URIs asynchronously and store them in Files.

		List<URI> targets = UriProvider.getInstance().getUriList();

		List<CompletableFuture<File>> futures = targets.stream().map(target -> {
			return HttpRequest.create(target).GET().responseAsync().thenCompose(response -> {
				Path dest = Paths.get("target/downloads", target.getHost(), target.getPath());
				System.out.println(dest.toString());
				if (response.statusCode() == 200) {
					System.out.println("200");
					return response.bodyAsync(HttpResponse.asFile(dest));
				} else {
					System.out.println("else");
					return CompletableFuture.completedFuture(dest);
				}
			})
					// convert Path -> File
					.thenApply((Path dest) -> {
						return dest.toFile();
					});
		}).collect(Collectors.toList());

		// all async operations waited for here

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		// all elements of futures have completed and can be examined.
		// Use File.exists() to check whether file was successfully downloaded

		 for (CompletableFuture<File> completableFuture : futures) {
			 if (completableFuture.isDone()){
				 
				 try {
					System.out.println(completableFuture.get().getAbsolutePath());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}
	}
}
