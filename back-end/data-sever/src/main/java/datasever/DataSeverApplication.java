package datasever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import datasever.kopis.KopisService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class DataSeverApplication {
	private final KopisService kopisService;
	
	public static void main(String[] args) {
		SpringApplication.run(DataSeverApplication.class, args);
	}
	
	@Scheduled(fixedDelay = 100000000)
	public void executeTask() {
		kopisService.getMusicalIdList();
		kopisService.saveMusical();
	}
}
