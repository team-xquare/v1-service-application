package io.github.v1serviceapplication.global.security;

import io.github.v1serviceapplication.global.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader("Request-User-Id") != null ? request.getHeader("Request-User-Id") : null;
        UserRole userRole = request.getHeader("Request-User-Role") != null ? UserRole.valueOf(request.getHeader("Request-User-Role")) : null;
        List<String> userAuthorities = request.getHeader("Request-User-Authorities") != null ? Collections.singletonList(request.getHeader("Request-User-Authorities")) : null;

        if (userId != null) {
            Authentication authentication = tokenProvider.authenticateUser(userId, userRole, userAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}