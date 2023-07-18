package weiver.controller;


import javax.persistence.Id;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import weiver.entity.User;
import weiver.service.LoginService;


@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	// 회원가입
	@PostMapping(value = "/signupTZest")
	public ResponseEntity<String> signup(@RequestParam("userId") String userId,
			@RequestParam("userPw") String userPw,
			@RequestParam("userPwCheck") String userPwCheck,
			@RequestParam("userNickname") String userNickname) {
		
		boolean userIdExists = loginService.checkUserExists(userId);
		boolean userNicknameExists = loginService.checkUserNicknameExists(userNickname);
		
		// front required 속성으로 굳이 필요 없어짐
		if(userId == null || userId.equals("") || 
			userPw == null || userPw.equals("") || 
			userNickname == null || userNickname.equals("")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 정보를 모두 입력해주세요.");
		}
		
		// 유저 id 중복 확인
		if (userIdExists) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
		}

		if (userNicknameExists) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임입니다.");
		}

		// 패스워드, 패스워드 확인 체크
		if(!userPw.equals(userPwCheck)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력하신 비밀번호와 확인 비밀번호가 다릅니다.");
		}
		
		System.out.println(userId);
		System.out.println(userPw);
		System.out.println(userNickname);
		
		// 회원 가입 try - catch
		try {
			boolean result = loginService.saveUser(userId, userPw, userNickname);
			
			if (result) {
				return ResponseEntity.ok("회원가입이 완료되었습니다.");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 에러가 발생했습니다.");
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 에러가 발생했습니다.");
	}
	
	// 로그인
	@PostMapping(value =  "/loginTest")
	public ResponseEntity<String> loginTest(@RequestParam(value = "id") String userId, 
			@RequestParam(value = "pw") String userPw, HttpSession session) {
		
		
		if(userId == null || userId == "" || userPw == null || userPw == "") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디, 비밀번호를 입력해주세요");
		}
		
		try {
			User user = loginService.loginTest(userId, userPw);
			
			if (user != null) {
				session.setAttribute("userId", user.getId());
				session.setAttribute("userNickname", user.getNickname());				
				return ResponseEntity.ok("로그인에 성공했습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디 또는 비밀번호가 틀렸습니다.");
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 문제가 발생했습니다.");
	}
}
