package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weiver.entity.PostLike;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByUserId(String userId);

    int countByUserId(String userId);
}
