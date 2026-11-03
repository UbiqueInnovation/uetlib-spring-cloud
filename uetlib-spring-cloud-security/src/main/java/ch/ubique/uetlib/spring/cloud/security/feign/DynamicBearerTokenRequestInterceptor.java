package ch.ubique.uetlib.spring.cloud.security.feign;

import ch.ubique.uetlib.spring.boot.security.JwtUtil;
import ch.ubique.uetlib.spring.cloud.feign.BearerTokenRequestInterceptor;

import feign.RequestTemplate;

@SuppressWarnings("unused")
public class DynamicBearerTokenRequestInterceptor implements BearerTokenRequestInterceptor {
    @Override
    public void apply(final RequestTemplate requestTemplate) {
        apply(requestTemplate, JwtUtil.getTokenValue());
    }
}
