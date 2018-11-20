package com.solituder.security.auth.jwt;

//import com.svlada.security.auth.JwtAuthenticationToken;
//import com.svlada.security.auth.jwt.extractor.TokenExtractor;
//import com.svlada.security.config.WebSecurityConfig;
//import com.svlada.security.model.token.RawAccessJwtToken;
import com.solituder.security.auth.JwtAuthenticationToken;
import com.solituder.security.auth.jwt.extractor.TokenExtractor;
import com.solituder.security.auth.token.RawAccessJwtToken;
import com.solituder.security.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Performs validation of provided JWT Token.
 * 
 * @author vladimir.stankovic
 *  JwtTokenAuthenticationProcessingFilter 过滤器 被应用到了每一个API（/api/**） 异常的刷新令牌端点（/api/auth/token）以及 login 点（/api/auth/login）。
    这个过滤器拥有一下的一些职责：
    1. 检查访问令牌在X-Authorization头。如果发现访问令牌的头,委托认证JwtAuthenticationProvider否则抛出身份验证异常
    2. 调用成功或失败策略基于由JwtAuthenticationProvider执行身份验证过程的结果

    确保chain.doFilter(request, response) 被调用,成功的验证了身份。你想在下一个处理器中，优先处理这些请求, 因为最后一个过滤器 FilterSecurityInterceptor#doFilter
    会响应的实际调用方法是在Controller 中的处理访问API 资源的方法。
 * Aug 5, 2016
 */
public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter
{
    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;
    
    @Autowired
    public JwtTokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler,TokenExtractor tokenExtractor, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException, IOException, ServletException{
        String tokenPayload = request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME); // sos 注意 这里 是后台要求的 token 请求头标志！
        RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,Authentication authResult) throws IOException, ServletException{
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,AuthenticationException failed) throws IOException, ServletException{
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
