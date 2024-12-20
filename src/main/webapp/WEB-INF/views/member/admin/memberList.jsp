<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="nowpage" value="1" />
<c:if test="${ !empty requestScope.currentPage }">
	<c:set var="nowpage" value="${ requestScope.currentPage }" />
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bon voyage</title>
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="/first/resources/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<nav>
<c:import url="/WEB-INF/views/common/menubar.jsp" />
</nav>
<br>
<h1 align="center">회원 리스트</h1>
<div class="container">
	<c:if test="${ !empty sessionScope.loginUser and loginUser.memType eq 'ADMIN' }">
		<br>
		<fieldset align="center">
			<form method="get" action="memberListSearch.do" class="d-flex mb-4" id="ss" style="float:left">
		        <select name="memStatus" id="searchItem" class="form-select w-auto">
		            <option value="ALL" id="all">전체회원</option>
		            <option value="ACTIVE" id="active">활동회원</option>
		            <option value="BLOCKED" id="blocked">정지회원</option>
		            <option value="INACTIVE" id="inactive">휴면회원</option>
		        </select>
		        <select name="action" id="searchItem" class="form-select w-auto">
		            <option value="memId" id="memId">아이디</option>
		            <option value="memNickNm" id="memNickNm">닉네임</option>
		            <option value="memName" id="memName">이름</option>
		        </select>
		        <input type="search" name="keyword" class="form-control d-inline-block" style="width: auto;" placeholder="검색어를 입력해주세요" size="80">
		        <button type="submit" class="btn btn-success">검색</button>
		    </form>
	    </fieldset>
	    <div class="container">
		<div class="row row-cols-1 row-cols-md-4 g-5">
		<c:forEach items="${memberList}" var="memberList">
			<div class="col">
				<div class="card text-center" style="width: 18rem; background: white;">
					<div class="card-body">
						<h5 class="card-title">${ memberList.memNickNm }</h5>
						<p class="card-text">${ memberList.memId }</p>
						<a href="${pageContext.servletContext.contextPath}/memberDetail.do?memId=${ memberList.memId }" class="btn btn-primary">상세보기</a>
					</div>
				</div>
			</div>
		</c:forEach>
		</div>
		</div>
	</c:if>
</div>

<!-- Bootstrap 5 JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>

<c:if test="${ empty sessionScope.loginUser or loginUser.memType eq 'USER' }">
	<% response.sendRedirect("main.do"); %>
</c:if>
<c:import url="/WEB-INF/views/common/pagingView.jsp"/>
<br>
<footer>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</footer>
</body>
</html>