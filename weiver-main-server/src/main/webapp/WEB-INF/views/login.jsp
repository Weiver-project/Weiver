<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="config.jsp" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Weiver</title>

  <!--css 연결-->
  <link rel="stylesheet" href="/css/public.css">
  <link rel="stylesheet" href="/css/login.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

  <!-- axios -->
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>

<body class="container">
  <header><a><i></i></a></header>
  <article>
    <img id="logo" src="/img/logo.png" alt="logo" height="100" width="450">
    <div class="login-title">로그인</div>
    <!-- 로그인 입력 폼 -->
    <form id="login-form" action="${baseURL}/loginTest" method="post">
      <input type="email" name="userId" placeholder=" 이메일을 입력하세요" >
      <input type="password" name="userPw" placeholder=" 비밀번호를 입력하세요" >
      <input class="submit" type="submit" value="Login" style="cursor: pointer;">
    </form>
    <!-- 회원가입, 비번 찾기 -->
    <div class="login-support">
      <a href="${baseURL}/signup">회원가입</a>
      <a href="#">비밀번호를 잊으셨나요?</a>
    </div>
    <!-- 간편로그인 구분 선 -->
    <div class="dividing-line">
      <span>간편 로그인</span>
    </div>
    <!-- 간편 로그인 -->
    <div id="simple-login">
      <a href="#"><img class="google-login" src="/img/google_icon.png" alt="구글"></a>
      <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${kakaoAPIKey}&redirect_uri=${kakaoRedirectUrl}"><img class="kakao-login" src="/img/kakako-icon.png" alt="카카오"></a>
      <a href="#"><img class="naver-login" src="/img/naver-icon.png" alt="네이버"></a>
    </div>
  </article>
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

  <script>
  	const loginForm = document.querySelector("#login-form");
  	const userId = document.getElementsByName("userId")[0];
    const userPw = document.getElementsByName("userPw")[0];
    
    /* 폼 제출 이벤트 핸들러 */
    loginForm.addEventListener("submit", function (event) {
        const formData = new FormData(loginForm);
    
    	event.preventDefault(); // 기본 제출 동작 방지
    	
    	/* 로그인 axios 요청 */
        axios.post("${baseURL}/loginTest", formData)
        		.then(response => {
        			const data = response.data;
        			if(response.status === 200) {
        				alert(data);
        				window.location.href = "${baseURL}/main";
        			}
  	   			})
  	   			.catch((error) => {
  	   				const errorMessage = error.response.data;
  	   				alert(errorMessage);
  	   			});
   	});
  </script>
</body>

</html>