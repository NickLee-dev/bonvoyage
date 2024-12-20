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
<script>
    // 파일 선택 시 이름을 업데이트하는 함수 (파일마다 구분)
    function updateFileName(fileNumber, input) {
        if (input.files[0]) {
            var fileName = input.files[0].name;
            document.getElementById('fileName' + fileNumber).textContent = fileName;
            // 파일이 선택되면 삭제 버튼을 보이게 함
            document.getElementById('deleteButton' + fileNumber).style.display = 'inline';
        }
    }

    // 파일 삭제 버튼을 눌렀을 때 처리하는 함수 (파일마다 구분)
    function deleteFile(fileNumber) {
        // 해당 파일 입력 필드를 초기화
        document.getElementById('insertFile' + fileNumber).value = null; 
        
        // 파일명을 빈 칸으로 설정
        document.getElementById('fileName' + fileNumber).textContent = null; 
        
        // 삭제 버튼을 다시 숨김
        document.getElementById('deleteButton' + fileNumber).style.display = 'none';
        
     	// hidden 필드에 삭제 여부 설정
        document.getElementById('delete' + fileNumber).value = 'yes';
    }
</script>
</head>
<body>

<c:import url="/WEB-INF/views/common/menubar.jsp"/>	

<br>
<div class="container">
<div class="h2 text-center mt-2 mb-5">
	공지사항 수정 페이지
</div>
<form action="unotice.do" method="post" enctype="multipart/form-data">

<%-- 추가 input필요 데이터 --%>
<input type="hidden" name="noticeId" value="${ notice.noticeId }">
<input type="hidden" name="updateCheck" value="Y">
<input type="hidden" name="oFile1" value="${notice.oFile1}">
<input type="hidden" name="oFile2" value="${notice.oFile2}">
<input type="hidden" name="oFile3" value="${notice.oFile3}">
<input type="hidden" name="rFile1" value="${notice.rFile1}">
<input type="hidden" name="rFile2" value="${notice.rFile2}">
<input type="hidden" name="rFile3" value="${notice.rFile3}">
<%-- 삭제 여부를 위한 hidden 필드 추가 --%>
<input type="hidden" id="delete1" name="delete1" value="">
<input type="hidden" id="delete2" name="delete2" value="">
<input type="hidden" id="delete3" name="delete3" value="">

<table class="table">
<tr><th class="text-center">제 목</th>
<td colspan="3">
	<textarea name="title" cols="140" rows="2" >${ notice.title }</textarea>
</td></tr>
<tr>
	<th class="text-center" width="200px">작성자</th>
	<td width="200px"><input type="hidden" name="adminId" readonly value="${notice.adminId}">${notice.adminId}</td>
	<th class="text-center" width="200px">게시일</th>
	<td width="200px"><input type="hidden" name="createdAt" readonly value="${notice.createdAt}">${notice.createdAt}</td>
</tr>
<tr>
	<th class="text-center" >내용</th>
	<td colspan="3">
		<textarea name="content" rows="20" cols="140">${notice.content}</textarea>
	</td>
</tr>
<tr>
    <th class="text-center">첨부파일1</th>
    <td colspan="3">
        <!-- 실제 파일 선택 input은 숨김 -->
        <input type="file" id="insertFile1" name="insertFile1" style="display: none;" onchange="updateFileName(1, this)">
        <!-- 파일 선택 버튼 -->
        <button type="button" onclick="document.getElementById('insertFile1').click()">파일 선택</button>
        <!-- 파일명 표시 -->
        <span id="fileName1">${notice.oFile1}</span> &nbsp;
        <!-- 삭제 버튼, 기존 파일이 있을 경우 표시 -->
        <input type="button" id="deleteButton1" name="delete1" value="x" onclick="deleteFile(1)" style="display:${!empty notice.oFile1 ? 'inline' : 'none'};">
    </td>
</tr>
<tr>
    <th class="text-center">첨부파일2</th>
    <td colspan="3">
        <!-- 실제 파일 선택 input은 숨김 -->
        <input type="file" id="insertFile2" name="insertFile2" style="display: none;" onchange="updateFileName(2, this)">
        <!-- 파일 선택 버튼 -->
        <button type="button" onclick="document.getElementById('insertFile2').click()">파일 선택</button>
        <!-- 파일명 표시 -->
        <span id="fileName2">${notice.oFile2}</span> &nbsp;
        <!-- 삭제 버튼, 기존 파일이 있을 경우 표시 -->
        <input type="button" id="deleteButton2" name="delete2" value="x" onclick="deleteFile(2)" style="display:${!empty notice.oFile2 ? 'inline' : 'none'};">
    </td>
</tr>
<tr>
    <th class="text-center">첨부파일3</th>
    <td colspan="3">
        <!-- 실제 파일 선택 input은 숨김 -->
        <input type="file" id="insertFile3" name="insertFile3" style="display: none;" onchange="updateFileName(3, this)">
        <!-- 파일 선택 버튼 -->
        <button type="button" onclick="document.getElementById('insertFile3').click()">파일 선택</button>
        <!-- 파일명 표시 -->
        <span id="fileName3">${notice.oFile3}</span> &nbsp;
        <!-- 삭제 버튼, 기존 파일이 있을 경우 표시 -->
        <input type="button" id="deleteButton3" name="delete3" value="x" onclick="deleteFile(3)" style="display:${!empty notice.oFile3 ? 'inline' : 'none'};">
    </td>
</tr>
</table>
<div class="text-center">
<input class="btn btn-success" type="submit" value="수정"> &nbsp;
<button class="btn btn-success" onclick="javascript:history.go(-1); return false;">수정취소</button> &nbsp;
<button class="btn btn-success" onclick="javascript:location.href='${pageContext.servletContext.contextPath}/sanotice.do'; return false;">목록</button>
</div>
</form>		
</div>

<c:import url="/WEB-INF/views/common/footer.jsp"/>	

</body>
</html>