package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import weiver.entity.PostLike;

import javax.transaction.Transactional;
import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByUserId(String userId);

    int countByUserId(String userId);

	int countByPostId(Long id);

	PostLike getPostLikeById(Long id);

	@Modifying
	@Transactional
	int deletePostLikeById(Long id);
}
