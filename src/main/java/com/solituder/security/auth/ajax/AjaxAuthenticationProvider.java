package com.solituder.security.auth.ajax;

//import com.svlada.entity.User;
//import com.svlada.security.model.UserContext;
//import com.svlada.user.service.DatabaseUserService;
import com.solituder.model.User;
import com.solituder.security.auth.token.UserContext;
import com.solituder.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider
{
    @Autowired private BaseService baseService;
    private final BCryptPasswordEncoder encoder;
//    private final DatabaseUserService userService;


    @Autowired
    public AjaxAuthenticationProvider(final BaseService baseService, final BCryptPasswordEncoder encoder) {
        this.baseService = baseService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)throws AuthenticationException
    {
        Assert.notNull(authentication, "No authentication data provided");
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        Map user = null;
        try{
            user = baseService.findForObject("Sys_userMapper.findByUserName",username);
        } catch (Exception e){ e.printStackTrace();}
        if(user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        String temp = (String) user.get("password");
        if (!encoder.matches(password, temp)) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }
//        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
//                .collect(Collectors.toList());
        List<GrantedAuthority> authorities = new ArrayList<>();  // 这里设置权限和角色
        authorities.add( new SimpleGrantedAuthority("ROLE_ADMIN") );
        UserContext userContext = UserContext.create(username, authorities);
        Authentication auth = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        return auth;
//        return new UsernamePasswordAuthenticationToken(null, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
