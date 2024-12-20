package com.synergysoft.bonvoyage.chatting.model.dto;

import java.sql.Timestamp;

public class Chatting implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2076173561604351179L;
	private String textMessage;
    private String sender; 
    private String receiver; 
    private String connectYN;
    private Timestamp sendTime;
    
    
    
    public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Chatting(Timestamp sendTime) {
		super();
		this.sendTime = sendTime;
	}

	public Chatting() {
		super();
	}

	public Chatting(String textMessage, String sender, String receiver, String connectYN) {
		super();
		this.textMessage = textMessage;
		this.sender = sender;
		this.receiver = receiver;
		this.connectYN = connectYN;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getConnectYN() {
		return connectYN;
	}

	public void setConnectYN(String connectYN) {
		this.connectYN = connectYN;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Chatting [textMessage=" + textMessage + ", sender=" + sender + ", receiver=" + receiver + ", connectYN="
				+ connectYN + ", sendTime=" + sendTime + "]";
	}

	
	
}
