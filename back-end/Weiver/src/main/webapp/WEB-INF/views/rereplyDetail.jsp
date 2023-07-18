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
    <a href="#"><i class="bi bi-chevron-left"></i></a>
  </header>
  <article>
     <!-- 댓글 컨테이너 -->
            <!-- 작성된 댓글 (아이디, 댓글, 좋아요 아이콘, 대댓글 링크, 버튼) -->
            
            <br>
            <c:forEach var="reply" items="${reply}">
                <div class="commentAndBtn" data-comment-id="${reply.id}">
        <div class="commentIDAndContent">
            <div class="commentID">${reply.user.nickname}</div>
            <div class="commentContent" id="commentContent_${reply.id}">${reply.content}</div>
            <div class="likeAndRecomment">
                <i class="bi-suit-heart"></i>
                <span>${reply.id}</span>
                <a href="#" style="text-decoration: none;">
                    <span class="recommentBtn">대댓글</span>
                </a>
                        </div>
                    </div>
                    </div>

                <!-- 대댓글 컨테이너 -->
                <c:forEach var="rereply" items="${rereply}">
                    <c:if test="${rereply.reply.id == reply.id}">
                        <div class="recommentWrap">
                            <div class="recommentGroup">
                                <i class="bi-arrow-return-right"></i>
                                <div class="recommentInfo">
                                    <div class="commentID">${rereply.user.nickname}</div>
                                    <div class="commentContent">${rereply.content}</div>
                                    <div class="likeAndRecomment">
                                        <i class="bi-suit-heart"></i>
                                        <span>${rerepliesForReply.size()}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="commentBtnGroup">
                                <button type="button" class="replyUpdateBtn" data-id="${reply.id}">수정</button>
                                <button type="button" class="replyDeleteBtn" data-id="${reply.id}">삭제</button>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </c:forEach>
  </article>
  <form class="input-reple">
    <input type="text">
    <input type="submit">
  </form>
  <footer>Copyright Weiver 2023 All Rights Reserved</footer>
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