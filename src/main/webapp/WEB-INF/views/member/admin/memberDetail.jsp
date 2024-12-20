<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bonvoyage</title>
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
</head>
<body>
	<nav>
		<c:import url="/WEB-INF/views/common/menubar.jsp"/>	
	</nav>
	<br>
	<h1 align="center">회원 상세 조회</h1>
	<br>
	<form action="memberUpdate.do" method="post">
		<input type="hidden" name="memPw" value="${ requestScope.member.memPw }">
		<table align="center" width="1800px" cellSpacing="5" cellpadding="10" border="1">
			<tr colspan="2">
				<th>아이디</th>
				<td><input type="text" class="form-control" style="background-color: #bbbbbb;" name="memId" value="${ requestScope.member.memId }" readonly></td>
				
				<th>이름</th>
				<td><input type="text" class="form-control" name="memName" value="${ requestScope.member.memName }"></td>
				
				<th>닉네임</th>
				<td><input type="text" class="form-control" name="memNickNm" value="${ requestScope.member.memNickNm }"></td>
				
				<th>타입</th>
				<td>
					<c:if test="${ requestScope.member.memType eq 'ADMIN' }">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memType" value="${ requestScope.member.memType }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="관리자" readonly>
					</c:if>
					<c:if test="${ requestScope.member.memType eq 'USER' }">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memType" value="${ requestScope.member.memType }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="유저" readonly>
					</c:if>
				</td>
				<th colspan="2">
					<input type="submit" class="btn btn-outline-success" value="수정하기"> &nbsp;
					
					<c:url var="memberAccountUpdate" value="memberAccountUpdate.do">
						<c:param name="memId" value="${ requestScope.member.memId }" />
					</c:url>
					<c:url var="memberAccountUpdateClear" value="memberAccountUpdateClear.do">
						<c:param name="memId" value="${ requestScope.member.memId }" />
					</c:url>
					<button type="button" class="btn btn-outline-success" onclick="location.href='${memberAccountUpdate}'">계정조치</button> &nbsp;
					<button type="button" class="btn btn-outline-success" onclick="location.href='${memberAccountUpdateClear}'">계정조치 해제</button> &nbsp;
				</th>
			</tr>
			
			<tr>
				<th>전화번호</th>
				<td><input type="tel" class="form-control" name="memPhone" value="${ requestScope.member.memPhone }"></td>
				
				<th>생년월일</th>
				<td><input type="date" class="form-control" name="memBirth" value="${ requestScope.member.memBirth }"></td>
				
				<th>소셜구분</th>
				<td>
					<c:if test="${ requestScope.member.memSocial eq 'COMMON' }">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memSocial" value="${ requestScope.member.memSocial }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="본보야지" readonly>
					</c:if>
					
					<c:if test="${ requestScope.member.memSocial eq 'KAKAO' }">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memSocial" value="${ requestScope.member.memSocial }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="카카오" readonly>
					</c:if>
					
					<c:if test="${ requestScope.member.memSocial eq 'NAVER' }">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memSocial" value="${ requestScope.member.memSocial }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="네이버" readonly>
					</c:if>
					
					<c:if test="${ requestScope.member.memSocial eq 'GOOGLE' }">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memSocial" value="${ requestScope.member.memSocial }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="구글" readonly>
					</c:if>
				</td>
				
				<th>가입날짜</th>
				<td><input type="date" class="form-control" style="background-color: #bbbbbb;" value="${ requestScope.member.memJoinDate }" readonly></td>
				
				<th colspan="2">
					<c:url var="memberAdmin" value="memberAdmin.do">
						<c:param name="memId" value="${ requestScope.member.memId }" />
					</c:url>
					<c:url var="memberAdminDelete" value="memberAdminDelete.do">
						<c:param name="memId" value="${ requestScope.member.memId }" />
					</c:url>
					<button type="button" class="btn btn-outline-success" onclick="location.href='${memberAdmin}'">관리자부여</button> &nbsp;
					<button type="button" class="btn btn-outline-success" onclick="location.href='${memberAdminDelete}'">관리자박탈</button> &nbsp;
				</th>
			</tr>
			
			<tr>
				<th>로그인기록</th>
				<td><input type="date" class="form-control" style="background-color: #bbbbbb;" value="${ requestScope.member.memLoginLog }" readonly></td>
				
				<th>로그아웃기록</th>
				<td><input type="date" class="form-control" style="background-color: #bbbbbb;" value="${ requestScope.member.memLogoutLog }" readonly></td>
				
				<th>사용자상태</th>
				<td>
					<c:if test="${ requestScope.member.memStatus eq 'ACTIVE'}">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memStatus" value="${ requestScope.member.memStatus }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="활동회원" readonly>
					</c:if>
					
					<c:if test="${ requestScope.member.memStatus eq 'BLOCKED'}">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memStatus" value="${ requestScope.member.memStatus }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="정지회원" readonly>
					</c:if>
					
					<c:if test="${ requestScope.member.memStatus eq 'INACTIVE'}">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memStatus" value="${ requestScope.member.memStatus }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="휴면회원" readonly>
					</c:if>
					
					<c:if test="${ requestScope.member.memStatus eq 'LEFT'}">
						<input type="hidden" class="form-control" style="background-color: #bbbbbb;" name="memStatus" value="${ requestScope.member.memStatus }" readonly>
						<input type="text" class="form-control" style="background-color: #bbbbbb;" value="탈퇴회원" readonly>
					</c:if>
					
				</td>
				
				<th>상태적용날짜</th>
				<td><input type="date" class="form-control" style="background-color: #bbbbbb;" value="${ requestScope.member.memStatusDate }" readonly></td>
				
				<th>비밀번호수정일</th>
				<td><input type="date" class="form-control" style="background-color: #bbbbbb;" value="${ requestScope.member.memPwUpdate }" readonly></td>
			</tr>
		</table>
	</form>
	
	<br>
	<footer>
		<c:import url="/WEB-INF/views/common/footer.jsp"/>	
	</footer>
</body>
</html>