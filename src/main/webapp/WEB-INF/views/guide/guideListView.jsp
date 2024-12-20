<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="nowpage" value="1" />
<c:if test="${ !empty requestScope.currentPage }">
	<c:set var="nowpage" value="${ requestScope.currentPage }" />
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<!-- Bootstrap 5 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>

<style type="text/css">
.card:hover {
    transform: scale(1.05); /* 살짝 확대 */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
    transition: all 0.3s ease; /* 부드러운 전환 효과 */
}
</style>
</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp" />

<br>
<br>
<br>

<h1 class="text-center"> ❝ 지역소담이 테마 블로그 ❞</h1>
<br>
<br>
<br>


<div style="text-align: center; padding-bottom: 20px;">
<h4>지역소담이란 지역의 아름다움과 소소한 매력을 잘 소개해주는 사람이란 의미를 지닌 지역 투어가이드를 말합니다.</h4>
<h4>예쁘고 정갈한 대한민국의 숨은 명소를 서로 공유해보세요!</h4>
</div>

<br>
<br>




<%-- 항목별 검색 기능 추가 --%>

	<%-- 검색 항목별 값 입력 전송용 폼 만들기 --%>
<!-- 통합 검색 폼 -->
<!-- <div style="display: flex; justify-content: flex-end;"> -->
<!-- 목록 버튼은 폼 외부로 -->
<div class="container my-5">
<div class="row">
    <!-- 검색 폼을 왼쪽에 배치 -->
    <div class="col text-end">
        <form action="${pageContext.request.contextPath}/gsearch.do" method="get" style="display: inline-block; margin-right: 10px;"> <!-- 오른쪽 여백 추가 -->
            <fieldset>
                <!-- 검색 기준 선택 -->
                <select name="action" class="form-select" style="width: 150px; display: inline-block;">
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                    <option value="location">지역</option>
                </select>
                <!-- 검색어 입력 -->
                <input type="search" name="keyword" size="50" class="form-control d-inline-block" style="width: auto;" placeholder="제목 또는 내용 또는 지역을 입력해주세요.">
                <!-- 검색 버튼 -->
                <input type="submit" value="검색" class="btn btn-secondary">
            </fieldset>
        </form>
        
        <!-- 목록 버튼을 검색 폼 오른쪽에 배치 -->
       <br>
      <br>
        <button type="button" class="btn btn-outline-secondary mb-4" style="border-radius: 50px; margin-right: 10px;" onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/sagBlog.do?page=1';">목록</button>
        <button type="button" class="btn btn-outline-secondary mb-4"  style="border-radius: 50px; margin-right: 10px;" onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/chattingPage.do';">채팅</button>

    <!-- 블로그 쓰기 버튼 -->
    <button  type="button" class="btn btn-outline-secondary mb-4" onclick="javascript:location.href='${pageContext.servletContext.contextPath}/gmoveWrite.do';">블로그 쓰기</button>
    </div>
</div>

    <!-- 블로그 카드 목록 -->
<div class="row row-cols-1 row-cols-md-3 g-4">
    <!-- 블로그 정보를 반복문으로 출력 -->
    <c:forEach items="${list}" var="g">
        <div class="col">
            
            <div class="card h-100" onclick="location.href='${ pageContext.servletContext.contextPath }/gdetail.do?guidepostId=${ g.guidepostId }'; return false;" style="cursor: pointer;">
                <!-- rFile1이 있으면 해당 이미지를, 없으면 기본 이미지를 출력 -->
                <c:choose>
                    <c:when test="${not empty g.rFile1}">
                    <img src="${pageContext.request.contextPath}/resources/guide_upfiles/${g.rFile1}?v=${System.currentTimeMillis()}" alt="이미지" />
                    
                       <%--  <img src="${pageContext.request.contextPath}/resources/guide_upfiles/${g.rFile1}" class="card-img-top" alt="블로그 이미지" style="max-width: 100%; height: auto;"> --%>
                    </c:when>
                    <c:otherwise>
                        <img src="https://via.placeholder.com/300x150" class="card-img-top" alt="기본 이미지" style="max-width: 100%; height: auto;">
                    </c:otherwise>
                </c:choose>
                <div class="card-body">
                    <h5 class="card-title">${g.guideTitle}</h5>
                    <br>
                    <%-- <h6 class="card-text">지역 : ${g.guideLocation}</h6> --%>
                    <h6 class="card-text">블로그 주인장 : ${g.guideUserId }</h6>
                    <p class="card-text">조회수 : ${g.readCount}</p>
                   <p class="card-text"> ❤️ ${g.likeCount}</p>
                   
                    
    				<div class="text-end">
                    <a href="${pageContext.servletContext.contextPath}/gdetail.do?guidepostId=${g.guidepostId}" class="btn btn-dark">${ g.guideLocation }</a>
                	</div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

</div>

 

<!-- Bootstrap 5 JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
<%-- 페이징 출력 뷰 포함 처리 --%>
	<c:import url="/WEB-INF/views/common/pagingView.jsp" />
	
	<hr>
	<c:import url="/WEB-INF/views/common/footer.jsp" />
	
	
</body>
</html>
