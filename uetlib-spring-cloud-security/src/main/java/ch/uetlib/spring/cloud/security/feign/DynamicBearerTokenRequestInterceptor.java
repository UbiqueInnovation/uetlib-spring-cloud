package ch.uetlib.spring.cloud.security.feign;

import ch.uetlib.spring.boot.security.JwtUtil;
import ch.uetlib.spring.cloud.feign.BearerTokenRequestInterceptor;

import feign.RequestTemplate;

@SuppressWarnings("unused")
public class DynamicBearerTokenRequestInterceptor implements BearerTokenRequestInterceptor {
    @Override
    public void apply(final RequestTemplate requestTemplate) {
        apply(requestTemplate, JwtUtil.getTokenValue());
    }
}
