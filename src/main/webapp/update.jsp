<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<html>
<head>
<style>
#container{
text-align:center;
position: absolute;
    top: 50%;
    left: 50%;
    margin-right: -50%;
    transform: translate(-50%, -50%)
}
</style>
</head>
<body>
<div id="container">
<p style="font-size:20">Web Page is updating please come back after few minutes</p>
<img src="${pageContext.request.contextPath}/images/update.png" />
</div>
</body>
</html>
