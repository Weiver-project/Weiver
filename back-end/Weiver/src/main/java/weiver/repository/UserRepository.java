package weiver.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import weiver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findPasswordById(String id);
	
	// 유저 정보 수정(사진, 이름)
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.nickname = :nickname, u.profileImg = :img where u.id = :id")
	void updateInfoById(@Param("nickname") String nickname, @Param("img") String profileImg, @Param("id") String id);
	
	// 유저 정보 수정(비밀번호)
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.password = :password where u.id = :id")
	void updatePasswordById(@Param("password") String password, @Param("id") String id);
}
