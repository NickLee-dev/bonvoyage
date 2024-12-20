<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5cc86f31dc51c7974b7eaa99b903eea0&libraries=services,drawing,clusterer"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/bmap.js"></script>

<style type="text/css">
 	#routePlace {
		overflow-x: auto;
		white-space: nowrap; /* 테이블들이 한 줄로 나열되도록 */
		width: 100%; /* 부모 요소 너비를 기준으로 스크롤 발생 */
	}
	
	.routeInput {
		width: 300px;
	}
</style>
<style type="text/css">
        /* 드롭다운 리스트 스타일 */
        .suggestions {
            border: 1px solid #ccc;
            border-radius: 4px;
            max-height: 200px;
            overflow-y: auto;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 300px;
            z-index: 2;
            display: none;
            position: fixed;
        }
        
         .suggestions.show {
            display: block; /* 검색 결과가 있을 때만 드롭다운을 표시 */
        }

        .suggestions li {
            padding: 10px;
            cursor: pointer;
            border-bottom: 1px solid #eee;
            transition: background-color 0.2s ease-in-out;
        }

        .suggestions li:last-child {
            border-bottom: none;
        }

        .suggestions li:hover {
            background-color: #f2f2f2;
        }

        /* 드롭다운 리스트의 스크롤바 스타일 */
        .suggestions::-webkit-scrollbar {
            width: 6px;
        }

        .suggestions::-webkit-scrollbar-thumb {
            background-color: #ccc;
            border-radius: 3px;
        }
