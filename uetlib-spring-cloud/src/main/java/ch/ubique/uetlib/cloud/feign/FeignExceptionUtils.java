package ch.ubique.uetlib.cloud.feign;

import feign.FeignException;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.util.ByteBufferBackedInputStream;

@SuppressWarnings("unused")
public final class FeignExceptionUtils {

    private FeignExceptionUtils() {}

    public static <T> @Nullable T readFeignExceptionBody(
            final FeignException feignException,
            final TypeReference<T> typeReference,
            final ObjectMapper objectMapper) {
        return feignException
                .responseBody()
                .map(
                        body -> {
                            try (final var inputStream = new ByteBufferBackedInputStream(body)) {
                                return objectMapper.readValue(inputStream, typeReference);
                            }
                        })
                .orElse(null);
    }
}
