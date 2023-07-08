package entity;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

@Document(collection = "actor")
@Builder
public class Actor {
	@Id
	private String _id;
	private String name;
	private String profileImage;
	private Casting[] castings;
	private ArrayList<Casting> Acastings;
	
	public void insertRole(Casting role) {
		Acastings.add(role);
	}
}
