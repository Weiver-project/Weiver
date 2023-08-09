package weiver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "SUBSCRIBE")
public class Subscribe {
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "xxx_SEQUENCE_GENERATOR")  
	@SequenceGenerator(name="xxx_SEQUENCE_GENERATOR", sequenceName = "SUBSCRIBE_SEQUENCE", initialValue = 1, allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "id")
	private User userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MUSICAL_ID", referencedColumnName = "ID")
	private Musical musicalId;
	
	@Column(name = "TYPE")
	private String type;
}
