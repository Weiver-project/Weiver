package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weiver.entity.Post;
import weiver.entity.Reply;

import java.util.List;

import javax.transaction.Transactional;


@Repository
public interface CommunityRepository extends JpaRepository<Post, Long> {
	
	@Override
	List<Post> findAll();
	
	//타입에 따라 게시글 리스트 보임
	List<Post> findAllByType(String type);
	
	//조회수가 높은 순부터 인기 게시글 노출
	@Query("SELECT p FROM Post p ORDER BY p.viewed DESC")
		 List<Post> findAllByOrderByViewedDesc();

	//id에 따라 하나의 게시글만 가져옴.
	Post getPostById(Long id);
		
	//title, content에 포함된 키워드에 따라 커뮤니티 게시글 조회
	List<Post> findByTitleContainingOrContentContaining(String title, String content);
		
	// 게시글 수정: 타입, 제목, 내용
	@Modifying
	@Transactional
	@Query("UPDATE Post p SET p.type = :type, p.title = :title, p.content = :content WHERE p.id = :id")
	int updatePost(@Param("id") Long id, @Param("type") String type, @Param("title") String title, @Param("content") String content);
		
	//id에 따른 게시글 삭제
	@Modifying
	@Transactional
	int deletePostById(Long id);

	//내가 쓴 게시글 리스트 조회
	List<Post> findByUserId(String userId);

	//내가 쓴 게시글 리스트 개수 조회
	int countByUserId(String userId);
	
}
