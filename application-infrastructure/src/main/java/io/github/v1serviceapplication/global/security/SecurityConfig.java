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
                .antMatchers(HttpMethod.GET, "/study-room").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.POST, "/study-room").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.DELETE, "/study-room/{study_room_id}").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.GET, "/study-room/status").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.GET, "/weekend-meal").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.POST, "/weekend-meal").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.POST, "/picnic").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.PUT, "/stay").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.GET, "/stay").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.GET, "/stay/codes/status").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.POST, "/stay/signup").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.DELETE, "/stay/signup/{user-uuid}").hasAnyRole(STUDENT, SCHOOL)
                .antMatchers(HttpMethod.GET, "/admin/weekend-meal").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/admin/picnic").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/admin/stay").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/weekend-meal/excel").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.PATCH, "/admin/picnic/arrive/{picnic-id}").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.PATCH, "/admin/picnic/accept/{picnic-id}").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.DELETE, "/admin/picnic/refuse/{picnic-id}").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/admin/stay/{student-id}").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.PUT, "/admin/stay/{student-id}").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/stay/list").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/stay/excel").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/weekend-meal/excel").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/picnic/excel").hasAnyRole(DORMITORY)
                .antMatchers(HttpMethod.GET, "/admin/picnic/detail/{picnic-id}").hasAnyRole(DORMITORY)
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new FilterConfig(objectMapper));

    }
}
