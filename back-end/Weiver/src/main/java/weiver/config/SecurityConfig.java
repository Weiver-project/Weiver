package weiver.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.JwtHandlerAdapter;
import lombok.RequiredArgsConstructor;
import weiver.jwt.JwtFilter;
import weiver.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final TokenProvider tokenProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.httpBasic().disable()	// 비활성화 목록
				.csrf().disable()
				.cors().and()
				
				.authorizeRequests()	// 요청 없이 사용 가능한 url
				.antMatchers("/login", 
							 "/community/**",
							 "/musical/**",
							 "/actor/**",
							 "/casting/**",
							 "/signup").permitAll()
		
				.antMatchers("/post/**",	// 권한이 필요한 서비스
							 "/update/**",
							 "/delete/**",
							 "/insert/**",
							 "/like/**").hasRole("USER")
				
				.anyRequest().permitAll()
				 
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				
				.and()
				.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
				.build();
		
		// 로그인 설정
//		httpSecurity.formLogin()
//					.loginPage("/login")
//					.loginProcessingUrl("/signin")
//					.usernameParameter("userId")
//					.passwordParameter("userPw")
//					.defaultSuccessUrl("/")
//					.failureUrl("/login")
//					.successHandler(
//							new AuthenticationSuccessHandler() {
//								
//								@Override
//								public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//										Authentication authentication) throws IOException, ServletException {
//									System.out.println("authentication : " + authentication.getName());
//									response.sendRedirect("/");	// 인증 성공 시 url
//									
//								}
//							}
//					)
//					.failureHandler(
//							new AuthenticationFailureHandler() {
//								
//								@Override
//								public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//										AuthenticationException exception) throws IOException, ServletException {
//									System.out.println("authenticationException : " + exception.getMessage());
//									response.sendRedirect("/login");
//									
//								}
//							}
//					);
		
//		httpSecurity.authorizeRequests()
//		.antMatchers("/login", "/community/**", "/musical/**", "/actor/**", "/casting/**", "/signup").permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		
		// 비활성화 목록
//		httpSecurity
//					.csrf().disable()	// csrf 비활성화
//					.headers().frameOptions().disable();	// X-Frame-Options 헤더를 비활성화 
//		
//		// 로그 아웃
//		httpSecurity.logout()
//					.logoutUrl("/logout")
//					.logoutSuccessUrl("/");
//		return httpSecurity.build();
		
	}
}
