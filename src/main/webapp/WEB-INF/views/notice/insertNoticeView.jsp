<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp"/>	
<br>
<div class="container">
	<h1 align="center">공지사항 등록 페이지</h1>
	<br>
	<form class="container" action="inotice.do" method="post" enctype="multipart/form-data">
		<table class="table">
			<tr>
			<td width="150">제목</td>
			<td><textarea cols="140" rows="2" name="title"></textarea></td>
			</tr>
			<tr>
			<td>작성자</td>
			<td><input type="hidden" name="adminId" value="${ sessionScope.loginUser.memId }" readonly>${ sessionScope.loginUser.memId }</td>
			</tr>
			<tr>
			<td>내용</td>
			<td><textarea cols="140" rows="10" name="content"></textarea></td>
			</tr>
			<tr>
			<td>첨부파일1</td>
			<td><input type="file" name="insertFile1"></td>
			</tr>
			<tr>
			<td>첨부파일2</td>
			<td><input type="file" name="insertFile2"></td>
			</tr>
			<tr>
			<td>첨부파일3</td>
			<td><input type="file" name="insertFile3"></td>
			</tr>
		</table>
		<div class="container text-center">
				<input class="btn btn-success" type="submit" value="입력"> &nbsp;
				<input class="btn btn-success" type="button" value="목록" onclick="javascript:history.go(-1);return false;">
		</div>
	</form>
</div>
<c:import url="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>