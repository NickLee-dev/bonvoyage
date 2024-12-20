package com.synergysoft.bonvoyage.qna.model.dto;

import java.sql.Date;

public class Qna implements java.io.Serializable{

	private static final long serialVersionUID = 2320831880198548830L;
	
	private String qnaId;			// QNA_ID	VARCHAR2(255 BYTE)
	private String title;			// TITLE	VARCHAR2(500 BYTE)
	private String userContent;		// USER_CONTENT	CLOB
	private String userId;			// USER_ID	VARCHAR2(255 BYTE)
	private Date qCreatedAt;		// Q_CREATED_AT	DATE
	private Date qUpdatedAt;		// Q_UPDATED_AT	DATE
	private String adminId;			// ADMIN_ID	VARCHAR2(255 BYTE)
	private String isAccept;		// IS_ACCEPT	CHAR(1 BYTE)
	private String adminContent;	// ADMIN_CONTENT	CLOB
	private Date aCreatedAt;		// A_CREATED_AT	DATE
	private String isSecret;		// IS_SECRET	CHAR(1 BYTE)
	private Date deleteAt;		// DELETE_AT	DATE
	private String deleteCheck;		// DELETE_CHECK	CHAR(1 BYTE)
	private String oFile1;			// OFILE1	VARCHAR2(500 BYTE)
	private String oFile2;			// OFILE2	VARCHAR2(500 BYTE)
	private String oFile3;			// OFILE3	VARCHAR2(500 BYTE)
	private String oFile4;			// OFILE4	VARCHAR2(500 BYTE)
	private String oFile5;			// OFILE5	VARCHAR2(500 BYTE)
	private String rFile1;			// RFILE1	VARCHAR2(500 BYTE)
	private String rFile2;			// RFILE2	VARCHAR2(500 BYTE)
	private String rFile3;			// RFILE3	VARCHAR2(500 BYTE)
	private String rFile4;			// RFILE4	VARCHAR2(500 BYTE)
	private String rFile5;			// RFILE5	VARCHAR2(500 BYTE)
	
	public Qna() {}

	public Qna(String qnaId, String title, String userContent, String userId, Date qCreatedAt, Date qUpdatedAt,
			String adminId, String isAccept, String adminContent, Date aCreatedAt, String isSecret, Date deleteAt,
			String deleteCheck, String oFile1, String oFile2, String oFile3, String oFile4, String oFile5,
			String rFile1, String rFile2, String rFile3, String rFile4, String rFile5) {
		super();
		this.qnaId = qnaId;
		this.title = title;
		this.userContent = userContent;
		this.userId = userId;
		this.qCreatedAt = qCreatedAt;
		this.qUpdatedAt = qUpdatedAt;
		this.adminId = adminId;
		this.isAccept = isAccept;
		this.adminContent = adminContent;
		this.aCreatedAt = aCreatedAt;
		this.isSecret = isSecret;
		this.deleteAt = deleteAt;
		this.deleteCheck = deleteCheck;
		this.oFile1 = oFile1;
		this.oFile2 = oFile2;
		this.oFile3 = oFile3;
		this.oFile4 = oFile4;
		this.oFile5 = oFile5;
		this.rFile1 = rFile1;
		this.rFile2 = rFile2;
		this.rFile3 = rFile3;
		this.rFile4 = rFile4;
		this.rFile5 = rFile5;
	}

	public String getQnaId() {
		return qnaId;
	}

	public void setQnaId(String qnaId) {
		this.qnaId = qnaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserContent() {
		return userContent;
	}

	public void setUserContent(String userContent) {
		this.userContent = userContent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getqCreatedAt() {
		return qCreatedAt;
	}

	public void setqCreatedAt(Date qCreatedAt) {
		this.qCreatedAt = qCreatedAt;
	}

	public Date getqUpdatedAt() {
		return qUpdatedAt;
	}

	public void setqUpdatedAt(Date qUpdatedAt) {
		this.qUpdatedAt = qUpdatedAt;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}

	public String getAdminContent() {
		return adminContent;
	}

	public void setAdminContent(String adminContent) {
		this.adminContent = adminContent;
	}

	public Date getaCreatedAt() {
		return aCreatedAt;
	}

	public void setaCreatedAt(Date aCreatedAt) {
		this.aCreatedAt = aCreatedAt;
	}

	public String getIsSecret() {
		return isSecret;
	}

	public void setIsSecret(String isSecret) {
		this.isSecret = isSecret;
	}

	public Date getDeleteAt() {
		return deleteAt;
	}

	public void setDeleteAt(Date deleteAt) {
		this.deleteAt = deleteAt;
	}

	public String getDeleteCheck() {
		return deleteCheck;
	}

	public void setDeleteCheck(String deleteCheck) {
		this.deleteCheck = deleteCheck;
	}

	public String getoFile1() {
		return oFile1;
	}

	public void setoFile1(String oFile1) {
		this.oFile1 = oFile1;
	}

	public String getoFile2() {
		return oFile2;
	}

	public void setoFile2(String oFile2) {
		this.oFile2 = oFile2;
	}

	public String getoFile3() {
		return oFile3;
	}

	public void setoFile3(String oFile3) {
		this.oFile3 = oFile3;
	}

	public String getoFile4() {
		return oFile4;
	}

	public void setoFile4(String oFile4) {
		this.oFile4 = oFile4;
	}

	public String getoFile5() {
		return oFile5;
	}

	public void setoFile5(String oFile5) {
		this.oFile5 = oFile5;
	}

	public String getrFile1() {
		return rFile1;
	}

	public void setrFile1(String rFile1) {
		this.rFile1 = rFile1;
	}

	public String getrFile2() {
		return rFile2;
	}

	public void setrFile2(String rFile2) {
		this.rFile2 = rFile2;
	}

	public String getrFile3() {
		return rFile3;
	}

	public void setrFile3(String rFile3) {
		this.rFile3 = rFile3;
	}

	public String getrFile4() {
		return rFile4;
	}

	public void setrFile4(String rFile4) {
		this.rFile4 = rFile4;
	}

	public String getrFile5() {
		return rFile5;
	}

	public void setrFile5(String rFile5) {
		this.rFile5 = rFile5;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Qna [qnaId=" + qnaId + ", title=" + title + ", userContent=" + userContent + ", userId=" + userId
				+ ", qCreatedAt=" + qCreatedAt + ", qUpdatedAt=" + qUpdatedAt + ", adminId=" + adminId + ", isAccept="
				+ isAccept + ", adminContent=" + adminContent + ", aCreatedAt=" + aCreatedAt + ", isSecret=" + isSecret
				+ ", deleteAt=" + deleteAt + ", deleteCheck=" + deleteCheck + ", oFile1=" + oFile1 + ", oFile2="
				+ oFile2 + ", oFile3=" + oFile3 + ", oFile4=" + oFile4 + ", oFile5=" + oFile5 + ", rFile1=" + rFile1
				+ ", rFile2=" + rFile2 + ", rFile3=" + rFile3 + ", rFile4=" + rFile4 + ", rFile5=" + rFile5 + "]";
	}

	
	
	
}
