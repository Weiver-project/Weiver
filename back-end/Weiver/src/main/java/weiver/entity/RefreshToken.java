package weiver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "refreshToken")
	private String refreshToken;

	public RefreshToken(String id, String refreshToken) {
		super();
		this.id = id;
		this.refreshToken = refreshToken;
	}
	
	public RefreshToken updateToken(String token) {
		this.refreshToken = token;
		return this;
	} 
}
