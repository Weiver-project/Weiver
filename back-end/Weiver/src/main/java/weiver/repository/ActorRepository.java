package weiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import weiver.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, String>{
	Actor getById(String id);
}
