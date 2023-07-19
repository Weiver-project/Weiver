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
	public List<String> search(String keyword) throws IOException {
		String API_KEY = "AIzaSyBWqVIZqxy91VIx5Hzc8RlBYxpbOqs9GYg";
		
		List<String> clips = new ArrayList<String>();
		
		String apiurl = "https://www.googleapis.com/youtube/v3/search";
		apiurl += "?key=" + API_KEY;
		apiurl += "&part=snippet&type=video&maxResults=4&videoEmbeddable=true";
		apiurl += "&q="+URLEncoder.encode(keyword,"UTF-8");
		
		URL url = new URL(apiurl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
		
		String inputLine;
		while((inputLine = br.readLine()) != null) {
			String videoId = inputLine;
			if(videoId.contains("videoId")) {
				clips.add(videoId.split(": \"")[1].split("\"")[0]);
			}
		}
		br.close();
		
		for(String s : clips) {
			System.out.println(s);
		}
		
		return clips;
	}
}