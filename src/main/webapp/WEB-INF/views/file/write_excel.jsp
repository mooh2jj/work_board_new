<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script type="text/javascript">
// 엑셀 파일인지 구분
function checkFileType(filePath) {
    var fileFormat = filePath.split(".");

    if (fileFormat.indexOf("xls") > -1 || fileFormat.indexOf("xlsx") > -1) {
      return true;
      } else {
      return false;
    }
  }

  function check() {

    var file = $("#excelFile").val();

    if (file == "" || file == null) {
    	alert("파일을 선택해주세요.");
    	return false;
    
    } else if (!checkFileType(file)) {
	    alert("엑셀 파일만 업로드 가능합니다.");
	    return false;
    }

   	var fileFormat = file.split("."); 
   	var fileType = fileFormat[1];
   	
    if (confirm("업로드 하시겠습니까?")) {
    	$("#excelUploadForm").attr("action","/fileUpload/excelUploadAjax.do");
      	var options = {

        success : function(data) {
            console.log(data);
          	alert("모든 데이터가 업로드 되었습니다.");

        },
        type : "POST",
        data : {"excelType" : fileType}
        };
      
      $("#excelUploadForm").ajaxSubmit(options);
      
    }
  }

</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<div align="center">
<h2>Excel파일 업로드 등록</h2>
<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data"
        method="post">
	<div class="uploadDiv">
		<input type="file" id="excelFile" name="excelFile">
	</div>
	<div class="uploadResult">
	</div>

	<button type="button" onclick="check();">업로드</button>
	<button type="reset" id="btnCancle">취소</button>
</form>
</div> 	
</body>
</html>