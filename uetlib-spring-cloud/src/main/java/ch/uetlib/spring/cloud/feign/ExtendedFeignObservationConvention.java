package ch.uetlib.spring.cloud.feign;

import static org.apache.commons.lang3.StringUtils.isBlank;

import static java.util.Objects.requireNonNull;

import feign.RequestTemplate;
import feign.micrometer.DefaultFeignObservationConvention;
import feign.micrometer.FeignContext;

import io.micrometer.common.KeyValues;
import io.micrometer.common.docs.KeyName;

import org.jspecify.annotations.Nullable;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Change the observation meter name from {@code "http.client.requests"} (see {@link
 * DefaultFeignObservationConvention#getName()} to {@code "feign.http.client.requests"} so it does
 * not clash with the same meter name from the Spring Boot RestClient and it will fail with:
 *
 * <p>
 *
 * {@snippet lang=text:
 * Registration has failed: Prometheus requires that all meters with the same name have the same set of tag keys. There is already an existing meter named 'http_client_requests_active_seconds'
 * }
 *
 * <p>Additionally, two new metrics are added:
 *
 * <p>- {@code http.base_url}: The base URL of the target service. - {@code http.target}: The full
 * target URL of the request, which is equal to the configured `url` (base url + base path) for this
 * feign client.
 *
 * <p>
 *
 * <p>Final as this class is a singleton and should not be extended. If you DO want to extend it,
 * extend {@link DefaultFeignObservationConvention} directly
 */
public final class ExtendedFeignObservationConvention extends DefaultFeignObservationConvention {

    private static final String UNKNOWN = "UNKNOWN";

    @Override
    public String getName() {
        return "feign." + super.getName();
    }

    @Override
    public KeyValues getLowCardinalityKeyValues(final FeignContext context) {
        final var requestTemplate = requireNonNull(context.getCarrier()).requestTemplate();
        final var superKeyValues = super.getLowCardinalityKeyValues(context);

        final var targetUrl = getTargetUrl(requestTemplate);
        final var baseUrl = extractHostPort(targetUrl);

        final var additionalKeyValues =
                KeyValues.of(
                        AdditionalClientTags.BASE_URL.withValue(baseUrl),
                        AdditionalClientTags.TARGET.withValue(targetUrl));

        return superKeyValues.and(additionalKeyValues);
    }

    private String getTargetUrl(@Nullable final RequestTemplate requestTemplate) {
        if (requestTemplate == null) {
            return UNKNOWN;
        }

        final var target = requestTemplate.feignTarget();
        if (target == null) {
            return UNKNOWN;
        }

        final var url = target.url();
        if (isBlank(url)) {
            return UNKNOWN;
        }

        return url;
    }

    private String extractHostPort(final String url) {
        final var uriComponents = UriComponentsBuilder.fromUriString(url).build();
        final var host = requireNonNull(uriComponents.getHost());
        final var port = uriComponents.getPort();
        return port == -1 ? host : host + ":" + port;
    }

    enum AdditionalClientTags implements KeyName {
        BASE_URL {
            @Override
            public String asString() {
                return "http.base_url";
            }
        },
        TARGET {
            @Override
            public String asString() {
                return "http.target";
            }
        }
    }
}
