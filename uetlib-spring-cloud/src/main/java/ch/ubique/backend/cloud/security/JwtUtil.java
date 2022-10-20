package ch.ubique.backend.cloud.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
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
        return notNullOrThrow(getJwt().getClaimAsString(claimName), claimName);
    }

    public static String getClaimAsString(final String claimName, final String fallbackClaimName) {
        return notNullOrFallback(
                getJwt().getClaimAsString(claimName), () -> getClaimAsString(fallbackClaimName));
    }

    public static List<String> getClaimAsStringList(final String claimName) {
        return notNullOrThrow(getJwt().getClaimAsStringList(claimName), claimName);
    }

    public static List<String> getClaimAsStringList(
            final String claimName, final String fallbackClaimName) {
        return notNullOrFallback(
                getJwt().getClaimAsStringList(claimName),
                () -> getClaimAsStringList(fallbackClaimName));
    }

    public static Boolean getClaimAsBoolean(final String claimName) {
        return notNullOrThrow(getJwt().getClaimAsBoolean(claimName), claimName);
    }

    public static Boolean getClaimAsBoolean(
            final String claimName, final String fallbackClaimName) {
        return notNullOrFallback(
                getJwt().getClaimAsBoolean(claimName), () -> getClaimAsBoolean(fallbackClaimName));
    }

    public static Instant getClaimAsInstant(final String claimName) {
        return notNullOrThrow(getJwt().getClaimAsInstant(claimName), claimName);
    }

    public static Instant getClaimAsInstant(
            final String claimName, final String fallbackClaimName) {
        return notNullOrFallback(
                getJwt().getClaimAsInstant(claimName), () -> getClaimAsInstant(fallbackClaimName));
    }

    public static long getClaimAsLong(final String claimName) {
        return notNullOrThrow(claimAsLong(getJwt().getClaim(claimName)), claimName);
    }

    public static long getClaimAsLong(final String claimName, final String fallbackClaimName) {
        return notNullOrFallback(
                claimAsLong(getJwt().getClaim(claimName)), () -> getClaimAsLong(fallbackClaimName));
    }

    public static List<Long> getClaimAsLongList(final String claimName) {
        return claimAsList(claimName, JwtUtil::claimAsLong);
    }

    public static List<Long> getClaimAsLongList(
            final String claimName, final String fallbackClaimName) {
        return claimAsList(claimName, fallbackClaimName, JwtUtil::claimAsLong);
    }

    public static double getClaimAsDouble(final String claimName) {
        return notNullOrThrow(claimAsDouble(getJwt().getClaim(claimName)), claimName);
    }

    public static double getClaimAsDouble(final String claimName, final String fallbackClaimName) {
        return notNullOrFallback(
                claimAsDouble(getJwt().getClaim(claimName)),
                () -> getClaimAsDouble(fallbackClaimName));
    }

    public static List<Double> getClaimAsDoubleList(final String claimName) {
        return claimAsList(claimName, JwtUtil::claimAsDouble);
    }

    public static List<Double> getClaimAsDoubleList(
            final String claimName, final String fallbackClaimName) {
        return claimAsList(claimName, fallbackClaimName, JwtUtil::claimAsDouble);
    }

    private static <T> List<T> claimAsList(
            final String claimName, final Function<Object, T> claimConverter) {
        final var claim = getJwt().getClaims().get(claimName);
        return claimAsList(claim, claimName, claimConverter);
    }

    private static <T> List<T> claimAsList(
            final String claimName,
            final String fallbackClaimName,
            final Function<Object, T> claimConverter) {
        final var claim = getJwt().getClaims().get(claimName);
        if (claim != null) {
            return claimAsList(claim, claimName, claimConverter);
        }
        return claimAsList(fallbackClaimName, claimConverter);
    }

    @SuppressWarnings("java:S3740")
    private static <T> List<T> claimAsList(
            final Object claim, final String claimName, final Function<Object, T> claimConverter) {
        //noinspection rawtypes
        if (claim instanceof List claimList) {
            //noinspection unchecked
            return ((List<Object>) claimList)
                    .stream()
                            .map(
                                    claimObject ->
                                            notNullOrThrow(claimConverter.apply(claimObject), claimName))
                            .toList();
        }
        return List.of(notNullOrThrow(claimConverter.apply(claim), claimName));
    }

    private static <T> T notNullOrThrow(final T claimValue, final String claimName) {
        return Optional.ofNullable(claimValue).orElseThrow(throwClaimNotFound(claimName));
    }

    private static <T> T notNullOrFallback(
            final T claimValue, final Supplier<T> fallbackClaimSupplier) {
        return Optional.ofNullable(claimValue).orElseGet(fallbackClaimSupplier);
    }

    private static Long claimAsLong(final Object claimObject) {
        try {
            return claimObject instanceof String claim
                    ? Long.parseLong(claim)
                    : ((Number) claimObject).longValue();
        } catch (NullPointerException | NumberFormatException exception) {
            return null;
        }
    }

    private static Double claimAsDouble(final Object claimObject) {
        try {
            return claimObject instanceof String claim
                    ? Double.parseDouble(claim)
                    : ((Number) claimObject).doubleValue();
        } catch (NullPointerException | NumberFormatException exception) {
            return null;
        }
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
