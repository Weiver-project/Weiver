package weiver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import weiver.dto.User;
import weiver.repository.UserRepository;

@Service
public class UserService {
	
//	복잡한 쿼리 짤때
	@Autowired
	private MongoTemplate mongoTemplate;
	
//	단순한 crd 사용할 때
	@Autowired
	private UserRepository userRepository;
	
//	전체 검색
	public void findAll() {
//		List<User> result = mongoTemplate.findAll(User.class);
		List<User> result = userRepository.findAll();
		System.out.println(result);
	}
	
//	관리자 아닌 일반 유저 검색
	public void findByAdmin(boolean admin) {
		List<User> result = userRepository.findByAdmin(admin);
		System.out.println(result);
	}
	
//	유저 비밀번호 수정
	public void updatePassword(String id, String password) {
		
		Optional<User> user = userRepository.findById(id);
				
//		if(!result) {
//			System.out.println("업뎃");
//			Query query = new Query();
//			
//			Criteria criteria = Criteria.where("_id").is(id);
//	
//			query.addCriteria(criteria);
//			Update update = Update.update("password", password);
//			
//			mongoTemplate.updateFirst(query, update, User.class);
//		}
	}
		
//	유저 프로필 이미지 변경
	public void updateImg(String id, String img) {
		
		Query query = new Query();
		
		Criteria criteria = Criteria.where("_id").is(id);
		query.addCriteria(criteria);
		
		Update update = Update.update("profileImg", img);
		
		mongoTemplate.updateFirst(query, update, User.class);
		
	}
	
//	유저 프로필 이름 변경
	public void updateName(String id, String name) {
		
		Query query = new Query();
		
		Criteria criteria = Criteria.where("_id").is(id);
		query.addCriteria(criteria);
		
		Update update = Update.update("nickname", name);
		
		mongoTemplate.updateFirst(query, update, User.class);
		
	}
	
}
