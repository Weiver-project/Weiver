package weiver.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@Document(collection = "user")
public class User {
	@Id
	private String id;
	
	private String profileImg;
	private String password;
	private String nickname;
	private int	jjim;
	private int isWatched;
	private int myBoard;
	private int myComment;
	private int likeContent;
	private boolean admin;
	
}
