package weiver.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean{
	private final TokenProvider tokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// Request Header 에서 JWT 토큰  추출
		String token = resolveToken((HttpServletRequest) request);
		
		
		// validateToken 으로 토큰 유효성 검사
		// Authentication 객체를 현재 실행 중인 스레드의 보안 컨텍스트에 설정함
		if(StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
			Authentication authentication = tokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	// Request Header 에서 JWT 토큰  추출
	private String resolveToken(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
		
		// StringUtils.hasText() : null, 길이 0 이상인지 확인
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7); // 실제 토큰 값을 반환 받음, Bearer로 시작되는 부분 생략
		}
		return null;
	}
}
