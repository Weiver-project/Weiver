package weiver.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import weiver.entity.Subscribe;

@Repository
public interface SubscribeRepository extends MongoRepository<Subscribe, String>{
	/*
	 * 인기 뮤지컬 조회
	 * 찜, 봤어요 중 원하는 타입이 가장 많은 뮤지컬 3개 가져오기
	 */
	List<Subscribe> findTop3ByTypeOrderByMusicalIdDesc(String type);
	
	
	//유저의 봤어요 목록 조회
	@Query(value = "{'type': '봤어요', 'userId': ?0}", fields = "{'musicalId': 1}")
    List<String> findMusicalIdsByViewedAndUserId(String userId);
	 
	//유저의 찜 목록 조회
	@Query(value = "{'type': '찜', 'userId': ?0}", fields = "{'musicalId': 1}")
	List<String> findMusicalIdsByJJimAndUserId(String userId);
}
