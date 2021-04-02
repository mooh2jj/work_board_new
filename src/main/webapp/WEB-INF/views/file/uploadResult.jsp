<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path"  value="${pageContext.request.contextPath}"  />	    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
	var msg = "${msg}";
	
	if( msg == 'fileUpladed'){
		alert("파일 업로드가 완료되었습니다.");
		location.href = "${path}/board/list.do";
	} else {
		alert("파일 업로드가 실패하였습니다.");
		location.href = "${path}/board/list.do";
	}

</script>

</body>
</html>