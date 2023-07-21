package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import weiver.entity.Actor;
import weiver.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String>{
	Admin getAdminById(String id);
}