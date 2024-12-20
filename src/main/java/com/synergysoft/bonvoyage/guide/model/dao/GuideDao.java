package com.synergysoft.bonvoyage.guide.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.guide.model.dto.Guide;

@Repository("guideDao")
public class GuideDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public ArrayList<Guide> selectAllGuide(Paging paging){
		List<Guide> list = sqlSessionTemplate.selectList("guideMapper.selectAllGuide", paging);
		return (ArrayList<Guide>)list;
		
	}


	public int insertGuide(Guide guide) {
		return sqlSessionTemplate.insert("guideMapper.insertGuide", guide);
	}


	public Guide selectGuide(String guidepostId) {
		return sqlSessionTemplate.selectOne("guideMapper.selectGuide", guidepostId);
	}


	public int likeCount(String guidepostId) {
		return sqlSessionTemplate.update("guideMapper.likeCount", guidepostId);
	}


	public int updateGuide(Guide guide) {
		return sqlSessionTemplate.update("guideMapper.updateGuide", guide);
	}


	public int deleteGuide(String guidepostId) {
		return sqlSessionTemplate.delete("guideMapper.deleteGuide", guidepostId);
	}


	public int selectListCount() {
		return sqlSessionTemplate.selectOne("guideMapper.selectListCount");
	}

// 검색용 메소드----------------------------------------------------------------
	public int selectSearchTitleCount(String keyword) {
		return sqlSessionTemplate.selectOne("guideMapper.selectSearchTitleCount", keyword);
	}


	public ArrayList<Guide> selectSearchTitle(Search search) {
		List<Guide> list = sqlSessionTemplate.selectList("guideMapper.selectSearchTitle", search);
		return (ArrayList<Guide>)list;
	}


	public int selectSearchContentCount(String keyword) {
		return sqlSessionTemplate.selectOne("guideMapper.selectSearchContentCount", keyword);
	}


	public ArrayList<Guide> selectSearchContent(Search search) {
		List<Guide> list = sqlSessionTemplate.selectList("guideMapper.selectSearchContent", search);
		return (ArrayList<Guide>)list;
	}


	public int selectSearchLocationCount(String keyword) {
		return sqlSessionTemplate.selectOne("guideMapper.selectSearchLocationCount", keyword);
	}


	public ArrayList<Guide> selectSearchLocation(Search search) {
		List<Guide> list = sqlSessionTemplate.selectList("guideMapper.selectSearchLocation", search);
		return (ArrayList<Guide>)list;
	}


	public ArrayList<Guide> selectTop3() {
		List<Guide> list = sqlSessionTemplate.selectList("guideMapper.selectTop3");
		return (ArrayList<Guide>)list;
	}


	public int readCount(String guidepostId) {
		return sqlSessionTemplate.update("guideMapper.readCount", guidepostId);
	}


	public int updateGuideLikeCount(String postId) {
		return sqlSessionTemplate.update("guideMapper.updateGuideLikeCount", postId);
	}






	
	



}//EndGuidDao
