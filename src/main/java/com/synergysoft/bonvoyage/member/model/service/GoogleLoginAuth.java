package com.synergysoft.bonvoyage.member.model.service;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Component("googleLoginAuth")
public class GoogleLoginAuth {
	private final static Logger logger = LoggerFactory.getLogger(GoogleLoginAuth.class);

	private final static String CLIENT_ID = "1026897669776-hk79tbpi0nihevh9li9vipn2ltgltaeb.apps.googleusercontent.com";
	private final static String CLIENT_SECRET = "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";
	private final static String REDIRECT_URI = "http://ktj0514.synology.me:8080/bonvoyage/googleLogin.do";
	private final static String GOOGLE_SCOPE = "email openid profile";
	private final static String PROFILE_API_URL = "https://www.googleapis.com/oauth2/v2/userinfo";
	private static final String SESSION_STATE = "googleSessionState";

	/* 세션 유효성 검증을 위한 난수 생성기 */
	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	/* http session에 데이터 저장 */
	private void setSession(HttpSession session, String state) {
		session.setAttribute(SESSION_STATE, state);
	}

	/* http session에서 데이터 가져오기 */
	private String getSession(HttpSession session) {
		return (String) session.getAttribute(SESSION_STATE);
	}

	// 구글 아이디로 인증 URL 생성 Method
	public String getAuthorizationUrl(HttpSession session) {
		// 세션 유효성 검증을 위해 난수 생성
		String state = generateRandomString();
		// 생성한 난수 값을 session에 저장
		setSession(session, state);

		/* Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성 */
		// 앞서 생성한 난수값을 인증 URL생성시 사용함
		OAuth20Service oauthService = new ServiceBuilder().apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
				.callback(REDIRECT_URI).state(state).scope(GOOGLE_SCOPE).build(GoogleLoginApi.instance());

		logger.info("googleURL : " + oauthService.toString());
		return oauthService.getAuthorizationUrl();
	}

	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
		// 세션의 유효성 검증을 위해 세션에 저장된 속성값을 반환받아 저장
		// String sessionState = (String) session.getAttribute(SESSION_STATE);
		String sessionState = getSession(session);

		// 매개변수로 받은 값과 세션에 저장된 값이 다른 경우
		if (sessionState != null && state != null && sessionState.equals(state)) {
			OAuth20Service oauthService = new ServiceBuilder().apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
					.callback(REDIRECT_URI).state(state).scope(GOOGLE_SCOPE).build(GoogleLoginApi.instance());
			/* Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득 */
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
			return accessToken;
		} else {
			return null;
		}

	}

	// 사용자 접근 토큰을 사용해 사요아의 프로필을 제공하는 api 호출
	public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException {
		OAuth20Service oauthService = new ServiceBuilder().apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
				.callback(REDIRECT_URI).scope(GOOGLE_SCOPE).build(GoogleLoginApi.instance());
		// 사용자의 프로필을 제공하는 API를 요청하기 위한 객체 생성
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
		// 사용자 접속 토큰과 API 요청 객체를 전달하여 로그인 사용의 프로필 요청
		oauthService.signRequest(oauthToken, request);
		// 사용자 프로필을 저장
		Response response = request.send();
		String responseBody = response.getBody();

		return responseBody;
	}

}
