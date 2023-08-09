package weiver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import weiver.web.dto.PerformingMusical;
import weiver.web.dto.ResponseCastingDTO;
import weiver.web.dto.SimpleMusicalDTO;
import weiver.domain.entity.Actor;
import weiver.domain.repository.ActorRepository;
import weiver.domain.repository.CastingRepository;
import weiver.domain.repository.MusicalRepository;

@Service
@RequiredArgsConstructor
public class ActorService {
	private final ActorRepository actorRepository;
	private final MusicalRepository musicalRepository;
	private final CastingRepository castingRepository;

	public Actor getActorInfo(String actorId) {
		Actor actor = actorRepository.getById(actorId);
		
		return actor;
	}
	
	// 배우 상세 페이지 뮤지컬 리스트 조회
	public List<PerformingMusical> getMusicalListByActorId(String actorId) {
		List<PerformingMusical> musicalPosterImgList = musicalRepository.findMusicalsByActorId(actorId);
		
		return musicalPosterImgList;
	}
	
	// 뮤지컬 상세 페이지 캐스팅 조회
	public List<ResponseCastingDTO> getCastingByMusicalId(String id) {
		List<ResponseCastingDTO> casting = castingRepository.getCastingByMusicalId(id);
		return casting;
	}

	// 오늘의 배우
	public Actor getRandomActor() throws Exception{	
		Actor actor = actorRepository.getRandomActor();
		return actor;
	}
	
}
