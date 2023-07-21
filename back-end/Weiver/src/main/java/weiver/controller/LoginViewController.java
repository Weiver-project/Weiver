package weiver.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.service.KakaoService;
import weiver.service.LoginService;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginViewController {
	@Autowired
	private LoginService loginService; 
	
	@Value("${kakao.restapikey}")
	private String kakaoAPIKey;
	
	@Value("${kakao.redirecturl}")
	private String kakaoRedirectUrl;
	
	@Autowired
	private KakaoService kakaoService;
	
	// 로그인 페이지
	@GetMapping(value = "/login")
	public String loginPage(HttpSession session, Model model) {
		
		if(session.getAttribute("userId") != null) {
			return "redirect:/main";
		}		
		
		model.addAttribute("kakaoAPIKey", kakaoAPIKey);
		model.addAttribute("kakaoRedirectUrl", kakaoRedirectUrl);
		
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
	@GetMapping(value = "/signOut")
	public String removeUser(HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		
		try {
			loginService.removeUser(userId);
			session.invalidate();
			log.info("회원 탈퇴됨");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/main";
	}
	
	// 카카오 로그인
	@GetMapping(value = "/kakao")
	public String kakaoLogin(@RequestParam("code") String code, Model model) {
		try {
			String accessToken = kakaoService.getKakaoAccessToken(code);
			
			System.out.println(accessToken);
			Map<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
			
			// 아이디있으면 로그인 처리 후 main
			// if 로그인
			// else 회원가입 페이지
			
			// 없으면 회원가입 main
			// 이미 존재할 경우 회원가입 X, 로그인 페이지로
			
			System.out.println("유저 닉네임" + (String) userInfo.get("nickname"));
			System.out.println("유저 이메일" + (String) userInfo.get("email"));
			
			Long currentTimeMillis = System.currentTimeMillis();
			
			String userId = (String) userInfo.get("email");
			String userNickname = (String) userInfo.get("nickname");
			String userPw = currentTimeMillis.toString();
			System.out.println("비밀번호" + userPw);
			
			boolean result = loginService.saveUser(userId, userPw, userNickname);
			
			if (result) {
				model.addAttribute("userId", userId);
				model.addAttribute("userNickname", userNickname);
				return "main";
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "signup";
	}
}
