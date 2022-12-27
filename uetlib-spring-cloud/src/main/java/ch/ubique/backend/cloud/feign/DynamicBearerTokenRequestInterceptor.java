package ch.ubique.backend.cloud.feign;

import ch.ubique.backend.cloud.security.JwtUtil;
import feign.RequestTemplate;

public class DynamicBearerTokenRequestInterceptor implements BearerTokenRequestInterceptor {
    @Override
    public void apply(final RequestTemplate requestTemplate) {
        apply(requestTemplate, JwtUtil.getTokenValue());
    }
}
