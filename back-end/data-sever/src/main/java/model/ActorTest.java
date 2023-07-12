package model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.tomcat.jni.Time;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClientFactory;

import entity.Actor;

@Service
public class ActorTest {
	
	public static void main(String[] args) {
		
		Document doc = null;
		
		try {
			
			String mainURL = "http://www.playdb.co.kr";
			String mainURL2 = "http://www.playdb.co.kr/artistdb/";
			// 뮤지컬 배우 첫 페이지
			// beta
//			String URL = "http://www.playdb.co.kr/artistdb/list.asp?code=013003";

			int pageNo = 1; // 초기 페이지 넘버
			String ActorListURL = "https://www.playdb.co.kr/artistdb/list_iframe.asp?Page=" + pageNo + "&code=013003&sub_code=&ImportantSelect=&ClickCnt=Y&NameSort=&Country=Y&TKPower=&WeekClickCnt=&NameStart=&NameEnd=";
						
			doc = Jsoup.connect(ActorListURL).get();			
			
			// 최대 페이지 알아내기
			Elements maxPages = doc.select("body > table > tbody > tr > td");
			int maxPage = Integer.parseInt(maxPages.get(maxPages.size()-1).text().split("/")[1].split("]")[0]);
			System.out.println("최대 페이지 수 : " + maxPage);
			
			// 페이지 반복
			for(pageNo = 2; pageNo <= 2; pageNo++) {
				
				// 페이지별로 배우 리스트 접근
				ActorListURL = "https://www.playdb.co.kr/artistdb/list_iframe.asp?Page=" + pageNo + "&code=013003&sub_code=&ImportantSelect=&ClickCnt=Y&NameSort=&Country=Y&TKPower=&WeekClickCnt=&NameStart=&NameEnd=";
				doc = Jsoup.connect(ActorListURL).get();
				
				// 각각 배우 상세로 들어가는 방법
				// beta
	//			Elements imgSrcs = doc.select("#first_list > table > tbody > tr > td > table > tbody > tr > td > table"
	//						+ "> tbody > tr > td > a");
				Elements imgSrcs = doc.select("body > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a");
				
				// 모든 배우에 대한 반복
				for(Element imgSrc : imgSrcs) {
					if((imgSrc.text()).equals("")) {
						String actorDetailURL = mainURL2 + imgSrc.attr("href");
	//					System.out.println("배우 상세 페이지 링크 : " + actorDetailURL);
						
						// 새로 연결
						doc = Jsoup.connect(actorDetailURL).get();
						
						// 배우 이미지 주소
						String actorImage = doc.select("#manImage").attr("src");
//						System.out.println("배우 이미지 링크 : " + actorImage);
						
						// 배우 이름
						String actorName = doc.select(".title").get(0).text();
//						System.out.println("배우 이름 : " + actorName.split(" ")[0]);
						
						// 출연 작품 : 이미지 .img_size4
						// iframe 접근을 위해 actorNo값을 알아낸다.
						String contentImageURL = "#detailcontents > iframe#iFrmContent";					
						String contentactorNo = (doc.select(contentImageURL).attr("src")).split("=")[2];
						
						// iframeURL
	//					http://www.playdb.co.kr/artistdb/Detail_Content_new.asp?TabKind=3&ManNo=45997
						String contentIframeURL = mainURL2 + "Detail_Content_new.asp?TabKind=3&ManNo=" + contentactorNo;
						// contentIframeURL로 연결
						doc = Jsoup.connect(contentIframeURL).get();
//						System.out.println("출연작품 iframe 링크 : " + contentIframeURL);
						
						// 전체 Casting 객체 생성
						ArrayList<Actor.Casting> actorAllCastings = new ArrayList<Actor.Casting>();
						
						// 출연 작품 이미지 정보
						String tableImageURL = ".detail_contentsbox > table > tbody > tr > td > table > tbody > tr > td > a > img.img_size4";
						Elements images = doc.select(tableImageURL);
						for(Element image : images) {
							actorAllCastings.add(Actor.Casting.builder()
													.posterImage(image.attr("src"))
													.build());
						}
						
						// 출연 작품 정보 (이미지 제외)
						String tableRoleURL = ".detail_contentsbox > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td";
						Elements roles = doc.select(tableRoleURL);
						
						for(int i = 0; i < roles.size(); i++) {
								
							switch(i % 5) {
								// 출연 작품 Title
								case(0):
									String title = roles.get(i).text();
//									System.out.println("제목 : " + title);
									actorAllCastings.get(i/5).setTitle(title);
									break;
								// 출연 작품 Date
								case(1):
									String date = roles.get(i).text();
//									System.out.println("기간 : " + date);
								
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
									Date stDate = null, edDate = null;
									try {
										// 예외 처리
										if(roles.get(i).text().trim().split("~")[1].equals(" 오픈런")) {
											stDate = formatter.parse(roles.get(i).text().trim().split(" ~ ")[0]);
											edDate = new Date(); // 현재 시각으로 대체											
										}else {
											stDate = formatter.parse(roles.get(i).text().trim().split("~")[0]);
											edDate = formatter.parse(roles.get(i).text().trim().split("~")[1]);
											
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}
									
//									String stDate = roles.get(i).text().trim().split("~")[0];
									actorAllCastings.get(i/5).setStDate(stDate);
//									String edDate = roles.get(i).text().trim().split("~")[1];
									actorAllCastings.get(i/5).setEdDate(edDate);
									break;
								// 출연 작품 장소 (사용 안함)
								case(2):
									String place = roles.get(i).text();
//									System.out.println("장소 : " + place);
									break;
								// 출연 작품 Role
								case(3):
									String role = roles.get(i).text();
//									System.out.println("역할 : " + role);
									actorAllCastings.get(i/5).setRole(role);
									break;
								// 여백 처리
								case(4):
									String blank = roles.get(i).text();
//									System.out.println("여백 : " + blank);
									break;								
							} // switch
							
						} // for
						
						// 가져올 정보
//						배우 ID : contentactorNo
//						배우 이미지 주소 : actorImage
//						배우 이름 : actorName.split(" ")[0]
//						출연 작품 정보 : actorCastings
						System.out.println("배우 ID : " + contentactorNo);
						System.out.println("배우 이미지 주소 : " + actorImage);
						System.out.println("배우 이름 : " + actorName.split(" ")[0]);
						
						
						// Casting에서 뮤지컬만 골라내기
						// "역" Casting 객체 생성
						ArrayList<Actor.Casting> actorCastings = new ArrayList<Actor.Casting>();
						for(Actor.Casting c : actorAllCastings) {
							if(c.getRole().endsWith("역")) {
								actorCastings.add(c);
							}
						}
						
						
						// Actor 객체 생성
						Actor actor = Actor.builder()
											._id(contentactorNo)
											.name(actorName.split(" ")[0])
											.profileImage(actorImage)
											.castings(actorCastings.toArray(Actor.Casting[]::new))
											.build();
						
						System.out.println(actor);
						for(Actor.Casting c : actor.getCastings()) {
							System.out.println(c);
						}
						
						System.out.println("====================  정보 입력 완료  ========================");
//						for(Casting c : actorCasting) {
//							System.out.println(c);
//							
//							//날짜 비교 테스트 : 특정 날짜에 "공연 중"인 뮤지컬을 찾을 수 있는가?
//							SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
//							Date testDate =null;
//							try {
//								testDate = formatter.parse("2023.06.01");
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							
//							System.out.println("기준 날짜 : " + testDate);
//							if(testDate.after(c.getStDate()) && testDate.before(c.getEdDate())) {
//								
//								System.out.println(c.getStDate() + "" + c.getEdDate() + "사이에 있다");
//							}
//						}
						
						System.out.println("cycle");
						System.out.println(actorName.split(" ")[0] + " 정보 조회 종료");
					}
				}
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
