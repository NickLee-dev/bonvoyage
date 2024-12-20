package com.synergysoft.bonvoyage.qna.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.notice.model.dto.Notice;
import com.synergysoft.bonvoyage.qna.model.dto.Qna;

@Repository("qnaDao")
public class QnaDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/*
	 * public ArrayList<Qna> selectTopQna() { List<Qna> list =
	 * sqlSessionTemplate.selectList("qnaMapper.selectTopQna"); return
	 * (ArrayList<Qna>)list; }
	 */
	// 전체검색 페이징
	public int selectListCount() {
		return sqlSessionTemplate.selectOne("qnaMapper.selectListCount");
	}
	// 전체리스트
	public ArrayList<Qna> selectAllQna(Paging paging) {
		List<Qna> list = sqlSessionTemplate.selectList("qnaMapper.selectAllQna",paging);
		return (ArrayList<Qna>)list;
	}
	// 검색용**************************************************
	public int selectSearchTitleListCount(String keyword) {
		return sqlSessionTemplate.selectOne("qnaMapper.selectSearchTitleListCount",keyword);
	}

	public int selectSearchUserContentListCount(String keyword) {
		return sqlSessionTemplate.selectOne("qnaMapper.selectSearchUserContentListCount",keyword);
	}

	public int selectSearchUserIdListCount(String keyword) {
		return sqlSessionTemplate.selectOne("qnaMapper.selectSearchUserIdListCount",keyword);
	}

	public ArrayList<Qna> selectSearchTitleQna(Search search) {
		List<Qna> list = sqlSessionTemplate.selectList("qnaMapper.selectSearchTitleQna",search);
		return (ArrayList<Qna>)list;
	}

	public ArrayList<Qna> selectSearchUserContentQna(Search search) {
		List<Qna> list = sqlSessionTemplate.selectList("qnaMapper.selectSearchUserContentQna",search);
		return (ArrayList<Qna>)list;
	}

	public ArrayList<Qna> selectSearchUserIdQna(Search search) {
		List<Qna> list = sqlSessionTemplate.selectList("qnaMapper.selectSearchUserIdQna",search);
		return (ArrayList<Qna>)list;
	}
	// 검색용**************************************************
	
	// qna 입력용
	public int insertQna(Qna qna) {
		return sqlSessionTemplate.insert("qnaMapper.insertQna", qna);
	}
	
	// qna 상세보기
	public Qna moveSelectQna(String qnaId) {
		return sqlSessionTemplate.selectOne("qnaMapper.moveSelectQna",qnaId);
	}
	
	// qna 관리자 답변
	public int updateAdminQna(Qna qna) {
		return sqlSessionTemplate.update("qnaMapper.updateAdminQna",qna);
	}
	
	// qna 수정
	public int updateQna(Qna qna) {
		return sqlSessionTemplate.update("qnaMapper.updateQna",qna);
	}
	public int deleteQna(String qnaId) {
		return sqlSessionTemplate.update("qnaMapper.deleteQna",qnaId);
	}
}
