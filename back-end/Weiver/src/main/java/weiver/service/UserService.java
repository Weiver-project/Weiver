package weiver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weiver.entity.User;
import weiver.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void test() {
        List<User> result = userRepository.findAll();
        System.out.println(result);
    }
}
