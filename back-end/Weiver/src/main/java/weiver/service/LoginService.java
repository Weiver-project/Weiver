package weiver.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weiver.entity.User;
import weiver.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	 
	// 아이디 중복 확인
	public boolean checkUserExists(String userId) {
		boolean result = userRepository.existsById(userId);
		return result;
	}

	// 닉네임 중복 확인
	public boolean checkUserNicknameExists(String userNickname) {
		boolean result = userRepository.existsByNickname(userNickname);
		return result;
	}

	// 회원 가입
	@Transactional
	public boolean saveUser(String userId, String userPw, String userNickname) throws Exception{
		// 암호화된 패스워드
		String encodedPassword = passwordEncoder.encode(userPw);

		System.out.println(userPw);
		System.out.println(encodedPassword);

		User user = User.builder()
							.id(userId)
							.password(encodedPassword)
							.nickname(userNickname)
							.profileImg("defaultProfileImgSrc")
							.essentialAgree("Y")
							.personalAgree("Y")
							.ageAgree("Y")
//							.activated("Y")
							.build();

		User result = userRepository.save(user);

		if(result != null) {
			return true;
		}

		return false;
	}
	
	// 로그인
	public User findByIdAndPassword(String id, String pw) {

		Optional<User> OptionalUser = userRepository.findById(id);

		if (OptionalUser.isPresent()) {
			User user = OptionalUser.get();
			if (user.getPassword().equals(pw)) {
				return user;
			}
		}

		return null;
	}

	public User loginTest(String userId, String userPw) {
		User user = userRepository.getUserById(userId);
		
		if(user == null) {
			return null;
		}
		
		if(user.getPassword() == userPw) {
			return user;
		}
		
		return null;
	}

}
