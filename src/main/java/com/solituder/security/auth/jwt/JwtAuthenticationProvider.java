package com.solituder.security.auth.jwt;

//import com.svlada.security.auth.JwtAuthenticationToken;
//import com.svlada.security.config.JwtSettings;
//import com.svlada.security.model.UserContext;
//import com.svlada.security.model.token.JwtToken;
//import com.svlada.security.model.token.RawAccessJwtToken;
import com.solituder.security.auth.JwtAuthenticationToken;
import com.solituder.security.auth.token.RawAccessJwtToken;
import com.solituder.security.auth.token.UserContext;
import com.solituder.security.config.JwtSettings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
// * instance of {@link //JwtToken} to perform authentication.
 JwtTokenAuthenticationProcessingFilter
 JwtAuthenticationProvider
 SkipPathRequestMatcher
 JwtHeaderTokenExtractor
 BloomFilterTokenVerifier
 WebSecurityConfig
 * @author vladimir.stankovic
 *
 * Aug 5, 2016
 *
 * JwtAuthenticationProvider 拥有一下的一些职责：
1. 验证 access token 的签名
2. 从访问令牌中提取身份和授权声明和使用它们来创建UserContext
3. 如果访问令牌是畸形的,过期的或者只是如果令牌不签署与适当的签名密钥身份验证就会抛出异常
 */
@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtSettings jwtSettings;
    
    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject();
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UserContext context = UserContext.create(subject, authorities);
        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
