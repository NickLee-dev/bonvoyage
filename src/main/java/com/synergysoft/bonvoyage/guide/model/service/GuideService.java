package com.synergysoft.bonvoyage.guide.model.service;

import java.util.ArrayList;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.guide.model.dto.Guide;


public interface GuideService {
	ArrayList<Guide> selectAllGuide(Paging paging);

	int insertGuide(Guide guide);

	Guide selectGuide(String guidepostId);

	int likeCount(String guidepostId);

	int updateGuide(Guide guide);

	int deleteGuide(String guidepostId);

	int selectListCount();
	
	int readCount(String guidepostId);
	
	//검색용 메소드---------------------------------------------------------------------------------

	int selectSearchTitleCount(String keyword);

	ArrayList<Guide> selectSearchTitle(Search search);

	int selectSearchContentCount(String keyword);

	ArrayList<Guide> selectSearchContent(Search search);

	int selectSearchLocationCount(String keyword);

	ArrayList<Guide> selectSearchLocation(Search search);

	ArrayList<Guide> selectTop3();

	int updateGuideLikeCount(String postId);





	

}
