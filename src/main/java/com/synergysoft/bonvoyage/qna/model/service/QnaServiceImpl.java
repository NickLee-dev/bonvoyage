package com.synergysoft.bonvoyage.qna.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.notice.model.dto.Notice;
import com.synergysoft.bonvoyage.qna.model.dao.QnaDao;
import com.synergysoft.bonvoyage.qna.model.dto.Qna;

@Service("qnaService")
public class QnaServiceImpl implements QnaService{
	// Dao 생성
	@Autowired
	private QnaDao qnaDao;

	/*
	 * @Override public ArrayList<Qna> selectTopQna() { return
	 * qnaDao.selectTopQna(); }
	 */
	// 전체검색 페이징
	@Override
	public int selectListCount() {
		return qnaDao.selectListCount();
	}
	// 전체검색
	@Override
	public ArrayList<Qna> selectAllQna(Paging paging) {
		return qnaDao.selectAllQna(paging);
	}
	//검색용***************************************************
	@Override
	public int selectSearchTitleListCount(String keyword) {
		return qnaDao.selectSearchTitleListCount(keyword);
	}

	@Override
	public int selectSearchUserContentListCount(String keyword) {
		return qnaDao.selectSearchUserContentListCount(keyword);
	}

	@Override
	public int selectSearchUserIdListCount(String keyword) {
		return qnaDao.selectSearchUserIdListCount(keyword);
	}

	@Override
	public ArrayList<Qna> selectSearchTitleQna(Search search) {
		return qnaDao.selectSearchTitleQna(search);
	}

	@Override
	public ArrayList<Qna> selectSearchUserContentQna(Search search) {
		return qnaDao.selectSearchUserContentQna(search);
	}

	@Override
	public ArrayList<Qna> selectSearchUserIdQna(Search search) {
		return qnaDao.selectSearchUserIdQna(search);
	}
	//검색용***************************************************
	
	// QnA 등록용
	@Override
	public int insertQna(Qna qna) {
		return qnaDao.insertQna(qna);
	}
	
	// QnA 상세보기
	@Override
	public Qna moveSelectQna(String qnaId) {
		return qnaDao.moveSelectQna(qnaId);
	}
	
	// 관리자 상세보기
	@Override
	public int updateAdminQna(Qna qna) {
		return qnaDao.updateAdminQna(qna);
	}
	
	// QnA 수정
	@Override
	public int updateQna(Qna qna) {
		return qnaDao.updateQna(qna);
	}
	@Override
	public int deleteQna(String qnaId) {
		return qnaDao.deleteQna(qnaId);
	}
	
	
	
}
