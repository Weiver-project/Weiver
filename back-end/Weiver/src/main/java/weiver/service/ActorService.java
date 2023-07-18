package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weiver.entity.Actor;
import weiver.repository.ActorRepository;

@Service
public class ActorService {
	@Autowired
	private ActorRepository actorRepository;

	public Actor getActorInfo(String actorId)  throws Exception{
		Actor actor = actorRepository.getActorById(actorId);
		
		return actor;
	}
	
}
