package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import weiver.entity.Actor;

public interface ActorRepository extends JpaRepository<Actor, String>{
	Actor getActorById(String id);
}
