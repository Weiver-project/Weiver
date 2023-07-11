package datasever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import datasever.kopis.KopisService;
import datasever.kopis.PlayDBService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class DataSeverApplication {
	private final KopisService kopisService;
	private final PlayDBService playDBService;
	
	public static void main(String[] args) {
		SpringApplication.run(DataSeverApplication.class, args);
		
	}
	
	//1000*60*60*24*7 = 60,480,000 = 1주일
	@Scheduled(fixedDelay = 60480000)
	public void executeTask() {
		playDBService.saveActor();

		kopisService.getMusicalIdList();
		kopisService.saveMusical();
		
	}
}
