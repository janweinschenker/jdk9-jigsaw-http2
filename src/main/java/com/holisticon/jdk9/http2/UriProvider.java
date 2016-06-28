package com.holisticon.jdk9.http2;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UriProvider {

	private static final Logger LOG = Logger.getLogger(UriProvider.class.getName());

	public static UriProvider getInstance() {
		UriProvider instance = new UriProvider();
		return instance;
	}

	public List<URI> getUriList() {

		List<URI> uriList = new ArrayList<URI>();

		try {
			// addUri(uriList,"https://www.tagesschau.de");
			addUri(uriList, "https://www.google.de");
			addUri(uriList, "https://www.amazon.de");

		} catch (URISyntaxException e) {
			LOG.log(Level.WARNING, "URISyntaxException", e);
		}
		return uriList;
	}

	private void addUri(List<URI> uriList, String uriString) throws URISyntaxException {
		URI uri = new URI(uriString);
		uriList.add(uri);
	}

}
