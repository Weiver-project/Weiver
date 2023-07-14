package com.example.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ACTOR")
public class Actor {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;
	@Column(name = "PROFILE_IMAGE")
	private String profileImage;
}
