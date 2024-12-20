package com.synergysoft.bonvoyage.notice.model.service;

import java.util.ArrayList;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.notice.model.dto.Notice;


public interface NoticeService {
	
	ArrayList<Notice> selectAllNotice(Paging paging);
	int insertNotice(Notice notice);
	Notice selectDetailNotice(String noticeId);
	int selectListCount();
	int updateNotice(Notice notice);
	int deleteNotice(Notice notice);
	int selectSearchTitleListCount(String keyword);
	ArrayList<Notice> selectSearchTitleNotice(Search search);
	int selectSearchContentListCount(String keyword);
	ArrayList<Notice> selectSearchContentNotice(Search search);
	ArrayList<Notice> selectTopNotice();
	int updateReadCount(String noticeId);

}
