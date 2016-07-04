package com.holisticon.jdk9.http2;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CancelClient {

	public static void main(String[] args){
		CancelClient client = new CancelClient();
		try {
			client.send();
		} catch (InterruptedException | ExecutionException
				| URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HttpResponse send() throws InterruptedException,
			ExecutionException, URISyntaxException {
		HttpRequest request = HttpRequest
				.create(new URI("http://www.holisticon.de"))
				.body(HttpRequest.noBody()).version(Version.HTTP_2).GET();
		CompletableFuture<HttpResponse> future = request.responseAsync();
		Thread.sleep(100);
		if (!future.isDone()) {
			future.cancel(true);
			System.out.println("Sorry, timeout!");
		}
		System.out.println("Done!");
		HttpResponse response = future.get();
		System.out.println(response.uri().toASCIIString());
		return response;
	}
}
