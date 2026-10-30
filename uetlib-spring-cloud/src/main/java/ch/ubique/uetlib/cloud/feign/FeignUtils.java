package ch.ubique.uetlib.cloud.feign;

import com.pivovarit.function.exception.WrappedException;
import feign.Response;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.util.StreamUtils;

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
