<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>passwordUpdate</title>

    <!--css 연결-->
    <link rel="stylesheet" href="/css/login.css">
    <link rel="stylesheet" href="/css/public.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <!-- axios -->
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
    <!-- header -->
    <header>
        <img src="/img/logo.png" alt="logo" height="70" width="300">
    
        <!-- 타이틀 -->
        <div class="title">
            <div class="back">
                <a href="http://3.36.252.181:8081/mypage/setting"><i class="bi bi-chevron-left"></i></a>
            </div>
            <div class="name">비밀번호 변경</div>
        </div>
    </header>
    
    <div class="page">

            
        <form id="updatePasswordForm" action="http://3.36.252.181:8081/mypage/updatePW" method="POST">
            <!-- 입력 칸 -->
            <div class="text_input">

                <!-- ID -->
                <div class="ID">
                    <p>ID (이메일)</p>
                    <input name="userId" type="email" readonly value="${requestScope.userInfo.id}">
                </div>

                <!-- 닉네임 -->
                <div class="data_input">
                    <p>기존 비밀번호</p>
                    <input name="myPw" type="password" placeholder="비밀번호를 입력주세요" required>
                </div>

                <div class="data_input">
                    <p>새 비밀번호</p>
                    <input name="newPw" id="newPw" type="password" placeholder="새 비밀번호를 입력주세요" required>
                </div>

                <div class="data_input">
                    <p>새 비밀번호 확인</p>
                    <input name="checkPw" id="checkPw" type="password" placeholder="새 비밀번호를 다시 입력해주세요" required>
                </div>

            </div>

        </form>
    </div>
    <!-- 저장 -->
    <div class="btn">
        <button class="submit_btn" type="submit" disabled>저장</button>
    </div>

    <!-- footer -->
    <footer>Copyright Weiver 2023 All Rights Reserved</footer>


    <!-- navibar -->
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
    
    <script type="text/javascript">
        /* 버튼 활성화 */
        $(document).ready( function () {
            $('#newPw, #checkPw').change(function () {
                $(".submit_btn").attr("disabled", false);
            });
        });

        /* 비밀번호 확인 */
        const Button = document.querySelector(".submit_btn");
        const Form = document.querySelector("#updatePasswordForm");
        Button.addEventListener("click", (event) => {
            event.preventDefault();

            if(Button.checkValidity()) {
                const formData = new FormData(Form);

                axios.post("updatePW", formData)
                    .then(response => {
                        const data = response.data;
                        if(response.status === 200) {
                            alert(data);
                            window.location.href = "http://3.36.252.181:8081/mypage/myinfo";
                        }
                    })
                    .catch((error) => {
                        const errorMessage = error.response.data;
                        alert(errorMessage);
                    });
            } else {
                Form.reportValidity();
            }
        });
    </script>
</body>
</html>