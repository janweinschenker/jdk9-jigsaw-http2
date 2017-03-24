/**
 *
 */
package de.holisticon.jdk9showcase.http2client.util;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains some utilities to create http clients and requests.
 *
 * @author janweinschenker
 */
public class ExampleUtils {

    private static final Logger LOG = Logger
            .getLogger(ExampleUtils.class.getName());

    private ExampleUtils() {

    }

    public static HttpClient createHttpClient(Version version) {
        return HttpClient
                .newBuilder()
                .version(version)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .executor(Executors.newFixedThreadPool(4))
                .build();
    }

    public static HttpRequest createHttpRequest(URI uri, Version version) {
        return HttpRequest.newBuilder(uri)
                          .version(version)
                          .GET()
                          .build();
    }

    /**
     * Create an http request for the given URI and http protocol version.
     */
    public static HttpRequest createHttpRequest(
            String uriString, Version version) {
        try {
            URI uri = new URI(uriString);
            return createHttpRequest(uri, version);
        } catch (URISyntaxException e) {
            LOG.log(Level.SEVERE, "URISyntaxException", e);
        }
        return null;
    }

    public static void printResponse(HttpResponse<String> response) {
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO,
                "##################################################");
        LOG.log(Level.INFO, "");

        if (response.body() != null && response.body().length() <= 200) {
            LOG.log(Level.INFO, "Response:     " + response.body());
        } else if (response.body() != null && response.body().length() > 200) {
            LOG.log(Level.INFO,
                    "Response:     " + response.body().substring(0, 199));
        }
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO, "HTTP-Version: " + response.version());
        LOG.log(Level.INFO, "Statuscode:   " + response.statusCode());
        LOG.log(Level.INFO, "Header:");
        response.headers().map().forEach(
                (header, values) -> LOG.log(Level.INFO, "  " + header + " = "
                        + values.stream().map(String::trim)
                                .reduce(String::concat).orElse("hallo")));

        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO,
                "##################################################");
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO, "");
        LOG.log(Level.INFO, "");
    }

}
