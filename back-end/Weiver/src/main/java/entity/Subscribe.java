package entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subscribe")
public class Subscribe {
	@Id
	private String _id;
	private String type;
	private String userId;
	private String musicalId;
	private String musicalTitle;
}
