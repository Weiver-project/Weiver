package weiver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Actor;
import weiver.repository.ActorRepository;
import weiver.repository.MusicalRepository;

@Service
@RequiredArgsConstructor
public class ActorService {
	private final ActorRepository actorRepository;
	private final MusicalRepository musicalRepository;

	public Actor getActorInfo(String actorId)  throws Exception{
		Actor actor = actorRepository.getById(actorId);
		
		return actor;
	}

	public List<SimpleMusicalDTO> getmusicalListByActorId(String actorId) throws Exception{
		List<SimpleMusicalDTO> musicalPosterImgList = musicalRepository.findMusicalsByActorId(actorId);
		
		return musicalPosterImgList;
	}
	
}
