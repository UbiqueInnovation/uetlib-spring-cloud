package ch.ubique.backend.spring.cloud.feign.handler;

import ch.ubique.backend.spring.boot.problem.handler.ResponseEntityExceptionSkeleton;

import feign.FeignException;

import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings("unused")
public interface FeignExceptionAdviceTrait extends ResponseEntityExceptionSkeleton {

    /**
     * Handle all exceptions raised within Spring MVC handling of the request. This is an extension
     * to {@link ResponseEntityExceptionHandler#handleException(Exception, WebRequest)} for more
     * exceptions.
     *
     * @param ex the exception to handle
     * @param request the current request
     */
    // add more here
    @ExceptionHandler(FeignException.class)
    @Nullable
    default ResponseEntity<Object> handleFeign(final Exception ex, final WebRequest request)
            throws Exception {
        final var headers = new HttpHeaders();
        return switch (ex) {
            case final FeignException subEx -> handleFeign(subEx, headers, request);
            default -> throw ex;
        };
    }

    /**
     * Customise the handling of {@link FeignException}.
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
    default ResponseEntity<Object> handleFeign(
            final FeignException ex, final HttpHeaders headers, final WebRequest request) {
        final var status = HttpStatusCode.valueOf(ex.status());
        final var body =
                createProblemDetail(ex, status, ex.getLocalizedMessage(), null, null, request);
        return handleExceptionInternal(ex, body, headers, status, request);
    }
}
