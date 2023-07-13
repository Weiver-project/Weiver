//package weiver.service;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import lombok.RequiredArgsConstructor;
//import weiver.dto.PoPularMusicalDTO;
//import weiver.repository.MusicalRepository;
//import weiver.repository.SubscribeRepository;
//
//@Service
//@RequiredArgsConstructor
//public class MusicalService {
//	private final MusicalRepository musicalRepository;
//	private final SubscribeRepository subscribeRepository;
//	
//	
//	/*인기 뮤지컬 조회*/
//	public List<PoPularMusicalDTO> getLikedMusical(){
//		List<String> musicalIds = subscribeRepository.findTop3MusicalIdsByDesiredType();
//		List<PoPularMusicalDTO> popularMusical = musicalRepository.findMusicalTitleByIds(musicalIds);
//		return popularMusical;
//	}
//
//}
