package io.github.v1serviceapplication.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.v1serviceapplication.global.security.jwt.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtParser jwtParser;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().disable()
                .formLogin().disable()

                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(HttpMethod.GET, "/study-room").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new FilterConfig(jwtParser, objectMapper));

    }
}
