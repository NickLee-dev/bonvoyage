package com.synergysoft.bonvoyage.member.model.service;

import java.util.ArrayList;
import java.util.Map;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.member.model.dto.Member;
import com.synergysoft.bonvoyage.member.model.dto.MyComment;

// jmoh03 (오정민)
public interface MemberService {
	
	Member selectLogin(String memId);
	String getReturnAccessToken(String code);
	Map<String, Object> getMemberInfo(String kakaoToken);
	Member selectSocialLogin(String memId);
	Member selectSocialLogin(Member member);
	Member selectIDSearch(String memPhone);
	Member selectPWSearch(String memId);
	Member selectMyinfo(String memId);
	ArrayList<MyComment> selectCommentAll(MyComment mycomment);
	ArrayList<MyComment> selectCommentRouteBoard(MyComment mycomment);
	ArrayList<MyComment> selectCommentGuideBoard(MyComment mycomment);
	int selectCommentAllCount(String memId);
	int selectCommentRouteBoardCount(String memId);
	int selectCommentGuideBoardCount(String memId);
	ArrayList<Member> selectMember(Paging paging);
	int selectMemberListCount();
	ArrayList<MyComment> selectCommentAllSearch(MyComment mycomment);
	ArrayList<MyComment> selectCommentRouteBoardSearch(MyComment mycomment);
	ArrayList<MyComment> selectCommentGuideBoardSearch(MyComment mycomment);
	int selectCommentAllSearchCount(MyComment mycomment);
	int selectCommentRouteBoardSearchCount(MyComment mycomment);
	int selectCommentGuideBoardSearchCount(MyComment mycomment);
	ArrayList<Member> selectMemberSearch(Member search);
	int selectMemberListSearchCount(Member searchCount);
	Member selectMemberDetail(String memId);
	int selectCheckId(String memId);
	Member selectMemberByEmailId(Member member);
	
	int insertMember(Member member);
	int insertSocialMember(Member member);
	
	int updateLoginLog(String memId);
	int updateLogoutLog(String memId);
	int updateMyinfo(Member member);
	int updateMemberPw(Member member);
	int updateLeft(String memId);
	int updateMember(Member member);
	int updateMemberAccount(String memId);
	int updateMemberAccountClear(String memId);
	int updateMemberAdmin(String memId);
	int updateMemberAdminDelete(String memId);
	
}
