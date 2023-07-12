package weiver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import weiver.entity.Post;


@Repository
public interface CommunityRepository extends JpaRepository<Post, Long> {
	
	@Override
	List<Post> findAll();
	
	//타입에 따라 게시글 리스트 보임
	List<Post> findAllByType(String type);
	
//	@Query("SELECT p FROM Post p ORDER BY p.viewed DESC")
//	List<Post> getBestPostDesc();
}
