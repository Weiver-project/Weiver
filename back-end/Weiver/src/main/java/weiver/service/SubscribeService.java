package weiver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Musical;
import weiver.entity.Subscribe;
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

        List<String> findSubscribes = subscribeRepository.findByUserIdAndMusicalIdAndType(userId, musicalId, type);

        if(findSubscribes.size() == 0) { // 데이터가 없다면 추가
            subscribeRepository.save(Subscribe.builder()
                                            .userId(userRepository.getUserById(userId))
                                            .musicalId(Musical.builder()
                                                    .id(musicalId)
                                                    .build())
                                            .type(type).build());
            return true;
        }else { // 데이터가 있다면 삭제
            subscribeRepository.deleteByUserIdAndMusicalIdAndType(userId, musicalId, type);
        }

        return false;
    }

    public Subscribe getSubscribe(String userId, String musicalId, String type) {
        return subscribeRepository.getSubscribeByUserIdAndMusicalIdAndType(userId, musicalId, type);
    }
}
