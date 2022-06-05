package io.github.v1serviceapplication.global.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.*;
import io.github.v1serviceapplication.global.error.exception.JwtInvalidSignatureException;
import io.github.v1serviceapplication.global.error.exception.JwtTokenExpiredException;
import io.github.v1serviceapplication.global.security.property.JwtProperty;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtParser {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORITY_KEY = "authorities";

    private final JwtProperty jwtProperty;

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.replace(TOKEN_PREFIX, "");
        }
        return null;
    }

    public Authentication authenticateUser(String token) throws ParseException, JOSEException {
        JWTClaimsSet claims = getClaims(token);
        List<String> authoritiesClaim = (List<String>)claims.getClaim(AUTHORITY_KEY);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String aut : authoritiesClaim) {
            authorities.add(new SimpleGrantedAuthority(aut));
        }
        UserDetails userDetails = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private JWTClaimsSet getClaims(String token) throws ParseException, JOSEException {
        try {
            JWSVerifier verifier = new MACVerifier(jwtProperty.getSecret());
            Date now = new Date();
            SignedJWT signedJWT = SignedJWT.parse(token);

            if (signedJWT.verify(verifier) && signedJWT.getJWTClaimsSet().getExpirationTime().after(now)) {
                return signedJWT.getJWTClaimsSet();
            } else {
                if (!signedJWT.verify(verifier)) {
                    throw JwtInvalidSignatureException.EXCEPTION;
                } else {
                    throw JwtTokenExpiredException.EXCEPTION;
                }
            }
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorOffset());
        } catch (JOSEException e) {
            throw new JOSEException(e.getMessage());
        }
    }
}
