package com.prj.travelRecord.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
    	log.info("doFilter");
    	// 헤더에서 JWT 를 받아옵니다.
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
		// 유효한 토큰인지 확인합니다.
		if (token != null && jwtTokenProvider.validateToken(token)) {
			// 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			// SecurityContext 에 Authentication 객체를 저장합니다.
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("토큰 유효하다");
		}
		filterChain.doFilter(servletRequest, servletResponse);
    }

	
}
