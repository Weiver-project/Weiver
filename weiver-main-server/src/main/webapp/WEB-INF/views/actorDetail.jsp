<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="config.jsp" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name=“viewport” content=“width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no”>
<title>배우 상세 페이지</title>

<!--css 연결-->
<link href="../css/actor.css" rel="stylesheet" type="text/css" />
<link href="../css/public.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<header>
	<div>
		<img src="../img/logo.png" alt="logo" height="70" width="300">
	</div>

	<div>
		<div>
			<a href="javascript:history.back();"><i class="bi-chevron-left"></i></a>
		</div>
		<div class="nameTag">

			<h1>${actor.name}</h1>
		</div>
	</div>
</header>

<body>
<div  >
	<div >
		<div class="nameTag">
			<img src="${actor.profileImage}"  alt="logo" height="200" width="200" style="border-radius: 300px">
		</div>
		<table>
	    	<tr class="td-margin">
	    		<td class="td-margin">
	    			출연 작품 <hr>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td class="td-margin" style="display:flex; flex-wrap: wrap">
		    		<c:forEach items="${requestScope.musicalList}" var="musical">
		    		<div style="max-width: 200px;">
		    			<a href="${baseURL}/musical-detail/${musical.getId()}" style="text-decoration: none;">
		    				<img src="${musical.getPosterImage()}" alt="${musical.getId()}" height="200" width="200" class="img-margin">
		    			</a>
		    			
		    		</div>
		    		
		    		</c:forEach>
	    		</td>
	    	</tr>
	    </table>
	</div>   
	
</div>

<footer>Copyright Weiver 2023 All Rights Reserved</footer>

<nav>
    <a href="${baseURL}/main"><i class="bi bi-house-door-fill"></i>
        <div>HOME</div>
    </a>
    <a href="${baseURL}/community"><i class="bi bi-chat-dots-fill"></i>
        <div>COMMUNITY</div>
    </a>
    <a href="${baseURL}/mypage/myinfo"><i class="bi bi-person-fill"></i>
        <div>MY PAGE</div>
    </a>
</nav>


</body>

</html>