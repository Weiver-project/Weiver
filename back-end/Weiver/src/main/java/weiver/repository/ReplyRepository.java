package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import weiver.entity.Post;
import weiver.entity.Reply;
import weiver.entity.User;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    //내가 쓴 댓글 리스트 조회
    List<Reply> findByUserId(String userId);

    //내가 쓴 댓글 리스트 개수
    int countByUserId(String userId);
    
    //댓글 생성
    @Modifying
    @Query("INSERT INTO Reply (post, user, content, createdTime) SELECT p, u, :content, CURRENT_TIMESTAMP FROM Post p, User u WHERE p.id = :postId AND u.id = :userId")
    int insertReply(@Param("postId") Long postId, @Param("userId") String userId, @Param("content") String content);


    //아이디에 따라 게시글 1개 조회
	Reply getReplyById(Long id);
	
	//post id에 따라 댓글 조회
	List<Reply> findRepliesByPostId(Long postId);
	
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