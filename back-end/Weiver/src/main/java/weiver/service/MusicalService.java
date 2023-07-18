
package weiver.service;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import weiver.dto.PerformingMusical;
import weiver.dto.PoPularMusicalDTO;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Musical;
import weiver.repository.CastingRepository;
import weiver.repository.MusicalRepository;
import weiver.repository.SubscribeRepository;

@Service
@RequiredArgsConstructor
public class MusicalService {
	private final MusicalRepository musicalRepository;
	private final SubscribeRepository subscribeRepository;
	private final CastingRepository castingRepository;


	/*인기 뮤지컬 조회*/
	public List<PoPularMusicalDTO> getLikedMusical(){
		List<PoPularMusicalDTO> poPularMusicalDTOList = new ArrayList<>();

		List<Object[]> musicalObjectList = subscribeRepository.findTop3MusicalIdsByDesiredType();

		for(Object[] o : musicalObjectList){
			String id = (String) o[0];
			String title = (String) o[1];
			PoPularMusicalDTO poPularMusicalDTO = PoPularMusicalDTO.builder().id(id).title(title).build();
			poPularMusicalDTOList.add(poPularMusicalDTO);
		}

		return poPularMusicalDTOList;
	}

	/*공연 중인 뮤지컬 조회*/
	public List<PerformingMusical> getPerformingMusical(){
		return musicalRepository.findPerformingMusicals(new Date());
	}

	/*뮤지컬 검색*/
	public List<SimpleMusicalDTO> getMusicalByKeyword(String keyword){
		return musicalRepository.findMusicalsByTitleKeyword(keyword);
	}

	/*뮤지컬 아이디로 조회*/
	public Optional<Musical> getMusicalById(String id){
		return musicalRepository.findById(id);
	}

	/*배우 출연작 조회*/
	public List<PerformingMusical> getMusicalByActor(Long actorId){
		List<PerformingMusical> performingMusicals = new ArrayList<>();

		List<Object[]> objects = castingRepository.findMusicalIdAndPosterByActorId(actorId);

		for(Object[] o : objects){
			String id = (String) o[0];
			String poster = (String) o[1];
			PerformingMusical performingMusical = PerformingMusical.builder().id(id).posterImage(poster).build();
			performingMusicals.add(performingMusical);
		}
		return performingMusicals;
	}
}

