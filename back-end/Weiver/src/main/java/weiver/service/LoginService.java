package weiver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import weiver.dto.User;
import weiver.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	public User signIn(String id, String pw) {
		
		Optional<User> OptionalUser = userRepository.findById(id);
		
		if (OptionalUser.isPresent()) {
			User user = OptionalUser.get();
			if (user.getPassword().equals(pw)) {
				return user;
			}
		}
		
		return null;
	}
	
}
