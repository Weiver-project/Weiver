package entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "casting")
@Builder
@Getter
@ToString
@Setter
public class Casting {
	@Id
	private String _id;
	private String title;
	private String posterImage;
	private Date stDate;
	private Date edDate;
	private String role;
}
