package com.synergysoft.bonvoyage.likecount.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.likecount.model.dao.LikeCountDao;
import com.synergysoft.bonvoyage.likecount.model.dto.LikeCount;

@Service("likeCountService")
public class LikeCountServiceImpl implements LikeCountService{

	@Autowired
	private LikeCountDao likeCountDao;

	@Override
	public LikeCount selectLikeCount(LikeCount likeCount) {
		return likeCountDao.selectLikeCount(likeCount);
	}

	@Override
	public int insertLikeCount(LikeCount likeCount) {
		return likeCountDao.insertLikeCount(likeCount);
	}
	
	
	//가이드 좋아요수 
	@Override
	public LikeCount gselectLikeCount(LikeCount likeCount) {
		return likeCountDao.gselectLikeCount(likeCount);
	}

	@Override
	public int ginsertLikeCount(LikeCount likeCount) {
		return likeCountDao.ginsertLikeCount(likeCount);
	}
}
