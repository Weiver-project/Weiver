package weiver.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCastingDTO {
	public String id;
	public String name;
	public String role;
	public String profileImage;
}
