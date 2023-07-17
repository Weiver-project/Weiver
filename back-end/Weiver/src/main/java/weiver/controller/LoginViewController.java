package weiver.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weiver.dto.UserDTO;
import weiver.entity.User;
import weiver.service.LoginService;

@RestController
@RequestMapping("/api")
public class LoginViewController {
	private final LoginService loginService;
	
	public LoginViewController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	// 로그인 페이지
	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}
	
	// 회원가입
	@PostMapping(value = "/signup")
	public ResponseEntity<User> signupPage(@Valid @RequestBody UserDTO userDto) {
		return ResponseEntity.ok(loginService.signup(userDto));
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
