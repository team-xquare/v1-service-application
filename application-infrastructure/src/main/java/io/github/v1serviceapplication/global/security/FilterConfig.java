package io.github.v1serviceapplication.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.v1serviceapplication.global.error.ErrorHandlingFilter;
import io.github.v1serviceapplication.global.security.jwt.JwtParser;
import io.github.v1serviceapplication.global.security.jwt.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtParser jwtParser;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity builder) {
        TokenFilter tokenFilter = new TokenFilter(jwtParser);
        ErrorHandlingFilter errorHandlingFilter = new ErrorHandlingFilter(objectMapper);
        builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        builder.addFilterBefore(errorHandlingFilter, TokenFilter.class);
    }
}
