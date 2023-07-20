package weiver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weiver.dto.SimpleMusicalDTO;
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

        Subscribe subscribe = subscribeRepository.save(Subscribe.builder()
                                                            .userId(userRepository.getUserById(userId))
                                                            .musicalId(musicalRepository.getMusicalById(musicalId))
                                                            .type(type).build());
        if (subscribe.getType().isEmpty()) {
            return true;
        }
        return false;
    }
}
