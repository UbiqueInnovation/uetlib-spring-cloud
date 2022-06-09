package ch.ubique.backend.cloud.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class JwtUtil {

    protected JwtUtil() {}

    protected static Jwt getJwt() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                instanceof Jwt jwt) {
            return jwt;
        }
        throw new BadCredentialsException("Unable to retrieve JWT");
    }

    public static String getClaimAsString(final String claimName) {
        return Optional.ofNullable(getJwt().getClaimAsString(claimName))
                .orElseThrow(throwClaimNotFound(claimName));
    }

    public static List<String> getClaimAsStringList(final String claimName) {
        return Optional.ofNullable(getJwt().getClaimAsStringList(claimName))
                .orElseThrow(throwClaimNotFound(claimName));
    }

    public static Boolean getClaimAsBoolean(final String claimName) {
        return Optional.ofNullable(getJwt().getClaimAsBoolean(claimName))
                .orElseThrow(throwClaimNotFound(claimName));
    }

    public static Instant getClaimAsInstant(final String claimName) {
        return Optional.ofNullable(getJwt().getClaimAsInstant(claimName))
                .orElseThrow(throwClaimNotFound(claimName));
    }

    public static String getTokenValue() {
        return getJwt().getTokenValue();
    }

    protected static Supplier<AccessDeniedException> throwClaimNotFound(final String claimName) {
        return () -> {
            throw new AccessDeniedException("Claim '" + claimName + "' not found in JWT");
        };
    }
}
