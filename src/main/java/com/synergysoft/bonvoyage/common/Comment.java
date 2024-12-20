package com.synergysoft.bonvoyage.common;

import java.sql.Date;

public class Comment implements java.io.Serializable {

	private static final long serialVersionUID = 1530900580100911620L;

	private String commentId;		// COMMENT_ID	VARCHAR2(255 BYTE)
	private String userId;				// USER_ID	VARCHAR2(255 BYTE)
	private String postId;				// POST_ID	VARCHAR2(255 BYTE)
	private String content;			// CONTENT	CLOB
	private java.sql.Date createdAt;		// CREATED_AT	DATE
	private java.sql.Date updatedAt;		// UPDATED_AT	DATE
	
	public Comment() {
		super();
	}

	public Comment(String commentId, String userId, String postId, String content, Date createdAt, Date updatedAt) {
		super();
		this.commentId = commentId;
		this.userId = userId;
		this.postId = postId;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", userId=" + userId + ", postId=" + postId + ", content=" + content
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
