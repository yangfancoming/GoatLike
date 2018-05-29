//package com.solituder.jwt;
//
//import com.solituder.model.resultmodel.JSONResult;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by 64274 on 2018/5/19.
// */
//public class JwtUtil
//{
//    static final long EXPIRATIONTIME = 432_000_000;     // 5天
//    static final String SECRET = "P@ssw02d";            // JWT密码
//    static final String TOKEN_PREFIX = "Bearer";        // Token前缀
//    static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
//
//    static void generateToken(HttpServletResponse response, String username) {
//        HashMap<String, Object> map = new HashMap<>(); //you can put any data in the map
//        map.put("roles", "ADMIN");
//        map.put("authorities", "AUTH_WRITE");
//        String JWT = Jwts.builder() // 生成JWT
//                .setClaims(map)
////                .claim("authorities", "ROLE_ADMIN")  // 保存权限（角色）到token中， 数据声明（Claim）其实就是一个Map，比如我们想放入用户名，可以简单的创建一个Map然后put进去就可以了
//                .setSubject(username)  // 用户名写入标题
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)) // 有效期设置
//                .signWith(SignatureAlgorithm.HS512, SECRET)  // 签名设置
//                .compact();
//        try {
//            response.setContentType("application/json"); // 将 JWT 写入 body
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getOutputStream().println(JSONResult.fillResultString(0, "", JWT));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    static Authentication parseToken(HttpServletRequest request) {
//        String token = request.getHeader(HEADER_STRING); // 从Header中拿到token
//        if(token == null) throw new TokenValidationException("Missing token");
//        Claims claims = Jwts.parser() // 解析 Token
//                .setSigningKey(SECRET)  // 验签
//                .parseClaimsJws(token.replace(TOKEN_PREFIX, "")) // 去掉 Bearer
//                .getBody();
//        String user = claims.getSubject();  // 拿用户名
//        List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities")); //从token中 得到 权限（角色）
//        return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities) :null;  // 返回验证令牌
//    }
//
//    static class TokenValidationException extends RuntimeException {
//        public TokenValidationException(String msg) {
//            super(msg);
//        }
//    }
//}
