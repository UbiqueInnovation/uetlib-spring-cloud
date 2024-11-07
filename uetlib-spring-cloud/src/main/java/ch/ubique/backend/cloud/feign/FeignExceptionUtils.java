package ch.ubique.backend.cloud.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;

import feign.FeignException;

import java.io.IOException;

@SuppressWarnings("unused")
public final class FeignExceptionUtils {

    private FeignExceptionUtils() {}

    public static <T> T readFeignExceptionBody(
            final FeignException feignException,
            final TypeReference<T> typeReference,
            final ObjectMapper objectMapper) {
        return feignException
                .responseBody()
                .map(
                        body -> {
                            try (final var inputStream = new ByteBufferBackedInputStream(body)) {
                                return objectMapper.readValue(inputStream, typeReference);
                            } catch (final IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                .orElse(null);
    }
}
