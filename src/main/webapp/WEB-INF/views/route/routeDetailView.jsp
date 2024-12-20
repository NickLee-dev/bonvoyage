<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>
<style type="text/css">
 	#routePlace {
		overflow-x: auto;
		white-space: nowrap; /* 테이블들이 한 줄로 나열되도록 */
		width: 100%; /* 부모 요소 너비를 기준으로 스크롤 발생 */
	}
	
	.routeInput {
		width: 300px;
	}
	textarea {
		background-color: white;
		resize: none !important;
		border: 0;
	}
</style>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">

	function commentShow() {
	    var commentSection = document.getElementById("commentSection");
	    if (commentSection.style.display === "none") {
	        commentSection.style.display = "block";
	    } else {
	        commentSection.style.display = "none";
	    }
	}
	
    function enableEdit(rowId, contentId) {
        // 수정 버튼을 클릭했을 때 실행되는 함수
        var content = document.getElementById(contentId);
        var editButton = document.getElementById("editBtn_" + rowId);
        var deleteButton = document.getElementById("deleteBtn_" + rowId);
        var saveButton = document.getElementById("saveBtn_" + rowId);
        var cancelButton = document.getElementById("cancelBtn_" + rowId);

        // 내용 칸을 입력 가능한 <input> 필드로 변경
        content.innerHTML = '<input type="text" id="editContent_' + rowId + '" value="' + content.textContent + '" />';

        // 수정, 삭제 버튼을 숨기고 수정완료, 취소 버튼을 표시
        editButton.style.display = 'none';
        deleteButton.style.display = 'none';
        saveButton.style.display = 'inline';
        cancelButton.style.display = 'inline';
    }

    function saveEdit(rowId, commentId) {
        // 수정완료 버튼을 클릭했을 때 실행되는 함수
        var newContent = document.getElementById("editContent_" + rowId).value;

        // 여기에 AJAX 요청을 보내서 서버에 수정된 내용을 저장하도록 합니다.
        $.ajax({
            url: 'routeCommentEdit.do',
            type: 'POST',
            data: {
                commentId: commentId,
                content: newContent
            },
            success: function(response) {
                if (response === "Success") {
                    // 수정 성공 시 화면 업데이트
                    alert("댓글이 수정되었습니다.");
                    location.reload(); // 또는 화면의 특정 부분만 업데이트
                } else {
                    // 오류 발생 시 오류 메시지 표시
                    alert("수정 중 오류가 발생했습니다: " + response);
                }
            },
            error: function(error) {
                alert("통신 중 오류가 발생했습니다.");
            }
        });
    }

    function cancelEdit(rowId) {
        // 취소 버튼을 클릭했을 때 실행되는 함수
        // 원래 내용을 다시 보여주고 버튼 상태를 원래대로 돌립니다.
        var originalContent = document.getElementById("content_" + rowId).dataset.originalContent;
        document.getElementById("content_" + rowId).innerHTML = originalContent;
        toggleEditButtons(rowId, false);
    }

    function toggleEditButtons(rowId, isEditing) {
        // 수정 및 삭제 버튼을 보여주거나 숨기는 함수
        var editButton = document.getElementById("editBtn_" + rowId);
        var deleteButton = document.getElementById("deleteBtn_" + rowId);
        var saveButton = document.getElementById("saveBtn_" + rowId);
        var cancelButton = document.getElementById("cancelBtn_" + rowId);

        if (isEditing) {
            editButton.style.display = 'none';
            deleteButton.style.display = 'none';
            saveButton.style.display = 'inline';
            cancelButton.style.display = 'inline';
        } else {
            editButton.style.display = 'inline';
            deleteButton.style.display = 'inline';
            saveButton.style.display = 'none';
            cancelButton.style.display = 'none';
        }
    }
	
</script>


<c:url var="delroute" value="droute.do">
	<c:param name="no" value="${ route.routeBoardId }" />
	<c:param name="rfile11" value="${ route.rfile1 }" />
	<c:param name="rfile22" value="${ route.rfile2 }" />
	<c:param name="rfile33" value="${ route.rfile3 }" />
	<c:param name="rfile44" value="${ route.rfile4 }" />
	<c:param name="rfile55" value="${ route.rfile5 }" />
