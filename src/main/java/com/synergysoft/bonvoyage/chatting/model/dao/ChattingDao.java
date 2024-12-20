package com.synergysoft.bonvoyage.chatting.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.chatting.model.dto.Chatting;

@Repository
public class ChattingDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public int insertChatting(Chatting msg) {
		return sqlSessionTemplate.insert("chatting.insertChatting", msg);
	}

	public Chatting selectChatRequest(String memberID) {
		return sqlSessionTemplate.selectOne("chatting.selectChatRequest", memberID);
	}

	public int deleteChatting(Chatting chatting) {
		return sqlSessionTemplate.delete("chatting.deleteChatting", chatting);
	}

	public int selectReceiver(String memberID) {
		return sqlSessionTemplate.selectOne("chatting.selectReceiver", memberID);
	}

}
