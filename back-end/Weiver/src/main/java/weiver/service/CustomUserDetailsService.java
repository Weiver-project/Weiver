package weiver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import weiver.entity.User;
import weiver.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepositoy;
	private final PasswordEncoder passwordEncoder;
	
	// userId를 조회해서 UserDetails 객체를 반환하거나 예외를 발생시킴
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		return userRepositoy.findById(userId)
				.map(this::createUserDetails) // 사용자 존재할 경우 createUserDetails 메소드 호출
				.orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다,"));
	}
	
	// 
	private UserDetails createUserDetails(User user) {
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
        		.password(passwordEncoder.encode(user.getPassword()))
        		.roles(user.getRoles().toArray(new String[0]))
        		.build();
    }
}
