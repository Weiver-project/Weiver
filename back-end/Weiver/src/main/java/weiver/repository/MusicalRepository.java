package weiver.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import weiver.dto.PerformingMusical;
import weiver.dto.PoPularMusicalDTO;
import weiver.dto.SimpleMusicalDTO;
import weiver.entity.Musical;

@Repository
public interface MusicalRepository extends MongoRepository<Musical, String>{
	//공연 중인 뮤지컬 조회
	@Query(value = "{$and: [{'stDate': {$lte: ?0}}, {'edDate': {$gte: ?0}}]}", fields = "{'title': 1, 'imagePoster': 1}")
    List<PerformingMusical> findMusicalsForToday(Date today);
    
	/*
	 * 키워드로 뮤지컬 조회
	 * regex로 title의 값에 keyword가 포함하는지 확인하는데, 
	 * 이때 옵션으로 대소문자를 구분하지 않도록 한다.
	 */
    @Query(value = "{'title': {$regex: ?0, $options: 'i'}}", fields = "{'id': 1, 'title' : 1, 'posterImage': 1, 'stDate' : 1, 'edDate': 1}")
    List<SimpleMusicalDTO> findMusicalsByTitleKeyword(String keyword);
    
    //뮤지컬 아이디 목록에 해당하는 뮤지컬 전부 조회
    @Query("{'_id': {$in: ?0}}")
    List<Musical> findMusicalsByIds(List<String> musicalIds);
    
    //뮤지컬 아이디 목록에 해당하는 뮤지컬 제목 전부 조회
    @Query(value = "{'_id': {$in: ?0}}", fields ="{'id':1,'title':1}")
    List<PoPularMusicalDTO> findMusicalTitleByIds(List<String> musicalIds);
    
	//뮤지컬 상세정보 조회

	
	//배우의 출연작에 해당하는 뮤지컬 정보 조회
    @Query(value = "{'title': ?0, 'stDate': ?1, 'edDate': ?2}", fields = "{'id': 1, 'posterImage' : 1}")
    List<PerformingMusical> findMusicalsByTitleAndDates(String title, Date stDate, Date edDate);

}