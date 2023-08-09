package weiver.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weiver.web.dto.PerformingMusical;
import weiver.web.dto.SimpleMusicalDTO;
import weiver.domain.entity.Musical;

import java.util.Date;
import java.util.List;

@Repository
public interface MusicalRepository extends JpaRepository<Musical, String> {
	//공연 중인 뮤지컬 조회
    @Query("SELECT m.id as id, m.posterImage as posterImage FROM Musical m WHERE m.stDate <= ?1 AND m.edDate >= ?1")
    List<PerformingMusical> findPerformingMusicals(Date today);

	/*
	 * 키워드로 뮤지컬 조회
	 * regex로 title의 값에 keyword가 포함하는지 확인하는데, 
	 * 이때 옵션으로 대소문자를 구분하지 않도록 한다.
	 */
    @Query("SELECT m.id as id, m.title as title, m.posterImage as posterImage, m.stDate as stDate, m.edDate as edDate FROM Musical m WHERE UPPER(m.title) LIKE CONCAT('%', UPPER(?1), '%') ORDER BY m.edDate DESC")
    List<SimpleMusicalDTO> findMusicalsByTitleKeyword(String keyword);
    
    // 배우 상세 페이지 해당 뮤지컬 포스터 조회
    @Query("SELECT m.id as id, m.posterImage as posterImage FROM Musical m JOIN Casting c ON c.musicalId.id = m.id WHERE c.actorId.id = :actorId ORDER BY m.edDate DESC")
	List<PerformingMusical> findMusicalsByActorId(@Param("actorId") String actorId);

	Musical getMusicalById(String id);
}