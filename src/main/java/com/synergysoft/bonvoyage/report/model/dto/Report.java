package com.synergysoft.bonvoyage.report.model.dto;

import java.util.Date;

public class Report implements java.io.Serializable {
	private static final long serialVersionUID = 4468055390785996667L;
	
	private int reportNum;
	private String reportId;
	private String postId;
	private String title; // 2024-10-07 추가
	private String reportUserId;
	private String memNickNm; // 2024-10-11 추가
	private String reportingReason;
	private String detail;
	private Date reportDate;
	private String checkedAdmin;
	private String checkedAdminId;
	private String checkedAssigned;
	private String assignedAdminId;
	private String action;
	private String keyword; // 제목, 내용 검색용
	private int startRow;	// 한페이지에 출력할 목록의 시작행
	private int endRow;		// 한페이지에 출력할 목록의 끝행

	public Report() {
		super();
	}

	public Report(String reportId, String postId, String title, String reportUserId, String reportingReason, String detail,
			Date reportDate, String checkedAdmin, String checkedAdminId, String checkedAssigned,
			String assignedAdminId) {
		super();
		this.reportId = reportId;
		this.postId = postId;
		this.title = title;
		this.reportUserId = reportUserId;
		this.reportingReason = reportingReason;
		this.detail = detail;
		this.reportDate = reportDate;
		this.checkedAdmin = checkedAdmin;
		this.checkedAdminId = checkedAdminId;
		this.checkedAssigned = checkedAssigned;
		this.assignedAdminId = assignedAdminId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getReportUserId() {
		return reportUserId;
	}

	public void setReportUserId(String reportUserId) {
		this.reportUserId = reportUserId;
	}

	public String getReportingReason() {
		return reportingReason;
	}

	public void setReportingReason(String reportingReason) {
		this.reportingReason = reportingReason;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getCheckedAdmin() {
		return checkedAdmin;
	}

	public void setCheckedAdmin(String checkedAdmin) {
		this.checkedAdmin = checkedAdmin;
	}

	public String getCheckedAdminId() {
		return checkedAdminId;
	}

	public void setCheckedAdminId(String checkedAdminId) {
		this.checkedAdminId = checkedAdminId;
	}

	public String getCheckedAssigned() {
		return checkedAssigned;
	}

	public void setCheckedAssigned(String checkedAssigned) {
		this.checkedAssigned = checkedAssigned;
	}

	public String getAssignedAdminId() {
		return assignedAdminId;
	}

	public void setAssignedAdminId(String assignedAdminId) {
		this.assignedAdminId = assignedAdminId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	@Override
	public String toString() {
		return "Report [reportId=" + reportId + ", postId=" + postId + ", title=" + title + ", reportUserId=" + reportUserId
				+ ", reportingReason=" + reportingReason + ", detail=" + detail + ", reportDate=" + reportDate
				+ ", checkedAdmin=" + checkedAdmin + ", checkedAdminId=" + checkedAdminId + ", checkedAssigned="
				+ checkedAssigned + ", assignedAdminId=" + assignedAdminId + "]";
	}

	public String getMemNickNm() {
		return memNickNm;
	}

	public void setMemNickNm(String memNickNm) {
		this.memNickNm = memNickNm;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getReportNum() {
		return reportNum;
	}

	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}

}
