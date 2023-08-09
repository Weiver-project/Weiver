package weiver.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weiver.domain.entity.User;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	// 동일 아이디 찾기
	boolean existsById(String userId);

	// 동일 닉네임 찾기
	boolean existsByNickname(String nickname);

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
