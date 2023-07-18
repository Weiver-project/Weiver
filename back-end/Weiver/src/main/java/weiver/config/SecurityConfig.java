package weiver.config;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import weiver.jwt.JwtAccessDeniedHandler;
import weiver.jwt.JwtAuthenticationEntryPoint;
import weiver.jwt.JwtSecurityConfig;
import weiver.jwt.TokenProvider;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 별로 접근을 제어하려고 사용
public class SecurityConfig{
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	public SecurityConfig(TokenProvider tokenProvider,
						  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
						  JwtAccessDeniedHandler jwtDeniedHandler) {
		this.tokenProvider = tokenProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtDeniedHandler;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// static 파일 무시
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().antMatchers("/css/**", "/img/**", "/js/**");
		
	}
	
	
	@Bean
	public SecurityFilterChain filerChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()	// 토큰을 사용하기 떄문에 사용하지 않음
			.exceptionHandling()	// 예외 처리 핸들링
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			
			.and()	// 동일한 origin에서만 iframe 로드
			.headers()
			.frameOptions()
			.sameOrigin()
			
			.and()	// 세션 사용하지 않음
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and().authorizeHttpRequests()
			.antMatchers("/loginPage/**").permitAll()
			.antMatchers("/community").permitAll()
			.antMatchers("/signin/**").permitAll()
			.antMatchers("/signupPage/**").permitAll()
			.antMatchers("/signup/**").permitAll()
			.antMatchers("/main/**").permitAll()
			.antMatchers("/error/**").permitAll()
			.anyRequest().authenticated()
			
			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
		return http.build();
	} 
}
