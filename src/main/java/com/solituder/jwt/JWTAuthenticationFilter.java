//package com.solituder.jwt;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Created by 64274 on 2018/5/29.
// */
//public class JWTAuthenticationFilter extends OncePerRequestFilter
//{
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
//    {
//        //        Integer.pa
//        try {
//            Authentication authentication = JwtUtil.parseToken(request); //最关键的部分就是这里, 我们直接注入了
//            SecurityContextHolder.getContext().setAuthentication(authentication );
//        } catch (Exception e) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
//            return;
//        }
//        filterChain.doFilter(request, response);
//    }
//}
