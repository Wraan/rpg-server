package com.rpg.service.security;

import com.rpg.exception.TokenException;
import com.rpg.model.security.User;
import com.rpg.repository.security.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class JsonWebTokenAuthenticationService {

    private Logger LOGGER = LogManager.getLogger(getClass());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${jwt.signing.key")
    private String SIGNING_KEY;

    @Autowired private UserDetailsService userDetailsService;

    public Authentication authenticate(HttpServletRequest request) {
        final String token = request.getHeader("x-auth-token");
        final Map<String, Object> tokenData = parseToken(token);

        if (Objects.nonNull(tokenData)) {
            UserDetails userDetails = getUserFromToken(tokenData);
            if (Objects.nonNull(userDetails) && userDetails.isEnabled()) {
                return new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
            }
        }
        return null;
    }

    public Authentication getUserFromToken(String token) {
        if (Objects.isNull(token))
            return null;

        final Map<String, Object> tokenData = parseToken(token);

        if (Objects.nonNull(tokenData)) {
            UserDetails userDetails = getUserFromToken(tokenData);
            if (Objects.nonNull(userDetails) && userDetails.isEnabled()) {
                return new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
            }
        }
        return null;
    }

    private Map<String, Object> parseToken(final String token) {
        if (Objects.nonNull(token)) {
            try {
//                Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
            Jwt jwt = JwtHelper.decode(token);
            Map<String, Object> claims = objectMapper.readValue(jwt.getClaims(), Map.class);
                int exp = (int) claims.get("exp");
                if(isTokenExpired(exp))
                    throw new TokenException("Token is expired");
                return claims;
            } catch (Exception e) {
                System.out.println("Token parse failed: " +  e);
                LOGGER.warn("Token parse failed", e);
                return null;
            }
        }
        return null;
    }

    private UserDetails getUserFromToken(final Map<String, Object> tokenData) {
        try {
            return userDetailsService.loadUserByUsername(tokenData.get("username").toString());
        } catch (UsernameNotFoundException e) {
            LOGGER.warn("No user", e);
        }
        return null;
    }

    private boolean isTokenExpired(long exp) {
        Date expiry = new Date(exp * 1000L);
        Date now = Calendar.getInstance().getTime();
        return now.compareTo(expiry) > 0;
    }
}
