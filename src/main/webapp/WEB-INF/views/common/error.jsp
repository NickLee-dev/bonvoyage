<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<!-- Bootstrap CSS CDN 링크 추가 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<style type="text/css">
* {font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;}
</style>
</head>
<body>
    <c:import url="/WEB-INF/views/common/menubar.jsp"/>

<div class="container h1 mt-5 text-center">
    오류 발생 : <%= request.getAttribute("message") %>
    <div class="btn-container mt-4 mb-5">
        <a href="javascript:history.go(-1);" class="btn btn-secondary">이전 페이지로 이동</a>
        <a href="main.do" class="btn btn-success">Home</a>
    </div>
</div>

<c:import url="/WEB-INF/views/common/footer.jsp"/>    
<!-- Bootstrap JS 및 Popper.js CDN 추가 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>