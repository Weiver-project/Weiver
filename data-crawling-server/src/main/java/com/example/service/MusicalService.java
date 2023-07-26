package com.example.service;

import com.example.constant.Sub_category;
import com.example.constant.URLs;
import com.example.entity.Musical;
import com.example.repository.MusicalRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MusicalService {
    private final MusicalRepository musicalRepository;
    private final ActorService actorService;
    private boolean isFirst = true;     //처음 실행하는지 체크


    /**스레드에서 돌아가는 전체 과정을 실행하는 함수*/
    public void task(){
        //시작 시간
        long startTime = System.currentTimeMillis();

        //크롤링 할 뮤지컬 장르
        String[] genres = {Sub_category.LICENSE, Sub_category.ORIGINAL, Sub_category.CREATIVE, Sub_category.MUSICAL};
        String CrawlingType = "";

        //처음 한번만 모든 뮤지컬을 가져오고 이후부터는 공연예정인 뮤지컬만 가져온다.
        if(isFirst){
            CrawlingType = Sub_category.ALL;
            isFirst = false;
        }
        else
            CrawlingType = Sub_category.LATER;

        /*모든 뮤지컬 아이디를 가져온다.*/
        for(String genre: genres){
            switch (genre){
                case Sub_category.LICENSE:
                    log.info("뮤지컬 장르: 라이센스");
                    break;
                case Sub_category.ORIGINAL:
                    log.info("뮤지컬 장르: 오리지널");
                    break;
                case Sub_category.CREATIVE:
                    log.info("뮤지컬 장르: 창작");
                    break;
                case Sub_category.MUSICAL:
                    log.info("뮤지컬 장르: 뮤지컬");
                    break;
            }

            //max 페이지 가져오기
            int maxPage = setMaxPage(genre, CrawlingType);

            //뮤지컬 아이디 리스트 가져오기
            List<String> musicalIds = setMusicalIds(genre, CrawlingType, maxPage);

            log.info("가져온 MUSICAL ID 개수: " + musicalIds.size());
//            log.info("MUSICAL ID LIST: " + musicalIds);

            saveAllMusical(musicalIds);
        }

        /*크롤링하는데 소요된 시간 출력*/
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        int minute = (int) (elapsedTime/60000);
        int hour = minute / 60;
        minute = minute % 60;

        log.info("크롤링 경과 시간: " + hour + "시간" + minute + "분");
    }

    /*뮤지컬 상세 페이지에서 뮤지컬 정보 저장*/
    @SneakyThrows
    public void saveAllMusical(List<String> musicalIds){
      List<Musical> musicals = new ArrayList<>();
        
        for(int i =0; i < musicalIds.size(); i++){
            //뮤지컬 1개에 대한 데이터 크롤링
            Musical musical = getMusical(musicalIds.get(i));
            musicals.add(musical);
            log.info(i + "번 MUSICAL(" + musicalIds.get(i) + ") 저장: " + musical);

            // 데이터를 1000개씩 저장
            if(i != 0 && i % 1000 == 0){
            	// musical 저장
            	musicalRepository.saveAll(musicals);

            	// 새로운 MusicalId를 채우기 위해 기존 리스트 초기화
            	musicals.clear();
            }
        }

        // 잔여 데이터 저장
        if(musicals.size() != 0) {
            musicalRepository.saveAll(musicals);
            musicals.clear();
        }

        // actor, casting 저장
        actorService.saveData(musicalIds);
    }

    /**뮤지컬 상세 페이지에서 정보 크롤링 후 저장*/
    @SneakyThrows   //RuntimeException 같이 어떤 예외가 발생했는지 정확히 알 수 없는 경우에 사용
    public Musical getMusical(String musicalId){
        String url = URLs.MUSICAL_DETAIL_URL + musicalId;

        Document doc = Jsoup.connect(url).timeout(30000).get();

        /*뮤지컬 상세 정보 태그 가져오기*/
        Element element = doc.selectFirst(".pddetail");

        //포스터 이미지 경로
        String poster = element.selectFirst("h2 > img").attr("src");

        //공연 제목
        String title = "";
        Element titleE = element.selectFirst(".title");
        if(titleE != null)
            title = titleE.text();

        //공연 일시 = img태그의 alt값이 일시인 태그 상위 td태그 옆에 td태그를 지정
        String temporary = element.selectFirst("img[alt=일시]").parent().nextElementSibling().text();
        Date stDate = null;
        Date edDate = null;

        //공연 일시가 오픈런인 경우를 제외하고 전부 Date 타입으로 변환
        if(!temporary.equals("오픈런")){
            String[] dates = temporary.trim().split("~");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

            //일시 정보를 Date 타입으로 변환해서 stDate, edDate로 저장
            //지정된 포맷의 10글자가 맞을 경우에만 Date로 변환
            if(dates[0].length() == 11){
                stDate = dateFormat.parse(dates[0].trim());
            }
            if(dates[1].length() == 11){
                edDate = dateFormat.parse(dates[1].trim());
            }
        }
        //장소
        String theater = element.selectFirst("img[alt=장소]").parent().nextElementSibling().text();

        //관람 등급
        Element viewAgeE = element.selectFirst("img[alt=관람등급]");
        String viewAge = "";
        if(viewAgeE != null)
            viewAge = viewAgeE.parent().nextElementSibling().text();

        //관람 시간
        Element runningTimeE = element.selectFirst("img[alt=관람시간]");
        String runningTime = "";
        if(runningTimeE != null)
            runningTime = runningTimeE.parent().nextElementSibling().text();

        //주인공
        Element mainCharacterE = doc.selectFirst(".detail_contentsbox > table > tbody > tr b");
        String mainCharacter = "";
        if(mainCharacterE != null)
            mainCharacter = mainCharacterE.text();

        //크롤링한 데이터로 뮤지컬 객체 생성
        Musical musical = Musical.builder()
                .id(musicalId)
                .title(title)
                .theater(theater)
                .posterImage(poster)
                .stDate(stDate)
                .edDate(edDate)
                .viewAge(viewAge)
                .runningTime(runningTime)
                .mainCharacter(mainCharacter)
                .build();

        return musical;
    }


    /**PlayDB에서 뮤지컬Id 정보를 전부 가져와서 저장*/
    @SneakyThrows
    public List<String> setMusicalIds(String genre, String type, int maxPage){
        int currentPage;
        String url = URLs.MUSICAL_URL + type + URLs.SUB_CATEGORY + genre + URLs.PAGE;
        List<String> musicalIds = new ArrayList<>();

        /*마지막 페이지까지 반복*/
        for(currentPage = 1; currentPage <= maxPage; currentPage++){
            Document doc = Jsoup.connect(url + currentPage).timeout(30000).get();
            log.info("크롤링 중인 페이지: " + currentPage);

            /*뮤지컬 정보가 들어있는 tr 태그 가져오기*/
            Element element = doc.selectFirst("#contents .container1 > table > tbody > tr:last-child");

            /*뮤지컬 아이디가 포함된 태그 가져오기*/
            Elements musicalHtml = element.select("a[onclick^=goDetail]");
            List<String> ids = musicalHtml.eachAttr("onclick");

            /*가져온 뮤지컬 태그에서 뮤지컬 아이디만 추출해서 저장 */
            for(String i : ids){
                String id = i.substring(i.indexOf("'") + 1, i.lastIndexOf("'"));
                musicalIds.add(id);
            }
        }
        return musicalIds;
    }

    /**크롤링할 장르페이지의 최대 페이지 수를 저장*/
    public int setMaxPage(String subCategory, String type){
        String MUSICAL_URL = URLs.MUSICAL_URL + type + URLs.SUB_CATEGORY + subCategory + URLs.PAGE;

        try {
            /*카테고리에 해당하는 페이지 정보 가져오기*/
            Document doc = Jsoup.connect(MUSICAL_URL).timeout(30000).get();
            Elements elements = doc.select("#contents .container1 > table > tbody > tr:last-child");

            /*마지막 페이지 가져오기*/
            String[] pages = elements.get(elements.size()-1).text().split("/");
            int maxPage = Integer.parseInt(pages[1].replaceAll("\\D+", ""));

            return maxPage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
