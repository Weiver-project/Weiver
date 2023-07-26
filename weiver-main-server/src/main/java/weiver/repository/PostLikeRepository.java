package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import weiver.entity.PostLike;

import javax.transaction.Transactional;
import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByUserId(String userId);

    int countByUserId(String userId);

	int countByPostId(Long id);

	PostLike getPostLikeById(Long id);

	 List<PostLike> findByUserIdAndPostId(String userId, Long postId);

	    @Modifying
	    @Transactional
	    @Query("DELETE FROM PostLike pl WHERE pl.user.id = :userId AND pl.post.id = :postId")
	    void deleteByUserIdAndPostId(@Param("userId") String userId, @Param("postId") Long postId);
	    
	    @Query("SELECT pl FROM PostLike pl WHERE pl.user.id = :userId AND pl.post.id = :postId")
	    PostLike checkPostLike(@Param("userId") String userId, @Param("postId") Long postId);
	    
}
