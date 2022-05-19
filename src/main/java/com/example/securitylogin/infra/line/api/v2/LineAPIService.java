package com.example.securitylogin.infra.line.api.v2;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.securitylogin.infra.line.api.v2.response.AccessToken;
import com.example.securitylogin.infra.line.api.v2.response.IdToken;
import com.example.securitylogin.infra.utils.CommonUtils;
import com.google.gson.Gson;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

@Service
public class LineAPIService {
	@Value("${line.oauth2.channel-id}")
	private String channelId;
	
	@Value("${line.oauth2.channel-secret}")
	private String channelSecret;
	
	@Value("${line.oauth2.channel-token}")
	private String channelToken;
	
	@Value("${line.oauth2.redirect-uri}")
	private String redirectUri;
	
	@Autowired
	private Gson gson;
	
	
	public StringBuilder makeLineAuthorizationURL() {
		StringBuilder authorization_url = new StringBuilder("https://access.line.me/oauth2/v2.1/authorize");
		authorization_url.append("?response_type=" + "code");
		authorization_url.append("&grant_type=authorization_code");
		authorization_url.append("&state=" + CommonUtils.getToken());
		authorization_url.append("&scope=profile%20openid");
		authorization_url.append("&redirect_uri=" + redirectUri);
		authorization_url.append("&client_id=" + channelId);
		authorization_url.append("&client_secret=" + channelSecret);

		return authorization_url;
	}
	
	public AccessToken issueAccessToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>(); 
		ResponseEntity<String> response = null;
		
		RestTemplate restTemplate = new RestTemplate();
		
		String token_url = "https://api.line.me/oauth2/v2.1/token";
		
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		body.add("redirect_uri", redirectUri);
		body.add("client_id", channelId);
		body.add("client_secret", channelSecret);
		
		HttpEntity<Object> request = new HttpEntity<Object>(body, headers);
		
		response = restTemplate.exchange(token_url, HttpMethod.POST, request, String.class);
		AccessToken accessToken = gson.fromJson(response.getBody(), AccessToken.class);
		return accessToken;
	}
	
	public IdToken getProfileInformationFromIDtoken(AccessToken accessToken) {
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>(); 
		ResponseEntity<String> response = null;
		
		RestTemplate restTemplate = new RestTemplate();
	    
		String endpoint_url = "https://api.line.me/oauth2/v2.1/verify";
		
		body.add("id_token", accessToken.id_token);
		body.add("client_id", channelId);
		
		HttpEntity<Object> request = new HttpEntity<Object>(body, headers);
		response = restTemplate.exchange(endpoint_url, HttpMethod.POST, request, String.class);
	    
		IdToken idToken = gson.fromJson(response.getBody(), IdToken.class);
		
		return idToken;
	}
	
	public void pushMessage(String userId, String message) {
		final LineMessagingClient client = LineMessagingClient
		        .builder(channelToken)
		        .build();

		final TextMessage textMessage = new TextMessage(message);
		final PushMessage pushMessage = new PushMessage(
				userId,
		        textMessage);

		final BotApiResponse botApiResponse;
		try {
		    botApiResponse = client.pushMessage(pushMessage).get();
		} catch (InterruptedException | ExecutionException e) {
		    e.printStackTrace();
		    return;
		}
		System.out.println(botApiResponse);

	}
}