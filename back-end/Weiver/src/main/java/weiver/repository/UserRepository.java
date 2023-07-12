package weiver.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import weiver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	boolean existsById(String userId);
	
	Optional<User> findById(String userId);
    
	public User findByIdAndPassword(String userId, String password);
	
	
}