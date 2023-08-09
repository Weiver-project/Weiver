package weiver.web.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import weiver.web.dto.PerformingMusical;
import weiver.domain.entity.Actor;
import weiver.service.ActorService;

@Controller
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;

    @GetMapping(value = "/actorDetail/{actorId}")
    public String getActorDetailPage(@PathVariable String actorId, Model model) {

            Actor actor = actorService.getActorInfo(actorId);
            List<PerformingMusical> musicalList = actorService.getMusicalListByActorId(actorId);

            model.addAttribute("actor", actor);
            model.addAttribute("musicalList", musicalList);

        return "actorDetail";
    }
}
