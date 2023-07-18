package weiver.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import weiver.service.LoginService;

@Controller
@Slf4j
public class LoginViewController {
	@Autowired
	private LoginService loginService; 
	
	// 로그인 페이지
	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}
	
	// 회원가입 페이지
	@GetMapping(value = "/signup")
	public String signupPage() {
		return "signup";
	}
	
	// 로그아웃
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		if(session != null) {
			session.invalidate();
			System.out.println("로그 아웃");
		}
		return "redirect:/main";
	}
	
	// 회원 탈퇴
	@GetMapping(value = "remove")
	public String removeUser(HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		
		try {
			loginService.removeUser(userId);
			session.invalidate();
			log.info("회원 탈퇴됨");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		return "redirect:/main";
		return "main";
	}
}
