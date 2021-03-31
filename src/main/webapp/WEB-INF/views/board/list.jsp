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
<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
<script type="text/javascript">
	
	function btnWrite(){
		location.href = "/board/write.do";
	}
	
	function btnFileUpload() {
		location.href = "/fileUpload/write.do";
	}
	
	function btnMultiFileUpload() {
		location.href = "/fileUpload/write_multi.do";
	}
	
	function btnAjaxUpload() {
		location.href = "/fileUpload/write_ajax.do";
	}
	
	function btnAjaxUpload2() {
		location.href = "/fileUpload/write_ajax2.do";
	}
	

</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>글 목록</h2>
<div>
	<c:if test="${sessionScope.email != null}">
		<span><button type="button" onclick="btnWrite();">글등록</button></span>
		<span><button type="button" onclick="btnFileUpload();">파일등록</button></span>
		<span><button type="button" onclick="btnMultiFileUpload();">다중파일등록</button></span>
		<span><button type="button" onclick="btnAjaxUpload();">Ajax파일등록</button></span>
		<span><button type="button" onclick="btnAjaxUpload2();">Ajax2파일등록</button></span>
	</c:if>
</div>
<br/>
<table border="1">
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>등록시간</th>
	</tr>
	<c:forEach var="row" items="${selectAll}">
		<tr>
			<td>${row.bno}</td>
			<td><a href="detail.do/${row.bno}">${row.title}</a></td>
			<td>${row.writer}</td>
			<td><fmt:formatDate value="${row.regDate}" pattern = "yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</c:forEach>
</table>


</body>
</html>