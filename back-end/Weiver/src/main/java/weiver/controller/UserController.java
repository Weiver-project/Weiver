package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import weiver.service.UserService;


@Controller
public class UserController {

	@Autowired
	private UserService userservice;
	
	@RequestMapping(value="/test1",method = RequestMethod.GET)
	public void test() {
		String id = "asdf@naver.com";
		String password = "asdf1234";
		userservice.test();
	}
	
	@RequestMapping(value="/test2",method = RequestMethod.GET)
	public void updatetest() {
		
	}
}
