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
        List<SimpleMusicalDTO> simpleMusicalDTOS = new ArrayList<>();

        List<Object[]> objects = subscribeRepository.findMusicalIdByUserIdAndType(userId, type);

        for(Object[] o : objects){
            String id = (String) o[0];
            String title = (String) o[1];
            String poster = (String) o[2];
            Date stDate = (Date) o[3];
            Date edDate = (Date) o[4];

            SimpleMusicalDTO simpleMusicalDTO = SimpleMusicalDTO.builder()
                    .id(id)
                    .title(title)
                    .posterImage(poster)
                    .stDate(stDate)
                    .edDate(edDate)
                    .build();

            simpleMusicalDTOS.add(simpleMusicalDTO);
        }
        return simpleMusicalDTOS;
    }
}
