package com.synergysoft.bonvoyage.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.member.model.dto.Member;
import com.synergysoft.bonvoyage.member.model.dto.MyComment;

//jmoh03 (오정민)
@Repository("memberDao")
public class MemberDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	// 일반 로그인
	public Member selectLogin(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectLogin", memId);
	}

	// 소셜 로그인
	public Member selectSocialLogin(Member member) {
		return sqlSessionTemplate.selectOne("memberMapper.selectSocialLogin", member.getMemId());
	}

	// 소셜 로그인
	public Member selectSocialLogin(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectSocialLogin", memId);
	}

	// 아이디 중복 검사
	public int selectCheckId(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectCheckId", memId);
	}

	// 아이디 찾기
	public Member selectIDSearch(String memPhone) {
		return sqlSessionTemplate.selectOne("memberMapper.selectIDSearch", memPhone);
	}

	// 비밀번호 찾기
	public Member selectPWSearch(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectPWSearch", memId);
	}

	// 내 정보 조회
	public Member selectMyinfo(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectMyinfo", memId);
	}
	
	// 내가 쓴 댓글 전체 조회
	public ArrayList<MyComment> selectCommentAll(MyComment mycomment) {
		List<MyComment> list = sqlSessionTemplate.selectList("memberMapper.selectCommentAll", mycomment);
		return (ArrayList<MyComment>) list;
	}

	// 내가 쓴 댓글 조회(경로게시판)
	public ArrayList<MyComment> selectCommentRouteBoard(MyComment mycomment) {
		List<MyComment> list = sqlSessionTemplate.selectList("memberMapper.selectCommentRouteBoard", mycomment);
		return (ArrayList<MyComment>) list;
	}

	// 내가 쓴 댓글 조회(가이드게시판)
	public ArrayList<MyComment> selectCommentGuideBoard(MyComment mycomment) {
		List<MyComment> list = sqlSessionTemplate.selectList("memberMapper.selectCommentGuideBoard", mycomment);
		return (ArrayList<MyComment>) list;
	}

	// 내가 쓴 댓글 전체 수 조회
	public int selectCommentAllCount(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectCommentAllCount", memId);
	}

	// 내가 쓴 댓글 수 조회(경로게시판)
	public int selectCommentRouteBoardCount(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectCommentRouteBoardCount", memId);
	}

	// 내가 쓴 댓글 수 조회(가이드게시판)
	public int selectCommentGuideBoardCount(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectCommentGuideBoardCount", memId);
	}
	
	// 내가 쓴 댓글 전체 검색 조회
	public ArrayList<MyComment> selectCommentAllSearch(MyComment mycomment) {
		List<MyComment> list = sqlSessionTemplate.selectList("memberMapper.selectCommentAllSearch", mycomment);
		return (ArrayList<MyComment>) list;
	}

	// 내가 쓴 댓글 검색 조회(경로게시판)
	public ArrayList<MyComment> selectCommentRouteBoardSearch(MyComment mycomment) {
		List<MyComment> list = sqlSessionTemplate.selectList("memberMapper.selectCommentRouteBoardSearch", mycomment);
		return (ArrayList<MyComment>) list;
	}

	// 내가 쓴 댓글 검색 조회(가이드게시판)
	public ArrayList<MyComment> selectCommentGuideBoardSearch(MyComment mycomment) {
		List<MyComment> list = sqlSessionTemplate.selectList("memberMapper.selectCommentGuideBoardSearch", mycomment);
		return (ArrayList<MyComment>) list;
	}

	// 내가 쓴 댓글 전체 검색 수 조회
	public int selectCommentAllSearchCount(MyComment mycomment) {
		return sqlSessionTemplate.selectOne("memberMapper.selectCommentAllSearchCount", mycomment);
	}

	// 내가 쓴 댓글 경로게시판 검색 수 조회
	public int selectCommentRouteBoardSearchCount(MyComment mycomment) {
		return sqlSessionTemplate.selectOne("memberMapper.selectCommentRouteBoardSearchCount", mycomment);
	}

	// 내가 쓴 댓글 가이드게시판 검색 수 조회
	public int selectCommentGuideBoardSearchCount(MyComment mycomment) {
		return sqlSessionTemplate.selectOne("memberMapper.selectCommentGuideBoardSearchCount", mycomment);
	}

	// 관리자 : 회원 목록 검색 조회
	public ArrayList<Member> selectMemberSearch(Member search) {
		List<Member> list = sqlSessionTemplate.selectList("memberMapper.selectMemberSearch", search);
		return (ArrayList<Member>) list;
	}

	// 관리자 : 회원 목록 검색 수 조회
	public int selectMemberListSearchCount(Member search) {
		return sqlSessionTemplate.selectOne("memberMapper.selectMemberListSearchCount", search);
	}

	// 회원 정보 찾기
	public Member selectMemberByEmailId(Member member) {
		return sqlSessionTemplate.selectOne("memberMapper.selectMemberByEmailId", member);
	}

	// 관리자 : 회원 목록 조회
	public ArrayList<Member> selectMember(Paging paging) {
		List<Member> list = sqlSessionTemplate.selectList("memberMapper.selectMember", paging);
		return (ArrayList<Member>) list;
	}

	// 관리자 : 회원 목록 카운트 조회
	public int selectMemberListCount() {
		return sqlSessionTemplate.selectOne("memberMapper.selectMemberListCount");
	}

	// 관리자 : 회원 상세 조회
	public Member selectMemberDetail(String memId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectMemberDetail", memId);
	}

	// 일반 회원가입
	public int insertMember(Member member) {
		return sqlSessionTemplate.insert("memberMapper.insertMember", member);
	}

	// 소셜 회원가입
	public int insertSocialMember(Member member) {
		return sqlSessionTemplate.insert("memberMapper.insertSocialMember", member);
	}

	// 로그인 로그 변경
	public int updateLoginLog(String memId) {
		return sqlSessionTemplate.update("memberMapper.updateLoginLog", memId);
	}

	// 로그아웃 로그 변경
	public int updateLogoutLog(String memId) {
		return sqlSessionTemplate.update("memberMapper.updateLogoutLog", memId);
	}

	// 내 정보 변경
	public int updateMyinfo(Member member) {
		return sqlSessionTemplate.update("memberMapper.updateMyinfo", member);
	}

	// 비밀번호 변경
	public int updateMemberPw(Member member) {
		return sqlSessionTemplate.update("memberMapper.updateMemberPw", member);
	}

	// 회원 탈퇴
	public int updateLeft(String memId) {
		return sqlSessionTemplate.update("memberMapper.updateLeft", memId);
	}

	// 관리자 : 회원 정보 수정
	public int updateMember(Member member) {
		return sqlSessionTemplate.update("memberMapper.updateMember", member);
	}

	// 관리자 : 회원 계정 조치
	public int updateMemberAccount(String memId) {
		return sqlSessionTemplate.update("memberMapper.updateMemberAccount", memId);
	}
	
	// 관리자 : 회원 계정 조치 해제
	public int updateMemberAccountClear(String memId) {
		return sqlSessionTemplate.update("memberMapper.updateMemberAccountClear", memId);
	}
	
	// 관리자 : 회원 관리자 부여
	public int updateMemberAdmin(String memId) {
		return sqlSessionTemplate.update("memberMapper.updateMemberAdmin", memId);
	}

	// 관리자 : 회원 관리자 박탈
	public int updateMemberAdminDelete(String memId) {
		return sqlSessionTemplate.update("memberMapper.updateMemberAdminDelete", memId);
	}

}
