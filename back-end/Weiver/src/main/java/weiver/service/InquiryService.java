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

    public boolean save(String id, String title, String content) throws Exception {

        Date date = new Date();

        Inquiry inquiry = Inquiry.builder()
                                    .id(3L)
                                    .userId(id)
                                    .title(title)
                                    .content(content)
                                    .createdTime(date).build();

        System.out.println(inquiry);

        int result = inquiryRepository.insert(inquiry);
        System.out.println(result);
        return true;
    }
}
