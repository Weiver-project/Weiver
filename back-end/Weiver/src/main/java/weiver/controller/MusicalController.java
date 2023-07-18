package weiver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import weiver.dto.PoPularMusicalDTO;
import weiver.service.MusicalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicalController {
    private final MusicalService musicalService;

    @GetMapping("/main")
    public String getMainPage(Model model){
        List<PoPularMusicalDTO> poPularMusicalDTOs = musicalService.getLikedMusical();

        model.addAttribute("popularMusical", poPularMusicalDTOs);

        return "main";
    }
}
