package weiver.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.domain.repository.UserRepository;
import weiver.service.KakaoService;
import weiver.service.LoginService;

import java.util.Map;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginViewController {
	@Autowired
	private LoginService loginService; 
	
	@Autowired
	private KakaoService kakaoService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${kakao.restapikey}")
	private String kakaoAPIKey;
	
	@Value("${kakao.redirecturl}")
	private String kakaoRedirectUrl;
	
	
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
	public String kakaoLogin(@RequestParam("code") String code, HttpSession session) {
		try {
			String accessToken = kakaoService.getKakaoAccessToken(code);
			
			System.out.println(accessToken);
			Map<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
			
			Long currentTimeMillis = System.currentTimeMillis();
			String userId = userInfo.get("email").toString().replaceAll("\"", "");
			String userNickname = userInfo.get("nickname").toString().replaceAll("\"", "");
			String userPw = currentTimeMillis.toString();
			
			// 아이디있으면 로그인 처리 후 main, 아이디가 없으면 회원가입 후 -> main
			// 이미 존재할 경우 회원가입 X, 로그인 페이지로
			boolean existsUser = userRepository.existsById(userId);
			boolean result = false;
			
			if(!existsUser) {
				result = loginService.saveUser(userId, userPw, userNickname);
								
			}
			
			if (result || existsUser) {
				session.setAttribute("userId", userId);
				session.setAttribute("userNickname", userNickname);
				session.setAttribute("kakao", "kakao"); // 카카오 유저 비밀번호 변경 페이지 막을 때 사용
				return "redirect:/main";
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "signup";
	}
}
