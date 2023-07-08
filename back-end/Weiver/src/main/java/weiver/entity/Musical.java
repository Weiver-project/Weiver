package weiver.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
@Document(collection = "musical")
public class Musical {
	@Id
	private String _id;
	private String posterImage;
	private String title;
	private String theater;
	private Date stDate;
	private Date edDate;
	private String runngingTime;
	private String viewingAge;
	private String ticketPrice;
	private List<String> actorIds;
}
