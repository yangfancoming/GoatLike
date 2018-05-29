//package com.solituder.security;
//
//import com.solituder.jwt.JWTAuthenticationFilter;
//import com.solituder.jwt.JWTLoginFilter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// * Created by 64274 on 2018/5/19.
// * @EnableGlobalMethodSecurity(prePostEnabled=true)
// */
////@Configuration
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true) // fuck 此句注解功能是  使得controller方法上的注解生效！！！   @PreAuthorize("hasAuthority('fuck')")
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter
//{
//    // 设置 HTTP 验证规则
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()   // 由于使用的是JWT，我们这里不需要csrf
//                //                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 基于token，所以不需要session
//                .authorizeRequests()  // 对请求进行认证
//                .antMatchers("/").permitAll() // 所有 / 的所有请求 都放行
//                .antMatchers(HttpMethod.POST, "/login").permitAll()  // 所有 /login 的POST请求 都放行
//                .antMatchers("/hello").hasAuthority("AUTH_WRITE")  // fuck 尼玛 权限和身份 是区别大小写的 我草 对/hello 请求 进行权限检测
//                //配置角色：注意Spring Security4之后，这里不必添加ROLE_前缀，Spring Security4会自动补上的。 注意:角色大小写敏感
//                .antMatchers(HttpMethod.POST,"/world").hasRole("ADMIN") // 对/world 请求 进行角色检测
//                .anyRequest().authenticated()  // 所有请求需要身份认证
//                .and() //and()是返回一个securityBuilder对象
//                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class) // 添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来处理 这个类处理所有的JWT相关内容
//                .addFilterBefore(new JWTAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class); // 添加一个过滤器验证其他请求的Token是否合法
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(new CustomAuthenticationProvider());  // 使用自定义身份验证组件
//    }
//}
