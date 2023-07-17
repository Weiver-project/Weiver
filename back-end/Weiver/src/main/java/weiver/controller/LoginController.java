package weiver.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weiver.dto.LoginDTO;
import weiver.dto.SignupDTO;
import weiver.dto.TokenDTO;
import weiver.dto.UserDTO;
import weiver.entity.User;
import weiver.jwt.JwtFilter;
import weiver.jwt.TokenProvider;
import weiver.service.LoginService;


@RestController
public class LoginController {
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final LoginService loginService;
	
	public LoginController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, LoginService loginService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.loginService = loginService;
	}
	
	// 로그인 기능
	@PostMapping("/signin")
	public ResponseEntity<TokenDTO> signin(@RequestBody LoginDTO loginDTO) {
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
	
	// 회원가입 기능
	@PostMapping(value = "/signup")
	public ResponseEntity<User> signupPage(@Valid @RequestBody SignupDTO signupDto) {
		System.out.println(signupDto.getId());
		System.out.println(signupDto.getPassword());
		System.out.println(signupDto.getNickname());
		return ResponseEntity.ok(loginService.signup(signupDto));
	}
	
	// 현재 Security Context에 저장이 되어있는 id에 해당하는 유저 객체와 권한 객체를 받음, USER, ADMIN 모두 호출 가능
	@GetMapping(value = "/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public  ResponseEntity<User> getMyUserInfo() {
		return ResponseEntity.ok(loginService.getMyUserWithAuthorities().get());
	}
	
	// id를 통해 유저 객체와 권한 객체를 리턴 받음, ADMIN만 모두 호출 가능
	@GetMapping(value = "/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public  ResponseEntity<User> getUserInfo(@PathVariable String id) {
		return ResponseEntity.ok(loginService.getUserWithAuthorities(id).get());
	}
}
