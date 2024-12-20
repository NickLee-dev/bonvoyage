<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>BonVoyage - 지역소담이 블로그 작성</title>

  <!-- Bootstrap 5 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- Toast UI Editor CSS -->
  <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

  <style>
    body {
      font-family: 'Arial', sans-serif;
      background-color: #f8f9fa;
    }
    h1 {
      margin: 30px 0;
    }
   #main {
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }
    .form-control {
      border-radius: 8px;
    }
    .btn-custom {
      background-color: #28a745;
      border-color: #28a745;
      color: white;
    }
    .btn-custom:hover {
      background-color: #218838;
      border-color: #1e7e34;
    }
    /* 새롭게 추가된 내용 박스 */
    #content-box {
      border: 2px solid #dee2e6;
      border-radius: 8px;
      padding: 15px;
      background-color: #f8f9fa;
    }
    .editor-title {
      font-weight: bold;
      margin-bottom: 10px;
    }
    #previewContainer {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
    }
    #previewContainer img {
      width: 150px;
      height: 150px;
      border: 1px solid navy;
      cursor: pointer;
    }
    #photofile {
      display: none;
    }
  </style>
</head>

<body>

  <!-- Navbar -->
  <c:import url="/WEB-INF/views/common/menubar.jsp" />

  <div class="container mt-5" id=main>
    <h3 class="text-center">지역소담이 블로그 작성</h3>

    <form action="ginsert.do" method="post" enctype="multipart/form-data">
      <div class="mb-3">
        <label for="guideTitle" class="form-label">제목</label>
        <input type="text" name="guideTitle" id="guideTitle" class="form-control" placeholder="블로그 제목을 입력하세요">
      </div>

      <div class="mb-3">
        <label for="guideWriter" class="form-label">작성자</label>
        <input type="text" name="guideWriter" id="guideWriter" class="form-control" readonly value="${sessionScope.loginUser.userId}">
      </div>

      <div class="mb-3">
        <label for="guideLocation" class="form-label">지역</label>
        <input type="text" name="guideLocation" id="guideLocation" class="form-control" placeholder="지역을 입력하세요">
      </div>

      <!-- 새롭게 추가된 내용 박스 -->
      <div id="content-box" class="mb-3">
        <div class="editor-title">내용</div>
        <textarea name="guideContent" id="guideContent" class="form-control" rows="8" placeholder="블로그 내용을 입력하세요" style="border: none; background-color: transparent;"></textarea>
      </div>

      <!-- 파일 첨부 필드와 미리보기 이미지 -->
      <div class="mb-3">
        
        <input type="file" id="photofile1" name="ofile1" class="form-control mb-2" onchange="previewImage(event, 1)">
        <div id="myphoto1">
          <img src="/bonvoyage/resources/images/noPhoto.jpg" id="photo1" style="width:150px;height:160px;border:1px solid navy;" alt="사진을 선택하세요.">
        </div>
      </div>

      <div class="mb-3">
        
        <input type="file" id="photofile2" name="ofile2" class="form-control mb-2" onchange="previewImage(event, 2)">
        <div id="myphoto2">
          <img src="/bonvoyage/resources/images/noPhoto.jpg" id="photo2" style="width:150px;height:160px;border:1px solid navy;" alt="사진을 선택하세요.">
        </div>
      </div>

      <div class="mb-3">
        
        <input type="file" id="photofile3" name="ofile3" class="form-control mb-2" onchange="previewImage(event, 3)">
        <div id="myphoto3">
          <img src="/bonvoyage/resources/images/noPhoto.jpg" id="photo3" style="width:150px;height:160px;border:1px solid navy;" alt="사진을 선택하세요.">
        </div>
      </div>

      <div class="mb-3">
        
        <input type="file" id="photofile4" name="ofile4" class="form-control mb-2" onchange="previewImage(event, 4)">
        <div id="myphoto4">
          <img src="/bonvoyage/resources/images/noPhoto.jpg" id="photo4" style="width:150px;height:160px;border:1px solid navy;" alt="사진을 선택하세요.">
        </div>
      </div>

      <div class="mb-3">
       
        <input type="file" id="photofile5" name="ofile5" class="form-control mb-2" onchange="previewImage(event, 5)">
        <div id="myphoto5">
          <img src="/bonvoyage/resources/images/noPhoto.jpg" id="photo5" style="width:150px;height:160px;border:1px solid navy;" alt="사진을 선택하세요.">
        </div>
      </div>

      <!-- Toast UI Editor -->
      <div id="editor" class="mb-3"></div>

      <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <input type="submit" value="등록하기" class="btn btn-secondary me-md-2">
        <input type="reset" value="작성취소" class="btn btn-custom">
        <input type="button" value="목록" class="btn btn-outline-secondary" onclick="javascript:history.go(-1); return false;">
      </div>
    </form>
  </div>

  <!-- Bootstrap 5 JS and dependencies -->
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>

  <!-- Toast UI Editor JS -->
  <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

  <script type="text/javascript">
    // 이미지 미리보기 처리 함수
    function previewImage(event, index) {
        var input = event.target;
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById("photo" + index).src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
    

  
  </script>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
