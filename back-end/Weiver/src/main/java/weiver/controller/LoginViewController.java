package weiver.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {
	// 로그인 페이지
	@GetMapping(value = "/loginPage")
	public String loginPage() {
		return "login";
	}
	
	// 회원가입 페이지
	@GetMapping(value = "/signupPage")
	public String signupPage() {
		return "signup";
	}
}
