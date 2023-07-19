package weiver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GoogleAPIService {
	
	// keyword에 맞는 GoogleAPI youtube clip videoId 값들을 반환해 준다.
	public static List<String> search(String keyword) throws IOException {
		String API_KEY = "AIzaSyBWqVIZqxy91VIx5Hzc8RlBYxpbOqs9GYg";
		
		List<String> clips = new ArrayList<String>();
		
		// 조건 추가
		String apiurl = "https://www.googleapis.com/youtube/v3/search";
		apiurl += "?key=" + API_KEY;
		apiurl += "&part=snippet&type=video&maxResults=4&videoEmbeddable=true";
		apiurl += "&q="+URLEncoder.encode(keyword,"UTF-8");
		
		// 가져오기
		URL url = new URL(apiurl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		// 값들을 buffer에 추가
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
		
		// 하나씩 가져온다.
		String inputLine;
		while((inputLine = br.readLine()) != null) {
			String videoId = inputLine;
			if(videoId.contains("videoId")) {
				clips.add(videoId.split(": \"")[1].split("\"")[0]);
			}
		}
		br.close();
		
		return clips;
	}
	
	// 위에서 받은 Clips를 하나하나 가져와 "영상ID" 값에 집어넣어주면 영상을 띄울 수 있다.
	
//	<iframe id="ytplayer" type="text/html" width="720" height="405"
//			src="https://www.youtube.com/embed/영상ID
//			frameborder="0" allowfullscreen>
	
}