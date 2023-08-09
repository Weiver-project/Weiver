package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weiver.domain.entity.Admin;
import weiver.domain.entity.Answer;
import weiver.domain.entity.Inquiry;
import weiver.domain.repository.AnswerRepository;

import java.util.Date;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public void answerInquiry(Inquiry inquiry, String answerContent, Admin admin) {
    	// Answer가 없다면 save
    	if(answerRepository.findAnswerByInquiryId(inquiry.getId()) == null) {
    		
	    	Answer answer = Answer.builder()
					    			.inquiry(inquiry)
					    			.admin(admin)
					    			.answer(answerContent)
					    			.createdTime(new Date())
					    			.build();
    	
    		answerRepository.save(answer);
    	}else {
    		System.out.println("이미 답변이 존재합니다.");
    	}
    }   
}
