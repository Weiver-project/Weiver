<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>passwordUpdate</title>

    <!--css 연결-->
    <link rel="stylesheet" href="/css/signup.css">
    <link rel="stylesheet" href="/css/public.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <!-- axios -->
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

</head>

<body>
    <!-- header -->
    <header>
        <img src="/img/logo.png" alt="logo" height="70" width="300">

        <!-- 타이틀 -->
        <div class="title">
            <div class="back">
                <a href="http://3.36.252.181:8081/admin/login"><i class="bi bi-chevron-left"></i></a>
            </div>
        </div>
    </header>

    <div class="page">
        <div class="signupText">
            <h1>BECOME A WEIVER MEMBER</h1>
            <p>환영합니다!</p>
            <p>회원 가입을 통해 WEIVER의 모든 기능을 이용해보세요</p>
        </div>

        <!-- 회원가입 form -->
        <form id="signupForm" action="http://3.36.252.181:8081/ad/signupTest" method="post">
            <!-- ID -->
            <div class="ID">
                <input id="adminId" class="info_input" type="text" name="adminId" placeholder="아이디를 입력해주세요" required style="text-transform: lowercase">
            </div>

            <!-- 닉네임 -->
            <div class="data_input">
                <input class="info_input" type="password" name="adminPw" placeholder="비밀번호를 입력주세요" required>
            </div>

            <div class="data_input">
                <input class="info_input" type="password" name="adminPwCheck" placeholder="비밀번호를 다시 입력해주세요" required>
            </div>
            <div class="data_input">
                <input class="info_input" type="text" name="adminName" placeholder="이름을 입력해주세요" required>
            </div>
        </form>

        <!-- 가입하기 버튼 -->
       	<div class="btn">
            <button class="signupBtn" type="submit">가입하기</button>
        </div>

        <!-- footer -->
        <footer>Copyright Weiver 2023 All Rights Reserved</footer>

        <!-- script -->
        <script>
            /* 회원가입 */
			const signupButton = document.querySelector(".signupBtn");
			const signupForm = document.querySelector("#signupForm");
			const signupCheckBoxes = document.querySelectorAll(".signupCheckBox");
			const emailValue = document.querySelector("#adminId")
			
			/* 소문자로 변형 */
			emailValue.addEventListener("input", (e) => {
				e.target.value = e.target.value.toLowerCase();
				console.log(e.target.value);
			})
			
			/* 회원가입 axios 요청 */
			signupButton.addEventListener("click", (event) => {
			    event.preventDefault(); // 기본 양식 제출을 방지
			
			    if (signupForm.checkValidity()) { // 양식이 유효한지 확인
			        const formData = new FormData(signupForm);
			
			        axios.post("/ad/signupTest", formData)
			            .then(response => {
			                const data = response.data;
			                if (response.status === 200) {
		                        alert(data);
		                        window.location.href = "http://3.36.252.181:8081/admin/login";
			                } 
			            })
			            .catch((error) => {
			            	const errorMessage = error.response.data;
			                alert(errorMessage);
			            });
			    } else {
			        signupForm.reportValidity(); // 양식이 유효하지 않으면 유효성 검사 메시지 표시
			    }
			});

        </script>
</body>

</html>