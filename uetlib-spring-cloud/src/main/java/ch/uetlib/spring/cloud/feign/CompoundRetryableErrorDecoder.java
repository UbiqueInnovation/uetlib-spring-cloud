package ch.uetlib.spring.cloud.feign;

import static feign.FeignException.errorStatus;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import java.util.List;

public class CompoundRetryableErrorDecoder implements ErrorDecoder {
    private final List<ErrorDecoder> errorDecoders;

    public CompoundRetryableErrorDecoder(final ErrorDecoder... errorDecoders) {
        this(List.of(errorDecoders));
    }

    public CompoundRetryableErrorDecoder(final List<ErrorDecoder> errorDecoders) {
        this.errorDecoders = errorDecoders;
    }

    @Override
    public Exception decode(final String methodKey, final Response response) {
        for (final var errorDecoder : errorDecoders) {
            final var decodedException = errorDecoder.decode(methodKey, response);
            if (decodedException instanceof final RetryableException retryableException) {
                return retryableException;
            }
        }

        return errorStatus(methodKey, response);
    }
}
