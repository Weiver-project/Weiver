
package weiver.service;

import java.util.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import weiver.web.dto.PerformingMusical;
import weiver.web.dto.PoPularMusicalDTO;
import weiver.web.dto.SimpleMusicalDTO;
import weiver.domain.entity.Musical;
import weiver.domain.repository.CastingRepository;
import weiver.domain.repository.MusicalRepository;
import weiver.domain.repository.SubscribeRepository;

@Service
@RequiredArgsConstructor
public class MusicalService {
	private final MusicalRepository musicalRepository;
	private final SubscribeRepository subscribeRepository;
	private final CastingRepository castingRepository;


	/*인기 뮤지컬 조회*/
	public List<PoPularMusicalDTO> getLikedMusical(){
		Pageable pageable = (Pageable) PageRequest.of(0, 3);
		List<PoPularMusicalDTO> poPularMusicalDTOList = subscribeRepository.findTop3MusicalByDesiredType(pageable);
		
		return poPularMusicalDTOList;
	}

	/*공연 중인 뮤지컬 조회*/
	public List<PerformingMusical> getPerformingMusical(){
		List<PerformingMusical> performingMusicals = musicalRepository.findPerformingMusicals(new Date());
		return performingMusicals;
	}

	/*뮤지컬 검색*/
	public List<SimpleMusicalDTO> getMusicalByKeyword(String keyword){
		return musicalRepository.findMusicalsByTitleKeyword(keyword);
	}

	/*뮤지컬 아이디로 조회*/
	public Optional<Musical> getMusicalById(String id){
		return musicalRepository.findById(id);
	}
	
	/*모든 뮤지컬 조회*/
	public List<Musical> getAllMusical(){
		return musicalRepository.findAll();
	}

	/*배우 출연작 조회*/
	public List<PerformingMusical> getMusicalByActor(Long actorId){
		List<PerformingMusical> performingMusicals = castingRepository.findMusicalIdAndPosterByActorId(actorId);
		return performingMusicals;
	}
}

