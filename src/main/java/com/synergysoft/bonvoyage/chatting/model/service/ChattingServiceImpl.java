package com.synergysoft.bonvoyage.chatting.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.chatting.model.dao.ChattingDao;
import com.synergysoft.bonvoyage.chatting.model.dto.Chatting;

@Service
public class ChattingServiceImpl implements ChattingService{
	
	@Autowired
    private ChattingDao chattingDao;

	@Override
	public int insertChatting(Chatting msg) {
		return chattingDao.insertChatting(msg);
	}

	@Override
	public Chatting selectChatRequest(String memberID) {
		return chattingDao.selectChatRequest(memberID);
	}

	@Override
	public int deleteChatting(Chatting chatting) {
		return chattingDao.deleteChatting(chatting);
	}

	@Override
	public int selectReceiver(String memberID) {
		return chattingDao.selectReceiver(memberID);
	}
	

}
