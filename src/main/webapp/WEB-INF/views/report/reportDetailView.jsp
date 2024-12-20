<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bonvoyage</title>
<c:url var="reportUpdateProcess" value="reportUpdateProcess.do">
	<c:param name="checkedAdminId" value="${ loginUser.memId }" />
	<c:param name="assignedAdminId" value="${ loginUser.memId }" />
	<c:param name="reportId" value="${ report.reportId }" />
</c:url>

<c:url var="reportUpdateReject" value="reportUpdateReject.do">
	<c:param name="checkedAdminId" value="${ loginUser.memId }" />
	<c:param name="assignedAdminId" value="${ loginUser.memId }" />
	<c:param name="reportId" value="${ report.reportId }" />
</c:url>

<c:url var="reportDelete" value="reportDelete.do">
	<c:param name="reportId" value="${ report.reportId }" />
</c:url>

<!-- <script type="text/javascript">
	//수정 페이지로 이동 버튼 클릭시 작동하는 함수
	function requestUpdateProcess() {
		location.href = "${ reportUpdateProcess }";
	}

	function requestUpdateReject() {
		location.href = "${ reportUpdateReject }";
	}

	//삭제하기 버튼 클릭시 작동하는 함수
	function requestDelete() {
		location.href = "${ reportDelete }";
	}
</script> -->
</head>
<body>
	<c:import url="/WEB-INF/views/common/menubar.jsp" />
	<c:if test="${ loginUser.memType eq 'ADMIN' || loginUser.memId eq report.reportUserId }">
		<hr>
		<h2 align="center">신고 사유 상세보기</h2>
		<br>
		<input type="hidden" name="postId" value="${ report.postId }">
		<input type="hidden" name="title" value="${ report.title }">
		<input type="hidden" name="reportUserId" value="${ report.reportUserId }">
		<input type="hidden" name="reportingReason" value="${ report.reportingReason }">
		<input type="hidden" name="detail" value="${ report.detail }">
		<input type="hidden" name="reportDate" value="${ report.reportDate }">
		<input type="hidden" name="checkedAdmin" value="${ report.checkedAdmin }">
		<input type="hidden" name="checkedAssigned" value="${ report.checkedAssigned }">
		<table align="center" width="700" border="1" cellspacing="0"
			cellpadding="5">
			<tr>
				<th>신고글</th>
				<td>
					<c:if test="${ fn:substring(report.postId, 0, 1) eq 'r' }">
						<a href="${ pageContext.servletContext.contextPath }/routedetail.do?no=${ report.postId }" class="text-dark text-decoration-none">${ report.title }</a>
					</c:if>
					<c:if test="${ fn:substring(report.postId, 0, 1) eq 'g' }">
						<a href="${ pageContext.servletContext.contextPath }/gdetail.do?guidepostId=${ report.postId }" class="text-dark text-decoration-none">${ report.title }</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>신고제목</th>
				<td>${ report.reportingReason }</td>
			</tr>
			<tr>
				<th>신고내용</th>
				<td>${ report.detail }</td>
			</tr>
			<tr>
				<th>등록날짜</th>
				<td><fmt:formatDate value="${ report.reportDate }"
						pattern="yyyy-MM-dd" /></td>
			</tr>
			<tr>
				<th colspan="2">
				<c:if test="${ !empty sessionScope.loginUser and loginUser.memType eq 'ADMIN' }">
						<button onclick="location.href='${reportUpdateProcess}'">처리하기</button>  &nbsp; 
			<button onclick="location.href='${reportUpdateReject}'">반려하기</button>  &nbsp; 
		</c:if>
					<button onclick="location.href='${reportDelete}'">삭제하기</button>
					&nbsp;
					<button onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/reportList.do';">목록</button>
					&nbsp;
					<button onclick="javascript:history.go(-1); return false;">이전
						페이지로 이동</button></th>
			</tr>
		</table>
	</c:if>
	<hr>
	<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>










