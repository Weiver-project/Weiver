package weiver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "user_tb")
public class User implements UserDetails{

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_pw")
	private String password;

	@Column(name = "user_nickname")
	private String nickname;

	@Column(name = "user_profile_img")
	private String profileImg;

	@Column(name = "essential_agree")
	private String essentialAgree;

	@Column(name = "personal_agree")
	private String personalAgree;

	@Column(name = "age_agree")
	private String ageAgree;

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();
	
	// getAuthorities()를 통해 권한을 얻음
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
				.map(SimpleGrantedAuthority::new) // 권한에 대한 문자열을 SimpleGrantedAuthority 객체로 매핑
				.collect(Collectors.toList()); // 리스트로 반환
	}

	@Override
	public String getUsername() {
		return id;	// 아이디 리턴
	}
	
	@Override
	public String getPassword() {
		return password; // 비밀번호 리턴
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;	// 계정이 만료되지 않았는지를 리턴
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;	// 계정이 잠겨있지 않은지를 리턴
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;	// 계정의 패스워드가 만료되지 않았는지를 리턴
	}

	@Override
	public boolean isEnabled() {
		return true;	// 사용 가능한 계정인지를 리턴
	}
}
