package weiver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weiver.dto.SimpleMusicalDTO;
import weiver.repository.SubscribeRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;


    /*찜, 봤어요 뮤지컬 조회*/
    public List<SimpleMusicalDTO> getSubscribeMusical(String userId, String type){
        List<SimpleMusicalDTO> simpleMusicalDTOS = subscribeRepository.findMusicalIdByUserIdAndType(userId, type);
        return simpleMusicalDTOS;
    }
}
