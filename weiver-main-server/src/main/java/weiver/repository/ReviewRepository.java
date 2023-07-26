package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import weiver.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Review getReviewByPostId(Long postId);
  

}