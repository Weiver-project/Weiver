package weiver.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "USER_TB")
public class User {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "USER_PW")
	private String password;
	
	@Column(name = "USER_NICKNAME")
	private String nickname;
	
	@Column(name = "USER_PROFILE_IMG")
	private String profileImg;
	
	@Column(name = "ESSENTIAL_AGREE")
	private String essentialAgree;
	
	@Column(name = "PERSONAL_AGREE")
	private String personalAgree;
	
	@Column(name = "AGE_AGREE")
	private String ageAgree;

}