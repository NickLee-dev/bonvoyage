package com.synergysoft.bonvoyage.notice.model.dto;

import java.sql.Date;

public class Notice implements java.io.Serializable{

	private static final long serialVersionUID = 7300351471653170316L;
	private String noticeId;					// NOTICE_ID	VARCHAR2(255 BYTE)
	private String title;						// TITLE	VARCHAR2(500 BYTE)
	private String content;					// CONTENT	CLOB
	private String adminId;					// ADMIN_ID	VARCHAR2(255 BYTE)
	private Date createdAt;					// CREATED_AT	DATE
	private Date updatedAt;					// UPDATED_AT	DATE
	private String updateCheck;				// UPDATE_CHECK	CHAR(1 BYTE)
	private Date deletedAt;					// DELETED_AT	DATE
	private String deleteCheck;				// DELETE_CHECK	CHAR(1 BYTE)
	private String oFile1;						// OFILE1	VARCHAR2(500 BYTE)
	private String oFile2;						// OFILE2	VARCHAR2(500 BYTE)
	private String oFile3;						// OFILE3	VARCHAR2(500 BYTE)
	private String rFile1;						// RFILE1	VARCHAR2(500 BYTE)
	private String rFile2;						// RFILE2	VARCHAR2(500 BYTE)
	private String rFile3;						// RFILE3	VARCHAR2(500 BYTE)
	private int readCount;						//READ_COUNT	NUMBER
	
	
	public Notice() {}

	public Notice(String noticeId, String title, String content, String adminId, Date createdAt, Date updatedAt,
			String updateCheck, Date deletedAt, String deleteCheck, String oFile1, String oFile2, String oFile3,
			String rFile1, String rFile2, String rFile3, int readCount) {
		super();
		this.noticeId = noticeId;
		this.title = title;
		this.content = content;
		this.adminId = adminId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.updateCheck = updateCheck;
		this.deletedAt = deletedAt;
		this.deleteCheck = deleteCheck;
		this.oFile1 = oFile1;
		this.oFile2 = oFile2;
		this.oFile3 = oFile3;
		this.rFile1 = rFile1;
		this.rFile2 = rFile2;
		this.rFile3 = rFile3;
		this.readCount = readCount;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdateCheck() {
		return updateCheck;
	}

	public void setUpdateCheck(String updateCheck) {
		this.updateCheck = updateCheck;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getDeleteCheck() {
		return deleteCheck;
	}

	public void setDeleteCheck(String deleteCheck) {
		this.deleteCheck = deleteCheck;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	@Override
	public String toString() {
		return "Notice [noticeId=" + noticeId + ", title=" + title + ", content=" + content + ", adminId=" + adminId
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", updateCheck=" + updateCheck
				+ ", deletedAt=" + deletedAt + ", deleteCheck=" + deleteCheck + ", oFile1=" + oFile1 + ", oFile2="
				+ oFile2 + ", oFile3=" + oFile3 + ", rFile1=" + rFile1 + ", rFile2=" + rFile2 + ", rFile3=" + rFile3
				+ ", readCount=" + readCount + "]";
	}



	

}
