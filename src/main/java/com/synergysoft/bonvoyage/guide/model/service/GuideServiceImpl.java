package com.synergysoft.bonvoyage.guide.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.guide.model.dao.GuideDao;
import com.synergysoft.bonvoyage.guide.model.dto.Guide;
import com.synergysoft.bonvoyage.notice.model.dto.Notice;


@Service("guideService")
public class GuideServiceImpl implements GuideService{
	
	// 가이브 블로그 메인화면 전체화면 출력용 서비스
	@Autowired
	private GuideDao guideDao;

	@Override
	public ArrayList<Guide> selectAllGuide(Paging paging) {
		return guideDao.selectAllGuide(paging);
	}
	@Override
	public int insertGuide(Guide guide) {
		return guideDao.insertGuide(guide);
	}

	@Override
	public Guide selectGuide(String guidepostId) {
		return guideDao.selectGuide(guidepostId);
	}

	@Override
	public int likeCount(String guidepostId) {
		return guideDao.likeCount(guidepostId);
	}

	@Override
	public int updateGuide(Guide guide) {
		return guideDao.updateGuide(guide);
	}

	@Override
	public int deleteGuide(String guidepostId) {
		return guideDao.deleteGuide(guidepostId);
	}

	@Override
	public int selectListCount() {
		return guideDao.selectListCount();
	}
	
	//검색용 메소드--------------------------------------------------
	@Override
	public int selectSearchTitleCount(String keyword) {
		return guideDao.selectSearchTitleCount(keyword);
	}
	@Override
	public ArrayList<Guide> selectSearchTitle(Search search) {
		return guideDao.selectSearchTitle(search);
	}
	@Override
	public int selectSearchContentCount(String keyword) {
		return guideDao.selectSearchContentCount(keyword);
	}
	@Override
	public ArrayList<Guide> selectSearchContent(Search search) {
		return guideDao.selectSearchContent(search);
	}
	@Override
	public int selectSearchLocationCount(String keyword) {
		return guideDao.selectSearchLocationCount(keyword);
	}
	@Override
	public ArrayList<Guide> selectSearchLocation(Search search) {
		return guideDao.selectSearchLocation(search);
	}
	//메인 조회수 top3
	@Override
	public ArrayList<Guide> selectTop3() {
		return guideDao.selectTop3();
	}
	@Override
	public int readCount(String guidepostId) {
		return guideDao.readCount(guidepostId);
	}
	@Override
	public int updateGuideLikeCount(String postId) {
		return guideDao.updateGuideLikeCount(postId);
	}

	







}
