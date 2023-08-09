package weiver.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import weiver.domain.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Review getReviewByPostId(Long postId);
  

}