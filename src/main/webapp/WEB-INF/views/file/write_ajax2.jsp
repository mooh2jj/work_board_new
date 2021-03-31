<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
     .fileDrop{
           width: 400px;
           height: 200px;
           border: 2px dotted gray;
           
     }
     
     /* small {
           margin-left: 3px;
           font-weight: bold;
           color: gray;
     } */
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">

$(document).ready(function(){
    $(".fileDrop").on("dragenter dragover", function(event) {
         event.preventDefault();         // 기본효과를 막음
    });
    // event : jQuery의 이벤트
    // originalEvent : js의 오리지날이벤트
    $(".fileDrop").on("drop", function(event) {
         event.preventDefault();         // 기본효과를 막음
         // 드래그된 파일의 정보
         var files = event.originalEvent.dataTransfer.files;
         // 첫번째 파일
         var file = files[0];
         // 콘솔에서 파일정보 확인
         console.log(file);
         
         // formData : ajax로 전달할 폼 객체 , 가상의 <form> 태그
         var formData = new FormData();        // AJAX을 이용하는 파일 업로드는 FormData를 이용해서 필요한 파리미터를 담아서 전송하는 방식임.
         // 폼 객체에 파일추가, append("변수명", 값)
         formData.append("uploadFile", file);
         
         // 파일 업로드 AJAX 통신
         $.ajax({
             type: "post",
	         url: "/fileUpload/uploadAjaxAction.do",
	         data: formData,            // formData : ajax로 전달할 폼 객체 , 가상의 <form> 태그
	         // formData.append("file", file); 한
	         dataType: "text",
	         processData: false,
	         // processData: true => get방식, false => post방식
	         contentType: false,
	         // contentType: true => application/x-www-form-urlencoded,
	         // false => multipart/form-data
	         success: function(data){
	             alert(data);
         }
         });
    });
});
	

</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<div align="center">
<h2>Ajax2파일 업로드 등록</h2>

    <!-- 파일을 업로드할 영역 -->
    <div class="fileDrop"></div>
    <!-- 업로드된 파일 목록 -->
    <div class="uploadedList"></div>
				

</div> 	
</body>
</html>