</c:url>

<c:url var="routelikecount" value="rlikecount.do">
	<c:param name="postId" value="${ route.routeBoardId }" />
	<c:param name="userId" value="${ loginUser.memId }" />
</c:url>

<c:url var="routeReport" value="routeReport.do">
	<c:param name="routeBoardId" value="${ route.routeBoardId }" />
	<c:param name="userId" value="${ route.userId }" />
	<c:param name="title" value="${ route.title }" />
</c:url>

</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp"/>
<br>


<div class="container">
	<h2 align="center">${ route.title }</h2>
		<div>
		 	<div>
		 		
		 	</div>
		 	<div class="text-end">
				<span>조회수 : ${ route.readCount }</span>
				<span></span>
				<br>
				<h5><span>교통수단 : ${ route.transport }</span></h5>
			</div>
		</div>
		 

		 
		 <div id="routePlace" class="d-flex">
		 	<div>
		 	<c:if test="${ !empty place[0].address }">
			 <table class="table loat-start me-5" style="width: 400px;">
			 	<tr >
			 		<th colspan="2" class="text-center p-1">장소</th>
			 	</tr>
			 	<tr>
			 		<th width="70" class="text-center">주소</th><td class="text-center">${ place[0].address }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">이름</th><td class="text-center">${ place[0].placeName }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">소개</th><td><textarea rows="6" cols="37"  disabled>${ place[0].placeContent }</textarea></td></tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ !empty route.rfile1 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile1 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ empty route.rfile1 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 			 	
			 </table>
			 </c:if>
			 </div>
			 
		 	<div>
		 	<c:if test="${ !empty place[1].address }">
			 <table class="table me-5" style="width: 400px;">
			 	<tr >
			 		<th colspan="2" class="text-center p-1">장소</th>
			 	</tr>
			 	<tr>
			 		<th width="70" class="text-center">주소</th><td class="text-center">${ place[1].address }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">이름</th><td class="text-center">${ place[1].placeName }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">소개</th><td><textarea rows="6" cols="37"  disabled>${ place[1].placeContent }</textarea></td></tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ !empty route.rfile2 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile2 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ empty route.rfile2 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 			 	
			 </table>
			 </c:if>
			 </div>
			 
		 	<div>
		 	<c:if test="${ !empty place[2].address }">
			 <table class="table me-5" style="width: 400px;">
			 	<tr >
			 		<th colspan="2" class="text-center p-1">장소</th>
			 	</tr>
			 	<tr>
			 		<th width="70" class="text-center">주소</th><td class="text-center">${ place[2].address }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">이름</th><td class="text-center">${ place[2].placeName }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">소개</th><td><textarea rows="6" cols="37"  disabled>${ place[2].placeContent }</textarea></td></tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ !empty route.rfile3 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile3 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ empty route.rfile3 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 			 	
			 </table>
			 </c:if>
			 </div>
			 
		 	<div>
		 	<c:if test="${ !empty place[3].address }">
			 <table class="table me-5" style="width: 400px;">
			 	<tr >
			 		<th colspan="2" class="text-center p-1">장소</th>
			 	</tr>
			 	<tr>
			 		<th width="70" class="text-center">주소</th><td class="text-center">${ place[3].address }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">이름</th><td class="text-center">${ place[3].placeName }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">소개</th><td><textarea rows="6" cols="37"  disabled>${ place[3].placeContent }</textarea></td></tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ !empty route.rfile4 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile4 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ empty route.rfile4 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 			 	
			 </table>
			 </c:if>
			 </div>
			 
		 	<div>
		 	<c:if test="${ !empty place[4].address }">
			 <table class="table me-5" style="width: 400px;">
			 	<tr >
			 		<th colspan="2" class="text-center p-1">장소</th>
			 	</tr>
			 	<tr>
			 		<th width="70" class="text-center">주소</th><td class="text-center">${ place[4].address }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">이름</th><td class="text-center">${ place[4].placeName }</td>
			 	</tr>
			 	<tr><th class="py-2 text-center">소개</th><td><textarea rows="6" cols="37"  disabled>${ place[4].placeContent }</textarea></td></tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ !empty route.rfile5 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile5 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ empty route.rfile5 }">
			 		<img src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 			 	
			 </table>
			 </c:if>
			 </div>
		 </div>
		 <hr>
		 <fieldset>
		 <legend></legend>
		 <div>
		 	<textarea disabled rows="20" cols="142" name="content" style="font-size: 13pt; border: 1px solid black;">${ route.content }</textarea>
		 </div>
		 </fieldset>
		 <br>
		</div>
		
		<div class="text-center">
		<table>
		<button class="btn btn-success" onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/routeall.do?page=1'; return false;">목록</button> &nbsp;
		<c:if test="${ sessionScope.loginUser.memId eq route.userId }">
			<button class="btn btn-success" onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/moveUpdateRoute.do?no=${ route.routeBoardId }'; return false;">수정하기</button> &nbsp;
			<button class="btn btn-success" onclick="javascript:location.href='${ delroute }'; return false;">삭제하기</button> &nbsp;
		</c:if>
		<button class="btn btn-outline-success" onclick="javascript:location.href='${ routelikecount }'; return false;">❤️ ${ route.likeCount }</button> &nbsp;
		<button class="btn btn-success" onclick="commentShow(); return false;">댓글&nbsp;(${ route.commentCount })</button> &nbsp;
		<button class="btn bg-danger" onclick="javascript:location.href='${ routeReport }'; return false;" style="color: white;">신고</button>
		</table>
		</div>

