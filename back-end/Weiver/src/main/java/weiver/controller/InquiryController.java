package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weiver.entity.Inquiry;
import weiver.service.InquiryService;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "inquiry")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    // 문의하기
    @GetMapping("/inquiryMain")
    public String inquiry(HttpSession session, Model model) {
    	String userId = (String) session.getAttribute("userId");
        List<Inquiry> inquiryList = inquiryService.findByUserId(userId);
        model.addAttribute(inquiryList);

        return "inquiry";
    }

    // 문의 작성폼
    @GetMapping("/inquiryForm")
    public String inquiryForm() {
        return "inquiryForm";
    }

    //
    @PostMapping("/inquiryInsert")
    public String insertInquiry(HttpSession session,
    							@RequestParam(value = "title") String title,
                                @RequestParam(value = "content") String content) {
    	
    	String userId = (String) session.getAttribute("userId");
    	
        try {
            inquiryService.save(userId, title, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/inquiry/inquiryMain" ;
    }

    @GetMapping("/inquiryDetail/{inquiryId}")
    public String inquiryDetail(@PathVariable Long inquiryId,
                                Model model) {
        Inquiry inquiry = inquiryService.findById(inquiryId);
        model.addAttribute("inquiry", inquiry);

        return "inquiryDetail";
    }
}
