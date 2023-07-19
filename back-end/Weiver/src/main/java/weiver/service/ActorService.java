package weiver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import weiver.dto.ResponseCastingDTO;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Actor;
import weiver.entity.Casting;
import weiver.repository.ActorRepository;
import weiver.repository.CastingRepository;
import weiver.repository.MusicalRepository;

@Service
@RequiredArgsConstructor
public class ActorService {
	private final ActorRepository actorRepository;
	private final MusicalRepository musicalRepository;
	private final CastingRepository castingRepository;

	public Actor getActorInfo(String actorId)  throws Exception{
		Actor actor = actorRepository.getById(actorId);
		
		return actor;
	}

	public List<SimpleMusicalDTO> getmusicalListByActorId(String actorId) throws Exception{
		List<SimpleMusicalDTO> musicalPosterImgList = musicalRepository.findMusicalsByActorId(actorId);
		
		return musicalPosterImgList;
	}

	public List<ResponseCastingDTO> getCastingByMusicalId(String id) {
		List<ResponseCastingDTO> casting = castingRepository.getCastingByMusicalId(id);
		return casting;
	}
	
}
