package weiver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Actor;
import weiver.service.ActorService;

@Controller
@Slf4j
public class ActorController {
	@Autowired
	private ActorService actorService;
	
	// 배우 상세 페이지 
	@GetMapping(value = "/actorDetail/{actorId}")
	public String actorDetail(@PathVariable String actorId,
								Model model) {
		
		try {
			Actor actor = actorService.getActorInfo(actorId);
			List<SimpleMusicalDTO> musicalList = actorService.getmusicalListByActorId(actorId);
			
			model.addAttribute("actor", actor);
			model.addAttribute("musicalList", musicalList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("배우 정보, 출연한 뮤지컬 정보 호출 예외");
		}
		
		return "actorDetail";
	}
}
