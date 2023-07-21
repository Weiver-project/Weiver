package weiver.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.entity.Answer;
import weiver.entity.Inquiry;
import weiver.repository.AnswerRepository;
import weiver.repository.InquiryRepository;
import weiver.service.AnswerService;
import weiver.service.InquiryService;

@Controller
@RequestMapping
public class AnswerController {
	@Autowired
	private InquiryService inquiryService;

    @Autowired
    private AnswerService answerService;

    @RequestMapping(value="/answerInquiryForm/{inquiryId}",method = RequestMethod.GET)
    public String answerInquiry(@PathVariable Long inquiryId,
    		Model model) {
    	model.addAttribute("inquiry", inquiryService.findById(inquiryId));
    	
    	return "answerInquiryForm";
    }
    
    
    
    
    @RequestMapping(value="/answerInquiry",method = RequestMethod.POST)
    public String answerInquiry(@RequestParam String answerContent, 
    		@RequestParam Long id, 
    		@RequestParam String title, 
    		@RequestParam String userId,
    		@RequestParam String content,    		
    		@RequestParam String createdTime,    		
    		Model model) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Date ct = new Date();
		try {
			ct = formatter.parse(createdTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
        Inquiry inquiry = Inquiry.builder()
        		.id(id)
                .userId(userId)
                .title(title)
                .content(content)
                .createdTime(ct)
                .build();
        
    	answerService.answerInquiry(inquiry, answerContent);
    	
    	return "redirect:http://localhost:8081/admin/getAllInquirys";
    }
    
    
    
}
