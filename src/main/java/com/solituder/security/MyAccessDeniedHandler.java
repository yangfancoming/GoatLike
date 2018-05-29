//package com.solituder.security;
//
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @version V1.0.0
// * @Description 无权限访问时触发
// * @Author liuyuequn weanyq@gmail.com
// * @Date 2017/8/18 9:34
// */
//@Component
//public class MyAccessDeniedHandler implements AccessDeniedHandler
//{
//    @Override
//    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e) throws IOException, ServletException
//    {
//        //返回json形式的错误信息
//        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        res.setCharacterEncoding("UTF-8");
//        res.setContentType("application/json");
//        res.getWriter().println("{\"code\":403,\"message\":\"小弟弟，你没有权限访问呀！\",\"data\":\"\"}");
//        res.getWriter().flush();
//    }
//}
