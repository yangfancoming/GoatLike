//package com.solituder.jwt;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
////import org.json.JSONObject;
//import com.solituder.model.resultmodel.JSONResult;
//import com.solituder.utils.GoatInfo;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Map;
//
///**
// * Created by 64274 on 2018/5/19.
// */
//public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter
//{
//    public JWTLoginFilter(String url, AuthenticationManager authManager) {
//        super(new AntPathRequestMatcher(url));
//        setAuthenticationManager(authManager);
//    }
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)throws AuthenticationException, IOException, ServletException
//    {
////        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
////        StringBuffer sb = new StringBuffer(""); //  {"_name":"123","_class":"123","_sid":"123","_sex":"1","_age":123}
////        String temp;
////        while ((temp = br.readLine()) != null) {sb.append(temp);}
////        br.close();
////        Map maps = (Map) JSON.parse(sb.toString());
////        String username = (String) maps.get("username");
////        String passwrod = (String) maps.get("passwrod");
//        ServletInputStream fuck = req.getInputStream();
//        AccountCredentials creds = new ObjectMapper().readValue(fuck, AccountCredentials.class);  // JSON反序列化成 AccountCredentials
////        return getAuthenticationManager().authenticate( new UsernamePasswordAuthenticationToken(username,passwrod));  // 返回一个验证令牌
//        return getAuthenticationManager().authenticate( new UsernamePasswordAuthenticationToken(creds.getUsername(),creds.getPassword()));  // 返回一个验证令牌
//    }
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException
//    {
//        JwtUtil.generateToken(res, auth.getName());
//    }
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
//    {
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getOutputStream().println(JSONResult.fillResultString(500, "Internal Server Error!!!", null));
//    }
//}
