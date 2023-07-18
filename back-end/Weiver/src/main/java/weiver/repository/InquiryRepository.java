package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weiver.entity.Inquiry;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByUserId(String userId);

    @Query("insert into INQUIRY (id, user_id, title, content, created_time) values (:#{#paraminquiry.id},:#{#paraminquiry.userId},:#{#paraminquiry.title},:#{#paraminquiry.createdTime})")
    int insert(@Param("paraminquiry") Inquiry inquiry);
}
