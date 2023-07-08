package weiver.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/mongotest",method = RequestMethod.GET)
	public void test() {
		System.out.println("---------");
		System.out.println(mongoTemplate.getDb().getName());
		userService.findAll();
		userService.findByAdmin(false);
	}
	
	@RequestMapping(value="/mongo",method = RequestMethod.GET)
	public void updatetest(@RequestParam String id,
								@RequestParam String password,
								@RequestParam String img,
								@RequestParam String name) {
		
		System.out.println("---------");
		System.out.println(mongoTemplate.getDb().getName());
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
