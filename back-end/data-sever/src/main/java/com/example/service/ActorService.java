package com.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Actor;
import com.example.entity.Casting;
import com.example.entity.Musical;
import com.example.repository.ActorRepository;
import com.example.repository.CastingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActorService {
	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private CastingRepository castingRepository;
	
	// Musical에서 배우 정보를 가져와 하나씩 추가
	public void saveData(String musicalId) {	
		String musicalURL = "http://www.playdb.co.kr/playdb/playdbDetail.asp?sReqPlayno=" + musicalId;
		
		Document doc = null;
		List<String> actorIds = new ArrayList<String>();
		
		try {
			doc = Jsoup.connect(musicalURL).get();
			
			// 배우 역할 List를 가져온다.
			String actorRoleListTag = ".detail_contentsbox > table > tbody > tr > td > table > tbody > tr";
			Elements actorRoleList = doc.select(actorRoleListTag);
			
			// Role에 따라 배우들을 분리한다.
			for(Element e : actorRoleList) {
				// 어떤 Role에 대해서 처리하는가?
				String role = e.select("b").text();
				
				Elements musicalHtml = e.select("img");
				
				// 해당 Role에 맞는 배우 ID를 actorIds에 저장한다.
				for(int i = 0; i < musicalHtml.size(); i++) {
					String actorId = musicalHtml.get(i).attr("src").split("_")[1];
					actorIds.add(actorId);
				}
				
				// actorIds를 순환하여 ActorDB 저장, CastingDB 저장
				for(String actorId : actorIds) {
					saveActor(actorId);
					saveCasting(actorId, musicalId, role);
				}
				actorIds.clear();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} // catch
	}// saveActor()
	
	// actor : id + name + img
	private void saveActor(String actorId) {
		// 기존에 없는 Actor만 저장한다.
		if(actorRepository.findById(actorId) == null) {
			Document doc = null;
			String mainURL2 = "http://www.playdb.co.kr/artistdb/detail.asp?ManNo=";
			String actorDetailURL = mainURL2 + actorId;
			
			// 새로 연결
			try {
				doc = Jsoup.connect(actorDetailURL).get();
				
				// 배우 이미지 주소
				String actorImage = doc.select("#manImage").attr("src");
		
				// 배우 이름
				String actorName = doc.select(".title").get(0).text();
		
				// ===================== Actor 객체 생성 ==========================
				Actor actor = Actor.builder()
						.id(actorId)
						.name(actorName.split(" ")[0])
						.profileImage(actorImage)
						.build();
				System.out.println(actor);
				
				actorRepository.save(actor);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else { // 배우가 이미 존재하는 경우
			System.out.println("이미 존재하는 Actor입니다.");
		}
	}
	
	// casting : id(시퀀스) + actor + musical + role
	private void saveCasting(String actorId, String musicalId, String role) {
		Actor actor = Actor.builder()
							.id(actorId)
							.name("")
							.build();
		Musical musical = Musical.builder()
								.id(musicalId)
								.build();
		
		if(castingRepository.findByActorIdAndMusicalId(actor, musical).size() == 0){
			Casting casting = Casting.builder()
									.actorId(actor)
									.musicalId(musical)
									.role(role)
									.build();
	
			System.out.println(casting);
			castingRepository.save(casting);
		}else {
			System.out.println("이미 존재하는 Casting입니다.");
		}
	}
	
}
