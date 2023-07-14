package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import weiver.dto.UserDTO;
import weiver.entity.User;
import weiver.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userservice;
	
	// 유저 아이디로 조회	
	@RequestMapping(value="/test1",method = RequestMethod.GET)
	public void test() {
		String id = "test1";
		userservice.test();
		userservice.findById(id);
	}
	
	// 유저 정보 수정(사진, 이름)
	@RequestMapping(value="/test2",method = RequestMethod.GET)
	public void updatetest() {
		String id = "test1";
		String nickname = "Doe";
		String profileImg = "profile_img1.jpg";
		userservice.updateInfo(nickname, profileImg, id);
	}
	
	// 유저 정보 수정(비밀번호)
	@RequestMapping(value="/test3",method = RequestMethod.GET)
	public void updatetest2() {
		String id = "asdfasdf@naver.com";
		String password = "asdfasdf";
		userservice.updateBcryptPassword(password, id);
	}
	
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
	public String profileUpdate(@PathVariable String userid,
						 Model model) {
		User userInfo = userservice.findById(userid);
		// DTO 바꿔야함 (issue: 비밀번호 노출)
		model.addAttribute("userInfo", userInfo);

		return "profileUpdate";
	}
}
