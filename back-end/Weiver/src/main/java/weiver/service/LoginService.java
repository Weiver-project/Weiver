package weiver.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weiver.dto.SignupDTO;
import weiver.entity.Authority;
import weiver.entity.User;
import weiver.repository.UserRepository;
import weiver.util.SecurityUtil;

@Service
public class LoginService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	// 회원가입
	@Transactional
	public User signup(SignupDTO signupDto) {
		if(userRepository.findOneWithAuthoritiesByid(signupDto.getId()).orElse(null) != null) {	// DB에 아이디를 검색함, id가 null이 아니면 예외 발생
			throw new RuntimeException("이미 가입되어 있는 유저입니다.");
		}
		
		if(userRepository.existsByNickname(signupDto.getNickname())) {
			throw new RuntimeException("이미 존재하는 닉네임입니다.");
		}
		
		// 유저 id 중복 확인
//		if (userIdExists) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
//		}
//		
//		// 닉네임 중복 확인
//		if (userNicknameExists) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임입니다.");
//		}
//		
//		// 비밀번호, 비밀번호확인 체크
//		if(!userPw.equals(userPwCheck)) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력하신 비밀번호와 확인 비밀번호가 다릅니다.");
//		}
		
		Authority authority = Authority.builder()
				.authorityName("ROLE_USER")
				.build();

		User user = User.builder()
						.id(signupDto.getId())
						.password(passwordEncoder.encode(signupDto.getPassword()))
						.nickname(signupDto.getNickname())
						.profileImg("baseURL")
						.essentialAgree("Y")
						.personalAgree("Y")
						.ageAgree("Y")
						.activated("Y")
						.authorities(Collections.singleton(authority))	// Set을 사용해서 단일권한 부여
						.build();
		
		return userRepository.save(user);
	}
	
	// id를 통해 유저 객체와 권한 객체를 리턴 받음
	@Transactional
	public Optional<User> getUserWithAuthorities(String id) {
		return userRepository.findOneWithAuthoritiesByid(id);
	}
	
	// 현재 Security Context에 저장이 되어있는 id에 해당하는 유저 객체와 권한 객체를 받음
	@Transactional
	public Optional<User> getMyUserWithAuthorities() {
		return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByid);	// flatMap : 단일 스트림으로 평면화
	}

}
