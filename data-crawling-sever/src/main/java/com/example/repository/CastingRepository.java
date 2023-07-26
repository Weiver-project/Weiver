package com.example.repository;

import com.example.entity.Actor;
import com.example.entity.Casting;
import com.example.entity.Musical;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastingRepository extends JpaRepository<Casting, String> {
	
	List<Casting> findByActorIdAndMusicalId(Actor actorId, Musical musicalId);
	
}
