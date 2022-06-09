package ch.ubique.backend.cloud.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public abstract class BearerTokenRequestInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";

    public void apply(final RequestTemplate requestTemplate, final String bearerTokenValue) {
        requestTemplate.header(
                AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, bearerTokenValue));
    }
}
