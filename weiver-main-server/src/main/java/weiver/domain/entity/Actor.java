package weiver.domain.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.springframework.lang.Nullable;

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
