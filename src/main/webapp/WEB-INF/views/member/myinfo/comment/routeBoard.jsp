<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- 현재 페이지 변수 생성 --%>
<c:set var="nowpage" value="1" />
<c:if test="${!empty requestScope.currentPage}">
    <c:set var="nowpage" value="${requestScope.currentPage}" />
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<!-- 부트스트랩 CSS 링크 -->
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<script type="text/javascript" src="/first/resources/js/jquery-3.7.1.min.js"></script>

</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp" />

<div class="container">
    <h1 class="text-center my-4">내가 쓴 댓글(경로추천)</h1>
    <c:if test="${!empty sessionScope.loginUser}">
    <form method="get" action="myRouteBoardCommentSearch.do" class="d-flex mb-4" id="ss" style="float:left">
        <input type="hidden" name="memId" value="${loginUser.memId}">
        <select name="action" id="search" class="form-select w-auto">
            <option value="title" id="title">게시글제목</option>
            <option value="commentContent" id="commentContent">댓글내용</option>
        </select>
        <input type="search" name="keyword" class="form mx-2" placeholder="검색어를 입력해주세요" size="80">
        <button type="submit" class="btn btn-success">검색</button> &nbsp;
        <button type="button" class="btn btn-outline-success" onclick="location.href='myAllComment.do?memId=${loginUser.memId}'">전체 댓글</button> &nbsp;
        <button type="button" class="btn btn-outline-success" onclick="location.href='myRouteBoardComment.do?memId=${loginUser.memId}'">경로추천</button> &nbsp;
        <button type="button" class="btn btn-outline-success" onclick="location.href='myGuideBoardComment.do?memId=${loginUser.memId}'">지역소담이</button> &nbsp;
    </form>
    <div>
        <table class="table table-sm">
            <thead class="table-success">
                <tr class="text-center">
                    <th width="200px">제목</th>
                    <th width="500px">댓글내용</th>
                    <th width="100px">작성자</th>
                    <th width="100px">등록일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.commentList}" var="comment">
                    <tr>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/routedetail.do?no=${comment.postId}" class="text-dark text-decoration-none">
                                ${comment.title}
                            </a>
                        </td>
                        <td> 
                        	<a href="${pageContext.servletContext.contextPath}/routedetail.do?no=${comment.postId}" class="text-dark text-decoration-none">
                        		${comment.commentContent}
                        	</a>
                        </td>
                        <td class="text-center">
                        	<a href="${pageContext.servletContext.contextPath}/memberDetail.do?memId=${comment.memId}" class="text-dark text-decoration-none">
                        		${comment.memNickNm}
                        	</a>
                        </td>
                        <td class="text-center">${comment.createdAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <c:import url="/WEB-INF/views/common/pagingView.jsp" />
</c:if>
</div>
<hr>
    <c:import url="/WEB-INF/views/common/footer.jsp" />
<!-- 부트스트랩 JS 링크 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>