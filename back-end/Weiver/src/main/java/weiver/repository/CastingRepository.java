package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import weiver.dto.PerformingMusical;
import weiver.dto.ResponseCastingDTO;
import weiver.entity.Casting;

import java.util.List;


@Repository
public interface CastingRepository extends JpaRepository<Casting, Long> {
    /*배우의 출연작에 해당하는 뮤지컬의 아이디와 포스터 이미지 조회*/
    @Query("SELECT c.musicalId, c.musicalId.posterImage FROM Casting c WHERE c.actorId.id = ?1")
    List<PerformingMusical> findMusicalIdAndPosterByActorId(Long actorId);

    // 뮤지컬 상세 페이지에 사용될 캐스팅, 배우 정보 조회   
    @Query("SELECT new weiver.dto.ResponseCastingDTO(c.role, a.name, a.profileImage) FROM Casting c JOIN Actor a ON c.actorId.id = a.id WHERE c.musicalId.id = :id")
    List<ResponseCastingDTO> getCastingByMusicalId(@Param(value = "id") String id);
}
