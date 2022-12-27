package ch.ubique.backend.cloud.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public interface BearerTokenRequestInterceptor extends RequestInterceptor {

    default void apply(final RequestTemplate requestTemplate, final String bearerTokenValue) {
        requestTemplate.header("Authorization", "Bearer " + bearerTokenValue);
    }
}
