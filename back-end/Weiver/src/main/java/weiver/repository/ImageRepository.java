package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import weiver.entity.Image;
import weiver.entity.Post;

public interface ImageRepository extends JpaRepository<Image, Long> {



}
