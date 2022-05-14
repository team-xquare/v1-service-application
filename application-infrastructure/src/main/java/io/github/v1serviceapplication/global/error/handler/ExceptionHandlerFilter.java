package io.github.v1serviceapplication.global.error.handler;

import io.github.v1serviceapplication.global.error.exception.BadRequestException;
import io.github.v1serviceapplication.global.error.exception.GlobalException;
import io.github.v1serviceapplication.global.error.exception.NotFoundException;
import io.github.v1serviceapplication.global.error.exception.UnexpectedException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (NestedServletException exception) {
            Throwable throwable = exception.getCause();

            logger.error("Error Occurred: " + throwable.getMessage());

            if (throwable instanceof GlobalException globalException) {

                sendErrorMessage(globalException, response);

            } else if (throwable instanceof MethodArgumentNotValidException ||
                    throwable instanceof MethodArgumentTypeMismatchException) {

                sendErrorMessage(BadRequestException.EXCEPTION, response);

            } else if(throwable instanceof NoHandlerFoundException) {

                sendErrorMessage(NotFoundException.EXCEPTION, response);

            } else {

                logger.error("It was Unexpected Error! \n" + throwable);
                sendErrorMessage(UnexpectedException.EXCEPTION, response);

            }

        }
    }

    private void sendErrorMessage(GlobalException exception, HttpServletResponse response) throws IOException {
        var errorResponse = ErrorResponse.builder()
                .message(exception.getErrorAttribute().getMessage())
                .status(exception.getErrorAttribute().getStatus())
                .build();

        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(exception.getErrorAttribute().getStatus());
        response.getWriter().write(errorResponse.toString());
    }
}