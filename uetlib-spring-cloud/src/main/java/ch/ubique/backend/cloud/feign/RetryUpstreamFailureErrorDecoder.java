package ch.ubique.backend.cloud.feign;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import org.springframework.http.HttpStatus;

import java.util.Set;

/**
 * Add this as a bean to a feign configuration class to trigger retries on 502, 503 and 504, not
 * just by default on {@link java.io.IOException}.
 */
@SuppressWarnings("unused")
public class RetryUpstreamFailureErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(final String methodKey, final Response response) {
        final var httpStatus = HttpStatus.valueOf(response.status());
        if (Set.of(
                        HttpStatus.BAD_GATEWAY,
                        HttpStatus.SERVICE_UNAVAILABLE,
                        HttpStatus.GATEWAY_TIMEOUT)
                .contains(httpStatus)) {
            return new RetryableException(
                    httpStatus.value(),
                    httpStatus.getReasonPhrase(),
                    response.request().httpMethod(),
                    null,
                    response.request());
        }
        return new Default().decode(methodKey, response);
    }
}
