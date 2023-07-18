package weiver.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import weiver.entity.Authority;
import weiver.entity.User;
import weiver.repository.UserRepository;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// 로그인 시 DB에서 유저 정보와 권한 정보를 가져옴, 해당 정보를 기반으로 userdetails.User 객체를 리턴함
	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userRepository.findOneWithAuthoritiesByid(username)
				.map(user -> createUser(username, user))
				.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터 베이스에서 찾을 수 없습니다."));
	}

	private org.springframework.security.core.userdetails.User createUser(String id, User user) {
		
		if(user.getActivated() == null || user.getActivated() == "" || !user.getActivated().equals("Y")) {
			throw new RuntimeException(id + " -> 활성화되어 있지 않습니다."); // 활성화 상태 확인
		}
		
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()	// 권한 정보를 가져옴
														.map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
														.collect(Collectors.toList());
		
		return new org.springframework.security.core.userdetails.User(user.getId(),			// id, pw를 userdetails.User객체에 리턴해줌
																	  user.getPassword(),
																	  grantedAuthorities);
	}
}
