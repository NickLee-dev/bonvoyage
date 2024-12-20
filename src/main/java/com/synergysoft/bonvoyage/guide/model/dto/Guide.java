package com.synergysoft.bonvoyage.guide.model.dto;

import java.sql.Date;

public class Guide implements java.io.Serializable {
	

	private static final long serialVersionUID = -3148970657314738393L;

	private String guidepostId; // POST_ID VARCHAR2(255),
	private String guideTitle; // TITLE VARCHAR2(500) NOT NULL,
	private String guideContent; // CONTENT CLOB NOT NULL,
	private String guideLocation; // LOCATION VARCHAR2(500) NOT NULL,
	private java.sql.Date guideCreatedAt;// CREATED_AT DATE DEFAULT SYSDATE,
	private java.sql.Date guideUpdatedAt;// UPDATED_AT DATE,
	private String guideUserId; // USER_ID VARCHAR2(255) NOT NULL,
	private int likeCount; // LIKE_COUNT NUMBER DEFAULT 0,
	private String oFile1; //	OFILE1	VARCHAR2(500),
	private String oFile2;//	OFILE2	VARCHAR2(500),
	private String oFile3;//	OFILE3	VARCHAR2(500),
	private String oFile4;//	OFILE4	VARCHAR2(500),
	private String oFile5;//	OFILE5	VARCHAR2(500),
	private String rFile1;//	RFILE1	VARCHAR2(500),
	private String rFile2;//	RFILE2	VARCHAR2(500),
	private String rFile3;//	RFILE3	VARCHAR2(500),
	private String rFile4;//	RFILE4	VARCHAR2(500),
	private String rFile5;//	RFILE5	VARCHAR2(500),
	private int readCount;// READ_COUNT	NUMBER
	
	
	public Guide() {
		super();
	
		
	}


	public Guide(String guidepostId, String guideTitle, String guideContent, String guideLocation, Date guideCreatedAt,
			Date guideUpdatedAt, String guideUserId, int likeCount, String oFile1, String oFile2, String oFile3,
			String oFile4, String oFile5, String rFile1, String rFile2, String rFile3, String rFile4, String rFile5,
			int readCount) {
		super();
		this.guidepostId = guidepostId;
		this.guideTitle = guideTitle;
		this.guideContent = guideContent;
		this.guideLocation = guideLocation;
		this.guideCreatedAt = guideCreatedAt;
		this.guideUpdatedAt = guideUpdatedAt;
		this.guideUserId = guideUserId;
		this.likeCount = likeCount;
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
		this.readCount = readCount;
	}


	public int getReadCount() {
		return readCount;
	}


	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}


	public Guide(String guidepostId, String guideTitle, String guideContent, String guideLocation, Date guideCreatedAt,
			Date guideUpdatedAt, String guideUserId, int likeCount, String oFile1, String oFile2, String oFile3,
			String oFile4, String oFile5, String rFile1, String rFile2, String rFile3, String rFile4, String rFile5) {
		super();
		this.guidepostId = guidepostId;
		this.guideTitle = guideTitle;
		this.guideContent = guideContent;
		this.guideLocation = guideLocation;
		this.guideCreatedAt = guideCreatedAt;
		this.guideUpdatedAt = guideUpdatedAt;
		this.guideUserId = guideUserId;
		this.likeCount = likeCount;
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


	public Guide(String guidepostId, String guideTitle, String guideContent, String guideLocation, Date guideCreatedAt,
			String guideUserId, int likeCount, String oFile1, String oFile2, String oFile3, String oFile4,
			String oFile5, String rFile1, String rFile2, String rFile3, String rFile4, String rFile5) {
		super();
		this.guidepostId = guidepostId;
		this.guideTitle = guideTitle;
		this.guideContent = guideContent;
		this.guideLocation = guideLocation;
		this.guideCreatedAt = guideCreatedAt;
		this.guideUserId = guideUserId;
		this.likeCount = likeCount;
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


	public String getGuidepostId() {
		return guidepostId;
	}


	public void setGuidepostId(String guidepostId) {
		this.guidepostId = guidepostId;
	}


	public String getGuideTitle() {
		return guideTitle;
	}


	public void setGuideTitle(String guideTitle) {
		this.guideTitle = guideTitle;
	}


	public String getGuideContent() {
		return guideContent;
	}


	public void setGuideContent(String guideContent) {
		this.guideContent = guideContent;
	}


	public String getGuideLocation() {
		return guideLocation;
	}


	public void setGuideLocation(String guideLocation) {
		this.guideLocation = guideLocation;
	}


	public java.sql.Date getGuideCreatedAt() {
		return guideCreatedAt;
	}


	public void setGuideCreatedAt(java.sql.Date guideCreatedAt) {
		this.guideCreatedAt = guideCreatedAt;
	}


	public java.sql.Date getGuideUpdatedAt() {
		return guideUpdatedAt;
	}


	public void setGuideUpdatedAt(java.sql.Date guideUpdatedAt) {
		this.guideUpdatedAt = guideUpdatedAt;
	}


	public String getGuideUserId() {
		return guideUserId;
	}


	public void setGuideUserId(String guideUserId) {
		this.guideUserId = guideUserId;
	}


	public int getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
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
		return "Guide [guidepostId=" + guidepostId + ", guideTitle=" + guideTitle + ", guideContent=" + guideContent
				+ ", guideLocation=" + guideLocation + ", guideCreatedAt=" + guideCreatedAt + ", guideUpdatedAt="
				+ guideUpdatedAt + ", guideUserId=" + guideUserId + ", likeCount=" + likeCount + ", oFile1=" + oFile1
				+ ", oFile2=" + oFile2 + ", oFile3=" + oFile3 + ", oFile4=" + oFile4 + ", oFile5=" + oFile5
				+ ", rFile1=" + rFile1 + ", rFile2=" + rFile2 + ", rFile3=" + rFile3 + ", rFile4=" + rFile4
				+ ", rFile5=" + rFile5 + ", readCount=" + readCount + "]";
	}
	
	
	




}
