package com.synergysoft.bonvoyage.chatting.model.service;

import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.chatting.model.dto.Chatting;

@Service
public interface ChattingService {

	int insertChatting(Chatting msg);

	Chatting selectChatRequest(String memberID);
	
	int deleteChatting(Chatting chatting);

	int selectReceiver(String memberID);
}
