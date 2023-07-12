package weiver.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.entity.User;
import weiver.service.LoginService;


@Controller
public class LoginController {
	@Autowired
	LoginService service;
	
	// 회원가입 페이지
	@GetMapping(value = "/signup")
	public String signupPage() {
		return "signup";
	}
	
	// 로그인 페이지
	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}
	
	// 회원가입 기능
	@PostMapping(value = "/signupTest")
	public ResponseEntity<String> signup(@RequestParam("userId") String userId,
	                     @RequestParam("userPw") String userPw,
	                     @RequestParam("userNickname") String userNickname) {
		
		if(userId == null || userId.equals("") || 
			userPw == null || userPw.equals("") || 
			userNickname == null || userNickname.equals("")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 정보를 모두 입력해주세요.");
		}
		
		System.out.println(userId);
		System.out.println(userPw);
		System.out.println(userNickname);
		
		try {
			boolean result = service.saveUser(userId, userPw, userNickname);
			
			if (result) {
				return ResponseEntity.ok("회원가입이 완료되었습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 오류가 발생했습니다.");
	}
	
	// 로그인 기능
	@PostMapping(value =  "/signin")
	public String login(@RequestParam(value = "id") String userId, 
						@RequestParam(value = "pw") String userPw) {
		
		System.out.println(userId);
		System.out.println(userPw);
		
		if (userId == null || userPw == null) {
	        return "error";
	    }
		
		User user = service.findByIdAndPassword(userId, userPw);
		
		if (user != null) {
	        return "redirect:/mainpage";
	    } else {
	        // 로그인 실패 처리
	        return "redirect:/login";
	    }
	}


}
