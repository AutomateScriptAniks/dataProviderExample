package helper;

import com.google.common.hash.Hashing;
import com.google.common.net.UrlEscapers;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.spi.AuthFilter;

import utils.TestData;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


public class HmacFilter implements AuthFilter {
    private Client client;

    public HmacFilter(Client client) {
        this.client = client;
    }

    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        Instant now = Instant.now();
        requestSpec.header("X-Authorization-Timestamp", now.toEpochMilli());
        requestSpec.header("Authorization", getAuthorisationHeaderValue(
                requestSpec.getMethod(),
                URI.create(requestSpec.getURI()),
                client.getId(),
                client.getSecret(),
                String.valueOf(now.toEpochMilli())));
        return ctx.next(requestSpec, responseSpec);
    }

    private static String getAuthorisationHeaderValue(String httpMethod, URI uri, String clientId,
                                                      String clientSecret, String timeStamp) {
        String nonce = getNonce();
        String textToSign = textToSign(httpMethod, uri, nonce, timeStamp);
        return String.format("id=%s,nonce=%s,version=%s,signature=%s",
                doubleQuoteAndEncode(clientId),
                doubleQuoteAndEncode(nonce),
                doubleQuoteAndEncode("v1"),
                doubleQuoteAndEncode(sign(clientSecret, textToSign)));
    }

    private static String sign(String clientSecret, String textToSign) {
        byte[] utf8s = textToSign.getBytes(Charset.forName("utf-8"));

        return Hashing.hmacSha256(clientSecret.getBytes()).hashBytes(utf8s).toString();
    }

    private static String textToSign(String httpMethod, URI uri, String nonce, String timestamp) {
        return String.format("%s\n%s\n%s\n%s\n%s\n%s\n", httpMethod, uri.getHost(), uri.getRawPath(),
                Optional.ofNullable(uri.getRawQuery()).orElse(""), nonce, timestamp);
    }

    private static String getNonce() {
        return UUID.randomUUID().toString();
    }

    private static String doubleQuoteAndEncode(String original) {
        return String.format("\"%s\"", UrlEscapers.urlFormParameterEscaper().escape(original));
    }
}
