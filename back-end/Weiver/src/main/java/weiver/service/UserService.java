package weiver.service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	
//	update 구문
	@Autowired
	private MongoTemplate mongoTemplate;
	
//	단순한 crd 사용할 때 https://jaime-note.tistory.com/51
	@Autowired
	private UserRepository userRepository;
	
//	일반 유저 전체 검색
	public void findByAdmin(boolean admin) {
		List<User> result = userRepository.findByAdmin(admin);
		System.out.println(result);
	}
	
//	유저 id 검색
	public void findByIdAndAdmin(String id, boolean admin) {
		List<User> result = userRepository.findByIdAndAdmin(id, admin);
		System.out.println(result);
	}
	
//	유저 비밀번호 수정
	public void updatePassword(String id, String password) {

		// password	중복 확인	
		boolean result = userRepository.existsByIdAndPassword(id, password);
				
		if(!result) {
			Query query = new Query();
			
			Criteria criteria = Criteria.where("_id").is(id);
	
			query.addCriteria(criteria);
			Update update = Update.update("password", password);
			
			mongoTemplate.updateFirst(query, update, User.class);
		}
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
