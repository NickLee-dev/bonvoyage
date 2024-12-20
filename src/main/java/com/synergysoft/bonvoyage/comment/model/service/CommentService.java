package com.synergysoft.bonvoyage.comment.model.service;

import java.util.ArrayList;

import com.synergysoft.bonvoyage.comment.model.dto.Comment;

public interface CommentService {

	int insertRouteComment(Comment comment);

	ArrayList<Comment> selectComment(String routeBoardId);

	int deleteRouteComment(String commentId);

	int updateRouteComment(Comment comment);
	
	ArrayList<Comment> selectComment1(String guideBoardId);

	int insertGuideComment(Comment comment);

	int deleteGuideComment(String commentId);

	int updateGuideComment(Comment comment);

}
