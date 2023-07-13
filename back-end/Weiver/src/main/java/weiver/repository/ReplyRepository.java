package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weiver.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    //내가 쓴 댓글 리스트 조회
    List<Reply> findByUserId(String userId);

    //내가 쓴 댓글 리스트 개수
    int countByUserId(String userId);
}
