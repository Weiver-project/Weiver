package com.example.entity;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity(name = "MUSICAL")
@AllArgsConstructor
@NoArgsConstructor
public class Musical {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "xxx_SEQUENCE_GENERATOR")
	@SequenceGenerator(name="xxx_SEQUENCE_GENERATOR", sequenceName = "xxx_SEQUENCE", initialValue = 1, allocationSize = 1)
	private String id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "THEATER")
	private String theater;
	@Column(name = "POSTER_IMAGE")
	private String posterImage;
	@Column(name = "STDATE")
	private Date stDate;
	@Column(name = "EDDATE")
	private Date edDate;
	@Column(name = "VIEW_AGE")
	private String viewAge;
	@Column(name = "RUNNING_TIME")
	private String runningTime;
	@Column(name = "MAIN_CHARACTER")
	private String mainCharacter;
}
