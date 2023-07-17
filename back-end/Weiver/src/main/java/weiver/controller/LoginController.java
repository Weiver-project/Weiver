package weiver.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weiver.dto.LoginDTO;
import weiver.dto.TokenDTO;
import weiver.jwt.JwtFilter;
import weiver.jwt.TokenProvider;


@RestController
@RequestMapping("/api")
public class LoginController {
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	public LoginController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}
	
	// 로그인 기능
	@PostMapping("/signin")
	public ResponseEntity<TokenDTO> signin(@Valid @RequestBody LoginDTO loginDTO) {
		System.out.println("id : " + loginDTO.getId());
		System.out.println("pw : " + loginDTO.getPassword());
		
		// loginDTO 의 id, password로 UsernamePasswordAuthenticationToken 객체를 생성함
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getId(), loginDTO.getPassword());
		
		// authenticationToken를 이용해서 Authentication객체를 생성하려고 authenticate 메소드가 실행이 될 때 CustomUserDetailsService의 loadUserByUsername 메소드가 실행되고 이 결과를 가지고 Authentication 객체를 생성함
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		// Authentication객체를 SecurityContext에 저장 후 그 인증 정보를 통해 createToken를 실행하고 JWT Token을 생성함
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.createToken(authentication);
		
		// JWT Token을 Response Header, Response Body에 넣어서 리턴함
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);		
		
		return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
	}
}
