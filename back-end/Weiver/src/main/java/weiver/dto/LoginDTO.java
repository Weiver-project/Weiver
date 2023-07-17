package weiver.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
	
	@NotNull
	@Size(min = 3, max = 30)
	private String id;
	
	@NotNull
	@Size(min = 3, max = 30)
	private String password;
}
