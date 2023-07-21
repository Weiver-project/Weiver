package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weiver.entity.Admin;
import weiver.entity.Answer;
import weiver.entity.Inquiry;
import weiver.repository.AnswerRepository;
import weiver.repository.InquiryRepository;

import java.util.Date;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public void answerInquiry(Inquiry inquiry, String answerContent) {
    	// Answer가 없다면 save
    	if(answerRepository.findAnswerByInquiryId(inquiry.getId()) == null) {
    		
    		// Admin 계정을 로그인에 연동해주어야 한다.
    		
	    	Answer answer = Answer.builder()
					    			.inquiry(inquiry)
					    			.admin(Admin.builder()
					    						.id("1234")
					    						.password("adminpw")
					    						.name("ad")
					    						.build())
					    			.answer(answerContent)
					    			.createdTime(new Date())
					    			.build();
    	
    		answerRepository.save(answer);
    	}else {
    		System.out.println("이미 답변이 존재합니다.");
    	}
    }   
}
