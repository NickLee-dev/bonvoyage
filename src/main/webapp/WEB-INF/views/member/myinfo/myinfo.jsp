<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bonvoyage</title>
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script type="text/javascript" src="/first/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	function validate(){
		var pwdValue = document.getElementById('userPwd').value;
		var pwdValue2 = document.getElementById('userPwd2').value;
		if(pwdValue !== pwdValue2){
			alert('암호와 암호확인이 일치하지 않습니다. 다시 입력하세요.');
			document.getElementById('userPwd').value = '';
			document.getElementById('userPwd2').value = '';
			document.getElementById('userPwd').focus();
			return false;
		}
		return true;
	}  //validate()
</script>
</head>
<body>
	<c:import url="/WEB-INF/views/common/menubar.jsp" />
	<br>
	<div class="container">
		<h1 align="center">내 정보 보기 페이지</h1>
		<br>
		<form action="myinfo/update.do" onsubmit="return validate();" method="post">
		
			<input type="hidden" name="originalMemPw" value="${ requestScope.member.memPw }">
			<div>
				<table class="table table-sm" id="outer" align="center" width="700" cellspacing="5px" cellpadding="0">
					<thead class="table-success">
						<tr>
							<c:if test="${ !empty sessionScope.loginUser and sessionScope.loginUser.memType eq 'ADMIN' }">
								<th colspan="2"><h3><b>[ 관리자 ] ${ loginUser.memNickNm }님 반갑습니다.</b></h3></th>
							</c:if>
							
							<c:if test="${ !empty sessionScope.loginUser and sessionScope.loginUser.memType eq 'USER' }">
								<th colspan="2"><h3><b>[ 유저 ] ${ loginUser.memNickNm }님 반갑습니다.</b></h3></th>
							</c:if>
						</tr>
					</thead>
					<tr>
						<th width="120">이메일</th>
						<td>
							<input type="text" class="form-control" name="memId" id="memId" value="${ requestScope.member.memId }" readonly>
						</td>
					</tr>
					<c:if test="${ !empty sessionScope.loginUser and sessionScope.loginUser.memSocial eq 'COMMON' }">
						<tr>
							<th>비밀번호</th>
							<td><input type="password" class="form-control" name="memPw" id="memPw"></td>
						</tr>
						<tr>
							<th>비밀번호 확인</th>
							<td><input type="password" class="form-control" id="memPwChk"></td>
						</tr>
					</c:if>
					<tr>
						<th>이름</th>
						<td>
							<input type="text" class="form-control" name="memName" id="memName" value="${ requestScope.member.memName }" readonly>				
						</td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td>
							<input type="text" class="form-control" name="memNickNm" id="memNickNm" value="${ requestScope.member.memNickNm }">				
						</td>
					</tr>
					<c:if test="${ !empty sessionScope.loginUser and sessionScope.loginUser.memSocial eq 'COMMON' }">
						<tr>
							<th>생년월일</th>
							<td>
								<input type="date" class="form-control" name="memBirth" value="${ requestScope.member.memBirth }" readonly>
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<input type="tel" class="form-control" name="memPhone" value="${ requestScope.member.memPhone }">
							</td>
						</tr>
					</c:if>
					<tr>
						<th colspan="2">
							<input type="submit" class="btn btn-outline-success" value="수정하기"> &nbsp;
							<c:url var="mdel" value="myinfo/left.do">
								<c:param name="memId" value="${ requestScope.member.memId }" />
							</c:url>
							<button type="button" class="btn btn-outline-success" onclick="location.href='myAllComment.do?memId=${loginUser.memId}'">내가 쓴 댓글</button> &nbsp;
							<a class="btn btn-primary" id="mdel" href="${mdel}">탈퇴하기</a> &nbsp;
							
							<a class="btn btn-primary" href="main.do">Home</a>
						</th>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<br>
	
	<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>