package com.synergysoft.bonvoyage.report.model.service;

import java.util.ArrayList;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.report.model.dto.Report;

public interface ReportService {
	
	ArrayList<Report> selectReport(Paging paging);
	ArrayList<Report> selectReportMe(Report report);
	ArrayList<Report> selectReportSearch(Report report);
	ArrayList<Report> selectReportMeSearch(Report report);
	int selectReportSearchCount(Report report);
	int selectReportMeSearchCount(Report report);
	Report selectReportDetail(String reportId);
	int selectReportListCount();
	int selectReportUserListCount(String memId);
	
	int insertReport(Report report);
	
	int updateReportProcess(Report report);
	int updateReportReject(Report report);
	
	int deleteReport(String reportId);
	
}
