package com.solituder.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author vladimir.stankovic
 *当用户没有权限访问某个资源的时候，你可以在这里自定义返回内容
 * Aug 4, 2016
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint
{
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)throws IOException, ServletException{
		response.sendError(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.name() ); //"Unauthorized"
	}
}
