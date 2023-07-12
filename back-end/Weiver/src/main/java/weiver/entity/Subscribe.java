package weiver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity(name = "SUBSCRIBE")
public class Subscribe {
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "xxx_SEQUENCE_GENERATOR")  
	@SequenceGenerator(name="xxx_SEQUENCE_GENERATOR", sequenceName = "xxx_SEQUENCE", initialValue = 1, allocationSize = 1)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "MUSICAL_ID")
	private String musicalId;
	
	@Column(name = "TYPE")
	private String type;
}
