package ch.ubique.backend.cloud.security.feign;

import ch.ubique.backend.cloud.feign.BearerTokenRequestInterceptor;
import ch.ubique.backend.security.JwtUtil;

import feign.RequestTemplate;

@SuppressWarnings("unused")
public class DynamicBearerTokenRequestInterceptor implements BearerTokenRequestInterceptor {
    @Override
    public void apply(final RequestTemplate requestTemplate) {
        apply(requestTemplate, JwtUtil.getTokenValue());
    }
}