<div class="container" style="display: none;" id="commentSection">
	<table class="table">
    <tr>
		<th style="text-align: center;" width="100">작성자</th>
        <th style="text-align: center;" width="800">내 용</th>
        <th style="text-align: center;" width="150">작성일자</th>
        <th></th>
    </tr>
       <c:forEach items="${ requestScope.clist }" var="c" varStatus="status">
        <tr>
            <td align="center">${ c.userId }</td>
            <td align="left" id="content_${ status.index }" data-original-content="${ c.content }">${ c.content }</td>
            <td align="center">${ c.createdAt }</td>
            <c:if test="${ c.userId eq loginUser.memId or loginUser.memType eq 'ADMIN' }">
	            <td align="center">
				<button class="btn btn-success" id="editBtn_${ status.index }" onclick="enableEdit('${ status.index }', 'content_${ status.index }'); return false;">수정</button>
				<c:url var="routecommentdelete" value="rcommentdelete.do">
	                 <c:param name="commentId" value="${ c.commentId }" />
	                 <c:param name="postId" value="${ route.routeBoardId }" />
	            </c:url>
				<button class="btn btn-success" id="deleteBtn_${ status.index }" onclick="javascript:location.href='${ routecommentdelete }'; return false;">삭제</button>
				<!-- 수정완료 버튼 -->
                <button class="btn btn-success" id="saveBtn_${ status.index }" style="display: none;" onclick="saveEdit('${ status.index }', '${ c.commentId }'); return false;">수정완료</button>
	                <!-- 취소 버튼 -->
                <button class="btn btn-success" id="cancelBtn_${ status.index }" style="display: none;" onclick="cancelEdit('${ status.index }'); return false;">취소</button>
	            </td>
            </c:if>
        </tr>
    </c:forEach>
	</table>

<c:if test="${ !empty loginUser }">
	<div>
		<form action="routecomment.do">
		<input type="hidden" name="postId" value="${ route.routeBoardId }">
		<input type="hidden" name="userId" value="${ sessionScope.loginUser.memId }">
		<table class="table">
		<tr>
		<td width="1100">
		<textarea name="content" rows="3" cols="140" style="border: 1px solid black;"></textarea>
		<!-- <input type="text" name="content" style="width: 1200px;"> -->
		</td>
		<td class="align-middle text-center">
		
		<input class="btn btn-success" type="submit"  value="등록">
		
		</td>
		</tr>
		</table>
		</form>
	</div>
</c:if>
</div> 

<br>
<c:import url="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>