package io.github.v1serviceapplication.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
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
                .antMatchers(HttpMethod.POST, "/study-room").permitAll()
                .antMatchers(HttpMethod.DELETE, "/study-room/{study_room_id}").permitAll()
                .antMatchers(HttpMethod.GET, "/study-room/status").permitAll()
                .antMatchers(HttpMethod.GET, "/weekend-meal").permitAll()
                .antMatchers(HttpMethod.POST, "/weekend-meal").permitAll()
                .antMatchers(HttpMethod.POST, "/picnic").permitAll()
                .antMatchers(HttpMethod.PUT, "/stay").permitAll()
                .antMatchers(HttpMethod.GET, "/stay").permitAll()
                .antMatchers(HttpMethod.GET, "/stay/codes/status").permitAll()
                .antMatchers(HttpMethod.POST, "/stay/signup").permitAll()
                .antMatchers(HttpMethod.DELETE, "/stay/signup/{user-uuid}").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/weekend-meal").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/picnic").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/stay").permitAll()
                .antMatchers(HttpMethod.PATCH, "/admin/picnic/arrive/{picnic-id}").permitAll()
                .antMatchers(HttpMethod.PATCH, "/admin/picnic/accept/{picnic-id}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/admin/picnic/refuse/{picnic-id}").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/{student-id}").permitAll()
                .antMatchers(HttpMethod.PUT, "/admin/stay/{student-id}").permitAll()
                .antMatchers(HttpMethod.GET, "/stay/list").permitAll()
                .antMatchers(HttpMethod.GET, "/stay/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/weekend-meal/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/picnic/excel").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new FilterConfig(objectMapper));

    }
}
