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
	$(function(){
		$('#btnUpdate').click(function(){
			
			if(confirm("수정하시겠습니까?")){
 				document.form1.action = "${path}/board/update.do";
				document.form1.submit();
			}
		});
		$('#btnDelete').click(function(){
			
			if(confirm("삭제하시겠습니까?")){
				document.form1.action = "${path}/board/delete.do";
				document.form1.submit();
			}
		});
	});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>상세페이지</h2>

	<form name="form1" id="form1">
		<table>
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" id="title" value="${dto.title}"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" id="content" rows="4" cols="80" placeholder="내용을 입력해주세요">${dto.content}</textarea></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="writer" id="writer" value="${dto.writer}" readonly="readonly"></td>
			</tr>
			<input type="hidden" name="bno" value="${dto.bno}"/>
			<tr>
				<td colspan="2" align="center">
					<button type="button" id="btnUpdate">수정</button>
					<button type="button" id="btnDelete">삭제</button>
					<button type="reset" id="btnCancle">취소</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>