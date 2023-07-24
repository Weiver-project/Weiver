<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
  <link rel="stylesheet" href="/css/searchAll.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body class="container">
  <header>
    <img id="logo" src="/img/logo.png" alt="logo" height="70" width="300">
    <a href="http://3.36.252.181:8081/mypage/myinfo"><i class="bi bi-chevron-left"></i></a>
    <h2>작품 목록</h2>
    <!-- 검색 타입 지정 -->
    <div class="select">
      <button class="like" onclick="showMusicalList('찜', this)">찜했어요</button>
      <button class="viewed" onclick="showMusicalList('봤어요', this)">봤어요</button>
    </div>
    <hr>
  </header>
  <article>
      <!-- 결과 리스트 -->
      <ul class="musical_list">
        <div id="musical_Jjim">
          <c:forEach var="Jjim" items="${JjimList}">
            <li>
              <a href="http://3.36.252.181:8081/musical-detail/${Jjim.getId()}">
                <img src="${Jjim.getPosterImage()}" alt="poster">
                <h2>${Jjim.getTitle()}</h2>
                <c:if test="${Jjim.getStDate() != null && Jjim.getEdDate() != null}">
                  <span>
                    <fmt:formatDate value="${Jjim.getStDate()}" pattern="yyyy-MM-dd"/> ~
                    <fmt:formatDate value="${Jjim.getEdDate()}" pattern="yyyy-MM-dd"/>
                  </span>
                </c:if>
              </a>
            </li>
          </c:forEach>
        </div>
        <div id="musical_IsWatched" style="display: none">
          <c:forEach var="IsWatched" items="${WatchedList}">
            <li>
              <a href="http://3.36.252.181:8081/musical-detail/${IsWatched.getId()}">
                <img src="${IsWatched.getPosterImage()}" alt="poster">
                <h2>${IsWatched.getTitle()}</h2>
                <c:if test="${IsWatched.getStDate() != null && IsWatched.getEdDate() != null}">
                    <span>
                      <fmt:formatDate value="${IsWatched.getStDate()}" pattern="yyyy-MM-dd"/> ~
                      <fmt:formatDate value="${IsWatched.getEdDate()}" pattern="yyyy-MM-dd"/>
                    </span>
                </c:if>
              </a>
            </li>
          </c:forEach>
        </div>
      </ul>
  </article>
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
    <script src="/js/subscribe.js"></script>
    <script type="text/javascript">
      function showMusicalList(category, button) {
        var jjimList = document.getElementById("musical_Jjim");
        var isWatchedList = document.getElementById("musical_IsWatched");

        if(category === '찜') {
          jjimList.style.display = "block";
          isWatchedList.style.display = "none";
        } else if(category === '봤어요') {
          jjimList.style.display = "none";
          isWatchedList.style.display = "block";
        }
      }

    </script>
</body>

</html>