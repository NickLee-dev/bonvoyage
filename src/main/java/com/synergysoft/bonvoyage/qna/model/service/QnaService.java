package com.synergysoft.bonvoyage.qna.model.service;

import java.util.ArrayList;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.notice.model.dto.Notice;
import com.synergysoft.bonvoyage.qna.model.dto.Qna;

public interface QnaService {

//	ArrayList<Qna> selectTopQna();
	int selectListCount();
	ArrayList<Qna> selectAllQna(Paging paging);
	int selectSearchTitleListCount(String keyword);
	int selectSearchUserContentListCount(String keyword);
	int selectSearchUserIdListCount(String keyword);
	ArrayList<Qna> selectSearchTitleQna(Search search);
	ArrayList<Qna> selectSearchUserContentQna(Search search);
	ArrayList<Qna> selectSearchUserIdQna(Search search);
	int insertQna(Qna qna);
	Qna moveSelectQna(String qnaId);
	int updateAdminQna(Qna qna);
	int updateQna(Qna qna);
	int deleteQna(String qnaId);

}
