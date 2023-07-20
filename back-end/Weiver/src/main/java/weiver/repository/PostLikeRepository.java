package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import weiver.entity.PostLike;

import java.util.List;

import javax.transaction.Transactional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByUserId(String userId);

    int countByUserId(String userId);

	PostLike getPostLikeById(Long id);
	
	@Modifying
	@Transactional
	int deletePostLikeById(Long id);
}
