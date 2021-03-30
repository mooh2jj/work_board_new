<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function join(){
		location.href = "join.do";
	}
	
	function checkForm(){
		form.email.value = form.email.value.trim();
		
		if(form.email.value == "" || form.email.value.length == 0){
			alert("아이디를 입력하세요");
			form.email.focus();
			return false;
		}
		
		form.pw.value = form.pw.value.trim();
		
		if(form.pw.value == "" || form.pw.value.length == 0){
			alert("패스워드를 입력하세요");
			form.pw.focus();
			return false;
		}
		
		return true;
	}
</script>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>로그인</h2>
<form name="form" action="login.do" onsubmit="return checkForm()" method="post">
	<table border="1">
		<tr>
			<td>ID</td>
			<td><input type="text" id="email" name="email"/></td>
		</tr>
		<tr>
			<td>PW</td>
			<td><input type="password" id="pw" name="pw"/></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<input type="submit" value="로그인"/>
			<c:if test="${message == 'error'}">
				<div style="color:red;">
						아이디 또는 비밀번호가 일치하지 않습니다.
				</div>
			</c:if>
			<c:if test="${message == 'logout'}">
				<div style="color:red;">
					로그아웃되었습니다.
				</div>
			</c:if>
			</td>
		</tr>
	</table>

</form>

</body>
</html>