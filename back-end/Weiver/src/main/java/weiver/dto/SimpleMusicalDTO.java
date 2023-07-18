package weiver.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public class SimpleMusicalDTO {
	private String id;
	private String title;
	private String posterImage;
	private Date stDate;
	private Date edDate;
}
