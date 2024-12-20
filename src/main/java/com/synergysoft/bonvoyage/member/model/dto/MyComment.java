package com.synergysoft.bonvoyage.member.model.dto;

import java.sql.Date;

public class MyComment {
	private String postId;
	private String commentId;
	private String memNickNm;
	private String memId;
	private String title;
	private String commentContent;
	private Date createdAt;
	private String action;
	private String keyword; // 제목, 내용 검색용
	private int startRow; // 한페이지에 출력할 목록의 시작행
	private int endRow; // 한페이지에 출력할 목록의 끝행

	public MyComment() {
		super();
	}

	public MyComment(String postId, String commentId, String memNickNm, String memId, String title,
			String commentContent, Date createdAt, int startRow, int endRow) {
		super();
		this.postId = postId;
		this.commentId = commentId;
		this.memNickNm = memNickNm;
		this.memId = memId;
		this.title = title;
		this.commentContent = commentContent;
		this.createdAt = createdAt;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getMemNickNm() {
		return memNickNm;
	}

	public void setMemNickNm(String memNickNm) {
		this.memNickNm = memNickNm;
	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
		return "MyComment [postId=" + postId + ", commentId=" + commentId + ", memNickNm=" + memNickNm + ", MemId="
				+ memId + ", title=" + title + ", commentContent=" + commentContent + ", createdAt=" + createdAt
				+ ", action=" + action + ", keyword" + keyword + ", startRow=" + startRow + ", endRow=" + endRow + "]";
	}

}
