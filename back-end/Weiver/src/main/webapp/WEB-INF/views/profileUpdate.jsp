<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>profileUpdate</title>
    <!--css 연결-->
    <link rel="stylesheet" href="/css/user.css">
    <link rel="stylesheet" href="/css/public.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>

    <div class="page">
        <!-- header -->
        <header>
            <img src="/img/logo.png" alt="logo" height="70" width="300">
        
            <!-- 타이틀 -->
            <div class="title">
                <div class="back">
                    <a href="http://3.36.252.181:8081/mypage/myinfo"><i class="bi bi-chevron-left"></i></a>
                </div>
                <div class="name">프로필 수정</div>
            </div>
        </header>
        

        <form action="http://3.36.252.181:8081/mypage/update" method="POST" enctype="multipart/form-data">

            <!-- 프로필 -->
            <div class="profileChange">
                <div class="profileChange_cut">
                    <img class="image-box" src="${requestScope.userInfo.profileImg}" alt="profile" height="150" width="150">
                    <input id="my-img" type="file" accept="image/*" name="profileImg">
                    <button id="my-button" class="change" type="button" onclick="onClickUpload();">변경</button>
                </div>
            </div>

            <!-- 입력 칸 -->
            <div class="text_input">

                <!-- ID -->
                <div class="ID">
                    <p>ID (이메일)</p>
                    <input name="userId" type="email" readonly value="${requestScope.userInfo.id}">
                </div>

                <!-- 닉네임 -->
                <div class="nickname">
                    <p>닉네임</p>
                    <input id="name" name="nickname" type="text" value="${requestScope.userInfo.nickname}">
                </div>

            </div>

            <div class="btn">
                <button class="submit_btn" type="submit" disabled>저장</button>
            </div>

        </form>
    </div>

    <!-- footer -->
    <footer>Copyright Weiver 2023 All Rights Reserved</footer>

    <!-- 저장 -->

    <!-- navibar -->
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
    <script type="text/javascript">

        /* 파일 버튼 - input 연결 */
        function onClickUpload() {
            let myimg = document.getElementById("my-img");
            myimg.click();
        }

        /* 버튼 활성화 */
        $(document).ready( function () {
            $('#my-img, #name').change(function () {
                $(".submit_btn").attr("disabled", false);
            });
        });

        /* 프로필 미리보기 */
        const fileDOM = document.querySelector('#my-img');
        const preview = document.querySelector('.image-box');

        fileDOM.addEventListener('change', () => {
            const reader = new FileReader();
            reader.onload = ({target}) => {
                preview.src = target.result;
            };
            reader.readAsDataURL(fileDOM.files[0]);
        });
    </script>
</body>
</html>