package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import weiver.dto.PoPularMusicalDTO;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Subscribe;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long>{
	/*
	 * 인기 뮤지컬 조회
	 * 찜, 봤어요 중 원하는 타입이 가장 많은 뮤지컬 3개 가져오기
	 */
	@Query("SELECT s.musicalId.id as id, s.musicalId.title as title FROM Subscribe s WHERE s.type = '봤어요' GROUP BY s.musicalId.id, s.musicalId.title ORDER BY COUNT(s.musicalId) DESC")
	List<PoPularMusicalDTO> findTop3MusicalByDesiredType(org.springframework.data.domain.Pageable pageable);

	//유저의 봤어요, 찜 목록 조회
	@Query("SELECT s.musicalId.id as id, s.musicalId.title as title, s.musicalId.posterImage as posterImage, s.musicalId.stDate as stDate, s.musicalId.edDate as edDate FROM Subscribe s WHERE s.userId = ?1 AND s.type = ?2")
    List<SimpleMusicalDTO> findMusicalIdByUserIdAndType(String userId, String type);

	//유저의 봤어요, 찜 목록 카운팅
	@Query("SELECT COUNT(?1) FROM Subscribe s WHERE s.userId.id = ?1 AND s.type = ?2")
	int countByUserIdAndType(String userId, String type);
	
	// UserId와 MusicalId, type으로 찜을 했는지 확인
	@Query("SELECT id  FROM Subscribe WHERE userId.id = ?1 AND musicalId.id = ?2 AND type =?3")
	List<String> findByUserIdAndMusicalIdAndType(String userId, String musicalId, String type);
	
	// UserId와 MusicalId로 subscribe 내역 삭제
	@Modifying
	@Transactional
	@Query("DELETE Subscribe WHERE userId.id = ?1 AND musicalId.id = ?2 AND type =?3")
	void deleteByUserIdAndMusicalIdAndType(String userId, String musicalId, String type);
}
