<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="config.jsp" %>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Weiver</title>

  <!--css 연결-->
  <link rel="stylesheet" href="/css/public.css">
  <link rel="stylesheet" href="/css/searchAll.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body class="container">
    <header>
      <img id="logo" src="/img/logo.png" alt="logo" height="70" width="300">
      <a href="/community"><i class="bi bi-chevron-left"></i></a>
    </header>
    <article>
        <!-- 검색 폼 -->
        <form class="search" action="${baseURL}/community/search" method="get">
            <input type="text" name="keyword" placeholder=" 검색어를 입력하세요">
            <input type="submit" value="검색">
        </form>
        <!-- 검색 결과 리스트 -->
        
        <ul class="community_list">
        	
            <c:forEach var="post" items="${searchResults}">
            <a href="${baseURL}/community/${post.id}">
                <li>
                    <c:if test="${post != null}">
                        <img src="${post.image}" alt="게시글 이미지" class="post-image">
                    </c:if>
                    <h6 class="post-title">${post.title}</h6>
                    <span class="post-content">${post.content}</span>
                </li>
               </a>
            </c:forEach>
        </ul>
         
    </article>
    <footer>Copyright Weiver  2023 All Rights Reserved</footer>
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

</html>