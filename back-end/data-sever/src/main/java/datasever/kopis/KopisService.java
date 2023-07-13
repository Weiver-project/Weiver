package datasever.kopis;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
import dto.MusicalIdDto;
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

	
	/*뮤지컬 아이디 중복 검사*/
	public boolean isexist(List<MusicalIdDto> musicalIds, String id) {
		for(MusicalIdDto i : musicalIds) {
    		//DB에서 공연 아이디 중 같은 ID가 있는지 확인 후 있다면 추가하지 않음    
    		if(i.getId().equals(id)){
    			return true;
    		}
    	}
		return false;
	}
	
	
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
        List<MusicalIdDto> musicalIds = musicalRepository.getAllMusicalIds();
        
        
        //DB에 없는 뮤지컬 아이디 추가
        for (Element element : mt20idList) {
        	String id = element.text();
        	if(!isexist(musicalIds, id))
        		musicalIdList.add(id); 
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
			
			try {
				//String -> Date 타입 변경 및 7일 범위 계산을 위한 변수 생성
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
				
				Date stDate = dateFormat.parse(stdate);
				calendar.setTime(stDate);
				calendar.add(Calendar.DAY_OF_MONTH, -7);
				Date preStDate = calendar.getTime();
				
				Date edDate = dateFormat.parse(eddate);
				calendar.setTime(edDate);
				calendar.add(Calendar.DAY_OF_MONTH, 7);
				Date postEdDate = calendar.getTime();
				
				//배우 정보 검색을 위한 제목, 극장 tmp 데이터 생성
				String tmpTitle = title.split(" ")[0];
				String tmpTheater;
				if(theater.contains("(")) {
					tmpTheater = theater.split("\\(")[0];

				} else {
					tmpTheater = theater.split(" ")[0];
				}
				
				
				//배우 정보 ,로 파싱
				String[] actorArray = actors.split(",");
				
//				System.out.println("배우 목록");
				for(int i = 0; i < actorArray.length; i++) {
					 actorArray[i] = actorArray[i].trim();
//					System.out.println(actor);
				}
				//배우 아이디 가져오기
				for(String actor : actorArray ) {
//					System.out.println("배우:" + actor + "\n제목:" + tmpTitle + "\n날짜: " + preStDate + " "+ stDate + "\n날짜2: "+ edDate + " " + postEdDate + "\n극장: "+ tmpTheater);
					if(!actor.isEmpty()) {
						List<String> ids = actorRepository.findActorsByConditions(actor, tmpTitle, preStDate, stDate, edDate, postEdDate, tmpTheater);	
						//배우 아이디 추가
						for(String i : ids) {
							actorIds.add(i);
						}
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
			
			if(!actorIds.isEmpty()) {				
				//System.out.println(musical);
				musicalRepository.save(musical);
			}
			else {
				//System.out.println("안들어간 데이터: " + title);
			}
			
			actorIds.clear();
			
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
	}
}
