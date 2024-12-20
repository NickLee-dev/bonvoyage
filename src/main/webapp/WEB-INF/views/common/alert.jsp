<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bonvoyage</title>
</head>
<body>
<!-- <script>
    var msg = "<c:out value='${msg}'/>";
    var url = "<c:out value='${url}'/>";
    alert(msg);
    location.href = url;
</script> -->
<script type="text/javascript">
		var message = "${msg}";
		var url = "${url}";
		alert(message);
		location.href = url;
	</script>
</body>
</html>