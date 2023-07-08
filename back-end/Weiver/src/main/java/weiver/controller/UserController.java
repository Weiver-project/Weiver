package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value="/mongotest",method = RequestMethod.GET)
	public void test() {
		userService.findByAdmin(false);
		userService.findByIdAndAdmin("qwer1234@daum.com", false);
	}
	
	@RequestMapping(value="/mongo",method = RequestMethod.GET)
	public void updatetest(@RequestParam String id,
								@RequestParam String password,
								@RequestParam String img,
								@RequestParam String name) {
		
		if(password != null && password != "") {
			userService.updatePassword(id, password);
		}
		
		if(img != null && img != "") {
			userService.updateImg(id, img);
		}
		
		if(name != null && name != "") {
			userService.updateName(id, name);
		}
		
	}
}
