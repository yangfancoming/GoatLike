//package com.solituder.security;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.ArrayList;
//
///**
// * Created by 64274 on 2018/5/19.
// */
//public class CustomAuthenticationProvider implements AuthenticationProvider // 自定义身份认证验证组件
//{
//    @Override
//    public boolean supports(Class<?> authentication) {  // 是否可以提供输入类型的认证服务
//        boolean mark = authentication.equals(UsernamePasswordAuthenticationToken.class);
//        return mark;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException
//    {
//        // 获取认证的用户名 & 密码
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        // 认证逻辑
//        if (name.equals("admin") && password.equals("123456")) { //  fuck  这里 验证硬编码的账号/密码
//            ArrayList<GrantedAuthority> authorities = new ArrayList<>();  // 这里设置权限和角色
//            authorities.add( new GrantedAuthorityImpl("ROLE_ADMIN") );
//            //            authorities.add( new GrantedAuthorityImpl("AUTH_WRITE") );
//            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);// 生成令牌
//            return auth;
//        }else {
//            throw new BadCredentialsException("密码错误~");
//        }
//    }
//}
