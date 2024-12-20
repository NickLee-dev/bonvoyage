package com.synergysoft.bonvoyage.comment.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.comment.model.dao.CommentDao;
import com.synergysoft.bonvoyage.comment.model.dto.Comment;

@Service("commentService")
public 	class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;
	
	@Override
	public int insertRouteComment(Comment comment) {
		return commentDao.insertRouteComment(comment);
	}

	@Override
	public ArrayList<Comment> selectComment(String routeBoardId) {
		return commentDao.selectComment(routeBoardId);
	}

	@Override
	public int deleteRouteComment(String commentId) {
		return commentDao.deleteRouteComment(commentId);
	}

	@Override
	public int updateRouteComment(Comment comment) {
		return commentDao.updateRouteComment(comment);
	}
//가이드 게시판 댓글처리
	@Override
	public ArrayList<Comment> selectComment1(String guideBoardId) {
		return commentDao.selectComment1(guideBoardId);
	}

	@Override
	public int insertGuideComment(Comment comment) {
		return commentDao.insertGuideComment(comment);
	}

	@Override
	public int deleteGuideComment(String commentId) {
		return commentDao.deleteGuideComment(commentId);
	}

	@Override
	public int updateGuideComment(Comment comment) {
		return commentDao.updateGuideComment(comment);
	}

}
