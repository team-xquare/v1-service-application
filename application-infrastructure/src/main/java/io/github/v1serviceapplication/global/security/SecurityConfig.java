package io.github.v1serviceapplication.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.v1serviceapplication.global.role.UserRole;
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

    private final ObjectMapper objectMapper;


    private static final String STUDENT = "ROLE_" + UserRole.STU.name();
    private static final String SCHOOL = "ROLE_" + UserRole.SCH.name();
    private static final String DORMITORY = "ROLE_" + UserRole.DOR.name();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().disable()
                .formLogin().disable()

                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/applications/study-room/**", "/applications/weekend-meal", "/applications/picnic", "/applications/stay/**").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.POST, "/stay/signup").permitAll()
                .antMatchers(HttpMethod.DELETE, "/stay/signup/{user-uuid}").permitAll()
                .antMatchers(HttpMethod.POST, "/picnic-reservation").permitAll()
                .antMatchers(HttpMethod.DELETE, "/picnic-reservation/{picnic-reservation-id}").permitAll()
                .antMatchers(HttpMethod.GET, "/picnic-reservation").authenticated()
                .antMatchers("/applications/admin/**").hasAnyRole(DORMITORY)
                .antMatchers("/applications/swagger-ui/**").permitAll()
                .antMatchers("/applications/docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new FilterConfig(objectMapper));

    }
}
