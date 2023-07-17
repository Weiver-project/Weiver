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

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	// Id로 유저 검색
	Optional<User> findById(String id);

	// 동일 아이디 찾기
	boolean existsById(String userId);

	
	// 동일 닉네임 찾기
	boolean existsByNickname(String nickname);
	

	// 인증된 유저 검색
	@EntityGraph(attributePaths = "authorities")	// User Entity 조회 시 authorities 필드를 함께 조회함, @EntityGraph = Eager 조회
	Optional<User> findOneWithAuthoritiesByid(String id);

	//id에 따라 하나의 유저 정보만 가져옴. >> communityService에 사용
	User getUserById(String id);
    
	// 유저 정보 수정(사진)
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.profileImg = :img where u.id = :id")
	int updateImgById(@Param("img") String profileImg, @Param("id") String id);

	// 유저 정보 수정(이름)
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.nickname = :nickname where u.id = :id")
	int updateNameById(@Param("nickname") String nickname, @Param("id") String id);


	// 유저 정보 수정(비밀번호)
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.password = :password where u.id = :id")
	int updatePasswordById(@Param("password") String password, @Param("id") String id);
}
