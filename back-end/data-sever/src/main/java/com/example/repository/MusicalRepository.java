package com.example.repository;


import com.example.entity.Musical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicalRepository extends JpaRepository<Musical, String> {

}
