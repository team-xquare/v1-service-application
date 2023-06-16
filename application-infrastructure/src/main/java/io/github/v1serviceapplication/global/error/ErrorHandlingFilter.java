package io.github.v1serviceapplication.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ErrorHandlingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApplicationException e) {
            errorToJson(e.getErrorCode(), response);
        } catch (Exception e) {
            if (e.getCause() instanceof ApplicationException) {
                errorToJson(((ApplicationException) e.getCause()).getErrorCode(), response);
            } else {
                errorToJson(ErrorCode.INTERNAL_SERVER_ERROR, response);
                Sentry.captureException(e);
            }
        }
    }

    private void errorToJson(ErrorCode errorCode, HttpServletResponse response) throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        new ErrorResponse(errorCode)
                )
        );
    }
}
