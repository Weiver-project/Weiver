package weiver.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
	
	private SecurityUtil() {}
	
	// SecurityContext에서 Authentication객체를 꺼내와서 Authentication를 객체를 통해 id를 리턴해줌
	// SecurityContext에 Authentication객체가 저장되는 시점 = JwtFilter의 doFilter에서 Request가 들어올 때 if문을 통과하면서 저장됨
	public static Optional<String> getCurrentUsername() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null) {
			logger.debug("SecurityUtil : Security Context에 인증 정보가 없습니다.");
			return Optional.empty();
		}
		
		String id = null;
		
		// Principal = Spring Security에서 현재 인증된 사용자
		// UserDetails = Spring Security에서 인증된 사용자를 담고 있는 Interface
		if(authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();	// 이미 로그인 된 상황
			id = springSecurityUser.getUsername();
		} else if(authentication.getPrincipal() instanceof String) {						// 비 로그인 상황
			id = (String) authentication.getPrincipal();
		}
		
		return Optional.ofNullable(id);
	}
}
