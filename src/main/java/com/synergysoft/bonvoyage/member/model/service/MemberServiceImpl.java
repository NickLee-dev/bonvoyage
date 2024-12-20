package com.synergysoft.bonvoyage.member.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.member.model.dao.MemberDao;
import com.synergysoft.bonvoyage.member.model.dto.Member;
import com.synergysoft.bonvoyage.member.model.dto.MyComment;

//jmoh03 (오정민)
@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;

	@Override
	// 일반 로그인
	public Member selectLogin(String memId) {
		return memberDao.selectLogin(memId);
	}

	@Override
	// 카카오 토큰값 받아오기 2024. 10. 04 작성 및 테스트 성공
	public String getReturnAccessToken(String code) {
		String access_token = "";
		String refresh_token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			// HttpURLConnection 설정 값 셋팅
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// buffer 스트림 객체 값 셋팅 후 요청
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=bcd1f6d5790735ccc553978585165423"); // 앱 KEY VALUE
			sb.append("&redirect_uri=http://ktj0514.synology.me:8080/bonvoyage/kakaoLogin.do"); // 앱 CALLBACK 경로
			sb.append("&code=" + code);
			bw.write(sb.toString());
			bw.flush();

			// RETURN 값 result 변수에 저장
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String br_line = "";
			String result = "";

			while ((br_line = br.readLine()) != null) {
				result += br_line;
			}

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			// 토큰 값 저장 및 리턴
			access_token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_token;
	}
	
	@Override
	// 유저 정보 가져오기 2024. 10. 04 작성 및 테스트 성공
	public Map<String, Object> getMemberInfo(String access_token) {
		Map<String, Object> resultMap = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String br_line = "";
			String result = "";

			while ((br_line = br.readLine()) != null) {
				result += br_line;
			}
			System.out.println("response:" + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakao_account.getAsJsonObject().get("email").getAsString();
			resultMap.put("nickname", nickname);
			resultMap.put("email", email);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@Override
	// 소셜 로그인
	public Member selectSocialLogin(Member member) {
		return memberDao.selectSocialLogin(member);
	}
	
	@Override
	// 소셜 로그인
	public Member selectSocialLogin(String memId) {
		return memberDao.selectSocialLogin(memId);
	}

	@Override
	// 아이디 중복 검사
	public int selectCheckId(String memId) {
		return memberDao.selectCheckId(memId);
	}

	@Override
	// 아이디 찾기
	public Member selectIDSearch(String memPhone) {
		return memberDao.selectIDSearch(memPhone);
	}

	@Override
	// 비밀번호 찾기
	public Member selectPWSearch(String memId) {
		return memberDao.selectPWSearch(memId);
	}
	
	@Override
	// 회원 정보 찾기
	public Member selectMemberByEmailId(Member member) {
		return memberDao.selectMemberByEmailId(member);
	}

	@Override
	// 내 정보 조회
	public Member selectMyinfo(String memId) {
		return memberDao.selectMyinfo(memId);
	}

	@Override
	// 내가 쓴 댓글 전체 조회
	public ArrayList<MyComment> selectCommentAll(MyComment mycomment) {
		return memberDao.selectCommentAll(mycomment);
	}

	@Override
	// 내가 쓴 댓글 조회(경로게시판)
	public ArrayList<MyComment> selectCommentRouteBoard(MyComment mycomment) {
		return memberDao.selectCommentRouteBoard(mycomment);
	}

	@Override
	// 내가 쓴 댓글 조회(가이드게시판)
	public ArrayList<MyComment> selectCommentGuideBoard(MyComment mycomment) {
		return memberDao.selectCommentGuideBoard(mycomment);
	}
	
	@Override
	// 내가 쓴 댓글 전체 수 조회
	public int selectCommentAllCount(String memId) {
		return memberDao.selectCommentAllCount(memId);
	}

	@Override
	// 내가 쓴 댓글 수 조회(경로게시판)
	public int selectCommentRouteBoardCount(String memId) {
		return memberDao.selectCommentRouteBoardCount(memId);
	}

	@Override
	// 내가 쓴 댓글 수 조회(가이드게시판)
	public int selectCommentGuideBoardCount(String memId) {
		return memberDao.selectCommentGuideBoardCount(memId);
	}

	@Override
	// 관리자 : 회원 목록 조회
	public ArrayList<Member> selectMember(Paging paging) {
		return memberDao.selectMember(paging);
	}

	@Override
	// 관리자 : 회원 목록 카운트 조회
	public int selectMemberListCount() {
		return memberDao.selectMemberListCount();
	}
	
	@Override
	// 내가 쓴 댓글 검색 전체 조회
	public ArrayList<MyComment> selectCommentAllSearch(MyComment mycomment) {
		return memberDao.selectCommentAllSearch(mycomment);
	}

	@Override
	// 내가 쓴 댓글 검색 조회(경로게시판)
	public ArrayList<MyComment> selectCommentRouteBoardSearch(MyComment mycomment) {
		return memberDao.selectCommentRouteBoardSearch(mycomment);
	}

	@Override
	// 내가 쓴 댓글 검색 조회(가이드게시판)
	public ArrayList<MyComment> selectCommentGuideBoardSearch(MyComment mycomment) {
		return memberDao.selectCommentGuideBoardSearch(mycomment);
	}

	@Override
	// 내가 쓴 댓글 검색 전체 수 조회
	public int selectCommentAllSearchCount(MyComment mycomment) {
		return memberDao.selectCommentAllSearchCount(mycomment);
	}

	@Override
	// 내가 쓴 댓글 검색 수 조회(경로게시판)
	public int selectCommentRouteBoardSearchCount(MyComment mycomment) {
		return memberDao.selectCommentRouteBoardSearchCount(mycomment);
	}

	@Override
	// 내가 쓴 댓글 검색 수 조회(가이드게시판)
	public int selectCommentGuideBoardSearchCount(MyComment mycomment) {
		return memberDao.selectCommentGuideBoardSearchCount(mycomment);
	}

	@Override
	// 관리자 : 회원 목록 검색 조회
	public ArrayList<Member> selectMemberSearch(Member search) {
		return memberDao.selectMemberSearch(search);
	}

	@Override
	// 회원 목록 검색 수 조회
	public int selectMemberListSearchCount(Member search) {
		return memberDao.selectMemberListSearchCount(search);
	}

	@Override
	// 관리자 : 회원 상세 조회
	public Member selectMemberDetail(String memId) {
		return memberDao.selectMemberDetail(memId);
	}

	@Override
	// 일반 회원가입
	public int insertMember(Member member) {
		return memberDao.insertMember(member);
	}

	@Override
	// 소셜 회원가입
	public int insertSocialMember(Member member) {
		return memberDao.insertSocialMember(member);
	}
	
	@Override
	// 로그인 로그 수정
	public int updateLoginLog(String memId) {
		return memberDao.updateLoginLog(memId);
	}

	@Override
	// 로그아웃 로그 수정
	public int updateLogoutLog(String memId) {
		return memberDao.updateLogoutLog(memId);
	}

	@Override
	// 내 정보 수정
	public int updateMyinfo(Member member) {
		return memberDao.updateMyinfo(member);
	}
	
	@Override
	// 비밀번호 수정
	public int updateMemberPw(Member member) {
		return memberDao.updateMemberPw(member);
	}

	@Override
	// 회원 탈퇴
	public int updateLeft(String memId) {
		return memberDao.updateLeft(memId);
	}

	@Override
	// 관리자 : 회원 정보 수정
	public int updateMember(Member member) {
		return memberDao.updateMember(member);
	}

	@Override
	// 관리자 : 회원 계정 조치
	public int updateMemberAccount(String memId) {
		return memberDao.updateMemberAccount(memId);
	}
	

	@Override
	// 관리자 : 회원 계정 조치 해제
	public int updateMemberAccountClear(String memId) {
		return memberDao.updateMemberAccountClear(memId);
	}

	@Override
	// 관리자 : 회원 관리자 부여
	public int updateMemberAdmin(String memId) {
		return memberDao.updateMemberAdmin(memId);
	}

	@Override
	// 관리자 : 회원 관리자 박탈
	public int updateMemberAdminDelete(String memId) {
		return memberDao.updateMemberAdminDelete(memId);
	}

}
