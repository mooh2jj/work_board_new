<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>기본 에러 화면</h2>

	<table border="1">
		<tr>
			<td align="center"><b>기본 에러 화면입니다.</b></td>
		</tr>
	</table>
	<br/>
	
	<table border="1">
		<tr>
			<td align="center">
				<br><br><br><br>
				메세지 : ${exception.message}
				<br><br><br><br>
			</td>
		</tr>
	</table>
</body>
</html>