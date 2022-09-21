package ch.ubique.backend.cloud.feign;

import feign.RequestTemplate;

public class StaticBearerTokenRequestInterceptor extends BearerTokenRequestInterceptor {

    private final String bearerTokenValue;

    public StaticBearerTokenRequestInterceptor(final String bearerTokenValue) {
        this.bearerTokenValue = bearerTokenValue;
    }

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        apply(requestTemplate, bearerTokenValue);
    }
}
