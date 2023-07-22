package weiver.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import weiver.entity.User;
import weiver.service.LoginService;


@RestController
@Slf4j
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	// 회원가입....
	@PostMapping(value = "/signupTest")
	public ResponseEntity<String> signup(@RequestParam("userId") String userId,
			@RequestParam("userPw") String userPw,
			@RequestParam("userPwCheck") String userPwCheck,
			@RequestParam("userNickname") String userNickname) {
		
		boolean userIdExists = loginService.checkUserExists(userId);
		boolean userNicknameExists = loginService.checkUserNicknameExists(userNickname);
		
		// front required 속성으로 굳이 필요 없어짐.
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
			logger.error("LoginContorller : 회원가입 중 문제 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 에러가 발생했습니다.");
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 에러가 발생했습니다.");
	}
	
	// 로그인
	@PostMapping(value =  "/loginTest")
	public ResponseEntity<String> loginTest(@RequestParam(value = "userId") String userId, 
											@RequestParam(value = "userPw") String userPw,
											HttpSession session) {
		logger.info("요청 아이디 : " + userId);
		logger.info("요청 비밀번호 : " + userPw);
		
		try {			
			if(userId == null || userId.isEmpty() || userPw == null || userPw.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디, 비밀번호를 입력해주세요");
			}
			
			User user = loginService.loginTest(userId, userPw);
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("userNickname", user.getNickname());
			
			logger.info("로그인 컨트롤러 DB 호출 ID : " + user.getId());
			logger.info("로그인 컨트롤러 DB 호출 PW : " + user.getPassword());
			return ResponseEntity.ok("로그인에 성공했습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("로그인 인증 실패");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 틀렸습니다.");
		}

	}
}
