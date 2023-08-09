package weiver.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import weiver.domain.entity.Inquiry;
import weiver.service.AdminService;
import weiver.web.controller.AdminController;

import java.util.ArrayList;
import java.util.List;

public class AdminControllerTest {

    private AdminService adminService = mock(AdminService.class);

    @Test
    public void 관리자문의_페이지가_잘넘어온다() {
        // Given
        List<Inquiry> mockInquiries = new ArrayList<>();
        when(adminService.getAllInquirys()).thenReturn(mockInquiries);

        AdminController controller = new AdminController(adminService);
        Model model = mock(Model.class);

        // When
        String viewName = controller.getAdminMainPage(model);

        // Then
        verify(model).addAttribute("inquiries", mockInquiries);
        assertEquals("adminInquiries", viewName);
    }


}
