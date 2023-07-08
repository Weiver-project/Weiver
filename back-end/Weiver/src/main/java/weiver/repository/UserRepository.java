package weiver.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import weiver.dto.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
//	일반 유저만 검색
	public List<User> findByAdmin(boolean admin);
	
//	유저 비밀번호만 검색
	public boolean existsByIdAndPassword(String id, String password);
	
}
