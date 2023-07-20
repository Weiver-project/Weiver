package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import weiver.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	Post getPostById(Long id);	
}
