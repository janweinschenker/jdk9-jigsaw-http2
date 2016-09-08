package de.holisticon.jdk9.http2.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create a list of {@link URI} objects.
 * 
 * @author janweinschenker
 *
 */
public class UriProvider {

	private static final Logger LOG = Logger.getLogger(UriProvider.class.getName());

	private static final String PROTOCOL = "http";

	public static UriProvider getInstance() {
		UriProvider instance = new UriProvider();
		return instance;
	}

	public List<URI> getUriList() {

		List<URI> uriList = new ArrayList<URI>();

		try {
			addUri(uriList, PROTOCOL + "://www.ist-http2-aktiviert.de");
			addUri(uriList, PROTOCOL + "://www.google.de");
			addUri(uriList, PROTOCOL + "://www.http2demo.io");
			addUri(uriList, PROTOCOL + "://nghttp2.org");

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
