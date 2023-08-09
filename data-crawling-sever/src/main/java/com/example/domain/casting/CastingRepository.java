package com.example.domain.casting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastingRepository extends JpaRepository<Casting, String> {
	
}
