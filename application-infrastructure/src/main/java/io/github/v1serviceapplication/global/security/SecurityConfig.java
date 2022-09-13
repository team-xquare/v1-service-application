package io.github.v1serviceapplication.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .antMatchers(HttpMethod.POST, "/study-room").hasAuthority("ROLE_STU")
                .antMatchers(HttpMethod.GET, "/study-room/status").hasAuthority("ROLE_STU")
                .antMatchers(HttpMethod.POST, "/picnic").hasAuthority("ROLE_STU")
                .antMatchers(HttpMethod.PUT, "/stay").hasAuthority("ROLE_STU")
                .antMatchers(HttpMethod.GET, "/stay").hasAuthority("ROLE_STU")
                .antMatchers(HttpMethod.GET, "/stay/codes/status").permitAll()
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/weekend-meal").hasAuthority("ROLE_STU")
                .antMatchers(HttpMethod.GET, "/weekend-meal").hasAuthority("ROLE_STU")
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new FilterConfig(objectMapper));

    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
}
