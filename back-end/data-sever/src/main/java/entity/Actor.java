package entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "actor")
@Builder
@Setter
@Getter
@ToString
public class Actor {
	@Id
	private String _id;
	private String name;
	private String profileImage;
	private Casting[] castings;
	
	@Builder
	@Getter
	@Setter	
	@ToString
	public static class Casting {
		private String title;
		private String theater;
		private String posterImage;
		private Date stDate;
		private Date edDate;
		private String role;
	}

}
