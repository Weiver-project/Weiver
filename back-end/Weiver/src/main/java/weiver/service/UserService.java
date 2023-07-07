package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import weiver.dto.User;

@Service
public class UserService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void findAll() {
		System.out.println(mongoTemplate.getDb().getName());
		System.out.println(mongoTemplate.findAll(User.class));
	}
	
}
