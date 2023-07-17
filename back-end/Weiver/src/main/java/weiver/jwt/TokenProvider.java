package weiver.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider implements InitializingBean{
	private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	private static final String AUTHORITIES_KEY = "auth";
	private final String secret;
	private final long tokenValidityInMilliseconds;
	private Key key;
	
	public TokenProvider(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds) {
		this.secret = secret;
		this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
	}
	
	// afterPropertiesSet 한 이유 : Bean이 생성이 되고 의존성 주입을 받은 후에 secret값을 Decode해서 key 변수에 할당
	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] ketBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(ketBytes);
	}
	
	// Authentication 객체의 권한 정보를 이용해서 Token 생성
	public String createToken(Authentication authentication) {
		String authorites = authentication.getAuthorities().stream()
							.map(GrantedAuthority::getAuthority)
							.collect(Collectors.joining(","));
		
		long now = (new Date().getTime());
		Date validity = new Date(now + this.tokenValidityInMilliseconds);
		
		return Jwts.builder()
				.setSubject(authentication.getName())	// 사용자 id
				.claim(AUTHORITIES_KEY, authorites)		// JWT에 사용자의 권한 추가
				.signWith(key, SignatureAlgorithm.HS512)	// 사용될 해쉬알고리즘
				.setExpiration(validity)					// 유효시간
				.compact();
	}
	
	// Token의 정보를 이용해서 Authenticaion 객체를 리턴
	// JWS = 서명이 검증된 JWT
	// Claims = JWT의 클레임 정보를 포함하는 객체
	// GrantedAuthority = 사용자의 권한
	// principal = 사용자 객체
	// Authentication = 인증된 사용자
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts
						.parserBuilder()
						.setSigningKey(key)	// 파싱에 사용될 서명 키를 설정
						.build()
						.parseClaimsJws(token)	// JWT 토큰을 파싱하여 서명 검증을 수행하고 Jws<Claims> 객체를 반환
						.getBody();	// Jws 객체에서 클레임 정보를 추출
		
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
						.map(SimpleGrantedAuthority::new)	// authority를 SimpleGrantedAuthority로 매핑
						.collect(Collectors.toList());
		
		// 사용자 객체를 만듦
		User principal = new User(claims.getSubject(), "", authorities);
		
		// UsernamePasswordAuthenticationToken을 통해 Authentication 객체를 만듦
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}
	
	// 토큰 유효성 검사, 토큰을 파싱해보고 예외를 처리함
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			logger.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			logger.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			logger.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			logger.info("잘못된 파라미터 입니다.");
		} 
		return false;
	}
}
