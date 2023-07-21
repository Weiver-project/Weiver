package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weiver.entity.Inquiry;
import weiver.repository.InquiryRepository;

import java.util.Date;
import java.util.List;

@Service
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    public List<Inquiry> findByUserId(String userId) {
        return inquiryRepository.findByUserId(userId);
    }

    public boolean save(String userId, String title, String content) throws Exception {

        Date date = new Date();

        Inquiry inquiry = Inquiry.builder()
                                    .userId(userId)
                                    .title(title)
                                    .content(content)
                                    .createdTime(date).build();

        Inquiry result = inquiryRepository.save(inquiry);
        if(!result.getTitle().isEmpty()) {
            return true;
        }
        return false;
    }

    public Inquiry findById(Long id) {
        return inquiryRepository.getInquiryById(id);
    }

	public void deleteInquiry(Long inquiryId) {
		inquiryRepository.deleteById(inquiryId);
	}


}
