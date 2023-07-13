package entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ACTOR")
public class Actor {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "xxx_SEQUENCE_GENERATOR")
	@SequenceGenerator(name="xxx_SEQUENCE_GENERATOR", sequenceName = "xxx_SEQUENCE", initialValue = 1, allocationSize = 1)
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;
	@Column(name = "NAME")
	private String profileImage;
}
