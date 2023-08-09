package weiver.controllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import weiver.domain.entity.Actor;
import weiver.service.ActorService;
import weiver.web.controller.ActorController;
import weiver.web.dto.PerformingMusical;

import java.util.ArrayList;
import java.util.List;

public class ActorControllerTest {

    private ActorService actorService = mock(ActorService.class);

    @Test
    public void 배우_상세페이지가_잘넘어온다() {
        // Given
        String actorId = "123";
        Actor mockActor = new Actor();
        List<PerformingMusical> mockMusicalList = new ArrayList<>();
        when(actorService.getActorInfo(actorId)).thenReturn(mockActor);
        when(actorService.getMusicalListByActorId(actorId)).thenReturn(mockMusicalList);

        ActorController controller = new ActorController(actorService);
        Model model = mock(Model.class);

        // When
        String viewName = controller.getActorDetailPage(actorId, model);

        // Then
        verify(model).addAttribute("actor", mockActor);
        verify(model).addAttribute("musicalList", mockMusicalList);
        assertEquals("actorDetail", viewName);
    }
}
