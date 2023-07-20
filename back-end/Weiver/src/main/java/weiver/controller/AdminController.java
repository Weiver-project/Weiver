package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import weiver.service.AdminService;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	// 배우 정보 수정
	@RequestMapping(value="/updateActor/{actorId}",method = RequestMethod.GET)
	public void test(@PathVariable String actorId, Model model) {
		
		
	}

	
}

