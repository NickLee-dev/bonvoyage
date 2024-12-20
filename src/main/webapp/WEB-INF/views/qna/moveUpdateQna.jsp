<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<script type="text/javascript">

	// 비밀글여부 체크 옵션
	function setSecretValue(checkbox){
		const hiddenField = document.querySelector('input[name="isSecret"]');
		hiddenField.value = checkbox.checked ? 'Y' : 'N' ;
	}

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
<c:import url="/WEB-INF/views/common/menubar.jsp" />
<br>
<div class="container">
<div class="h1 text-center my-2">
	Q&A 수정 페이지</div>
	<br>
	<form action="uqna.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="qnaId" value="${ requestScope.qna.qnaId }">
		<input type="hidden" name="oFile1" value="${qna.oFile1}">
		<input type="hidden" name="oFile2" value="${qna.oFile2}">
		<input type="hidden" name="oFile3" value="${qna.oFile3}">
		<input type="hidden" name="oFile4" value="${qna.oFile4}">
		<input type="hidden" name="oFile5" value="${qna.oFile5}">
		<input type="hidden" name="rFile1" value="${qna.rFile1}">
		<input type="hidden" name="rFile2" value="${qna.rFile2}">
		<input type="hidden" name="rFile3" value="${qna.rFile3}">
		<input type="hidden" name="rFile4" value="${qna.rFile4}">
		<input type="hidden" name="rFile5" value="${qna.rFile5}">
		<%-- 삭제 여부를 위한 hidden 필드 추가 --%>
		<input type="hidden" id="delete1" name="delete1" value="">
		<input type="hidden" id="delete2" name="delete2" value="">
		<input type="hidden" id="delete3" name="delete3" value="">
		<input type="hidden" id="delete4" name="delete4" value="">
		<input type="hidden" id="delete5" name="delete5" value="">
		
		
		<table class="table">
			<tr>
			<td class="text-center" >제목</td>
			<td><textarea cols="140" rows="2" name="title">${ requestScope.qna.title }</textarea></td>
			</tr>
			<tr>
			<td class="text-center" width="200px">작성자</td>
			<td><input type="hidden" name="userId" value="${ requestScope.qna.userId }" readonly>${ requestScope.qna.userId }</td>
			</tr>
			<tr>
			<td class="text-center">비밀글 여부</td>
			<td>
				<c:if test="${ requestScope.qna.isSecret == 'Y' }">
					<input type="hidden" name="isSecret" value="Y">
					<input type="checkbox" id="isSecretCheckbox" value="Y"  checked onclick="setSecretValue(this)">
				</c:if>
				<c:if test="${ requestScope.qna.isSecret == 'N' }">
					<input type="hidden" name="isSecret" value="N">
					<input type="checkbox" id="isSecretCheckbox" value="N" onclick="setSecretValue(this)">
				</c:if>
			</td>
			</tr>
			<tr>
			<td class="text-center">내용</td>
			<td><textarea cols="140" rows="10" name="userContent">${ requestScope.qna.userContent }</textarea></td>
			</tr>
			<tr>
			    <td class="text-center">첨부파일1</td>
			    <td>
			        <!-- 실제 파일 선택 input은 숨김 -->
			        <input type="file" id="insertFile1" name="insertFile1" style="display: none;" onchange="updateFileName(1, this)">
			        <!-- 파일 선택 버튼 -->
			        <button type="button" onclick="document.getElementById('insertFile1').click()">파일 선택</button>
			        <!-- 파일명 표시 -->
			        <span id="fileName1">${qna.oFile1}</span> &nbsp;
			        <!-- 삭제 버튼, 기존 파일이 있을 경우 표시 -->
			        <input type="button" id="deleteButton1" name="delete1" value="x" onclick="deleteFile(1)" style="display:${!empty qna.oFile1 ? 'inline' : 'none'};">
			    </td>
			</tr>
			<tr>
			    <td class="text-center">첨부파일2</td>
			    <td>
			        <!-- 실제 파일 선택 input은 숨김 -->
			        <input type="file" id="insertFile2" name="insertFile2" style="display: none;" onchange="updateFileName(2, this)">
			        <!-- 파일 선택 버튼 -->
			        <button type="button" onclick="document.getElementById('insertFile2').click()">파일 선택</button>
			        <!-- 파일명 표시 -->
			        <span id="fileName2">${qna.oFile2}</span> &nbsp;
			        <!-- 삭제 버튼, 기존 파일이 있을 경우 표시 -->
			        <input type="button" id="deleteButton2" name="delete2" value="x" onclick="deleteFile(2)" style="display:${!empty qna.oFile2 ? 'inline' : 'none'};">
			    </td>
			</tr>
			<tr>
			    <td class="text-center">첨부파일3</td>
			    <td>
			        <!-- 실제 파일 선택 input은 숨김 -->
			        <input type="file" id="insertFile3" name="insertFile3" style="display: none;" onchange="updateFileName(3, this)">
			        <!-- 파일 선택 버튼 -->
			        <button type="button" onclick="document.getElementById('insertFile3').click()">파일 선택</button>
			        <!-- 파일명 표시 -->
			        <span id="fileName3">${qna.oFile3}</span> &nbsp;
			        <!-- 삭제 버튼, 기존 파일이 있을 경우 표시 -->
			        <input type="button" id="deleteButton3" name="delete3" value="x" onclick="deleteFile(3)" style="display:${!empty qna.oFile3 ? 'inline' : 'none'};">
			    </td>
			</tr>
			<tr>
			    <td class="text-center">첨부파일4</td>
			    <td>
			        <!-- 실제 파일 선택 input은 숨김 -->
			        <input type="file" id="insertFile4" name="insertFile4" style="display: none;" onchange="updateFileName(4, this)">
			        <!-- 파일 선택 버튼 -->
			        <button type="button" onclick="document.getElementById('insertFile4').click()">파일 선택</button>
			        <!-- 파일명 표시 -->
			        <span id="fileName4">${qna.oFile4}</span> &nbsp;
			        <!-- 삭제 버튼, 기존 파일이 있을 경우 표시 -->
			        <input type="button" id="deleteButton4" name="delete4" value="x" onclick="deleteFile(4)" style="display:${!empty qna.oFile4 ? 'inline' : 'none'};">
			    </td>
			</tr>
			<tr>
			    <td class="text-center">첨부파일5</td>
			    <td>
			        <!-- 실제 파일 선택 input은 숨김 -->
			        <input type="file" id="insertFile5" name="insertFile5" style="display: none;" onchange="updateFileName(5, this)">
			        <!-- 파일 선택 버튼 -->
			        <button type="button" onclick="document.getElementById('insertFile5').click()">파일 선택</button>
			        <!-- 파일명 표시 -->
			        <span id="fileName5">${qna.oFile5}</span> &nbsp;
			        <!-- 삭제 버튼, 기존 파일이 있을 경우 표시 -->
			        <input type="button" id="deleteButton5" name="delete5" value="x" onclick="deleteFile(5)" style="display:${!empty qna.oFile5 ? 'inline' : 'none'};">
			    </td>
			</tr>
		</table>
				
			<div class="text-center">
				<input class="btn btn-success" type="submit" value="수정하기"> &nbsp;
				<input class="btn btn-success" type="button" value="돌아가기" onclick="javascript:history.go(-1);return false;">
			</div>
	</form>
</div>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>