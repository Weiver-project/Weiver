package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import weiver.dto.PerformingMusical;
import weiver.entity.Casting;

import java.util.List;


@Repository
public interface CastingRepository extends JpaRepository<Casting, Long> {
    /*배우의 출연작에 해당하는 뮤지컬의 아이디와 포스터 이미지 조회*/
    @Query("SELECT c.musicalId, c.musicalId.posterImage FROM Casting c WHERE c.actorId.id = ?1")
    List<PerformingMusical> findMusicalIdAndPosterByActorId(Long actorId);
}
