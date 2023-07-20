package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import weiver.entity.ReReply;

public interface ReReplyLikeRepository extends JpaRepository<ReReply, Long> {
    @Query("SELECT COUNT(?1) FROM ReReplyLike r WHERE r.rereply.id = ?1")
    int count(Long id);
}
