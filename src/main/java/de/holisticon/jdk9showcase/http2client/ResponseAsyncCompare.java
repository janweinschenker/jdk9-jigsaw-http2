package de.holisticon.jdk9showcase.http2client;

import de.holisticon.jdk9showcase.http2client.util.ExampleUtils;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author janweinschenker
 * @see HttpResponse
 * @see HttpRequest
 */
public class ResponseAsyncCompare {

    private static final Logger LOG = Logger
            .getLogger(ResponseAsyncCompare.class.getName());

    public static void main(String[] args) {

        try {
            ResponseAsyncCompare compare = new ResponseAsyncCompare();
            compare.startRequest(args);
            System.exit(0);
        } catch (URISyntaxException e) {
            LOG.log(Level.SEVERE, "URISyntaxException", e);
        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, "InterruptedException", e);
        } catch (ExecutionException e) {
            LOG.log(Level.SEVERE, "ExecutionException", e);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException", e);
        }

    }

    private void startRequest(String[] args)
            throws URISyntaxException, InterruptedException, ExecutionException,
            IOException {
        Version httpVersion = Version.valueOf(args[0]);
        URI uri = getUri(httpVersion);

        Date start = new Date();
        HttpClient client = ExampleUtils.createHttpClient(httpVersion);
        Version responseVersion = Version.HTTP_1_1;

        LOG.log(Level.INFO, "URI: " + uri.toString());

        for (int i = 0; i < Integer.valueOf(args[1]); i++) {
            HttpRequest request = ExampleUtils
                    .createHttpRequest(uri, httpVersion);
            HttpResponse<String> httpResponse = client
                    .send(request, HttpResponse.BodyHandler.asString());
            responseVersion = httpResponse.version();
        }

        Date end = new Date();
        long diff = end.getTime() - start.getTime();
        LOG.log(Level.INFO, "");
                LOG.log(Level.INFO, "Milliseconds: " + diff);
        LOG.log(Level.INFO,
                "Response HTTP version: " + responseVersion.toString());
        LOG.log(Level.INFO, "");
    }

    private URI getUri(Version httpVersion) throws URISyntaxException {
        String port = Version.HTTP_2.equals(httpVersion) ? "8443" : "8080";
        return new URI("https://localhost:" + port + "/greeting?name=JavaLand");
    }

}
