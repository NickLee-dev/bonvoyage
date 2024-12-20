package com.synergysoft.bonvoyage.likecount.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.likecount.model.dto.LikeCount;

@Repository("likeCountDao")
public class LikeCountDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public LikeCount selectLikeCount(LikeCount likeCount) {
		return sqlSessionTemplate.selectOne("likecountMapper.selectLikeCount", likeCount);
	}

	public int insertLikeCount(LikeCount likeCount) {
		return sqlSessionTemplate.insert("likecountMapper.insertLikeCount", likeCount);
	}

	public LikeCount gselectLikeCount(LikeCount likeCount) {
		return sqlSessionTemplate.selectOne("likecountMapper.gselectLikeCount" , likeCount);
	}

	public int ginsertLikeCount(LikeCount likeCount) {
		return sqlSessionTemplate.insert("likecountMapper.ginsertLikeCount", likeCount);
	}
}
