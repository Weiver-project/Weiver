<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <div class="login-title">관리자 로그인</div>
    <!-- 로그인 입력 폼 -->
    <form id="login-form" action="http://3.36.252.181:8081/ad/loginTest" method="post">
      <input type="text" name="adminId" placeholder=" 관리자 아이디를 입력하세요" >
      <input type="password" name="adminPw" placeholder=" 비밀번호를 입력하세요" >
      <input class="submit" type="submit" value="Login" style="cursor: pointer;">
    </form>
    <!-- 회원가입, 비번 찾기 -->
    <div class="login-support">
      <a href="http://3.36.252.181:8081/admin/signup">회원가입</a>
    </div>
  </article>
<footer>Copyright Weiver 2023 All Rights Reserved</footer>

  <script>
  	const loginForm = document.querySelector("#login-form");
  	const userId = document.getElementsByName("adminId")[0];
    const userPw = document.getElementsByName("adminPw")[0];
    
    /* 폼 제출 이벤트 핸들러 */
    loginForm.addEventListener("submit", function (event) {
        const formData = new FormData(loginForm);
    
    	event.preventDefault(); // 기본 제출 동작 방지
    	
    	/* 로그인 axios 요청 */
        axios.post("/ad/loginTest", formData)
        		.then(response => {
        			const data = response.data;
        			if(response.status === 200) {
        				alert(data);
        				window.location.href = "http://3.36.252.181:8081/admin/main";
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