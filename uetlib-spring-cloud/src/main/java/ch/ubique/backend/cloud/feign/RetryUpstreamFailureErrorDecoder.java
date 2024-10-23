package ch.ubique.backend.cloud.feign;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Collection;
import java.util.Set;

/**
 * @deprecated since v2.5, use {@link CompoundRetryableErrorDecoder} to get the same behaviour as before
 *     {@snippet :
 * new CompoundRetryableErrorDecoder(
 *      new RetryOnStatusErrorDecoder(), new ErrorDecoder.Default())
 *}
 */
@Deprecated(since = "2.5")
@SuppressWarnings("unused")
public class RetryUpstreamFailureErrorDecoder implements ErrorDecoder {

    private final Collection<HttpStatusCode> statusCodesToRetry;

    public RetryUpstreamFailureErrorDecoder() {
        this(
                Set.of(
                        HttpStatus.BAD_GATEWAY,
                        HttpStatus.SERVICE_UNAVAILABLE,
                        HttpStatus.GATEWAY_TIMEOUT));
    }

    public RetryUpstreamFailureErrorDecoder(final Collection<HttpStatusCode> statusCodesToRetry) {
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
        return new Default().decode(methodKey, response);
    }
}
