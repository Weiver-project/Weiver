<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="config.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name=“viewport” content=“width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no”>
    <title>inquiryDetail</title>

    <!--css 연결-->
    <link rel="stylesheet" href="/css/inquiry.css">
    <link rel="stylesheet" href="/css/public.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body>
    <!-- header -->
    <header>
        <img src="/img/logo.png" alt="logo" height="70" width="300">
        
        <!-- 타이틀 -->
        <div class="title">
            <div class="back">
                <a href="${baseURL}/inquiry/inquiryMain"><i class="bi bi-chevron-left" style="color: #EFEFEF"></i></a>
            </div>
            <div class="name">문의하기</div>
        </div>
    </header>
    
    <div class="page">
        <!-- 문의 게시글 -->
        <div class="text_box">

            <!-- ID -->
            <div class="data_show">
                <p>제목</p>
                <div class="name">${inquiry.title}</div>
            </div>

            <div class="data_show">
                <p>문의사항</p>
                <textarea id="inquiry_text" class="inquiry_text" style="font-family: 'Pretendard-Regular', sans-serif;" disabled>${inquiry.content}</textarea>
            </div>
            <c:if test="${not empty inquiry.answer}">
                <div class="data_show">
                    <p>관리자 답변</p>
                    <div class="answer">${inquiry.answer.answer}</div>
                </div>

                <div class="data_show">
                    <p class="state">답변완료</p>
                </div>
            </c:if>
            <c:if test="${empty inquiry.answer}">
                <div class="data_show">
                    <p class="state">답변대기</p>
                </div>
            </c:if>
        </div>
    </div>

    <!-- footer -->
    <footer>Copyright Weiver 2023 All Rights Reserved</footer>

    <!-- navibar -->
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
    <script type="text/javascript">
        /* 문의사항 세로 크기 자동 조절 */
        function resize() {
            let textarea = document.getElementById("inquiry_text");

            let scrollHeight = textarea.scrollHeight;
            let style = window.getComputedStyle(textarea);
            let borderTop = parseInt(style.borderTop);
            let borderBottom = parseInt(style.borderBottom);

            textarea.style.height = (scrollHeight + borderTop + borderBottom)+"px";
        }

        window.addEventListener("load", resize);
        window.onresize = resize;
    </script>
</body>
</html>