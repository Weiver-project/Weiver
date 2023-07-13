package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weiver.entity.ReReply;

import java.util.List;

public interface ReReplyRepository extends JpaRepository<ReReply, Long> {
    //내가 쓴 대댓글 리스트 조회
    List<ReReply> findByUserId(String userId);

    //내가 쓴 대댓글 리스트 개수
    int countByUserId(String userId);
}
