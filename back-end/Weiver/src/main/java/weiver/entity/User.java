package weiver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "user_tb")
public class User{

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

	@Column(name = "activated")
	private String activated;
	
	
	// 매핑 테이블
	@ManyToMany
	@JoinTable(
				name = "user_authority",
				joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
				inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	private Set<Authority> authorities;

}
