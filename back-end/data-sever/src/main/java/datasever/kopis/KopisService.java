package datasever.kopis;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import datasever.repository.ActorRepository;
import datasever.repository.MusicalRepository;
import entity.Musical;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class KopisService {
	private String KOPIS_URL = "http://kopis.or.kr/openApi/restful/pblprfr";
	private String KOPIS_API_KEY = "7781e2eaaf074e35b03711cf70cf57d3";
	
	private final MusicalRepository musicalRepository;
	private final ActorRepository actorRepository;
	private WebClient webclient = WebClient.create();
	private List<String> musicalIdList = new ArrayList<>();
	
	private String stdate = "";
	private String eddate = "";

	
	/**뮤지컬 아이디 조회*/
	public void getMusicalIdList() {
		//오늘 날짜와 6개월 후 날짜를 "yyyyMMdd" 포맷으로 저장
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		this.stdate= now.format(formatter);
		now = now.plusMonths(6);
		this.eddate = now.format(formatter);
		
		String MUSICAL_LIST_URL = KOPIS_URL + "?service="+ KOPIS_API_KEY + "&stdate=" + stdate + "&eddate=" + eddate
				+ "&cpage=1&rows=999&shcate=GGGA";
		
		//전체 뮤지컬 리스트를 가져옴
		String musicalList = webclient.get()
								.uri(MUSICAL_LIST_URL)
								.retrieve()
								.bodyToMono(String.class)
								.block();
		
		//뮤지컬 리스트에서 공연ID 리스트를 추출
		Document doc = Jsoup.parse(musicalList);
        Elements mt20idList = doc.select("mt20id");
        
        //DB에 있는 뮤지컬 아이디 조회
        List<String> musicalIds = musicalRepository.getAllMusicalIds();
        
        for (Element element : mt20idList) {
            String id = element.text();
            
            //DB에서 공연 아이디 중 같은 ID가 있는지 확인 후 있다면 추가하지 않음    
            if(!musicalIds.contains(id)) {
            	musicalIdList.add(id);    
            }
        }
	}
	
	/**새로운 뮤지컬 정보 DB에 저장*/
	public void saveMusical() {
		List<String> actorIds = new ArrayList<>();
		
		//가져온 모든 뮤지컬 목록에 대해 실행
		for(String id : musicalIdList) {
			String MUSICAL_DETAIL_URL = KOPIS_URL + "/"+ id +"?service="+ KOPIS_API_KEY;
			//뮤지컬 상세 정보를 가져옴
			String musicalInfo = webclient.get()
									.uri(MUSICAL_DETAIL_URL)
									.retrieve()
									.bodyToMono(String.class)
									.block();
		
			//뮤지컬 정보 html에서 필요한 정보 추출
			Document doc = Jsoup.parse(musicalInfo);
			Element dbElement = doc.selectFirst("db");

			String _id = dbElement.selectFirst("mt20id").text();
			String posterImage = dbElement.selectFirst("poster").text();
			String theater = dbElement.selectFirst("fcltynm").text();
			String title = dbElement.selectFirst("prfnm").text();
			String stdate = dbElement.selectFirst("prfpdfrom").text();
			String eddate = dbElement.selectFirst("prfpdto").text();
			String runningTime = dbElement.selectFirst("prfruntime").text();
			String viewingAge = dbElement.selectFirst("prfage").text();
			String ticketPrice = dbElement.selectFirst("pcseguidance").text();
			String actors = dbElement.selectFirst("prfcast").text();
			//String -> Date 타입 변경
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
				Date stDate = dateFormat.parse(stdate);
				Date edDate = dateFormat.parse(eddate);
				//배우 정보 ,로 파싱
				String[] actorArray = actors.split(",");
				
				//배우 아이디 가져오기
				for(String actor : actorArray ) {
					List<String> ids = actorRepository.findActorIdsByNameAndCasting(actor, title, stDate, edDate);	
					//배우 아이디 추가
					for(String i : ids) {
						actorIds.add(i);
					}
				}
			
			//뮤지컬 정보 DB에 저장
			Musical musical = Musical.builder()
								._id(_id)
								.posterImage(posterImage)
								.title(title)
								.theater(theater)
								.stDate(stDate)
								.edDate(edDate)
								.runngingTime(runningTime)
								.viewingAge(viewingAge)
								.ticketPrice(ticketPrice)
								.actorIds(actorIds)
								.build();
			
			System.out.println(musical);
			
			musicalRepository.save(musical);
			
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
	}
}
