<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내가 쓴 글 페이지</title>

<!--css 연결-->
<link href="../css/user.css" rel="stylesheet" type="text/css" />
<link href="../css/public.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<header>
	<div>
		<img src="../img/logo.png" alt="logo" height="70" width="300">
	</div>

	<div>
		<div>
			<a href="javascript:history.back()"><i class="bi-chevron-left"></i></a>
		</div>
		<div class="nameTag">

			<h1>내가 쓴 글</h1>
		</div>
	</div>
</header>

<body>
<div>
	<div class="sortTag">
		<span class="boardNum">${postCount} 개의 글</span>
		
		<!-- 셀렉트 버튼(작성, 추천) -->
	    <select id="sortType" onchange="sortTypeChange()">
	        <option disabled>정렬 기준</option>
	        <option value="작성" selected>작성 순</option>
	        <option value="추천">추천 순</option>
	    </select>		
	</div>  
	
	<div>
		<c:forEach var="post" items="${postList}">
			<div class="myBoardCard">
				<a href="/community/${post.id}">
					<span class="card">
						<div class="date">${post.createdTime}</div>
						<div>${post.title}</div>
					</span>
					<span class="like">
						<i class="bi-suit-heart" style="font-size: 40px"></i>
						<div>
							${post.postlikes.size()}
						</div>
					</span>
				</a>
			</div>
		</c:forEach>
	</div>
</div>

<footer>Copyright Weiver 2023 All Rights Reserved</footer>

<nav>
    <a href="#"><i class="bi bi-house-door-fill"></i>
      <div>HOME</div>
    </a>
    <a href="#"><i class="bi bi-chat-dots-fill"></i>
      <div>COMMUNITY</div>
    </a>
    <a href="#"><i class="bi bi-person-fill"></i>
      <div>MY PAGE</div>
    </a>
  </nav>


</body>

</html>