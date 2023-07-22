package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weiver.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String>{
	// 동일 아이디 찾기
	boolean existsById(String userId);

	Admin getAdminById(String id);
}