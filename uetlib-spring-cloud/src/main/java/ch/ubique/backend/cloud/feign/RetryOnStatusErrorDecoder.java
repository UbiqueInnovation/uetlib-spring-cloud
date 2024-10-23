package ch.ubique.backend.cloud.feign;

import static feign.FeignException.errorStatus;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Add this as a bean to a feign configuration class to trigger retries on 502, 503 and 504 (or
 * others by using {@link #RetryOnStatusErrorDecoder(Collection)}, not just by default on {@link
 * IOException}.
 */
@SuppressWarnings("unused")
public class RetryOnStatusErrorDecoder implements ErrorDecoder {

    private final Collection<HttpStatusCode> statusCodesToRetry;

    public RetryOnStatusErrorDecoder() {
        statusCodesToRetry =
                Set.of(
                        HttpStatus.BAD_GATEWAY,
                        HttpStatus.SERVICE_UNAVAILABLE,
                        HttpStatus.GATEWAY_TIMEOUT);
    }

    public RetryOnStatusErrorDecoder(final HttpStatusCode... statusCodesToRetry) {
        this.statusCodesToRetry = Set.of(statusCodesToRetry);
    }

    public RetryOnStatusErrorDecoder(final Collection<HttpStatusCode> statusCodesToRetry) {
        this.statusCodesToRetry = statusCodesToRetry;
    }

    @Override
    public Exception decode(final String methodKey, final Response response) {
        final var httpStatus = HttpStatus.valueOf(response.status());
        if (statusCodesToRetry.contains(httpStatus)) {
            return new RetryableException(
                    httpStatus.value(),
                    httpStatus.getReasonPhrase(),
                    response.request().httpMethod(),
                    (Long) null,
                    response.request());
        }
        return errorStatus(methodKey, response);
    }
}
