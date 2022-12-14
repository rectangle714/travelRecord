package com.prj.travelRecord.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import com.prj.travelRecord.config.security.JwtAuthenticationFilter;
import com.prj.travelRecord.config.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public HttpFirewall DefaultHttpFirewall() {
		return new DefaultHttpFirewall();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/h2-console/**","/api/v2/**","/swagger-ui.html","/swagger/**",
				"/swagger-resources/**","/v3/api-docs","/swagger-ui/**").permitAll(); // 누구나 h2-console 접속 허용, swagger 접속 허용
		http.httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
				.csrf().disable() // csrf 보안 토큰 disable처리.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지
				.and().authorizeRequests() // 요청에 대한 사용권한 체크
				.antMatchers("/login", "/join").permitAll().antMatchers("/admin/**").hasRole("ADMIN").anyRequest()
				.authenticated() // 그외 나머지 요청은 누구나 접근 가능
				.and().addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
						UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests(); // 권한요청 처리 설정 메서드
		return http.build();
	}
	


}
