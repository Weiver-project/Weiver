<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="config.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>${code}</p>
<p>${userInfo}</p>
<a href="https://kauth.kakao.com/oauth/logout?client_id=2aad40910868e3c5fa9594f8de34a07b&logout_redirect_uri=${baseURL}/member/do">로그아웃</a>
</body>
</html>
