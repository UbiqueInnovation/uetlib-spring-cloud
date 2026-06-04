package ch.ubique.backend.spring.cloud.feign.handler;

import static java.util.Objects.requireNonNull;

import ch.ubique.backend.spring.boot.problem.handler.UetlibExceptionHandlerSkeletonTrait;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import feign.FeignException.FeignServerException;

import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings("unused")
public interface FeignExceptionAdviceTrait extends UetlibExceptionHandlerSkeletonTrait {

    /**
     * Handle all exceptions raised within Spring MVC handling of the request. This is an extension
     * to {@link ResponseEntityExceptionHandler#handleException(Exception, WebRequest)} for more
     * exceptions.
     *
     * @param ex the exception to handle
     * @param request the current request
     */
    @ExceptionHandler(FeignException.class)
    @Nullable
    default ResponseEntity<Object> handleFeign(final FeignException ex, final WebRequest request) {
        final var headers = new HttpHeaders();
        return switch (ex) {
            case final FeignClientException subEx -> handleFeignClient(subEx, headers, request);
            case final FeignServerException subEx -> handleFeignServer(subEx, headers, request);
            default -> throw ex;
        };
    }

    /**
     * Customize the handling of {@link FeignClientException}.
     *
     * <p>By default, this method creates a {@link ProblemDetail} with the status and a short detail
     * message, and also looks up an override for the detail via {@link MessageSource}, before
     * delegating to {@link #handleExceptionInternal}.
     *
     * @param ex the exception to handle
     * @param headers the headers to use for the response
     * @param request the current request
     * @return a {@code ResponseEntity} for the response to use, possibly {@code null} when the
     *     response is already committed
     */
    @Nullable
    default ResponseEntity<Object> handleFeignClient(
            final FeignClientException ex, final HttpHeaders headers, final WebRequest request) {
        final var status = resolveStatusCodeOrDefault(ex.status(), HttpStatus.BAD_REQUEST);
        final var body =
                createProblemDetail(
                        ex, status, requireNonNull(ex.getLocalizedMessage()), null, null, request);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * Customize the handling of {@link FeignServerException}.
     *
     * <p>By default, this method creates a {@link ProblemDetail} with the status and a short detail
     * message, and also looks up an override for the detail via {@link MessageSource}, before
     * delegating to {@link #handleExceptionInternal}.
     *
     * @param ex the exception to handle
     * @param headers the headers to use for the response
     * @param request the current request
     * @return a {@code ResponseEntity} for the response to use, possibly {@code null} when the
     *     response is already committed
     */
    @Nullable
    default ResponseEntity<Object> handleFeignServer(
            final FeignServerException ex, final HttpHeaders headers, final WebRequest request) {
        final var status =
                resolveStatusCodeOrDefault(ex.status(), HttpStatus.INTERNAL_SERVER_ERROR);
        final var body =
                createProblemDetail(
                        ex, status, requireNonNull(ex.getLocalizedMessage()), null, null, request);
        return handleExceptionInternal(ex, body, headers, status, request);
    }
}
