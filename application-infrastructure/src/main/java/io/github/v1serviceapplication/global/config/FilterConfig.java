package io.github.v1serviceapplication.global.config;

import io.github.v1serviceapplication.global.error.handler.ExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Override
    public void configure(HttpSecurity builder) {
        ExceptionHandlerFilter exceptionFilter = new ExceptionHandlerFilter();
        builder.addFilter(exceptionFilter);
    }
}
