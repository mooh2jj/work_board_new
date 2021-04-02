<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
var maxSize = 5242880; //5MB

// 확장자 정규표현식
function checkExtension(fileName, fileSize) {	

	if (fileSize >= maxSize) {
		alert("파일 사이즈 초과");
		return false;
	}

	if (regex.test(fileName)) {
		alert("해당 종류의 파일은 업로드할 수 없습니다.");
		return false;
	}
	return true;
}

	$(function(){
				
		var cloneObj = $(".uploadDiv").clone();
		
		$("#btnSave").click(function(e) {
			
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			console.log("files",files);
			console.log("files.length",files.length);
 			
			for(var i =0; i <files.length; i++){
		 		if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}  
				
				formData.append("uploadFile", files[i]);
				console.log(files[i]);
			} 
			console.log(formData);
			
 			$.ajax({
				url : "/fileUpload/uploadAjaxAction.do",
 				processData : false,
				contentType : false, 
				data : formData,
				type : 'POST',
				dataType : 'text',
				success : function(result) {
					alert(result)
					console.log(result)

				}
				
			}); 
			
		});	
	
	});
	

</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<div align="center">
<h2>Ajax파일 업로드 등록</h2>

					<div class="uploadDiv">
						<input type="file" name="uploadFile" multiple="multiple">
					</div>
					<div class="uploadResult">
					</div>

		
					<button type="button" id="btnSave">저장</button>
					<button type="reset" id="btnCancle">취소</button>
				

</div> 	
</body>
</html>