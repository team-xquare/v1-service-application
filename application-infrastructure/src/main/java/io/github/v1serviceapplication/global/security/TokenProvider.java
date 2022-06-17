package io.github.v1serviceapplication.global.security;

import io.github.v1serviceapplication.global.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@RequiredArgsConstructor
@Component
public class TokenProvider {
    public Authentication authenticateUser(String userId, UserRole userRole, List<String> userAuthorities) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (userAuthorities != null) {
            for (String userAuthority : userAuthorities) {
                authorities.add(new SimpleGrantedAuthority(userAuthority));
            }
        }
        authorities.add(new SimpleGrantedAuthority(userRole.name()));
        UserDetails userDetails = new User(userId, "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}