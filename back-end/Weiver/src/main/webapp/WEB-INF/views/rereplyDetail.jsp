<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Weiver</title>

  <!--css 연결-->
  <link rel="stylesheet" href="/css/public.css">
  <link rel="stylesheet" href="/css/community.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body class="container">
  <header>
    <img id="logo" src="/img/logo.png" alt="logo" height="70" width="300">
    <a href="javascript:history.back();"><i class="bi bi-chevron-left"></i></a>
  </header>
  <article>
    <!-- 댓글 컨테이너 -->
    <!-- 작성된 댓글 (아이디, 댓글, 좋아요 아이콘, 대댓글 링크, 버튼) -->

    <br>
    <div class="commentAndBtn" data-comment-id="${reply.id}">
      <div class="commentIDAndContent">
        <div class="commentID">${reply.user.nickname}</div>
        <div class="commentContent" id="commentContent_${reply.id}">${reply.content}</div>
        <!--<div class="likeAndRecomment">
          <i class="bi-suit-heart"></i>
          <span>${reply.id}</span>
        </div>  -->
      </div>
    </div>

    <!-- 대댓글 컨테이너 -->
    <c:forEach var="reReply" items="${rereply}">
      <div class="recommentWrap">
        <div class="recommentGroup">
          <i class="bi-arrow-return-right"></i>
          <div class="recommentInfo">
            <div class="commentID">${reReply.user.nickname}</div>
            <div class="commentContent">${reReply.content}</div>
            <!--<div class="likeAndRecomment">
              <i class="bi-suit-heart"></i>
              <span>${reReply.id}</span>
            </div>-->
          </div>
        </div>
      </div>
    </c:forEach>
  </article>
  <form class="input-reple" action="http://3.36.252.181:8081/community/insert/rereply/${reply.post.id}/${reply.id}" method="post">
    <input type="text"  name="content">
    <input type="submit">
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
</body>
</html>
