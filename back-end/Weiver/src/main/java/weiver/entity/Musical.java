package weiver.entity;

import java.util.Date;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MUSICAL")
public class Musical {
	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "TITLE")
	@Nullable
	private String title;
	@Nullable
	@Column(name = "THEATER")
	private String theater;
	@Column(name = "POSTER_IMAGE")
	private String posterImage;
	@Column(name = "STDATE")
	@Nullable
	private Date stDate;
	@Column(name = "EDDATE")
	@Nullable
	private Date edDate;
	@Column(name = "VIEW_AGE")
	@Nullable
	private String viewAge;
	@Column(name = "RUNNING_TIME")
	@Nullable
	private String runningTime;
	@Nullable
	@Column(name = "MAIN_CHARACTER")
	private String mainCharacter;
}
