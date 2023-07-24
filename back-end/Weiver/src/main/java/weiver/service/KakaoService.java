package weiver.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Service
public class KakaoService {
	@Value(value = "${kakao.adminkey}")
	private String kakaoAdminkey; 
	
	public String getKakaoAccessToken(String code) throws Exception{
		String reqURL = "https://kauth.kakao.com/oauth/token";
		URL url = new URL(reqURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String token = "";
		try {
			// POST 방식, 서버에 데이터 출력 true
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			// POST 요청에 필요한 파라미터를 Stream을 통해 전송
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			
			stringBuilder.append("grant_type=authorization_code");
			stringBuilder.append("&client_id=aa451a1e9ceeda36154b17598b816da8");
			stringBuilder.append("&redirect_uri=http://3.36.252.181:8081/kakao");
			stringBuilder.append("&code=" + code);
			
			bufferedWriter.write(stringBuilder.toString());
			bufferedWriter.flush();	// 버퍼링된 데이터를 강제로 전송
			
			// 결과 코드가 200 이면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode1 : " + responseCode);
			
			// 요청을 통해 얻은 JSON타입의 Response 메시지 읽기
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			
			while((line = bufferedReader.readLine()) != null) {
				result += line;
			}
			
			// JSON.simple 사용
			JsonParser parser = new JsonParser();
            JsonObject elem = (JsonObject) parser.parse(result);
//			JsonElement element = JsonParser.parseString(result);
            
			String accessToken = elem.get("access_token").toString();
			String refreshToken = elem.get("refresh_token").toString();
			
			System.out.println("accessToken " + accessToken);
			System.out.println("refreshToken " + refreshToken);
			
			token = accessToken;
			
			bufferedReader.close();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	public Map<String, Object> getUserInfo(String token) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me?property_keys=%5B%22kakao_account.email%22%2C+%22properties.nickname%22%5D";
		
		// accessToken을 이용하여 사용자 정보 조회
		try {
			URL url = new URL(reqURL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Bearer " + token);
			urlConnection.setRequestMethod("GET");
			
			// 200 이면 성공
			int responseCode = urlConnection.getResponseCode();
			System.out.println("responseCode2 : " + responseCode);
	        
			// 요청을 통해 얻은 JSON타입의 Response 메시지 읽기
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = "";
			String res = "";
			
			while((line = bufferedReader.readLine()) != null) {
				res += line;
			}
			
			// GSON 라이브러리로 JSON 파싱
			JsonParser parser = new JsonParser();
            JsonObject obj = (JsonObject) parser.parse(res);
            JsonObject kakao_account = (JsonObject) obj.get("kakao_account");
            JsonObject properties = (JsonObject) obj.get("properties");
			
			String email = kakao_account.get("email").toString();
			String nickname = properties.get("nickname").toString();
			
			System.out.println("email : " + email);
			System.out.println("nickname : " + nickname);
			
			result.put("email", email);
			result.put("nickname", nickname);
			
			bufferedReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 로그 아웃
	public void kakaoLogout(String accessToken) {
		String reqURL = "http://kapi.kakao.com/v1/user/logout";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode = " + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String result = "";
			String line = "";
			
			while((line = br.readLine()) != null) {
				result+=line;
			}
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
