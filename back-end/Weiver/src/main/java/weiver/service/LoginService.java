package weiver.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import weiver.dto.TokenInfo;
import weiver.entity.User;
import weiver.jwt.JwtTokenProvider;
import weiver.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class LoginService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	
	public boolean checkUserId(String userId) {
		return userRepository.existsById(userId);
	} 
	
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
							.build();
		
		User result = userRepository.save(user);
		
		if(result != null) {
			return true;
		}
		
		return false;
	}
	
	// 로그인
	@Transactional
	public TokenInfo signin(String userId, String userPw) {
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(userId, userPw); // 사용자 id, pw를 통해 UsernamePasswordAuthenticationToken 객체를 생성함
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); // authenticationToken을 인증하고 성공하면 authenticationToken 객체를 반환
		TokenInfo tokenInfo = jwtTokenProvider.generateTokenDto(authentication); // authenticationToken 객체를 기반으로 토큰 생성
		
		return tokenInfo;
	}
}
