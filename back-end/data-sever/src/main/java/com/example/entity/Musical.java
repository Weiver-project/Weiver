package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MUSICAL")
public class Musical {
	@Id
	@Column(name = "ID")
	private String id;
	
	@Nullable
	@Column(name = "TITLE")
	private String title;
	
	@Nullable
	@Column(name = "THEATER")
	private String theater;
	
	@Nullable
	@Column(name = "POSTER_IMAGE")
	private String posterImage;
	
	@Nullable
	@Column(name = "STDATE")
	private Date stDate;
	
	@Nullable
	@Column(name = "EDDATE")
	private Date edDate;
	
	@Nullable
	@Column(name = "VIEW_AGE")
	private String viewAge;
	
	@Nullable
	@Column(name = "RUNNING_TIME")
	private String runningTime;
	
	@Nullable
	@Column(name = "MAIN_CHARACTER")
	private String mainCharacter;
}
