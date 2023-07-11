package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {


	@RequestMapping(value="/mongotest",method = RequestMethod.GET)
	public void test() {
	}
	
	@RequestMapping(value="/mongo",method = RequestMethod.GET)
	public void updatetest() {
		
	}
}
