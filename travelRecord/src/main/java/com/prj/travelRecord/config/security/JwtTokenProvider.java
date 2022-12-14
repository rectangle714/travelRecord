package com.prj.travelRecord.config.security;
//import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.prj.travelRecord.domain.MemberRole;
import com.prj.travelRecord.member.service.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	
	//private String secretKey = "약오르지까꿍꿍까지르오약약오르지까꿍꿍까지르오약약오르지까꿍꿍까지르오약";
	@Value("${jwt.secret}")
	private String secretKey;

	// 토큰 유효시간 30분
	private long tokenValidTime = 30 * 60 * 1000L;
	private final MemberService memberService;
	
//	private final UserDetailsService userDetailsService;
//	
//	public JwtTokenProvider(@Lazy UserDetailsService userDetailsService) {
//		this.userDetailsService = userDetailsService;
//	}
	

	// 객체 초기화, secretKey를 Base64로 인코딩한다.
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	// JWT 토큰 생성
	public String createToken(String loginId, MemberRole role) {
		Claims claims = Jwts.claims().setSubject(loginId); // JWT payload 에 저장되는 정보단위
		claims.put("roles", role); // 정보는 key / value 쌍으로 저장된다.
		Date now = new Date();
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		Key key = Keys.hmacShaKeyFor(keyBytes);

		return Jwts.builder().setClaims(claims) // 정보 저장
				.setIssuedAt(now) // 토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
				.signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과
				// signature 에 들어갈 secret값 세팅
				.compact();
	}

	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = memberService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// 토큰에서 회원 정보 추출
	public String getUserPk(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}

	// Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("authorization");
	}

	// 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	
}
