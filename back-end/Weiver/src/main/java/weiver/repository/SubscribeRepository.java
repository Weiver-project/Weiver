package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import weiver.entity.Subscribe;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long>{
	/*
	 * 인기 뮤지컬 조회
	 * 찜, 봤어요 중 원하는 타입이 가장 많은 뮤지컬 3개 가져오기
	 */
//	@Query("SELECT s.musicalId.id, s.musicalId.title FROM Subscribe s WHERE s.type = ?1 GROUP BY s.musicalId.id ORDER BY COUNT(s) DESC")
//	List<Object[]> findTop3MusicalByDesiredType(String type);

	@Query("SELECT s FROM Subscribe s")
	List<Subscribe> findTop3MusicalByDesiredType();


	//	유저의 봤어요, 찜 목록 조회
	@Query("SELECT s.musicalId, s.musicalId.title, s.musicalId.posterImage, s.musicalId.stDate, s.musicalId.edDate FROM Subscribe s WHERE s.userId = ?1 AND s.type = ?2")
    List<Object[]> findMusicalIdByUserIdAndType(String userId, String type);

	//유저의 봤어요, 찜 목록 카운팅
	int countByUserIdAndType(String userId, String type);
}
