package com.solituder.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solituder.security.CustomCorsFilter;
import com.solituder.security.UnauthorizedEntryPoint;
import com.solituder.security.auth.ajax.AjaxAuthenticationProvider;
import com.solituder.security.auth.ajax.AjaxLoginProcessingFilter;
import com.solituder.security.auth.jwt.JwtAuthenticationProvider;
import com.solituder.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.solituder.security.auth.jwt.SkipPathRequestMatcher;
import com.solituder.security.auth.jwt.extractor.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * WebSecurityConfig
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // fuck 此句注解功能是  使得controller方法上的注解生效！！！   @PreAuthorize("hasAuthority('fuck')")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String API_ROOT_URL = "/api/**";
    /**
     @Autowired相当于setter，在注入之前，对象已经实例化，是在这个接口注解的时候实例化的；
     而new只是实例化一个对象，而且new的对象不能调用注入的其他类
     */
    @Autowired private UnauthorizedEntryPoint unauthorizedEntryPoint;
//    AjaxLoginProcessingFilter
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;
    @Autowired private ObjectMapper objectMapper;
//    AuthenticationManagerBuilder
    @Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired private TokenExtractor tokenExtractor;
    @Autowired private AuthenticationManager authenticationManager;



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndpointList = Arrays.asList(AUTHENTICATION_URL,REFRESH_TOKEN_URL,"/console" );
        http
            .csrf().disable() // 我们已经禁用CSRF保护，因为我们不使用Cookie
            .exceptionHandling().authenticationEntryPoint(this.unauthorizedEntryPoint) // 当用户没有权限访问某个资源的时候，你可以在这里自定义返回内容
            .and()
                /**
                 SessionCreationPolicy.STATELESS 无状态的Session机制（即Spring不使用HTTPSession），
                 对于所有的请求都做权限校验，这样Spring Security的拦截器会判断所有请求的Header上有没有”X-Auth-Token”。对于异常情况（即当Spring Security发现没有），
                 Spring会启用一个认证入口：unauthorizedEntryPoint  这个入口只是简单的返回一个401即可
                 */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
                .permitAll() //  "/api/auth/login"   "/api/auth/token"  "/console"   不拦截 这些url
            .and()
                .authorizeRequests()
                .antMatchers(API_ROOT_URL).authenticated() // Protected API End-points  对所有符合 "/api/**" 的url 进行拦截

            .and()  //将自定义的 Filter 加入到 Spring Security 中的 Filter 链中的指定位置
                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList,API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);

    }
    //    AjaxLoginProcessingFilter( Ajax 登录处理过滤器 )
    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter(String loginEntryPoint) throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(loginEntryPoint, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
        JwtTokenAuthenticationProcessingFilter filter  = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }
}
