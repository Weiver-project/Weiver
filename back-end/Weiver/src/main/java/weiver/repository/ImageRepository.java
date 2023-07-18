package weiver.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import weiver.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	@Modifying
    @Transactional
    @Query(value = "INSERT INTO image (post_id, path) " +
            "VALUES (:postId, :path)", nativeQuery = true)
    int insertImage(@Param("postId") Long postId,
                   @Param("path") String path);



}