</style>
<script type="text/javascript">
window.onload = function() {
    // 파일 입력 필드와 이미지 미리보기 요소들을 매핑
    var files = [
        {input: "routePlacePhoto1", image: "photo1"},
        {input: "routePlacePhoto2", image: "photo2"},
        {input: "routePlacePhoto3", image: "photo3"},
        {input: "routePlacePhoto4", image: "photo4"},
        {input: "routePlacePhoto5", image: "photo5"}
    ];

    files.forEach(function(file) {
        var photofile = document.getElementById(file.input);
        var myphoto = document.getElementById(file.image);

        // 기본 이미지 설정
        const defaultImageSrc = '${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg';
        
        // 미리보기 이미지가 없으면 기본 이미지로 설정
        if (myphoto.src === "") {
            myphoto.src = defaultImageSrc;
        }

        // 파일이 변경될 때 미리보기 이미지 업데이트
        photofile.addEventListener('change', function(event) {
            const fileData = event.target.files[0];
            if (fileData) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    myphoto.src = e.target.result; // 미리보기 이미지 업데이트
                };
                reader.readAsDataURL(fileData); // 파일을 읽어서 데이터 URL로 변환
            } else {
                // 파일이 없을 경우 기본 이미지로 복구
                myphoto.src = defaultImageSrc;
            }
        });
    });
};
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp"/>
<br>
<div>
	<h2 align="center">추천 경로 작성</h2>
	<form class="container" action="uroute.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="userId" value="${ sessionScope.loginUser.memId }">
		<input type="hidden" name="routeBoardId" value="${ route.routeBoardId }">
		<input type="hidden" name="ofile1" value="${ route.ofile1 }">
		<input type="hidden" name="ofile2" value="${ route.ofile2 }">
		<input type="hidden" name="ofile3" value="${ route.ofile3 }">
		<input type="hidden" name="ofile4" value="${ route.ofile4 }">
		<input type="hidden" name="ofile5" value="${ route.ofile5 }">
		<input type="hidden" name="rfile1" value="${ route.rfile1 }">
		<input type="hidden" name="rfile2" value="${ route.rfile2 }">
		<input type="hidden" name="rfile3" value="${ route.rfile3 }">
		<input type="hidden" name="rfile4" value="${ route.rfile4 }">
		<input type="hidden" name="rfile5" value="${ route.rfile5 }">
		
		<table>
			<tr>
				<th width="150">제목</th>
				<%-- <td><textarea cols="120" rows="1" name="title" style="font-size: 14pt;">${ route.title }</textarea></td> --%>
				<td><input type="text" style="width: 1100px; font-size: 14pt;" name="title" value="${ route.title }"></td>
				<td>
					<select name="transport" id="transport" class="form-select w-auto">
						<option value="대중교통" id="publicTransport">대중교통</option>
						<option value="자전거" id="bicycle">자전거</option>
						<option value="자동차" id="car">자동차</option>
						<option value="도보" id="walking">도보</option>
						<option value="기차" id="train">기차</option>
					</select>
				</td>
			</tr>
		</table>
		 
		 <div id="routePlace" class="d-flex">
		 	<div>
			 <table class="float-start me-5">
			 	<tr >
			 		<th colspan="2" class="text-center p-1" width="300">장소</th>
			 	</tr>
			 	<tr><th width="100">주소</th>
			 		<td width="200">
			 			<input type="text" id="routePlaceAddress1" name="routePlaceAddress[]" class="routeInput" value="${ place[0].address }" onkeyup="handleKeyPress(event)" autocomplete="off"  placeholder="장소나 주소를 검색하세요." onkeydown="if(event.keyCode==13) return false">
			 			<ul id="suggestions1" class="suggestions"></ul>
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">이름</th>
			 		<td>
			 			<input type="text" id="routePlaceName1" name="routePlaceName[]" class="routeInput" value="${ place[0].placeName }">
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">사진첨부</th>
			 		<td>
			 			<input type="file" id="routePlacePhoto1" name="ofile11">
			 			<!-- <button type="button" id="resetPhoto1" onclick="resetFileInput('routePlacePhoto1', 'photo1'); return false;" >x</button> -->
			 		</td>
			 	</tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ empty route.rfile1 }">
			 		<img id="photo1" src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ !empty route.rfile1 }">
			 		<img id="photo1" src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile1 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 	<tr><th class="py-2">장소설명</th>
			 		<td>
			 			<textarea rows="3" cols="37" id="routePlaceContent1" name="routePlaceContent[]">${ place[0].placeContent }</textarea>
			 		</td>
			 	</tr>		 	
			 </table>
			 </div>
			 
			 <div>
			 <table class="me-5">
			 	<tr >
			 		<th colspan="2" class="text-center p-1" width="300">장소</th>
			 	</tr>
			 	<tr><th width="100">주소</th>
			 		<td width="200">
			 			<input type="text" id="routePlaceAddress2" name="routePlaceAddress[]" class="routeInput" value="${ place[1].address }" onkeyup="handleKeyPress(event)" autocomplete="off"  placeholder="장소나 주소를 검색하세요." onkeydown="if(event.keyCode==13) return false">
			 			<ul id="suggestions2" class="suggestions"></ul>
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">이름</th>
			 		<td>
			 			<input type="text" id="routePlaceName2" name="routePlaceName[]" class="routeInput" value="${ place[1].placeName }">
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">사진첨부</th>
			 		<td>
			 			<input type="file" id="routePlacePhoto2" name="ofile22">
			 			<!-- <button type="button" id="resetPhoto1" onclick="resetFileInput('routePlacePhoto1', 'photo1'); return false;" >x</button> -->
			 		</td>
			 	</tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ empty route.rfile2 }">
			 		<img id="photo2" src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ !empty route.rfile2 }">
			 		<img id="photo2" src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile2 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 	<tr><th class="py-2">장소설명</th>
			 		<td>
			 			<textarea rows="3" cols="37" id="routePlaceContent2" name="routePlaceContent[]">${ place[1].placeContent }</textarea>
			 		</td>
			 	</tr>		 	
			 </table>
			 </div>
			 
			 <div>
			 <table class="me-5">
			 	<tr >
			 		<th colspan="2" class="text-center p-1" width="300">장소</th>
			 	</tr>
			 	<tr><th width="100">주소</th>
			 		<td width="200">
			 			<input type="text" id="routePlaceAddress3" name="routePlaceAddress[]" class="routeInput" value="${ place[2].address }" onkeyup="handleKeyPress(event)" autocomplete="off"  placeholder="장소나 주소를 검색하세요." onkeydown="if(event.keyCode==13) return false">
			 			<ul id="suggestions3" class="suggestions"></ul>
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">이름</th>
			 		<td>
			 			<input type="text" id="routePlaceName3" name="routePlaceName[]" class="routeInput" value="${ place[2].placeName }">
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">사진첨부</th>
			 		<td>
			 			<input type="file" id="routePlacePhoto3" name="ofile33">
			 			<!-- <button type="button" id="resetPhoto1" onclick="resetFileInput('routePlacePhoto1', 'photo1'); return false;" >x</button> -->
			 		</td>
			 	</tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ empty route.rfile3 }">
			 		<img id="photo3" src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ !empty route.rfile3 }">
			 		<img id="photo3" src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile3 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 	<tr><th class="py-2">장소설명</th>
			 		<td>
			 			<textarea rows="3" cols="37" id="routePlaceContent3" name="routePlaceContent[]">${ place[2].placeContent }</textarea>
			 		</td>
			 	</tr>		 	
			 </table>
			 </div>
			 
			 <div>
			 <table class="me-5">
			 	<tr >
			 		<th colspan="2" class="text-center p-1" width="300">장소</th>
			 	</tr>
			 	<tr><th width="100">주소</th>
			 		<td width="200">
			 			<input type="text" id="routePlaceAddress4" name="routePlaceAddress[]" class="routeInput" value="${ place[3].address }" onkeyup="handleKeyPress(event)" autocomplete="off"  placeholder="장소나 주소를 검색하세요." onkeydown="if(event.keyCode==13) return false">
			 			<ul id="suggestions4" class="suggestions"></ul>
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">이름</th>
			 		<td>
			 			<input type="text" id="routePlaceName4" name="routePlaceName[]" class="routeInput" value="${ place[3].placeName }">
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">사진첨부</th>
			 		<td>
			 			<input type="file" id="routePlacePhoto4" name="ofile44">
			 			<!-- <button type="button" id="resetPhoto1" onclick="resetFileInput('routePlacePhoto1', 'photo1'); return false;" >x</button> -->
			 		</td>
			 	</tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ empty route.rfile4 }">
			 		<img id="photo4" src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ !empty route.rfile4 }">
			 		<img id="photo4" src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile4 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 	<tr><th class="py-2">장소설명</th>
			 		<td>
			 			<textarea rows="3" cols="37" id="routePlaceContent4" name="routePlaceContent[]">${ place[3].placeContent }</textarea>
			 		</td>
			 	</tr>		 	
			 </table>
			 </div>
			 
			 <div>
			 <table class="me-5">
			 	<tr >
			 		<th colspan="2" class="text-center p-1" width="300">장소</th>
			 	</tr>
			 	<tr><th width="100">주소</th>
			 		<td width="200">
			 			<input type="text" id="routePlaceAddress5" name="routePlaceAddress[]" class="routeInput" value="${ place[4].address }" onkeyup="handleKeyPress(event)" autocomplete="off"  placeholder="장소나 주소를 검색하세요." onkeydown="if(event.keyCode==13) return false">
			 			<ul id="suggestions5" class="suggestions"></ul>
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">이름</th>
			 		<td>
			 			<input type="text" id="routePlaceName5" name="routePlaceName[]" class="routeInput" value="${ place[4].placeName }">
			 		</td>
			 	</tr>
			 	<tr><th class="py-2">사진첨부</th>
			 		<td>
			 			<input type="file" id="routePlacePhoto5" name="ofile55">
			 			<!-- <button type="button" id="resetPhoto1" onclick="resetFileInput('routePlacePhoto1', 'photo1'); return false;" >x</button> -->
			 		</td>
			 	</tr>
			 	<tr>
			 	<td  class="py-2" colspan="2">
			 	<div style="width: 400px; height: 400px;">
			 		<c:if test="${ empty route.rfile5 }">
			 		<img id="photo5" src="${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg" style="width: 400px; height: 400px;">
			 		</c:if>
			 		<c:if test="${ !empty route.rfile5 }">
			 		<img id="photo5" src="${ pageContext.servletContext.contextPath }/resources/route_upfiles/${ route.rfile5 }" style="width: 400px; height: 400px;">
			 		</c:if>
			 	</div>
			 	</td>
			 	</tr>
			 	<tr><th class="py-2">장소설명</th>
			 		<td>
			 			<textarea rows="3" cols="37" id="routePlaceContent5" name="routePlaceContent[]">${ place[4].placeContent }</textarea>
			 		</td>
			 	</tr>		 	
			 </table>
			 </div>
		 </div>
				 		 		 
		 <fieldset>
		 <legend>상세내용</legend>
		 <div>
		 	<textarea rows="20" cols="160" name="content" style="font-size: 13pt;">${ route.content }</textarea>
		 </div>
		 </fieldset>		
		 
		<div class="container text-center">
		 	<input type="submit" value="수정"  class="btn btn-success"> &nbsp;
		 	<input type="reset" value="취소" class="btn btn-success"> &nbsp;
		 	<input type="button" onclick="javascript:history.go(-1); return false;" value="뒤로" class="btn btn-success">
		</div>
	</form>
</div>	
<c:import url="/WEB-INF/views/common/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>