<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<style type="text/css">
td.insert {
width:100px;
text-align:center;
}
body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 20px;
}

h1 {
    color: #333;
    margin-bottom: 20px;
}

table {
    width: 80%;
    margin: auto;
    border-collapse: collapse;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

td {
    padding: 10px;
    border: 1px solid #ddd;
}

.insert {
    background-color: #f0f0f0;
    font-weight: bold;
    text-align: right;
    padding-right: 10px;
}

input[type="text"],
textarea,
input[type="file"] {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box; /* 패딩이 전체 너비에 영향을 주지 않도록 함 */
}

input[type="text"]:focus,
textarea:focus,
input[type="file"]:focus {
    border-color: #007BFF; /* 포커스 시 강조 표시 */
}

input[type="submit"] {
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 10px 15px;
    cursor: pointer;
    font-size: 16px;
}

input[type="submit"]:hover {
    background-color: #0056b3; /* 호버 시 더 어두운 색상 */
}

textarea {
    resize: vertical; /* 세로로만 크기 조정 가능 */
}

@media (max-width: 600px) {
    table {
        width: 100%;
    }

    input[type="text"],
    textarea {
        font-size: 14px; /* 작은 화면에서 글자 크기 조정 */
    }
}

</style>
</head>
<body>
<nav>
<c:import url="/WEB-INF/views/common/menubar.jsp"/>	
</nav>
<br>
	<h1 align="center">신고 등록 페이지</h1>
	<br>
	<form action="reportInsert.do" method="post">
		<table border="1" align="center">
		
			<tr>
				<td class="insert" >제목</td>
				<td>
				<c:if test="${fn:contains(param.routeBoardId, 'route_')}">
					<input type="hidden" name="postId" value="${ route.routeBoardId }" readonly>
					<input type="text" value="${ route.title }" readonly>
				</c:if>
				<c:if test="${fn:contains(param.guidepostId, 'guide_')}">
					<input type="hidden" name="postId" value="${ guide.guidepostId }" readonly>
					<input type="text" value="${ guide.guideTitle }" readonly>
				</c:if>
				</td>
			</tr>

			<tr>
				<td class="insert" >작성자</td>
				<td>
					<input type="hidden" name="reportUserId" value="${ sessionScope.loginUser.memId }">
					<input type="text" value="${ sessionScope.loginUser.memNickNm }" readonly>
				</td>
			</tr>
			
			<tr>
				<td class="insert">신고내용</td>
				<td><input type="text" name="reportingReason" required></td>
			</tr>
			
			<tr>
				<td class="insert">신고상세내용</td>
				<td><textarea name="detail" cols="100" rows="10" required></textarea></td>
			</tr>
			
			<tr><td colspan="2" align="center"><input type="submit" value="입력"></td></tr>
		</table>
	</form>
<footer>
<c:import url="/WEB-INF/views/common/footer.jsp"/>	
</footer>
</body>
</html>