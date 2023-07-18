<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>WIEVER - MainPage</title>

  <!-- SWIPER 외부 라이브러리 연결-->
  <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
  <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>

  <!--css, js 연결-->
  <link rel="stylesheet" href="/css/musical.css">
  <link rel="stylesheet" href="/css/actor.css">
  <link rel="stylesheet" href="/css/community.css">
  <link rel="stylesheet" href="/css/searchAll.css">
  <link rel="stylesheet" href="/css/swiper.css">
  <link rel="stylesheet" href="/css/public.css">
  <script src="/js/swiper.js"></script>

  <!--부트스트랩 아이콘 연결-->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body>

<header><img src="../img/logo.png" alt="logo" height="70" width="300"></header>

<!--검색 창-->
<div class="mainSearch-container">
  <input type="text">
  <button>
    <a href="검색페이지 URL">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
           class="bi bi-search" viewBox="0 0 16 16">
        <path
                d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
      </svg>
    </a>
  </button>
</div>

<!--인기 뮤지컬 리스트 최대 3개 출력-->
<h1 class="title">인기 뮤지컬 🔥</h1>
<div class="popular_musical_list">
  <ul>
    <li>${popularMusicals.get(0).getTitle()}</li>
    <li>${popularMusicals.get(1).getTitle()}</li>
    <li>${popularMusicals.get(2).getTitle()}</li>
  </ul>
</div>

<!--커뮤니티 인기글 3개씩 총 9개 출력-->
<h1 class="title">커뮤니티 인기글 🔥</h1>
<div class="popular_community">
  <div class="swiper-container">
    <div class="swiper-wrapper">
      <div class="swiper-slide">
        <div class="card-container">
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 1</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 2</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 3</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
        </div>
      </div>
      <div class="swiper-slide">
        <div class="card-container">
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 4</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 5</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 6</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
        </div>
      </div>
      <div class="swiper-slide">
        <div class="card-container">
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 7</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 8</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">카드 9</h3>
            </div>
            <div class="card-footer">
              <span class="author">작성자</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Add Pagination -->
    <div class="swiper-pagination"></div>
    <!-- Add Navigation -->
    <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div>
  </div>
</div>

<!--오늘의 배우 2개씩 총 8개 출력-->
<h1 class="title">오늘의 배우✨</h1>
<div class="today_actor">
  <div class="actor_img">
    <img src="image1.jpg" alt="이미지 1">
    <span class="actor-name">배역 명 뿌려주세요</span>
  </div>
  <div class="actor-details">
    <div class="swiper-container">
      <div class="swiper-wrapper">
        <div class="swiper-slide">
          <div class="poster-container">
            <div class="poster">
              <img src="image1.jpg" alt="이미지 1">
            </div>
            <div class="poster">
              <img src="image2.jpg" alt="이미지 2">
            </div>
          </div>
        </div>
        <div class="swiper-slide">
          <div class="poster-container">
            <div class="poster">
              <img src="image4.jpg" alt="이미지 3">
            </div>
            <div class="poster">
              <img src="image5.jpg" alt="이미지 4">
            </div>
          </div>
        </div>
        <div class="swiper-slide">
          <div class="poster-container">
            <div class="poster">
              <img src="image7.jpg" alt="이미지 5">
            </div>
            <div class="poster">
              <img src="image8.jpg" alt="이미지 6">
            </div>
          </div>
        </div>
      </div>
      <!-- Add Pagination -->
      <div class="swiper-pagination"></div>
      <!-- Add Navigation -->
      <div class="swiper-button-next"></div>
      <div class="swiper-button-prev"></div>
    </div>
  </div>
</div>






<!--공연 중인 뮤지컬 4개씩 총 8개 출력-->
<h1 class="title">공연 중인 뮤지컬 🤩</h1>
<div class="progress-musical">
  <div class="swiper-container">
    <div class="swiper-wrapper">
      <c:forEach var="performingMusical" items="${performingMusicals}" varStatus="status">
        <c:if test="${status.index % 4 == 0}">
          <div class="swiper-slide">
            <div class="poster-container">
        </c:if>
        <div class="poster" id="${performingMusical.getId()}">
          <img src="${performingMusical.getPosterImage()}" alt="image ${status.index + 1}">
        </div>
        <c:if test="${status.index % 4 == 3 or status.last}">
            </div>
          </div>
        </c:if>
      </c:forEach>
    </div>
  </div>
</div>



    <!-- Add Pagination -->	
    <div class="swiper-pagination"></div>
    <!-- Add Navigation -->
    <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div>
  </div>
</div>



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

<footer>Copyright Weiver 2023 All Rights Reserved</footer>




</body>

</html>
