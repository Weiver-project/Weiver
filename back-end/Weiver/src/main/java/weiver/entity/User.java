package weiver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "user_tb")
public class User {

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
}
