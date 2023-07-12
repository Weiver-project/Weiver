package weiver.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "\"id\"")
	private String id;
	
	@Column(name = "\"user_pw\"")
	private String password;
	
	@Column(name = "\"user_nickname\"")
	private String nickname;
	
	@Column(name = "\"user_profile_img\"")
	private String profileImg;
	

}
