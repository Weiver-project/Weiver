package weiver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import weiver.entity.*;
import weiver.service.AdminService;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping(value = "admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	// 테스트
		@RequestMapping(value="/main",method = RequestMethod.GET)
		public String main(Model model) {
			System.out.println("main");
			model.addAttribute("actors", adminService.getAllActors());
			return "adminActors";
		}

	/*============================        Login         ===================================*/

	// 로그인
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String adminLogin(HttpSession session) {
		if(session.getAttribute("adminId") != null) {
			return "redirect:/admin/main";
		}

		return "adminLogin";
	}

	// 회원가입
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupPage() {
		return "adminSignup";
	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		if(session != null) {
			session.invalidate();
			System.out.println("로그 아웃");
		}
		return "redirect:/admin/login";
	}

	// 회원 탈퇴
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String removeAdmin(HttpSession session) {
		String adminId = (String) session.getAttribute("adminId");

		try {
			adminService.removeAdmin(adminId);
			session.invalidate();
			log.info("회원 탈퇴됨");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/admin/main";
	}

	/*============================        Actor         ===================================*/
	
	// 배우 리스트 조회
	@RequestMapping(value="/getAllActors",method = RequestMethod.GET)
	public String getAllActors(Model model) {
		model.addAttribute("actors", adminService.getAllActors());
				
		return "adminActors";
	}
	
	// 특정 배우 상세 조회
	@RequestMapping(value="/getActorDetail/{actorId}",method = RequestMethod.GET)
	public String getActorDetail(@PathVariable String actorId, Model model) {
		model.addAttribute("actor", adminService.getActor(actorId));
				
		return "adminActorDetail";
	}
	
	// 배우 정보 수정
	@RequestMapping(value="/updateActor",method = RequestMethod.GET)
	public String updateActor(@RequestParam Actor actor, Model model) {
		adminService.updateActor(actor);
		
		// model.addAttribute("actors", adminService.getAllActors());
		
		return "adminActors";
	}

	// 배우 정보 삭제
	@RequestMapping(value="/deleteActor/{actorId}",method = RequestMethod.GET)
	public String deleteActor(@PathVariable String actorId, Model model) {
		adminService.deleteActor(actorId);
				
		return "adminActors";
	}
	
	/*============================        Musical         ===================================*/
	
	// 뮤지컬 리스트 조회
	@RequestMapping(value="/getAllMusicals",method = RequestMethod.GET)
	public String getAllMusicals(Model model) {
		model.addAttribute("musicals", adminService.getAllMusicals());
				
		return "adminMusicals";
	}
	
	// 특정 뮤지컬 상세 조회
	@RequestMapping(value="/getMusicalDetail/{musicalId}",method = RequestMethod.GET)
	public String getMusicalDetail(@PathVariable String musicalId, Model model) {
		model.addAttribute("musical", adminService.getMusical(musicalId));
				
		return "adminMusicalDetail";
	}
	
	// 뮤지컬 정보 수정
	@RequestMapping(value="/updateMusical",method = RequestMethod.GET)
	public String updateMusical(@RequestParam Musical musical, Model model) {
		adminService.updateMusical(musical);
		
		// model.addAttribute("musicals", adminService.getAllMusicals());
		
		return "adminMusicals";
	}

	// 뮤지컬 정보 삭제
	@RequestMapping(value="/deleteMusical/{musicalId}",method = RequestMethod.GET)
	public String deleteMusical(@PathVariable String musicalId, Model model) {
		adminService.deleteMusical(musicalId);
				
		return "adminMusicals";
	}
	
	/*============================        User         ===================================*/
	
	// 유저 리스트 조회
	@RequestMapping(value="/getAllUsers",method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		model.addAttribute("users", adminService.getAllUsers());
				
		return "adminUsers";
	}
	
	// 특정 유저 상세 조회
	@RequestMapping(value="/getUserDetail/{userId}",method = RequestMethod.GET)
	public String getUserDetail(@PathVariable String userId, Model model) {
		model.addAttribute("user", adminService.getUser(userId));
				
		return "adminUserDetail";
	}
	
	// 유저 정보 수정
	@RequestMapping(value="/updateUser",method = RequestMethod.GET)
	public String updateUser(@RequestParam User user, Model model) {
		adminService.updateUser(user);
		
		// model.addAttribute("users", adminService.getAllUsers());
		
		return "adminUsers";
	}

	// 유저 정보 삭제
	@RequestMapping(value="/deleteUser/{userId}",method = RequestMethod.GET)
	public String deleteUser(@PathVariable String userId, Model model) {
		adminService.deleteUser(userId);
				
		return "adminUsers";
	}
	
	/*============================        Admin         ===================================*/
	
	// 관리자 리스트 조회
	@RequestMapping(value="/getAllAdmins",method = RequestMethod.GET)
	public String getAllAdmins(Model model) {
		model.addAttribute("admins", adminService.getAllAdmins());
				
		return "adminAdmins";
	}
	
	// 특정 관리자 상세 조회
	@RequestMapping(value="/getAdminDetail/{adminId}",method = RequestMethod.GET)
	public String getAdminDetail(@PathVariable String adminId, Model model) {
		model.addAttribute("admin", adminService.getAdmin(adminId));
				
		return "adminAdminDetail";
	}
	
	// 관리자 정보 수정
	@RequestMapping(value="/updateAdmin",method = RequestMethod.GET)
	public String updateAdmin(@RequestParam Admin admin, Model model) {
		adminService.updateAdmin(admin);
		
		// model.addAttribute("admins", adminService.getAllAdmins());
		
		return "adminAdmins";
	}

	// 관리자 정보 삭제
	@RequestMapping(value="/deleteAdmin/{adminId}",method = RequestMethod.GET)
	public String deleteAdmin(@PathVariable String adminId, Model model) {
		adminService.deleteAdmin(adminId);
				
		return "adminAdmins";
	}
	
	/*============================        Post         ===================================*/
	
	// 게시글 리스트 조회
	@RequestMapping(value="/getAllPosts",method = RequestMethod.GET)
	public String getAllPosts(Model model) {
		model.addAttribute("posts", adminService.getAllPosts());
				
		return "adminPosts";
	}
	
	// 특정 게시글 상세 조회
	@RequestMapping(value="/getPostDetail/{postId}",method = RequestMethod.GET)
	public String getPostDetail(@PathVariable Long postId, Model model) {
		model.addAttribute("post", adminService.getPost(postId));
				
		return "adminPostDetail";
	}
	
	// 게시글 정보 수정
	@RequestMapping(value="/updatePost",method = RequestMethod.GET)
	public String updatePost(@RequestParam Post post, Model model) {
		adminService.updatePost(post);
		
		// model.addAttribute("posts", adminService.getAllPosts());
		
		return "adminPosts";
	}

	// 게시글 정보 삭제
	@RequestMapping(value="/deletePost/{postId}",method = RequestMethod.GET)
	public String deletePost(@PathVariable Long postId, Model model) {
		adminService.deletePost(postId);
				
		return "adminPosts";
	}
	
	/*============================        Inquiry         ===================================*/
	
	// 문의 리스트 조회
	@RequestMapping(value="/getAllInquirys",method = RequestMethod.GET)
	public String getAllInquirys(Model model) {
		model.addAttribute("inquirys", adminService.getAllInquirys());
				
		return "adminInquirys";
	}
	
	// 특정 문의 상세 조회
	@RequestMapping(value="/getInquiryDetail/{inquriyId}",method = RequestMethod.GET)
	public String getInquiryDetail(@PathVariable Long inquiryId, Model model) {
		model.addAttribute("inquiry", adminService.getInquiry(inquiryId));
				
		return "adminInquiryDetail";
	}
	
	// 문의 정보 수정
	@RequestMapping(value="/updateInquiry",method = RequestMethod.GET)
	public String updateInquiry(@RequestParam Inquiry inquiry, Model model) {
		adminService.updateInquiry(inquiry);
		
		// model.addAttribute("inquirys", adminService.getAllInquirys());
		
		return "adminInquirys";
	}

	// 문의 정보 삭제
	@RequestMapping(value="/deleteInquiry/{inquiryId}",method = RequestMethod.GET)
	public String deleteInquiry(@PathVariable Long inquiryId, Model model) {
		adminService.deleteInquiry(inquiryId);
				
		return "adminInquirys";
	}
	

}

