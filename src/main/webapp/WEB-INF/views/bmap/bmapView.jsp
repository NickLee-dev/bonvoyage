<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" async src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5cc86f31dc51c7974b7eaa99b903eea0&libraries=services,drawing,clusterer"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>

</head>
<body>
<nav>
	<c:import url="/WEB-INF/views/common/menubar.jsp"/>
</nav>
<br>
<h2 align="center">출발지, 도착지, 그리고 경유지를 선택하여 나만의 경로를 만들어보세요!</h2>

<c:import url="/WEB-INF/views/bmap/bmapRoute.html" charEncoding="utf-8"></c:import>


<footer>
	<c:import url="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>
