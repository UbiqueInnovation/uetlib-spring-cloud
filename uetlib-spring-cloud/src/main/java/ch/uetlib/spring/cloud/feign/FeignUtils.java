package ch.uetlib.spring.cloud.feign;

import com.pivovarit.function.exception.WrappedException;

import feign.Response;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@SuppressWarnings("unused")
public final class FeignUtils {

    private FeignUtils() {}

    public static String readBody(final Response response) {
        try {
            return StreamUtils.copyToString(
                    response.body().asInputStream(), Charset.defaultCharset());
        } catch (final IOException e) {
            throw new WrappedException(e);
        }
    }
}
