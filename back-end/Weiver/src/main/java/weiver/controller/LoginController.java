package weiver.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	                     @RequestParam("userPwCheck") String userPwCheck,
	                     @RequestParam("userNickname") String userNickname) {
		
		boolean userIdExists = service.checkUserExists(userId);
		boolean userNicknameExists = service.checkUserNicknameExists(userNickname);
		
		// front required 속성으로 굳이 필요 없어짐
//		if(userId == null || userId.equals("") || 
//			userPw == null || userPw.equals("") || 
//			userNickname == null || userNickname.equals("")) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 정보를 모두 입력해주세요.");
//		}
		
		// 유저 id 중복 확인
		if (userIdExists) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
		}
		
		if (userNicknameExists) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임입니다.");
		}
		
		// 비밀번호, 비밀번호확인 체크
		if(!userPw.equals(userPwCheck)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력하신 비밀번호와 확인 비밀번호가 다릅니다.");
		}
		
		// 비밀번호 한글 체크
	
		
		System.out.println(userId);
		System.out.println(userPw);
		System.out.println(userNickname);
		
		// 회원 가입 try - catch
		try {
			boolean result = service.saveUser(userId, userPw, userNickname);
			
			if (result) {
				return ResponseEntity.ok("회원가입이 완료되었습니다.");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 에러가 발생했습니다.");
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 에러가 발생했습니다.");
	}
	
	// 로그인 기능 개발중
	@PostMapping(value =  "/signin")
	public String login(@RequestParam(value = "userId") String userId, 
						@RequestParam(value = "userPw") String userPw) {
		
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
