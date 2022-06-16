package io.github.v1serviceapplication.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.v1serviceapplication.global.error.ErrorHandlingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity builder) {
        TokenFilter tokenFilter = new TokenFilter(tokenProvider);
        ErrorHandlingFilter errorHandlingFilter = new ErrorHandlingFilter(objectMapper);
        builder.addFilterAt(tokenFilter, TokenFilter.class);
        builder.addFilterBefore(errorHandlingFilter, TokenFilter.class);
    }
}
