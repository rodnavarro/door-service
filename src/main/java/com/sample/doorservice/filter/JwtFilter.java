package com.sample.doorservice.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.doorservice.model.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sample.doorservice.service.DoorsService;
import com.sample.doorservice.util.JwtUtil;


@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtill;
	
	@Autowired
	private DoorsService service;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4YWRtaW4iLCJleHAiOjE1OTUxODMyMTcsImlhdCI6MTU5NTE0NzIxN30.S9w8LROFAKYCWU2nsHu07FcWGfI5vwesC4MvsqzGDyo
		String autherizationHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		try {
			if (autherizationHeader != null && autherizationHeader.startsWith("Bearer")) {
				token = autherizationHeader.substring(7);
				username = jwtUtill.extractUsername(token);
			}
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = service.loadUserByUsername(username);

				if (jwtUtill.validateToken(token, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception exception) {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(exception.getMessage());
			response.addHeader("Content-Type", "application/json");
			response.getWriter().print(mapper.writeValueAsString(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception.getMessage())));
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

}
