package weiver.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import weiver.domain.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	 /* InquiryID로 Answer가 존재하는지 체크 */
    @Query("SELECT a FROM Answer a WHERE a.id = ?1")
    Answer findAnswerByInquiryId(Long inquiryId);

}
