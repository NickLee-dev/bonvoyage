package com.synergysoft.bonvoyage.route.model.dto;

import java.sql.Date;

public class Route implements java.io.Serializable {
	private static final long serialVersionUID = -1477524178816632376L;
	
	private String routeBoardId;		// 게시글 식별코드
	private String userId;			// 작성자 ID
	private String title;				// 제목
	private String content;			// 내용
	private String transport;			// 교통수단 식별코드
	private java.sql.Date createdAt;	// 작성일자
	private java.sql.Date updatedAt;	// 수정일자
	private int likeCount;			// 좋아요 수
	private int totalDuration;		// 총 소요시간
	private String routeName;		// 추천 경로 이름
	private int commentCount;
	private String ofile1;
	private String ofile2;
	private String ofile3;
	private String ofile4;
	private String ofile5;
	private String rfile1;
	private String rfile2;
	private String rfile3;
	private String rfile4;
	private String rfile5;
	private String readCount;
	
	public Route() {
		super();
	}

	public Route(String userId, String title, String content, String transport, Date createdAt) {
		super();
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.transport = transport;
		this.createdAt = createdAt;
	}

	public Route(String routeBoardId, String userId, String title, String content, String transport, Date createdAt,
			Date updatedAt, int likeCount, int totalDuration, String routeName, int commentCount, String ofile1,
			String ofile2, String ofile3, String ofile4, String ofile5, String rfile1, String rfile2, String rfile3,
			String rfile4, String rfile5, String readCount) {
		super();
		this.routeBoardId = routeBoardId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.transport = transport;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.likeCount = likeCount;
		this.totalDuration = totalDuration;
		this.routeName = routeName;
		this.commentCount = commentCount;
		this.ofile1 = ofile1;
		this.ofile2 = ofile2;
		this.ofile3 = ofile3;
		this.ofile4 = ofile4;
		this.ofile5 = ofile5;
		this.rfile1 = rfile1;
		this.rfile2 = rfile2;
		this.rfile3 = rfile3;
		this.rfile4 = rfile4;
		this.rfile5 = rfile5;
		this.readCount = readCount;
	}

	public String getRouteBoardId() {
		return routeBoardId;
	}

	public void setRouteBoardId(String routeBoardId) {
		this.routeBoardId = routeBoardId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public java.sql.Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.sql.Date createdAt) {
		this.createdAt = createdAt;
	}

	public java.sql.Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(java.sql.Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	public String getOfile1() {
		return ofile1;
	}

	public void setOfile1(String ofile1) {
		this.ofile1 = ofile1;
	}

	public String getOfile2() {
		return ofile2;
	}

	public void setOfile2(String ofile2) {
		this.ofile2 = ofile2;
	}

	public String getOfile3() {
		return ofile3;
	}

	public void setOfile3(String ofile3) {
		this.ofile3 = ofile3;
	}

	public String getOfile4() {
		return ofile4;
	}

	public void setOfile4(String ofile4) {
		this.ofile4 = ofile4;
	}

	public String getOfile5() {
		return ofile5;
	}

	public void setOfile5(String ofile5) {
		this.ofile5 = ofile5;
	}

	public String getRfile1() {
		return rfile1;
	}

	public void setRfile1(String rfile1) {
		this.rfile1 = rfile1;
	}

	public String getRfile2() {
		return rfile2;
	}

	public void setRfile2(String rfile2) {
		this.rfile2 = rfile2;
	}

	public String getRfile3() {
		return rfile3;
	}

	public void setRfile3(String rfile3) {
		this.rfile3 = rfile3;
	}

	public String getRfile4() {
		return rfile4;
	}

	public void setRfile4(String rfile4) {
		this.rfile4 = rfile4;
	}

	public String getRfile5() {
		return rfile5;
	}

	public void setRfile5(String rfile5) {
		this.rfile5 = rfile5;
	}
	

	public String getReadCount() {
		return readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	
	
	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	@Override
	public String toString() {
		return "Route [routeBoardId=" + routeBoardId + ", userId=" + userId + ", title=" + title + ", content="
				+ content + ", transport=" + transport + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", likeCount=" + likeCount + ", totalDuration=" + totalDuration + ", routeName=" + routeName
				+ ", commentCount=" + commentCount + ", ofile1=" + ofile1 + ", ofile2=" + ofile2 + ", ofile3=" + ofile3
				+ ", ofile4=" + ofile4 + ", ofile5=" + ofile5 + ", rfile1=" + rfile1 + ", rfile2=" + rfile2
				+ ", rfile3=" + rfile3 + ", rfile4=" + rfile4 + ", rfile5=" + rfile5 + ", readCount=" + readCount + "]";
	}

	
}
