package weiver.repository;

import javax.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import weiver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findPasswordById(String id);
	
	// Id로 유저 검색
	Optional<User> findById(String id);
	
	// 동일 닉네임 찾기
	boolean existsByNickname(String nickname);
	
	// 인증된 유저 검색
	@EntityGraph(attributePaths = "authorities")	// User Entity 조회 시 authorities 필드를 함께 조회함, @EntityGraph = Eager 조회
	Optional<User> findOneWithAuthoritiesByid(String id);

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
