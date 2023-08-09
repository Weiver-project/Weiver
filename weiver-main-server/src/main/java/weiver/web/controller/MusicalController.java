package weiver.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import weiver.web.dto.PerformingMusical;
import weiver.web.dto.PoPularMusicalDTO;
import weiver.web.dto.ResponseCastingDTO;
import weiver.web.dto.SimpleMusicalDTO;
import weiver.domain.entity.Actor;
import weiver.domain.entity.Musical;
import weiver.domain.entity.Post;
import weiver.domain.entity.Subscribe;
import weiver.service.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MusicalController {
    private final MusicalService musicalService;
    private final ActorService actorService;
    private final CommunityService communityService;
	private final SubscribeService subscribeService;
    
    @GetMapping("/main")
    public String getMainPage(Model model){
        List<PoPularMusicalDTO> poPularMusicalDTOs = musicalService.getLikedMusical();
        List<PerformingMusical> performingMusicals = musicalService.getPerformingMusical();
        
        //인기 게시글 리스트 가져오기.
		List<Post> bestPostList = communityService.getBestPostDesc();

		//댓글 개수 가져오기
		model.addAttribute("bestPost", bestPostList);

        try {
			// 오늘의 배우 뮤지컬 정보.
			Actor randomActor = actorService.getRandomActor();
			List<PerformingMusical> musicalList = actorService.getMusicalListByActorId(randomActor.getId());
			List<PerformingMusical> limitedMusicalList = musicalList.subList(0, Math.min(musicalList.size(), 8));
			System.out.println("size : " + limitedMusicalList.size());

			model.addAttribute("randomActor", randomActor);
			model.addAttribute("limitedMusicalList", limitedMusicalList);
		} catch (Exception e) {
			e.printStackTrace();
		}


		//인기 뮤지컬 추가
        model.addAttribute("popularMusicals", poPularMusicalDTOs);
        //공연 중인 뮤지컬 추가
        model.addAttribute("performingMusicals", performingMusicals);
        
        return "main";
    }
    
    @GetMapping("/musical-detail/{id}")
    public String getMusicalDetail(@PathVariable String id, Model model, HttpSession session){
		Optional<Musical> musical =  musicalService.getMusicalById(id);

		if(musical.isPresent()) {
			model.addAttribute("musical", musical.get());
			List<ResponseCastingDTO> castingList = actorService.getCastingByMusicalId(musical.get().getId());
            
            if (castingList != null) {
                List<ResponseCastingDTO> limitedCastingList = castingList.subList(0, Math.min(castingList.size(), 10));
                model.addAttribute("castingList", limitedCastingList);
            }
		}

		if(session.getAttribute("userId") != null) {
			Subscribe subscribeJjim = subscribeService.getSubscribe(session.getAttribute("userId").toString(), id, "찜했어요");
			model.addAttribute("subscribeJjim", subscribeJjim);
			Subscribe subscribeWatched = subscribeService.getSubscribe(session.getAttribute("userId").toString(), id, "봤어요");
			model.addAttribute("subscribeWatched", subscribeWatched);
		}
//    	ResponseCastingDTO casting = actorService.getCastingByMusicalId(musical.get().getId());
//    	if (casting != null){
//    		model.addAttribute("casting", casting);
//    	}

		// Youtube API 가져오기
		try {
			List<String> clips = GoogleAPIService.search("뮤지컬" + musical.get().getTitle());
			model.addAttribute("clips", clips);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "musicalDetail";
    }
    
    @GetMapping("/musical-search")
    public String getMusicalSearch(@RequestParam(required = false) String keyword, Model model){
		if(keyword != null) {
			List<SimpleMusicalDTO> musicals = musicalService.getMusicalByKeyword(keyword);
			model.addAttribute("musicals", musicals);
		}

		return "musicalSearch";
    }
    
}
