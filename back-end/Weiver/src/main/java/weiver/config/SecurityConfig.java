package weiver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		// 로그인 설정
		httpSecurity.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/signin")
					.usernameParameter("userId")
					.passwordParameter("userPw")
					.defaultSuccessUrl("/");
		
		// 비활성화 목록
		httpSecurity.cors().disable()	// cors 비활성화
					.csrf().disable()	// csrf 비활성화
					.headers().frameOptions().disable();	// X-Frame-Options 헤더를 비활성화 
		
		
		httpSecurity.logout()
					.logoutUrl("/logoup")
					.logoutSuccessUrl("/");
		return httpSecurity.build();
	}
}
