<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<c:set var="path"  value="${pageContext.request.contextPath}"  />     
<a href="${path}/member/join.do">회원가입</a> |


<!-- <div style="text-align: left;"> -->
<c:choose>
	<c:when test="${sessionScope.email == null}">
		<a href="${path}/member/loginmain">로그인</a> |
	</c:when>
	<c:otherwise>
		<a href="${path}/board/home.do">Home</a> |
		<a href="${path}/board/list.do">게시판</a> |
		&nbsp;
		<span style="float:right;">
			${sessionScope.name}님이 로그인 중입니다.
			<a href="${path}/member/logout.do">로그아웃</a> 
		</span>
	</c:otherwise>
</c:choose>
<!-- </div> -->