package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import weiver.entity.ReReply;

import java.util.List;

import javax.transaction.Transactional;

public interface ReReplyRepository extends JpaRepository<ReReply, Long> {
    //내가 쓴 대댓글 리스트 조회
    List<ReReply> findByUserId(String userId);

    //내가 쓴 대댓글 리스트 개수
    int countByUserId(String userId);
    
    //아이디로 대댓글 하나 조회
	ReReply getRereplyById(Long id);
	
	//post_id와 reply_id으로 대댓글 하나 조회
	List<ReReply> findByPostIdAndReplyId(Long postId, Long replyId);
	
	//대댓글 수정
	@Modifying
	@Transactional
	@Query("UPDATE ReReply re SET re.content = :content WHERE re.id = :id")
	int updateRereply(@Param("id") Long id, @Param("content") String content);
	
	
	//대댓글 삭제
	@Modifying
	@Transactional
	int deleteRereplyById(Long id);

}
