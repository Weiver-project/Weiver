package entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "casting")
public class Casting {
	@Id
	private String _id;
	private String title;
	private String posterImage;
	private Date stDate;
	private Date edDate;
	private String role;
}
