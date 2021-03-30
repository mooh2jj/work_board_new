<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#btnSave").click(function() {
			
			var form1 = $("#form1");
			
			var title = $("#title").val();
			var content = $("#content").val();
			
			if (title == "" || title.length == 0) {
				alert("제목을 입력하세요");
				form1.title.focus(); // 안쓴거 해당입력창에 포커스!
				return;
			}
			
			if (content == "" || content.length == 0) {
				alert("내용을 입력하세요");
				form1.content.focus(); // 안쓴거 해당입력창에 포커스! 
				return;
			}
			

			form1.attr("action","/fileUpload/insert.do");
			form1.attr("method","post");
			form1.submit();
		});
	});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<div align="center">
<h2>파일업로드 등록</h2>
	<form name="form1" id="form1" method="post" enctype="multipart/form-data">
		<table align ="center">
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" id="title" size="50" placeholder="제목을 입력해주세요"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" id="content" rows="4" cols="80" placeholder="내용을 입력해주세요"></textarea></td>
			</tr>
			<tr>
				<td>file</td>
				<td><input type="file" name="file"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="button" id="btnSave">저장</button>
					<button type="reset" id="btnCancle">취소</button>
				</td>
			</tr>
		</table>
	</form>
</div> 	
</body>
</html>