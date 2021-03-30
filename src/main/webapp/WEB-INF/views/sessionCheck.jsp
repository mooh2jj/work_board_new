<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path"  value="${pageContext.request.contextPath}"  />
<html>
<body>
<script type="text/javascript">
	var msg = "${message}";
	var sessionName= "${sessionScope.name}";	// 안잡힘.
	
	if(msg == 'success'){
		
		alert(sessionName+"님 게시판에 오신 걸 환영합니다!!");
		location.href = "${path}/board/home.do";
	}else{
		location.href = "${path}/member/loginmain";
	}
</script>

</body>
</html>
