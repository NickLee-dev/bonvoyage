<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
.card:hover {
    transform: scale(1.05); /* 살짝 확대 */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
    transition: all 0.3s ease; /* 부드러운 전환 효과 */
}
</style>
</head>
<body>
<nav>
	<c:import url="/WEB-INF/views/common/menubar.jsp"/>
</nav>
<br>
<div class="container">
<h2 align="center">여행, 이렇게 떠나보세요</h2>
<br>
<h4 align="center">나만의 특별한 여행경로를 추천합니다</h4>
<br>



<br>
<div class="row">
    <%-- 항목별 검색기능 --%>
    <div class="col">
    <form method="get" action="sroute.do" class="d-flex mb-2" id="ss" style="float:left">
        <select name="action" id="search" class="form-select w-auto">
            <option value="title" id="title">제목</option>
            <option value="content" id="content">내용</option>
            <option value="userId" id="userId">작성자</option>
        </select>
        <input type="search" name="keyword" class="form mx-2" placeholder="검색어를 입력해주세요" size="80">
        <button type="submit" class="btn btn-success" style="width: 70px;">검색</button>
        <%-- 글쓰기 버튼 - 로그인시 표시 --%>
    </form>
    </div>
         <div class="col text-end align-middle">
	    	<c:if test="${ !empty loginUser }">
		    		<button class="btn btn-success align-middle" onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/moveWriteRoute.do';">글쓰기</button>
			</c:if>
		</div>
</div>

<div class="row row-cols-1 row-cols-md-3 g-4">
	<c:forEach items="${ requestScope.list }" var="r">
		<div class="col">
			<div class="card h-100" onclick="location.href='${ pageContext.servletContext.contextPath }/routedetail.do?no=${ r.routeBoardId }'; return false;" style="cursor: pointer;">
				<c:choose>
					<c:when test="${ not empty r.rfile1 }">
						<img src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ r.rfile1 }">
					</c:when>
					<c:otherwise>
						<img src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="max-width: 100%; height: auto;">
					</c:otherwise>
				</c:choose>
				<div	class="card-body">
					<h5 class="card-title">${ r.title }</h5>
					<p class="card-text"><strong>[교통수단]</strong> ${ r.transport }</p>
					<p class="card-text"><strong>[작성자]</strong> ${ r.userId }</p>
					<p class="card-text"><strong>[조회수]</strong> ${ r.readCount }</p>
					<p class="card-text">❤️ ${ r.likeCount }</p>
				</div>
			</div>
		</div>
	</c:forEach>
</div>





















<%-- <table class="table table-sm">
	<thead class="table-success">
    <tr class="text-center">
        <th width="70">순 번</th>
        <th widht="500">제 목</th>
        <th width="250">작성자</th>
        <th width="200">작성일자</th>
        <th width="100">좋아요</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ requestScope.list }" var="r">
        <tr>
            <td align="center">${fn:substring(r.routeBoardId,6,-1)}</td>
            <td align="left"><a style="text-decoration: none; color: black;" href="${ pageContext.servletContext.contextPath }/routedetail.do?no=${ r.routeBoardId }"> &nbsp; ${ r.title }</a></td>
            <td align="center">${ r.userId }</td>
            <td align="center">${ r.createdAt }</td>
            <td align="center">${ r.likeCount }</td>
        </tr>
    </c:forEach>
    </tbody>
</table> --%>

</div>
    <%-- 페이징 출력 뷰 포함 처리 --%>
    <c:import url="/WEB-INF/views/common/pagingView.jsp" />
<footer>
<c:import url="/WEB-INF/views/common/footer.jsp"/>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>