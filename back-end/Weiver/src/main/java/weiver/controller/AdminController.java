package weiver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import weiver.entity.Actor;
import weiver.entity.Admin;
import weiver.entity.Inquiry;
import weiver.entity.Musical;
import weiver.entity.Post;
import weiver.entity.User;
import weiver.service.AdminService;

@Controller
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
	
	// 뮤지컬 리스트 조회
	@RequestMapping(value="/getAllUsers",method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		model.addAttribute("users", adminService.getAllUsers());
				
		return "adminUsers";
	}
	
	// 특정 뮤지컬 상세 조회
	@RequestMapping(value="/getUserDetail/{userId}",method = RequestMethod.GET)
	public String getUserDetail(@PathVariable String userId, Model model) {
		model.addAttribute("user", adminService.getUser(userId));
				
		return "adminUserDetail";
	}
	
	// 뮤지컬 정보 수정
	@RequestMapping(value="/updateUser",method = RequestMethod.GET)
	public String updateUser(@RequestParam User user, Model model) {
		adminService.updateUser(user);
		
		// model.addAttribute("users", adminService.getAllUsers());
		
		return "adminUsers";
	}

	// 뮤지컬 정보 삭제
	@RequestMapping(value="/deleteUser/{userId}",method = RequestMethod.GET)
	public String deleteUser(@PathVariable String userId, Model model) {
		adminService.deleteUser(userId);
				
		return "adminUsers";
	}
	
	/*============================        Admin         ===================================*/
	
	// 뮤지컬 리스트 조회
	@RequestMapping(value="/getAllAdmins",method = RequestMethod.GET)
	public String getAllAdmins(Model model) {
		model.addAttribute("admins", adminService.getAllAdmins());
				
		return "adminAdmins";
	}
	
	// 특정 뮤지컬 상세 조회
	@RequestMapping(value="/getAdminDetail/{adminId}",method = RequestMethod.GET)
	public String getAdminDetail(@PathVariable String adminId, Model model) {
		model.addAttribute("admin", adminService.getAdmin(adminId));
				
		return "adminAdminDetail";
	}
	
	// 뮤지컬 정보 수정
	@RequestMapping(value="/updateAdmin",method = RequestMethod.GET)
	public String updateAdmin(@RequestParam Admin admin, Model model) {
		adminService.updateAdmin(admin);
		
		// model.addAttribute("admins", adminService.getAllAdmins());
		
		return "adminAdmins";
	}

	// 뮤지컬 정보 삭제
	@RequestMapping(value="/deleteAdmin/{adminId}",method = RequestMethod.GET)
	public String deleteAdmin(@PathVariable String adminId, Model model) {
		adminService.deleteAdmin(adminId);
				
		return "adminAdmins";
	}
	
	/*============================        Post         ===================================*/
	
	// 뮤지컬 리스트 조회
	@RequestMapping(value="/getAllPosts",method = RequestMethod.GET)
	public String getAllPosts(Model model) {
		model.addAttribute("posts", adminService.getAllPosts());
				
		return "adminPosts";
	}
	
	// 특정 뮤지컬 상세 조회
	@RequestMapping(value="/getPostDetail/{postId}",method = RequestMethod.GET)
	public String getPostDetail(@PathVariable Long postId, Model model) {
		model.addAttribute("post", adminService.getPost(postId));
				
		return "adminPostDetail";
	}
	
	// 뮤지컬 정보 수정
	@RequestMapping(value="/updatePost",method = RequestMethod.GET)
	public String updatePost(@RequestParam Post post, Model model) {
		adminService.updatePost(post);
		
		// model.addAttribute("posts", adminService.getAllPosts());
		
		return "adminPosts";
	}

	// 뮤지컬 정보 삭제
	@RequestMapping(value="/deletePost/{postId}",method = RequestMethod.GET)
	public String deletePost(@PathVariable Long postId, Model model) {
		adminService.deletePost(postId);
				
		return "adminPosts";
	}
	
	/*============================        Inquiry         ===================================*/
	
	// 뮤지컬 리스트 조회
	@RequestMapping(value="/getAllInquirys",method = RequestMethod.GET)
	public String getAllInquirys(Model model) {
		model.addAttribute("inquirys", adminService.getAllInquirys());
				
		return "adminInquirys";
	}
	
	// 특정 뮤지컬 상세 조회
	@RequestMapping(value="/getInquiryDetail/{inquriyId}",method = RequestMethod.GET)
	public String getInquiryDetail(@PathVariable Long inquiryId, Model model) {
		model.addAttribute("inquiry", adminService.getInquiry(inquiryId));
				
		return "adminInquiryDetail";
	}
	
	// 뮤지컬 정보 수정
	@RequestMapping(value="/updateInquiry",method = RequestMethod.GET)
	public String updateInquiry(@RequestParam Inquiry inquiry, Model model) {
		adminService.updateInquiry(inquiry);
		
		// model.addAttribute("inquirys", adminService.getAllInquirys());
		
		return "adminInquirys";
	}

	// 뮤지컬 정보 삭제
	@RequestMapping(value="/deleteInquiry/{inquiryId}",method = RequestMethod.GET)
	public String deleteInquiry(@PathVariable Long inquiryId, Model model) {
		adminService.deleteInquiry(inquiryId);
				
		return "adminInquirys";
	}
	
	
}

