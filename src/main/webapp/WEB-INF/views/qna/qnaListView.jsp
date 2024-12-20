<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<%-- 부트스트랩 css링크 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<%-- jQuery --%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>
</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp" />

<div class="container">
	<div class="h1 text-center m-4">
    Q&A 게시판
	</div>
    <%-- 항목별 검색기능 --%>
    <form method="get" action="ssqna.do" id="ss" class="d-flex mb-2" style="float:left">
        <select name="action" id="search" class="form-select w-auto">
            <option value="title" id="title">제목</option>
            <option value="userContent" id="userContent">내용</option>
            <option value="userId" id="userId">작성자</option>
        </select>
        <input type="search" name="keyword" class="form mx-2" placeholder="검색어를 입력해주세요" size="80">
        <button type="submit" class="btn btn-success">검색</button>
    </form>
    <%-- QnA 입력페이지 이동 --%>
    <c:if test="${!empty sessionScope.loginUser && loginUser.memType ne 'ADMIN'}">
        <div class="text-end">
            <button onclick="window.location.href='miqna.do'" class="btn btn-success">
                Q&A 작성
            </button>
        </div>
    </c:if>    

    <%-- QnA 전체 테이블 --%>
        <table class="table table-sm">
            <thead class="table-success">
                <tr class="text-center">
                    <th width="80px">No</th>
                    <th width="500px">제목</th>
                    <th width="100px">작성자</th>
                    <th width="200px">등록일</th>
                    <th width="100px">비밀글 여부</th>
                    <th width="100px">답변여부</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.list}" var="q">
                    <tr>
                        <td class="text-center">${fn:substring(q.qnaId, 4, -1)}</td>
                        <td class="px-2">
                        	<c:if test="${ q.isSecret eq 'N'
                        				or( q.isSecret eq 'Y' and sessionScope.loginUser.memId eq q.userId)
                        				or( loginUser.memType eq 'ADMIN') }">
	                            <a href="${pageContext.servletContext.contextPath}/msqna.do?qnaId=${q.qnaId}" class="text-dark text-decoration-none">
	                                ${q.title}
	                            </a>
	                        </c:if>
	                        <c:if test="${ (q.isSecret eq 'Y' and sessionScope.loginUser.memId ne q.userId and loginUser.memType ne 'ADMIN')}">
	                                ${q.title}
	                        </c:if>
                        </td>
                        <td class="text-center">${q.userId }</td>
                        <td class="text-center">${q.qCreatedAt}</td>
                        <td class="text-center">
                        	<c:if test="${q.isSecret eq 'Y'}">
                        		<input type="checkbox" checked disabled>
                       		</c:if>
                        	<c:if test="${q.isSecret eq 'N'}">
                        		<input type="checkbox" disabled>
                        	</c:if>
                        </td>
                        <td class="text-center">
                         	<c:if test="${q.isAccept eq 'Y'}">
                        		<input type="checkbox" checked disabled>
                       		</c:if>
                        	<c:if test="${q.isAccept eq 'N'}">
                        		<input type="checkbox" disabled>
                        	</c:if>                       
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    <%-- 페이징 출력 뷰 포함 처리 --%>
    <c:import url="/WEB-INF/views/common/pagingView.jsp" />

</div>

<c:import url="/WEB-INF/views/common/footer.jsp" />

<!-- 부트스트랩 JS 링크 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>