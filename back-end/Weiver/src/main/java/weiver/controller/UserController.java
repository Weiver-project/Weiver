package weiver.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import weiver.dto.User;
import weiver.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/mongo",method = RequestMethod.GET)
	public void test() {
		System.out.println("---------");
		System.out.println(mongoTemplate.getDb().getName());
		
//		List<Document> userList = mongoTemplate.find(new Query(), Document.class, "user");
//		
//		System.out.println(userList);
		userService.findAll();
	}
}
