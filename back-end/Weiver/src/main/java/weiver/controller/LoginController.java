//package weiver.controller;
//
//
//
//import java.awt.PageAttributes.MediaType;
//import java.util.Locale;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import weiver.dto.User;
//import weiver.service.LoginService;
//
//
//@Controller
//public class LoginController {
//	@Autowired
//	LoginService service;
//	
//	@Autowired
//	private MongoTemplate mongoTemplate;
//	
//	@GetMapping(value = "/login")
//	public String loginPage() {
//		return "login";
//	}
//	
//	
//	@GetMapping(value = "/main")
//	public String main() {
//		return "main";
//	}
//	
//	@CrossOrigin(origins = {"*"})
//	@PostMapping(value =  "/signin")
////	consumes = {MediaType.APPLICATION_JSON_VALUE}
//	public String login(@RequestParam(required = false, value = "id") String id, 
//			@RequestParam(required = false, value = "pw") String pw) {
//		
//		System.out.println(id);
//		System.out.println(pw);
//		
//		if (id == null || pw == null) {
//	        return "error";
//	    }
//		
//		User user = service.signIn(id, pw);
//		
//		if (user != null) {
//	        return "redirect:/mainpage";
//	    } else {
//	        // 로그인 실패 처리
//	        return "redirect:/login";
//	    }
//	}
//
//
//}
