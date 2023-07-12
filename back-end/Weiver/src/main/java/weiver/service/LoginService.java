package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import weiver.entity.User;
import weiver.repository.UserRepository;

import java.util.Optional;


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
