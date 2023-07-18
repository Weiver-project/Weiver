//package weiver.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import weiver.service.UserService;
//
//@Controller
//public class UserController {
//
//	@Autowired
//	private UserService userservice;
//	
//	// 유저 아이디로 조회	
//	@RequestMapping(value="/test1",method = RequestMethod.GET)
//	public void test() {
//		String id = "test1";
//		userservice.test();
//		userservice.findById(id);
//	}
//	
//	// 유저 정보 수정(사진, 이름)
//	@RequestMapping(value="/test2",method = RequestMethod.GET)
//	public void updatetest() {
//		String id = "test1";
//		String nickname = "Doe";
//		String profileImg = "profile_img1.jpg";
//		userservice.updateInfo(nickname, profileImg, id);
//	}
//	
//	// 유저 정보 수정(비밀번호)
//	@RequestMapping(value="/test3",method = RequestMethod.GET)
//	public void updatetest2() {
//		String id = "test2";
//		String password = "password3";
//		userservice.updatePassword(password, id);
//	}
//	
//	// 유저가 쓴 게시글/댓글/좋아요한 글 조회
//	@RequestMapping(value="/test4",method = RequestMethod.GET)
//	public void test2() {
//		String id = "test1";
//		userservice.findPostsByUserId(id);
//		userservice.findRepliesByUserId(id);
////		userservice.findPostLikeByUserId(id);
//	}
//	
//}
