package com.example.service;

import com.example.constant.Sub_category;
import com.example.constant.URLs;
import com.example.domain.actor.Actor;
import com.example.domain.actor.ActorRepository;
import com.example.domain.casting.Casting;
import com.example.domain.casting.CastingRepository;
import com.example.domain.musical.Musical;
import com.example.domain.musical.MusicalRepository;
import com.example.dto.ActorsByRoleDto;
import com.example.dto.MusicalActorDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CrawlingService {
    private final MusicalRepository musicalRepository;
    private final ActorRepository actorRepository;
    private final CastingRepository castingRepository;
    private boolean isFirst = true;


    /*실행 흐름
     * 1. 처음 실행이라면 모든 데이터를, 아니라면 공연 예정 데이터에 대해서 PlayDB 웹 사이트의 각 장르별 뮤지컬 목록을 가져온다
     * 2. 각 목차의 최대 페이지를 가져와서 처음부터 끝까지 MusicalId를 가져와서 저장한다.
     * 3. 뮤지컬 아이디에 해당하는 상세페이지에서 뮤지컬 정보와 배우정보를 가져온다.
     * 4. List에 뮤지컬과 배우 정보를 저장하고 500개 단위로 배우, 뮤지컬, 캐스팅 정보를 저장한다.
     *  */

    /**반복문으로 사이클을 돌릴 때 예외가 발생하면 다음으로 넘어가지 않고 했던 작업을 다시 진행*/
    public void task() {

        long startTime = System.currentTimeMillis();

        String[] genres = {Sub_category.LICENSE, Sub_category.ORIGINAL, Sub_category.CREATIVE, Sub_category.MUSICAL};
        String CrawlingType = "";


        if (isFirst) {
            CrawlingType = Sub_category.ALL;
            isFirst = false;
        } else {
            CrawlingType = Sub_category.LATER;
        }

        for (String genre : genres) {
            switch (genre) {
                case Sub_category.LICENSE:
                    log.info("현재 크롤링 중인 뮤지컬 장르: 라이센스");
                    break;
                case Sub_category.ORIGINAL:
                    log.info("현재 크롤링 중인 뮤지컬 장르: 오리지널");
                    break;
                case Sub_category.CREATIVE:
                    log.info("현재 크롤링 중인 뮤지컬 장르: 창작");
                    break;
                case Sub_category.MUSICAL:
                    log.info("현재 크롤링 중인 뮤지컬 장르: 뮤지컬");
                    break;
            }

            int maxPage = setMaxPage(genre, CrawlingType);

            List<String> musicalIds = setMusicalIds(genre, CrawlingType, maxPage);
            log.info("가져온 MUSICAL ID 개수: " + musicalIds.size());

            getMusicalActorDtoAndSaveAll(musicalIds);
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        int minute = (int) (elapsedTime / 60000);
        int hour = minute / 60;
        minute = minute % 60;

        log.info("크롤링 경과 시간: " + hour + "시간" + minute + "분");
    }


    /*Timeout Exception 발생 시 다시 이전 작업을 최대 3번까지 수행*/
    public void getMusicalActorDtoAndSaveAll(List<String> musicalIds) {
        List<MusicalActorDto> musicalActorDtoList = new ArrayList<>();
        int maxRetries = 3;
        int retries = 0;

        for (int i = 0; i < musicalIds.size(); i++) {
            try{
                MusicalActorDto musicalActorDto = getMusicalActorDto(musicalIds.get(i));
                musicalActorDtoList.add(musicalActorDto);

                if ((musicalActorDtoList.size() != 0 && musicalActorDtoList.size() % 1000 == 0) || i == musicalIds.size() - 1) {
                    saveAll(musicalActorDtoList);
                    log.info("데이저 저장(" + (i + 1) + "/" + musicalIds.size() + ")");
                    musicalActorDtoList.clear();
                    retries = 0;
                }
            }
            catch (Exception e){
                log.error("예외 발생:" + e.getMessage());

                if(retries >= maxRetries){
                    break;
                }
                else {
                    i--;
                    retries++;
                }
            }
        }
    }

    @Transactional
    public void saveAll(List<MusicalActorDto> musicalActorDtoList) {
        for (MusicalActorDto musicalActorDto : musicalActorDtoList) {
            Musical musical = musicalActorDto.getMusical();
            musicalRepository.save(musical);

            saveActorsAndCastingForMusical(musicalActorDto, musical);
        }
    }

    private void saveActorsAndCastingForMusical(MusicalActorDto musicalActorDto, Musical musical) {
        for (ActorsByRoleDto actorsByRoleDto : musicalActorDto.getActorsByRoleDtoList()) {
            String role = actorsByRoleDto.getRole();
            List<Actor> actors = actorsByRoleDto.getActor();

            actorRepository.saveAll(actors);

            for (Actor actor : actors) {
                Casting casting = Casting.builder()
                        .actorId(actor)
                        .role(role)
                        .musicalId(musical)
                        .build();
                castingRepository.save(casting);
            }
        }
    }

    @SneakyThrows
    public MusicalActorDto getMusicalActorDto(String musicalId) {
        String url = URLs.MUSICAL_DETAIL_URL + musicalId;
        Document doc = Jsoup.connect(url).timeout(60000).get();

        Musical musical = getMusical(doc, musicalId);
        List<ActorsByRoleDto> ActorsByRoleDtoList = getActors(doc);

        return MusicalActorDto.builder()
                .musical(musical)
                .actorsByRoleDtoList(ActorsByRoleDtoList)
                .build();
    }

    @SneakyThrows
    public Musical getMusical(Document doc, String musicalId) {
        Element element = doc.selectFirst(".pddetail");

        String poster = element.selectFirst("h2 > img").attr("src");

        String title = "";
        Element titleElement = element.selectFirst(".title");
        if (titleElement != null)
            title = titleElement.text();


        String temporary = element.selectFirst("img[alt=일시]").parent().nextElementSibling().text();
        Date stDate = null;
        Date edDate = null;

        if (temporary.length() == 23) {
            String[] dates = temporary.trim().split(" ~ ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

            if (dates[0].length() == 10) {
                stDate = dateFormat.parse(dates[0]);
            }
            if (dates[1].length() == 10) {
                edDate = dateFormat.parse(dates[1]);
            }
        }

        String theater = element.selectFirst("img[alt=장소]").parent().nextElementSibling().text();

        Element viewAgeElement = element.selectFirst("img[alt=관람등급]");
        String viewAge = "";
        if (viewAgeElement != null)
            viewAge = viewAgeElement.parent().nextElementSibling().text();

        Element runningTimeElement = element.selectFirst("img[alt=관람시간]");
        String runningTime = "";
        if (runningTimeElement != null)
            runningTime = runningTimeElement.parent().nextElementSibling().text();

        Element mainCharacterElement = doc.selectFirst(".detail_contentsbox > table > tbody > tr b");
        String mainCharacter = "";
        if (mainCharacterElement != null)
            mainCharacter = mainCharacterElement.text();

        return Musical.builder()
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
    }

    public List<ActorsByRoleDto> getActors(Document doc) {
        Elements actorListByRole = doc.select(".detail_contentsbox > table > tbody > tr > td > table > tbody > tr");
        List<ActorsByRoleDto> actorsByRoleDtos = new ArrayList<>();

        for (Element e : actorListByRole) {
            ActorsByRoleDto actorsByRoleDto = new ActorsByRoleDto();

            String role = e.select("td[width=120]").text();
            Elements profileImages = e.select("td[width=80] > a > img");
            Elements IdAndName = e.select("td[width=52] > a");

            actorsByRoleDto.setRole(role);

            for (int i = 0; i < profileImages.size(); i++) {
                String src = profileImages.get(i).attr("src");
                String id = IdAndName.get(i).attr("href").split("=")[1];
                String name = IdAndName.get(i).text();

                actorsByRoleDto.getActor().add(new Actor(id, name, src));
            }

            actorsByRoleDtos.add(actorsByRoleDto);
        }

        return actorsByRoleDtos;
    }


    @SneakyThrows
    public List<String> setMusicalIds(String genre, String type, int maxPage) {
        String url = URLs.MUSICAL_URL + type + URLs.SUB_CATEGORY + genre + URLs.PAGE;
        List<String> musicalIds = new ArrayList<>();

        for (int currentPage=1; currentPage <= maxPage; currentPage++) {
            Document doc = Jsoup.connect(url + currentPage).timeout(60000).get();
            log.info("크롤링 중인 페이지: " + currentPage);

            Element musicalElement = doc.selectFirst("#contents .container1 > table > tbody > tr:last-child");

            List<String> idTags = musicalElement.select("a[onclick^=goDetail]").eachAttr("onclick");

            for (String idTag : idTags) {
                String id = idTag.substring(idTag.indexOf("'") + 1, idTag.lastIndexOf("'"));
                musicalIds.add(id);
            }
        }

        return musicalIds;
    }

    @SneakyThrows
    public int setMaxPage(String subCategory, String type) {
        String MUSICAL_URL = URLs.MUSICAL_URL + type + URLs.SUB_CATEGORY + subCategory + URLs.PAGE;

        Document doc = Jsoup.connect(MUSICAL_URL).timeout(60000).get();
        Elements pageElements = doc.select("#contents .container1 > table > tbody > tr:last-child");

        String[] pageNums = pageElements.get(pageElements.size() - 1).text().split("/");
        int maxPage = Integer.parseInt(pageNums[1].replaceAll("\\D+", ""));

        return maxPage;
    }
}
