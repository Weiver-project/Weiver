package weiver.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import weiver.domain.entity.ReplyLike;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    @Query("SELECT COUNT(?1) FROM ReplyLike r WHERE r.reply.id = ?1")
    int count(Long id);
}
