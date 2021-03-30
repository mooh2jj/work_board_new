<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path"  value="${pageContext.request.contextPath}"  />
<html>
<head>
	<title>Home</title>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<c:if test="${sessionScope.name != null}">
	<h2>${sessionScope.name}님 환영합니다!</h2>
</c:if>
<h2>Welcome! dsg 게시판에 오신 걸 환영합니다.</h2>
<img src="${path}/resources/img/horse.jpg"	style="height: 280px; margin-left: 10px;" />
</body>
</html>
