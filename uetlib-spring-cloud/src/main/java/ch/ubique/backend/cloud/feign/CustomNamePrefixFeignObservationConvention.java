package ch.ubique.backend.cloud.feign;

import feign.micrometer.DefaultFeignObservationConvention;

/**
 * Change the observation meter name from {@code "http.client.requests"} (see {@link
 * DefaultFeignObservationConvention#getName()} to {@code "feign.http.client.requests"} so it does
 * not class with the same meter name from the Spring Boot RestClient and it will fail with
 *
 * <p>
 *
 * {@snippet lang=text:
 * Registration has failed: Prometheus requires that all meters with the same name have the same set of tag keys. There is already an existing meter named 'http_client_requests_active_seconds'
 * }
 *
 * <p>Final as this class is a singleton and should not be extended. If you DO want to extend it,
 * extend {@link DefaultFeignObservationConvention} directly
 */
public final class CustomNamePrefixFeignObservationConvention
        extends DefaultFeignObservationConvention {

    private static final String NAME =
            "feign." + DefaultFeignObservationConvention.INSTANCE.getName();

    @Override
    public String getName() {
        return NAME;
    }
}
