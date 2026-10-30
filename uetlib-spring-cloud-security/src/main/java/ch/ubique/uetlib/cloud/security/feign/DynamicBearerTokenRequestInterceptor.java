package ch.ubique.uetlib.cloud.security.feign;

import ch.ubique.uetlib.cloud.feign.BearerTokenRequestInterceptor;
import ch.ubique.uetlib.security.JwtUtil;
import feign.RequestTemplate;

@SuppressWarnings("unused")
public class DynamicBearerTokenRequestInterceptor implements BearerTokenRequestInterceptor {
    @Override
    public void apply(final RequestTemplate requestTemplate) {
        apply(requestTemplate, JwtUtil.getTokenValue());
    }
}
