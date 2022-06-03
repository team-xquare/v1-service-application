package io.github.v1serviceapplication.global.security.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix= "auth.jwt")
public class JwtProperty {
    private final String secret;
}
