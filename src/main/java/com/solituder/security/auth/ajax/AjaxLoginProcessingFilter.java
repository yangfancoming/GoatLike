package com.solituder.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solituder.common.WebUtil;
import com.solituder.exceptions.AuthMethodNotSupportedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AjaxLoginProcessingFilter
 * 
 * @author vladimir.stankovic
 *
 * 首先，需要继承AbstractAuthenticationProcessingFilter, 目的是为了提供一般常用的Ajax 身份验证请求。
 * 反序列化JSON和基本验证的主要任务都是在的。
 * AjaxLoginProcessingFilter#attemptAuthentication这个方法里完成的。
 * 在成功验证JSON的主要检验逻辑是委托给 AjaxAuthenticationProvider 类实现。
 *
 * 在一个成功校验中， AjaxLoginProcessingFilter#successfulAuthentication 方法会被调用。
   在一个失败的检验中，AjaxLoginProcessingFilter#unsuccessfulAuthentication 方法被调用。
 *
 * Aug 3, 2016
 */
public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter
{
    private static Logger logger = LoggerFactory.getLogger(AjaxLoginProcessingFilter.class);

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    private final ObjectMapper objectMapper;
    
    public AjaxLoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException, IOException, ServletException
    {

//        if (! HttpMethod.POST.name().equals(request.getMethod()) || ! WebUtil.isAjax(request)) {
        if (! HttpMethod.POST.name().equals(request.getMethod()) ) {
            if(logger.isDebugEnabled()) {
                logger.debug("Authentication method not supported. Request method: " + request.getMethod());
            }
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }
        LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
        
        if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,Authentication authResult) throws IOException, ServletException{
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,AuthenticationException failed) throws IOException, ServletException{
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
