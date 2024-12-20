package com.synergysoft.bonvoyage.likecount.model.service;

import com.synergysoft.bonvoyage.likecount.model.dto.LikeCount;

public interface LikeCountService {

	LikeCount selectLikeCount(LikeCount likeCount);

	int insertLikeCount(LikeCount likeCount);
	
	

	LikeCount gselectLikeCount(LikeCount likeCount);

	int ginsertLikeCount(LikeCount likeCount);

}
