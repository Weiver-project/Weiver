package weiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weiver.entity.Inquiry;
import weiver.service.InquiryService;

import java.util.List;

@Controller
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    // 문의하기
    @GetMapping("/inquiry/{userId}")
    public String inquiry(@PathVariable String userId,
                          Model model) {
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
    @PostMapping("/inquiry")
    public String insertInquiry(@RequestParam(value = "title") String title,
                                @RequestParam(value = "content") String content) {
        try {
            inquiryService.save("asdf@naver.com", title, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "inquiry" ;
    }

    @GetMapping("/inquiryDetail/{inquiryId}")
    public String inquiryDetail() {


        return "inquiryDetail";
    }
}
