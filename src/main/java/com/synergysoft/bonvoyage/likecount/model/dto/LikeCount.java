package com.synergysoft.bonvoyage.likecount.model.dto;

public class LikeCount implements java.io.Serializable {
	private static final long serialVersionUID = -4837448792803479506L;
	
	private String userId;		// USER_ID
	private String postId;		// POST_ID
	
	// 생성자
	public LikeCount() {
		super();
	}

	public LikeCount(String userId, String postId) {
		super();
		this.userId = userId;
		this.postId = postId;
	}
	
	// getters & setters
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	// toString 오버라이딩
	@Override
	public String toString() {
		return "LikeCount [userId=" + userId + ", postId=" + postId + "]";
	}
	
	
	
}
