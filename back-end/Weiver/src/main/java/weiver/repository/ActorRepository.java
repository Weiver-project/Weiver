package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import weiver.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, String>{
	Actor getById(String id);
	
	// 랜덤 배우를 검색, FETCH FIRST 1 ROWS ONLY : 쿼리 결과에서 첫 번째 레코드만 가져옴
	@Query(value = "select * from(\r\n" + 
			"SELECT * FROM actor ORDER BY DBMS_RANDOM.VALUE\r\n" + 
			") where rownum < 2", nativeQuery = true)
	Actor getRandomActor() throws Exception;
}