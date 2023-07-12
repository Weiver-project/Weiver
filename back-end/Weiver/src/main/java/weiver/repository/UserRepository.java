package weiver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import weiver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
		
}
