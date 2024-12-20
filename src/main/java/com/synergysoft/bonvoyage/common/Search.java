package com.synergysoft.bonvoyage.common;

import java.sql.Date;

public class Search {
	
	// 회원에서 필요하여 선언
	private String action;	// 검색할 카테고리 지정용
	
	private String keyword; // 제목, 내용 검색용
	private int startRow;	// 한페이지에 출력할 목록의 시작행
	private int endRow;		// 한페이지에 출력할 목록의 끝행
	private Date begin;		// 등록날짜 검색의 시작날짜
	private	Date end;		// 등록날짜 검색의 끝날짜
	private int age;		// 회원 관리에서 연령대별 검색에서의 나이

	

	public Search() {}
	
	public String getAction() { // 회원에서 필요하여 선언
		return action;
	}

	public void setAction(String action) { // 회원에서 필요하여 선언
		this.action = action;
	}
	
	public String getKeyword() { 
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	@Override
	public String toString() {
		return "Search [action=" + action + ",keyword=" + keyword + ", startRow=" + startRow + ", endRow=" + endRow + ", begin=" + begin
				+ ", end=" + end + ", age=" + age + "]";
	}

	
}



