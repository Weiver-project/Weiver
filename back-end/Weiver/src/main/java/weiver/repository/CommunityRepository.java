package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weiver.entity.Post;

import java.util.List;


@Repository
public interface CommunityRepository extends JpaRepository<Post, Long> {
	
	@Override
	List<Post> findAll();
	
	//타입에 따라 게시글 리스트 보임
	List<Post> findAllByType(String type);
	
//	@Query("SELECT p FROM Post p ORDER BY p.viewed DESC")
//	List<Post> getBestPostDesc();
	
	//내가 쓴 게시글 리스트 조회
	List<Post> findByUserId(String userId);

	//내가 쓴 게시글 리스트 개수 조회
	int countByUserId(String userId);
}
