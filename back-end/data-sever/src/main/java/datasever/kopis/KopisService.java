package datasever.kopis;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import datasever.repository.MusicalRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class KopisService {
	private String MUSICAL_LIST_URL = "http://kopis.or.kr/openApi/restful/pblprfr";
	private String MUSICAL_DETAIL_URL = "";
	private String KOPIS_API_KEY = "7781e2eaaf074e35b03711cf70cf57d3";
	
	private final MusicalRepository musicalRepository;
	private WebClient webclient = WebClient.create();
	private List<String> musicalIdList = new ArrayList<>();
	

	
	/**뮤지컬 아이디 조회*/
	public void getMusicalIdList() {
		//오늘 날짜와 6개월 후 날짜를 "yyyyMMdd" 포맷으로 저장
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String today = now.format(formatter);
		now = now.plusMonths(6);
		String eddate = now.format(formatter);
		
		MUSICAL_LIST_URL += "?service="+ KOPIS_API_KEY + "&stdate=" + today + "&eddate=" + eddate
				+ "&cpage=1&rows=999&shcate=GGGA";
		
		//전체 뮤지컬 리스트를 가져옴
		String musicalList = webclient.get()
								.uri(MUSICAL_LIST_URL)
								.retrieve()
								.bodyToMono(String.class)
								.block();
		
		//뮤지컬 리스트에서 공연ID 리스트를 추출
		 Document doc = Jsoup.parse(musicalList, "", org.jsoup.parser.Parser.xmlParser());
        Elements mt20idList = doc.select("mt20id");
        
        //DB에 있는 뮤지컬 아이디 조회
        List<String> musicalIds = musicalRepository.getAllMusicalIds();
        
        for (Element element : mt20idList) {
            String id = element.text();
            
            //DB에서 공연 아이디 중 같은 ID가 있는지 확인 후 있다면 추가하지 않음    
            if(!musicalIds.contains(id)) {
            	musicalIdList.add(id);    
            	System.out.println(id);
            }
        }
	}
}
