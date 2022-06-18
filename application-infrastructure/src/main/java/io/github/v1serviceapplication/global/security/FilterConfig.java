package io.github.v1serviceapplication.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.v1serviceapplication.global.error.ErrorHandlingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity builder) {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        ErrorHandlingFilter errorHandlingFilter = new ErrorHandlingFilter(objectMapper);
        builder.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        builder.addFilterBefore(errorHandlingFilter, AuthenticationFilter.class);
    }
}
