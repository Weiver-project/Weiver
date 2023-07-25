<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="config.jsp" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name=“viewport” content=“width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no”>
	<title>문의 작성 페이지</title>

	<!--css 연결-->
	<link href="../css/posting.css" rel="stylesheet" type="text/css" />
	<link href="../css/public.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
	<!-- JQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<header>
	<div>
		<img src="../img/logo.png" alt="logo" height="70" width="300">
	</div>

	<div>
		<div>
			<a href="${baseURL}/inquiry/inquiryMain"><i class="bi-chevron-left"></i></a>
		</div>
		<div class="nameTag">

			<h1>문의하기</h1>
		</div>
	</div>
</header>

<body>
<div>
	<div>
		<!-- 문의 작성 폼 -->
		<form id="inquiryForm" action="${baseURL}/inquiry/inquiryInsert" method="POST">

			<div>
				<div>
					<div align="left">제목</div>
					<div>
						<textarea name="title" type="text" class="title" id="inquiryTitle"></textarea>
					</div>
					<div align="left" >문의사항</div>
					<div>
						<textarea name="content" type="text" class="content" id="editor"></textarea>
					</div>
				</div>
			</div>

			<div class="nameTag">
				<!-- 문의하기 버튼 -->
				<button><input type="submit" value="문의하기"></button>
			</div>
		</form>


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

<script>
	// 테스트
	const editor = document.getElementById('editor');

	// div contentedittable 텍스트 저장 https://devlifetestcase.tistory.com/81
	const divList = $('#editor').find('div');
	const firstText = $('#editor').html().split('<div>')[0] + '\n';
	let html = firstText.trim() == '' ? '' : firstText;

	divList.map(i => html += $(divList[i]).html() + '\n');
	html = html.replaceAll('<b>','')
				.replaceAll('</b>','')
				.replaceAll('&nbsp;',' ')
				.replaceAll('<br>','');
</script>

</html>