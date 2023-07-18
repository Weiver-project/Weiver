package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import weiver.entity.Actor;
import weiver.service.ActorService;

@Controller
public class ActorController {
	
	@Autowired
	private ActorService actorService;
	
	// 배우 상세 페이지 
	@GetMapping(value = "/actorDetail/{actorId}")
	public String actorDetail(@PathVariable String actorId,
								Model model) {
		
		try {
			Actor actor = actorService.getActorInfo(actorId);
			model.addAttribute("actor", actor);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "actorDetail";
	}
}
