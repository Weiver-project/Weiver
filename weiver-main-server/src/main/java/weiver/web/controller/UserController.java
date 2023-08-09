package weiver.web.controller;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weiver.web.dto.PostDTO;
import weiver.web.dto.ReplyDTO;
import weiver.web.dto.SimpleMusicalDTO;
import weiver.web.dto.UserDTO;
import weiver.domain.entity.User;
import weiver.service.AwsS3Service;
import weiver.service.LoginService;
import weiver.service.SubscribeService;
import weiver.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "mypage")
public class UserController {

	@Autowired
	private UserService userservice;

	@Autowired
	private SubscribeService subscribeService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private AwsS3Service awsS3Service;

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
//		userservice.updateInfo(nickname, profileImg, id);
	}

	// 유저 정보 수정(비밀번호)
	@RequestMapping(value="/test3",method = RequestMethod.GET)
	public void updatetest2() {
		String id = "test2";
		String password = "password3";
//		userservice.updatePassword(password, id);
	}

	// 유저가 쓴 게시글/댓글/좋아요한 글 조회
//	@RequestMapping(value="/test4",method = RequestMethod.GET)
//	public void test2() {
//		String id = "test1";
//		//내가 쓴 글
//		userservice.findPostsByUserId(id);
//		//내가 쓴 댓글
//		userservice.findRepliesByUserId(id);
//		//좋아요 누른 글
//		userservice.findPostLikeByUserId(id);
//	}

	// 유저 subscribe 테이블 조회
	@RequestMapping(value="/test5",method = RequestMethod.GET)
	public void test3() {
		String id = "test1";
		String type = "찜";
		userservice.findSubscribe(id,type);
	}

	// 마이 페이지
	@GetMapping("/myinfo")
	public String mypage(Model model, HttpSession session) {

		String userId = (String) session.getAttribute("userId");
		UserDTO userInfo = userservice.userInfo(userId);

		model.addAttribute("userInfo", userInfo);

		return "mypage";
	}

	// 프로필 수정 페이지
	@GetMapping("/profileUpdate")
	public String profileUpdateForm(HttpSession session,
									Model model) {

		String userId = (String) session.getAttribute("userId");
		User userInfo = userservice.findById(userId);
		System.out.println(userInfo.getId());
		System.out.println(userInfo.getPassword());
		// DTO 바꿔야함 (issue: 비밀번호 노출)
		model.addAttribute("userInfo", userInfo);

		return "profileUpdate";
	}

	// 프로필 수정
	@PostMapping("/update")
	public String profileUpdate(@RequestParam(value = "userId") String id,
								@RequestParam(value = "nickname") String nickname,
								@RequestParam(value = "profileImg") MultipartFile profileImg) {

		String prevName = userservice.findById(id).getNickname();
		String prevImg = userservice.findById(id).getProfileImg();
		boolean existName = loginService.checkUserNicknameExists(nickname);


		if( !nickname.equals(prevName) && !existName ) {
			boolean result = false;
			try {
				result = userservice.updateName(nickname, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			String url = awsS3Service.uploadFileV1(profileImg);
			if( !url.isEmpty() && !url.equals(prevImg)) {
				boolean result = false;
				try {
					result = userservice.updateImg(url, id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		return "redirect:/mypage/profileUpdate";
	}

	// 설정 페이지
	@GetMapping("/setting")
	public String setting() {
		return "setting";
	}

	// 비밀번호 수정 페이지
	@GetMapping("/password")
	public String passwordUpdateForm(HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
		User userInfo = userservice.findById(userId);
		// DTO 바꿔야함 (issue: 비밀번호 노출)
		model.addAttribute("userInfo", userInfo);
		return "passwordUpdate";
	}

	// 비밀번호 수정
	@PostMapping("/updatePW")
	public ResponseEntity<String> updatePW(@RequestParam("userId") String userId,
										   @RequestParam("myPw") String userPw,
										   @RequestParam("newPw") String newPw,
										   @RequestParam("checkPw") String checkPw,
										   HttpSession session) {

		String kakaoId = (String) session.getAttribute("kakao");

		if (kakaoId != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카카오 아이디는 비밀번호 변경이 안됩니다.");
		}

		String password = userservice.findById(userId).getPassword();
		boolean result = BCrypt.checkpw(userPw, password);

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

	// 내가 쓴 글 페이지
	@GetMapping("/myBoard")
	public String myBoard(HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
		List<PostDTO> postDTOList = userservice.findPostsByUserId(userId);

		List<PostDTO> postListTime = postDTOList.stream()
				.sorted(Comparator.comparing(PostDTO::getCreatedTime).reversed())
				.collect(Collectors.toList());

		List<PostDTO> postListLike = postDTOList.stream()
				.sorted(Comparator.comparing(PostDTO::getCountLikes).reversed())
				.collect(Collectors.toList());

		int postCount = userservice.countPostsByUserId(userId);
		model.addAttribute("postListTime", postListTime);
		model.addAttribute("postListLike", postListLike);
		model.addAttribute("postCount", postCount);

		return "myBoard";
	}

	// 내가 쓴 댓글 페이지
	@GetMapping("/myComment")
	public String myComment(HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");

		List<ReplyDTO> replyDTOList = userservice.findRepliesByUserId(userId);

		List<ReplyDTO> replyDTOListTime = replyDTOList.stream()
				.sorted(Comparator.comparing(ReplyDTO::getCreatedTime).reversed())
				.collect(Collectors.toList());

		List<ReplyDTO> replyDTOListLike = replyDTOList.stream()
				.sorted(Comparator.comparing(ReplyDTO::getCountLikes).reversed())
				.collect(Collectors.toList());

		int replyCount = userservice.countRepliesByUserId(userId);

		model.addAttribute("replyListTime", replyDTOListTime);
		model.addAttribute("replyListLike", replyDTOListLike);
		model.addAttribute("replyCount", replyCount);

		return "myComment";
	}


	// 내가 좋아요 누른 글들
	@GetMapping("/myLike")
	public String myLike(HttpSession session,Model model) {

		String userId = (String) session.getAttribute("userId");

		List<PostDTO> postLikeList = userservice.findPostLikeByUserId(userId);

		List<PostDTO> postLikeListTime = postLikeList.stream()
				.sorted(Comparator.comparing(PostDTO::getCreatedTime).reversed())
				.collect(Collectors.toList());

		List<PostDTO> postLikeListLike = postLikeList.stream()
				.sorted(Comparator.comparing(PostDTO::getCountLikes).reversed())
				.collect(Collectors.toList());

		int likeCount = postLikeList.size();

		model.addAttribute("postListTime", postLikeListTime);
		model.addAttribute("postListLike", postLikeListLike);
		model.addAttribute("postCount", likeCount);

		return "myLike";
	}

	// 내가 찜한 목록
	@GetMapping("/mySubscribe")
	public String mySubscribe(HttpSession session,
							  Model model) {
		String userId = (String) session.getAttribute("userId");

		List<SimpleMusicalDTO> JjimList = subscribeService.getSubscribeMusical(userId,"찜했어요");
		List<SimpleMusicalDTO> WatchedList = subscribeService.getSubscribeMusical(userId,"봤어요");

		model.addAttribute("JjimList", JjimList);
		model.addAttribute("WatchedList", WatchedList);

		return "mySubscribe";
	}
}

