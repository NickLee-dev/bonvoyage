package com.synergysoft.bonvoyage.common;

public class Paging implements java.io.Serializable{

	private static final long serialVersionUID = -152292818359704529L;
	
	private int startRow;		// 해당 페이지에 출력할 시작행 (쿼리문에 적용)
	private int endRow;			// 해당 페이지에 출력할 끝행 (쿼리문에 적용)
	private int listCount;		// 총 목록 갯수 (뷰페이지에서 사용)
	private int limit;			// 한 페이지에 출력할 갯수(뷰페이지에서 사용)
	private int currentPage;	// 출력할 현재 페이지(뷰페이지에서 사용)
	private int maxPage;		// 총 페이지 수 (마지막 페이지) (뷰페이지에서 사용)
	private int startPage;		// 헌재 페이지가 속한 페이지 그룹의 시작값 (뷰페이지에서 사용)
	private int endPage;		// 현재 페이지가 속한 페이지 구룹의 끝값 (뷰페이지에서 사용)
	private String urlMapping;	// 페이지 숫자 클릭시 요청할 url 저장용(검색기능 사용시 요청할 url 이 달라서 필요함) (뷰페이지에서 사용)
	private int groupLimit;		// 페이징 그룹 갯수 ( 뷰페이지에서 사용 )

	
	// 기본생성자 불필요
	
	// 매개변수 4개 있는 생성자
	
	public Paging(int listCount, int limit, int currentPage, String urlMapping, int groupLimit) {
		super();
		this.listCount = listCount;
		this.limit = limit;
		this.currentPage = currentPage;
		this.urlMapping = urlMapping;
		this.groupLimit = groupLimit;
	}
	
	// 페이지 계산 메소드
	public void calculate() {
		// 총 페이지 수 계산 : 
		this.maxPage = (int)((double)listCount / limit + 0.9); 
		
		// 뷰 페이지 출력에 사용할 페이지 그릅의 시작값 지정
		// 뷰 시작페이지
		this.startPage = (int)(((double)currentPage / groupLimit + 0.9)-1 ) * groupLimit + 1;
		// 뷰 종료페이지
		this.endPage = startPage + groupLimit - 1;
		
		// 마지막 그룹의 끝값을 마지막 페이지와 맞춤
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		// 요청한 현재 페이지에 출력될 목록의 행 번호를 계산
		// 시작 게시물
		this.startRow = (currentPage-1) * limit + 1;
		// 종료 게시물
		this.endRow = startRow + limit - 1;		
	}
	



	// setters and getters
	public int getStartRow() {
		return startRow;
	}


	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}


	public int getEndRow() {
		return endRow;
	}


	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}


	public int getListCount() {
		return listCount;
	}


	public void setListCount(int listCount) {
		this.listCount = listCount;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getMaxPage() {
		return maxPage;
	}


	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}


	public int getStartPage() {
		return startPage;
	}


	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}


	public int getEndPage() {
		return endPage;
	}


	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}


	public String getUrlMapping() {
		return urlMapping;
	}


	public void setUrlMapping(String urlMapping) {
		this.urlMapping = urlMapping;
	}


	public int getGroupLimit() {
		return groupLimit;
	}


	public void setGroupLimit(int groupLimit) {
		this.groupLimit = groupLimit;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// toString

	@Override
	public String toString() {
		return "Paging [startRow=" + startRow + ", endRow=" + endRow + ", listCount=" + listCount + ", limit=" + limit
				+ ", currentPage=" + currentPage + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage="
				+ endPage + ", urlMapping=" + urlMapping + ", groupLimit=" + groupLimit + "]";
	}
}
