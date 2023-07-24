package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import weiver.entity.Reply;

import javax.transaction.Transactional;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    //내가 쓴 댓글 리스트 조회 작성순
    List<Reply> findByUserId(String userId);

    //내가 쓴 댓글 리스트 개수
    int countByUserId(String userId);
    
    //댓글 생성


    //아이디에 따라 게시글 1개 조회
	Reply getReplyById(Long id);
	
	//post id에 따라 댓글 조회
	List<Reply> findRepliesByPostIdOrderByCreatedTime(Long postId);
	
	//댓글 수정
	@Modifying
	@Transactional
	@Query("UPDATE Reply r SET r.content = :content WHERE r.id = :id")
	int updateReply(@Param("id") Long id, @Param("content") String content);
	
	//댓글 삭제
	@Modifying
	@Transactional
	int deleteReplyById(Long id);

}