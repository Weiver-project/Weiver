package com.example.entity;

import lombok.*;
import reactor.util.annotation.Nullable;

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

	@Nullable
	@Column(name = "NAME")
	private String name;
	
	@Nullable
	@Column(name = "PROFILE_IMAGE")
	private String profileImage;
}
