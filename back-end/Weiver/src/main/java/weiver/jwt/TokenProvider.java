package weiver.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import weiver.dto.TokenInfo;

@Component
@Slf4j
public class TokenProvider {
	
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // Access 토큰 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60;	// Refresh 토큰 60분
	private final Key key;
	
	// secretKey 디코딩, HMAC-SHA로 key 객체 생성
	public TokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	// 유저 정보를 가져오고 AccessToken, RefreshToken 생성
	public TokenInfo generateTokenDto(Authentication authentication) {
		// 권한 가져오기
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		long now = (new Date()).getTime();
		
		// Access Token 생성
		Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim("auth", authorities)
				.setExpiration(accessTokenExpiresIn)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		// Refresh Token 생성
		String refreshToken = Jwts.builder()
				.setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		return TokenInfo.builder()
				.grantType("Bearer")
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	// 토큰에 있는 정보를 꺼냄
	public Authentication getAuthentication(String accessToken) {
		
		// 토큰 복호화
		Claims claims = parseClaims(accessToken);
		
		if(claims.get("auth") == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		// Claim 에서 authorities을 추출하여 Collection 객체로 변환
		// GrantedAuthority : Security 에서 제공하는 인터페이스
		// SimpleGrantedAuthority : Security에서 권한을 나타내는 클래스
		Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get("auth").toString().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		
		// UserDetails 객체를 만들어서 Authentication 리턴
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}
	
	// 토큰 정보 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
	
	// Jwt토큰 claim 추출
	private Claims parseClaims(String accessToken) {
        try {
        	// Jwt 파서 생성, 검증에 사용될 key 설정 build
        	// parseClaimsJws 를 통해 accessToken을 파싱하고 검증 body를 가져옴 
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();	// 만료 시 토큰의 클레임 정보 반환
        }
    }
}
