package weiver.service;


import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weiver.entity.User;
import weiver.repository.UserRepository;

@Service
public class LoginService {
	
	private static Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private UserRepository userRepository;
	 
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
		String encodedPassword = BCrypt.hashpw(userPw, BCrypt.gensalt(10));

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
							.build();

		User result = userRepository.save(user);

		if(result != null) {
			return true;
		}

		return false;
	}

	// 로그인
	@Transactional
	public User loginTest(String id, String userPw) throws Exception{
		User user = userRepository.getUserById(id);
		
		if(BCrypt.checkpw(userPw, user.getPassword())) {
			System.out.println("패스워드 일치 결과 true");
			return user;
		}
		
		return null;
	}

}
