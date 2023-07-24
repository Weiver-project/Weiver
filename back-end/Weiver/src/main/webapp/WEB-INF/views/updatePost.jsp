<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>글 작성 페이지</title>
	
	<!--css 연결-->
	<link rel="stylesheet" href="/css/posting.css">
    <link rel="stylesheet" href="/css/public.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body>

	<!-- 전체 컨테이너 -->
    <div class="backgroudBox">

        <!-- 헤더 -->
        <header>
            <img src="/img/logo.png" alt="logo" height="70" width="300" />
        </header>

        <!-- 뒤로가기 버튼 -->
        <div class="backBtn">
            <a href="http://3.36.252.181:8081/community">
                <i class="bi-chevron-left"></i>
            </a>
			<div class="nameTag">
				<h1>글 작성하기</h1>
			</div>
        </div>

	    <br>

		<form action="http://3.36.252.181:8081/community/${posts.id}" method="post" enctype="multipart/form-data">
             <input type="hidden" name="_method" value="PUT" />
             
				<div class = selectbtn>	
					<!-- 셀렉트 버튼(리뷰, 잡담) -->
					<select name = "type" id="selectFormType" class="readonly" onFocus="this.initialSelect = this.selectedIndex;" onChange="this.selectedIndex = this.initialSelect;">
						<option value="select" disabled>글 종류</option>
                        <option value="Chat" ${posts.type == 'Chat' ? 'selected' : ''}>잡담</option>
                        <option value="Review" ${posts.type == 'Review' ? 'selected' : ''}>리뷰</option>
					</select>
				</div>

				<br>
				
				<!-- 제목 작성 칸-->
				<span>제목</span>
				<div>
					<textarea name = "title" class="title" id="title">${posts.title}</textarea>
				</div>


				<!-- 작품 명 작성칸 -->
				<c:if test="${posts.type == 'Review'}">
					<div align="left" id="reviewForm">
						<br>
						작품명
						<div id="musicalDetailContainer" style="display: block; background-color: #172036; padding: 20px; border-radius: 10px; color: #fff; font-size: 18px; text-align: center;">
							<img src="${review.musical.posterImage}" alt="Musical Poster" style="height: 120px; width: 85px;">
							<h2 style="background-color: #172036; font-size: 25px; text-align: center; margin-top: -5px;">${review.musical.title}</h2>
							<p style="background-color: #172036; font-size: 15px; text-align: center;">${review.musical.theater}</p>
						</div>
					</div>
				</c:if>
				<br>

				<!-- 내용 작성칸 -->
				<span>내용</span>

						<!-- 내용칸 -->
					<div>
						<textarea name="content" type="text" class="content" id="editor">${posts.content}</textarea>
					</div>
				
				<br>
					<!-- 작성하기 버튼 -->
					<div class="nameTag">
						<input type="submit" value="작성하기" class="submit-btn">
					</div>
			</form>

<footer>Copyright Weiver 2023 All Rights Reserved</footer>

<nav>
    <a href="http://3.36.252.181:8081/main"><i class="bi bi-house-door-fill"></i>
        <div>HOME</div>
    </a>
    <a href="http://3.36.252.181:8081/community"><i class="bi bi-chat-dots-fill"></i>
        <div>COMMUNITY</div>
    </a>
    <a href="http://3.36.252.181:8081/mypage/myinfo"><i class="bi bi-person-fill"></i>
        <div>MY PAGE</div>
    </a>
</nav>

</div>
</body>

</html>