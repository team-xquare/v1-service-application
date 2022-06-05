package io.github.v1serviceapplication.global.security.jwt;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JwtParser jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtParser.getBearerToken(request);
        if (token != null) {
            Authentication authentication = null;
            try {
                authentication = jwtParser.authenticateUser(token);
            } catch (ParseException | JOSEException e) {
                e.printStackTrace();
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
