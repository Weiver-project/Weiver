package weiver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Musical;
import weiver.entity.Subscribe;
import weiver.entity.User;
import weiver.repository.MusicalRepository;
import weiver.repository.SubscribeRepository;
import weiver.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;
    private final MusicalRepository musicalRepository;

    /*찜, 봤어요 뮤지컬 조회*/
    public List<SimpleMusicalDTO> getSubscribeMusical(String userId, String type){
        List<SimpleMusicalDTO> simpleMusicalDTOS = subscribeRepository.findMusicalIdByUserIdAndType(userId, type);
        return simpleMusicalDTOS;
    }

    /*찜, 봤어요 저장*/
    public boolean insertSubscribe(String userId, String musicalId, String type) {
    	System.out.println("insertSubscribe");
    	System.out.println(userId);
    	System.out.println(musicalId);
    	System.out.println(type);
    	
    	
    	Subscribe findSubscribe = subscribeRepository.findByUserIdAndMusicalIdAndType(userId, musicalId, type);
    	System.out.println("findSubscribe 완료");
    	if(findSubscribe == null) { // 데이터가 없다면 추가
	        subscribeRepository.save(Subscribe.builder()
                                            .userId(userRepository.getUserById(userId))
                                            .musicalId(Musical.builder()
                                            				  .id(musicalId)
                                            				  .build())
                                            .type(type).build());
	        System.out.println(musicalId + " " + type + " Subscribe 추가");
	        return true;
    	}else { // 데이터가 있다면 삭제
    		subscribeRepository.deleteByUserIdAndMusicalIdAndType(userId, musicalId, type);
    		System.out.println(musicalId + " " + type + " Subscribe 삭제");
    	}
    	
        return false;
    }
}
