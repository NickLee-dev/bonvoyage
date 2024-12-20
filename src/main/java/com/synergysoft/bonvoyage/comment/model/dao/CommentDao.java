package com.synergysoft.bonvoyage.comment.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.comment.model.dto.Comment;

@Repository("commentDao")
public class CommentDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int insertRouteComment(Comment comment) {
		return sqlSessionTemplate.insert("commentMapper.insertRouteComment", comment);
	}

	public ArrayList<Comment> selectComment(String routeBoardId) {
		List<Comment> list = sqlSessionTemplate.selectList("commentMapper.selectComment", routeBoardId);
		return (ArrayList<Comment>)list;
	}

	public int deleteRouteComment(String commentId) {
		return sqlSessionTemplate.delete("commentMapper.deleteRouteComment", commentId);
	}

	public int updateRouteComment(Comment comment) {
		return sqlSessionTemplate.update("commentMapper.updateRouteComment", comment);
	}

	//가이드 게시판
	public ArrayList<Comment> selectComment1(String guideBoardId) {
		List<Comment> list = sqlSessionTemplate.selectList("commentMapper.selectComment", guideBoardId);
		return (ArrayList<Comment>)list;
	}

	public int insertGuideComment(Comment comment) {
		return sqlSessionTemplate.update("commentMapper.insertGuideComment", comment);
	}

	public int deleteGuideComment(String commentId) {
		return sqlSessionTemplate.update("commentMapper.deleteGuideComment", commentId);
	}

	public int updateGuideComment(Comment comment) {
		return sqlSessionTemplate.update("commentMapper.updateGuideComment", comment);
	}

}
