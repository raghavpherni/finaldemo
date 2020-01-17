package com.stackroute.keepnote.jwtfilter;


import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;



/* This class implements the custom filter by extending org.springframework.web.filter.GenericFilterBean.  
 * Override the doFilter method with ServletRequest, ServletResponse and FilterChain.
 * This is used to authorize the API access for the application.
 */


public class JwtFilter extends GenericFilterBean {

	
	
	

	/*
	 * Override the doFilter method of GenericFilterBean.
     * Retrieve the "authorization" header from the HttpServletRequest object.
     * Retrieve the "Bearer" token from "authorization" header.
     * If authorization header is invalid, throw Exception with message. 
     * Parse the JWT token and get claims from the token using the secret key
     * Set the request attribute with the retrieved claims
     * Call FilterChain object's doFilter() method */
	
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		final String authHeader = req.getHeader("authorization");
		//final String authHeader ="Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJKaG9uMTIzIiwic3ViIjoiMTIzNDU2IiwiaWF0IjoxNTc4OTE0Nzg0fQ.A05HnyZFt4dmGY1yKyDNX2KnKlPBr_6fhhvOAy2ERpg";
		// Enumeration<String> headerNames = req.getHeaderNames();
//		while (headerNames.hasMoreElements()) {
//			req.get
//			String string = (String) headerNames.nextElement();
//			System.out.println(string);
//		}
		System.out.println("authorization "+authHeader);
		if ("OPTIONS".equals(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(req, res);
		} else {

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new ServletException("Missing or invalid Authorization header");
		}
		final String token = authHeader.substring(7);
		final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		request.setAttribute("claims", claims);
		chain.doFilter(req, res);
		}
    }
}
