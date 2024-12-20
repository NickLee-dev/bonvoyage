package com.synergysoft.bonvoyage.report.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.report.model.dao.ReportDao;
import com.synergysoft.bonvoyage.report.model.dto.Report;

@Service("ReportService")
public class ReportServiceImpl implements ReportService {
	@Autowired
	private ReportDao reportDao;

	@Override
	// TODO Auto-generated method stub
	public ArrayList<Report> selectReport(Paging paging) {
		return reportDao.selectReport(paging);
	}

	@Override
	// TODO Auto-generated method stub
	public ArrayList<Report> selectReportMe(Report report) {
		return reportDao.selectReportMe(report);
	}

	@Override
	// TODO Auto-generated method stub
	public ArrayList<Report> selectReportSearch(Report report) {
		return reportDao.selectReportSearch(report);
	}

	@Override
	// TODO Auto-generated method stub
	public ArrayList<Report> selectReportMeSearch(Report report) {
		return reportDao.selectReportMeSearch(report);
	}

	@Override
	// TODO Auto-generated method stub
	public int selectReportSearchCount(Report report) {
		return reportDao.selectReportSearchCount(report);
	}

	@Override
	// TODO Auto-generated method stub
	public int selectReportMeSearchCount(Report report) {
		return reportDao.selectReportMeSearchCount(report);
	}

	@Override
	// TODO Auto-generated method stub
	public Report selectReportDetail(String reportId) {
		return reportDao.selectReportDetail(reportId);
	}

	@Override
	// TODO Auto-generated method stub
	public int selectReportListCount() {
		return reportDao.selectReportListCount();
	}

	@Override
	// TODO Auto-generated method stub
	public int selectReportUserListCount(String memId) {
		return reportDao.selectReportUserListCount(memId);
	}

	@Override
	// TODO Auto-generated method stub
	public int insertReport(Report report) {
		return reportDao.insertReport(report);
	}

	@Override
	// TODO Auto-generated method stub
	public int updateReportProcess(Report report) {
		return reportDao.updateReportProcess(report);
	}

	@Override
	// TODO Auto-generated method stub
	public int updateReportReject(Report report) {
		return reportDao.updateReportReject(report);
	}

	@Override
	// TODO Auto-generated method stub
	public int deleteReport(String reportId) {
		return reportDao.deleteReport(reportId);
	}

}
