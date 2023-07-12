package weiver.service;

import java.util.List;
import java.util.Optional;

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
    
    // 유저 Id로 조회   
    public void findById(String id) {
    	Optional<User> result = userRepository.findById(id);
    	System.out.println(result);
    }
    
    // 유저 정보 수정(사진, 이름)
    public void updateInfo(String nickname, String profileImg, String id) {
    	userRepository.updateInfoById(nickname, profileImg, id);
    	System.out.println(userRepository.findById(id));
    }
    
    // 유저 정보 수정(비밀번호)
    public void updatePassword(String password, String id) {
    	User user = userRepository.findPasswordById(id);
    	String result = user.getPassword();
    	if(!result.equals(password)) {
    		userRepository.updatePasswordById(password, id);
    		System.out.println(userRepository.findById(id));
    	}
    }
    
}
