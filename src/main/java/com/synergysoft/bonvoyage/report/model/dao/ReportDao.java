package com.synergysoft.bonvoyage.report.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.report.model.dto.Report;

@Repository("ReportDao")
public class ReportDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 관리자 : 전체 신고글 조회
	public ArrayList<Report> selectReport(Paging paging) {
		List<Report> list = sqlSessionTemplate.selectList("reportMapper.selectReport", paging);
		return (ArrayList<Report>) list;
	}
	
	// 유저 : 전체 신고글 조회
	public ArrayList<Report> selectReportMe(Report report) {
		List<Report> list = sqlSessionTemplate.selectList("reportMapper.selectReportMe", report);
		return (ArrayList<Report>) list;
	}
	
	// 관리자 : 전체 신고글 검색 조회
	public ArrayList<Report> selectReportSearch(Report report) {
		List<Report> list = sqlSessionTemplate.selectList("reportMapper.selectReportSearch", report);
		return (ArrayList<Report>) list;
	}
	
	// 유저 : 전체 신고글 검색 조회
	public ArrayList<Report> selectReportMeSearch(Report report) {
		List<Report> list = sqlSessionTemplate.selectList("reportMapper.selectReportMeSearch", report);
		return (ArrayList<Report>) list;
	}

	// 관리자 : 신고글 검색 전체 갯수 조회
	public int selectReportSearchCount(Report report) {
		return sqlSessionTemplate.selectOne("reportMapper.selectReportSearchCount", report);
	}

	// 유저 : 신고글 검색 전체 갯수 조회
	public int selectReportMeSearchCount(Report report) {
		return sqlSessionTemplate.selectOne("reportMapper.selectReportMeSearchCount", report);
	}

	// 신고글 상세 조회
	public Report selectReportDetail(String reportId) {
		return sqlSessionTemplate.selectOne("reportMapper.selectReportDetail", reportId);
	}

	// 관리자 : 신고글 전체 갯수 조회
	public int selectReportListCount() {
		return sqlSessionTemplate.selectOne("reportMapper.selectReportListCount");
	}

	// 유저 : 신고글 전체 갯수 조회
	public int selectReportUserListCount(String memId) {
		return sqlSessionTemplate.selectOne("reportMapper.selectReportUserListCount", memId);
	}

	// 신고글 등록
	public int insertReport(Report report) {
		return sqlSessionTemplate.insert("reportMapper.insertReport", report);
	}

	// 신고 처리
	public int updateReportProcess(Report report) {
		return sqlSessionTemplate.update("reportMapper.updateReportProcess", report);
	}

	// 신고 반려
	public int updateReportReject(Report report) {
		return sqlSessionTemplate.update("reportMapper.updateReportReject", report);
	}

	// 신고 삭제
	public int deleteReport(String reportId) {
		return sqlSessionTemplate.delete("reportMapper.deleteReport", reportId);
	}

}