package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weiver.dto.UserDTO;
import weiver.entity.User;
import weiver.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userservice;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 유저 아이디로 조회
	@RequestMapping(value="/test1",method = RequestMethod.GET)
	public void test() {
		String id = "test1";
		userservice.test();
		userservice.findById(id);
	}

	// 유저 정보 수정(사진, 이름)
//	@RequestMapping(value="/test2",method = RequestMethod.GET)
//	public void updatetest() {
//		String id = "test1";
//		String nickname = "Doe";
//		String profileImg = "profile_img1.jpg";
//		userservice.updateInfo(nickname, profileImg, id);
//	}

	// 유저 정보 수정(비밀번호)
//	@RequestMapping(value="/test3",method = RequestMethod.GET)
//	public void updatetest2() {
//		String id = "asdfasdf@naver.com";
//		String password = "asdfasdf";
//		userservice.updateBcryptPassword(password, id);
//	}

	// 유저가 쓴 게시글/댓글/좋아요한 글 조회
	@RequestMapping(value="/test4",method = RequestMethod.GET)
	public void test2() {
		String id = "test1";
		//내가 쓴 글
		userservice.findPostsByUserId(id);
		//내가 쓴 댓글
		userservice.findRepliesByUserId(id);
		//좋아요 누른 글
		userservice.findPostLikeByUserId(id);
	}

	// 유저 subscribe 테이블 조회
	@RequestMapping(value="/test5",method = RequestMethod.GET)
	public void test3() {
		String id = "test1";
		String type = "찜";
		userservice.findSubscribe(id,type);
	}

	@GetMapping("/mypage/{userid}")
	public String mypage(@PathVariable String userid,
						 Model model) {
		UserDTO userInfo = userservice.userInfo(userid);

		model.addAttribute("userInfo", userInfo);

		return "mypage";
	}

	@GetMapping("/profileUpdate/{userid}")
	public String profileUpdateForm(@PathVariable String userid,
									Model model) {
		User userInfo = userservice.findById(userid);
		// DTO 바꿔야함 (issue: 비밀번호 노출)
		model.addAttribute("userInfo", userInfo);

		return "profileUpdate";
	}

	@PostMapping("/update")
	public String profileUpdate(@RequestParam(value = "userId") String id,
								@RequestParam("nickname") String nickname,
								@RequestParam(value = "profileImg") MultipartFile profileImg) {

		// 파일 저장은 어디에?
		String prevName = userservice.findById(id).getNickname();
		String prevImg = userservice.findById(id).getProfileImg();
		String imgName = profileImg.getOriginalFilename();

		if( !nickname.equals(prevName) ) {
			boolean result = false;
			try {
				result = userservice.updateName(nickname, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}


		if( !imgName.isEmpty() && !imgName.equals(prevImg)) {
			boolean result = false;
			try {
				result = userservice.updateImg(imgName, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}

		return "redirect:/profileUpdate/" + id;
	}

	@GetMapping("/setting")
	public String setting() {
		return "setting";
	}

	@GetMapping("/password/{userid}")
	public String passwordUpdateForm(@PathVariable String userid,
									 Model model) {
		User userInfo = userservice.findById(userid);
		// DTO 바꿔야함 (issue: 비밀번호 노출)
		model.addAttribute("userInfo", userInfo);
		return "passwordUpdate";
	}

	@PostMapping("/updatePW")
	public ResponseEntity<String> updatePW(@RequestParam("userId") String userId,
										   @RequestParam("myPw") String userPw,
										   @RequestParam("newPw") String newPw,
										   @RequestParam("checkPw") String checkPw) {

		String password = userservice.findById(userId).getPassword();
		boolean result = passwordEncoder.matches(userPw, password);

		// 기존 비밀번호 확인
		if (!result) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 틀렸습니다.");
		}

		// 새 패스워드, 패스워드 확인 체크
		if(!newPw.equals(checkPw)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력하신 비밀번호와 확인 비밀번호가 다릅니다.");
		}

		try {
			boolean updateResult = userservice.updateBcryptPassword(newPw,userId);
			if (updateResult) {
				return ResponseEntity.ok("변경이 완료되었습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 에러가 발생했습니다.");
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경 중 에러가 발생했습니다.");
	}
}
