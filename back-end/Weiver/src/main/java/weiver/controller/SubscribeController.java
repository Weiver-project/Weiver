package weiver.controller;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.service.SubscribeService;


@Controller
@RequiredArgsConstructor
public class SubscribeController {
	private final SubscribeService subscribeService;
    
   
    @GetMapping("/addSubscribe/{musicalId}/{type}")
    public String addSubscribe(@PathVariable String musicalId, @PathVariable String type,
    							Model model, HttpSession Session){
    	
    	String userId = Session.getAttribute("userId").toString();
    	
    	if(musicalId != null) {
    		subscribeService.insertSubscribe(userId, musicalId, type);
    	}
    	
    	return "musicalDetail";
    }
    
    
}
