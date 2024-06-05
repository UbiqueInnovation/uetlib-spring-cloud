package ch.ubique.backend.cloud.feign;

import feign.RequestTemplate;

@SuppressWarnings("unused")
public class StaticBearerTokenRequestInterceptor implements BearerTokenRequestInterceptor {

    private final String bearerTokenValue;

    public StaticBearerTokenRequestInterceptor(final String bearerTokenValue) {
        this.bearerTokenValue = bearerTokenValue;
    }

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        apply(requestTemplate, bearerTokenValue);
    }
}
