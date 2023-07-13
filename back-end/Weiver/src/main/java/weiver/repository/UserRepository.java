package weiver.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import weiver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	// 동일 아이디 찾기
	boolean existsById(String userId);
	
	// 동일 닉네임 찾기
	boolean existsByNickname(String nickname);
	
	// 개발중
	Optional<User> findById(String userId);
    
	// 개발중
	public User findByIdAndPassword(String userId, String password);
	
	
}