package weiver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import weiver.jwt.JwtFilter;
import weiver.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtTokenProvider tokenProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.httpBasic().disable()
				.csrf().disable()
				.cors().disable()
				
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/signin")
				.and()
				
				.authorizeRequests()	// 요청 없이 사용 가능한 url
				.antMatchers("/login/**", 
							 "/community/**",
							 "/musical/**",
							 "/actor/**",
							 "/casting/**",
							 "/signup/**").permitAll()
		
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
		
		
		
	}
}
