package datasever.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import entity.Musical;

@Repository
public interface MusicalRepository extends MongoRepository<Musical, String>{
	
    @Query(value = "{}", fields = "{ 'id' : 1 }")
    List<String> getAllMusicalIds();

}
