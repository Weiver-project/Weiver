package com.example.entity;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Data
@Builder
@Entity(name = "MUSICAL")
@AllArgsConstructor
@NoArgsConstructor
public class Musical {
	@Id
	private String id;
	@Column(name = "TITLE")
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