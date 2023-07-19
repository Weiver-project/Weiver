package weiver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.dto.PerformingMusical;
import weiver.dto.PoPularMusicalDTO;
import weiver.dto.ResponseCastingDTO;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Musical;
import weiver.service.ActorService;
import weiver.service.MusicalService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MusicalController {
    private final MusicalService musicalService;
    private final ActorService actorService;
    
    @GetMapping("/main")
    public String getMainPage(Model model){
        List<PoPularMusicalDTO> poPularMusicalDTOs = musicalService.getLikedMusical();
        List<PerformingMusical> performingMusicals = musicalService.getPerformingMusical();

        	
        //인기 뮤지컬 추가
        System.out.println(poPularMusicalDTOs.get(0).getTitle());
        
        model.addAttribute("popularMusicals", poPularMusicalDTOs);
        //공연 중인 뮤지컬 추가
        model.addAttribute("performingMusicals", performingMusicals);

        return "main";
    }
    
    @GetMapping("/musical-detail/{id}")
    public String getMusicalDetail(@PathVariable String id, Model model){
    	Optional<Musical> musical =  musicalService.getMusicalById(id);
    	
    	if(musical.isPresent()) {
    		model.addAttribute("musical", musical.get());
    	}
    	
//    	ResponseCastingDTO casting = actorService.getCastingByMusicalId(musical.get().getId());
//    	if (casting != null){
//    		model.addAttribute("casting", casting);
//    	}
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
