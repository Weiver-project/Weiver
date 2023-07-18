package com.example.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACTOR")
public class Actor {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PROFILE_IMAGE")
	private String profileImage;
}
