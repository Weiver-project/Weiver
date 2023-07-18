package weiver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.dto.PerformingMusical;
import weiver.dto.PoPularMusicalDTO;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Musical;
import weiver.service.MusicalService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MusicalController {
    private final MusicalService musicalService;

    @GetMapping("/main")
    public String getMainPage(Model model){
        List<PoPularMusicalDTO> poPularMusicalDTOs = musicalService.getLikedMusical();
        List<PerformingMusical> performingMusicals = musicalService.getPerformingMusical();

        System.out.println(performingMusicals.size());
        
        for(PerformingMusical p: performingMusicals) {
        	System.out.println(p.getId());
        	System.out.println(p.getPosterImage());
        }
        
        model.addAttribute("popularMusicals", poPularMusicalDTOs);
        model.addAttribute("performingMusicals", performingMusicals);

        return "main";
    }
    
    @GetMapping("/musical-detail/{id}")
    public String getMusicalDetail(@PathVariable String id, Model model){
    	Optional<Musical> musical =  musicalService.getMusicalById(id);
    	
    	if(musical.isPresent()) {
    		model.addAttribute("musical", musical.get());
    	}
    	
    	return "musicalDetail";
    }
    
    @GetMapping("/musical-search")
    public String getMusicalSearch(@RequestParam(required = false) String keyword, Model model){
    	if(keyword != null) {
    		List<SimpleMusicalDTO> musicals = musicalService.getMusicalByKeyword(keyword);
    		model.addAttribute("musicals", musicals);
    		
    		System.out.println(musicals.size());
    	}    	
    	
    	return "musicalSearch";
    }
}
