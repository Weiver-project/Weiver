<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>inquiry</title>

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
                <a href="javascript:history.back()"><i class="bi bi-chevron-left" style="color: #EFEFEF"></i></a>
            </div>
            <div class="name">문의하기</div>
        </div>
    </header>
    
    <div class="page">

        
        <!-- 글 작성 -->
        <div class="write">
            <button onclick="location.href='/inquiryForm'">작성하기</button>
        </div>

        <!-- 문의 목록 -->
        <div class="inquiry_board">
            <c:forEach var="inquiry" items="${inquiryList}">
                <div class="list">
                    <div class="inquiry">
                        <a href="/inquiryDetail/${inquiry.id}">
                            <p>${inquiry.createdTime}</p>
                            <p>${inquiry.title} </p>
                        </a>
                    </div>
                    <div class="check">
                        <c:if test="${ empty inquiry.answer }">
                            <p>답변대기</p>
                        </c:if>
                        <c:if test="${ not empty inquiry.answer }">
                            <p>답변완료</p>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- footer -->
    <footer>Copyright Weiver 2023 All Rights Reserved</footer>

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
</body>
</html>