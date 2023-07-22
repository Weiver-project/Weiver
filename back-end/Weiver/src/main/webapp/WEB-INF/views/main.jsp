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
	<form action="/musical-search">
  <input type="text" name="keyword" style="border-radius: 8px; background: #25304A;">
  <button>
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
           class="bi bi-search" viewBox="0 0 16 16">
        <path
                d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
      </svg>
    </a>
  </button>
  </form>
</div>

<!--인기 뮤지컬 리스트 최대 3개 출력-->
<h1 class="title">인기 뮤지컬 🔥</h1>
<div class="popular_musical_list">
  <ul>
  <c:forEach var="musical" items="${popularMusicals}" varStatus="status">
  	<a href="/musical-detail/${musical.id}"><li>${status.index+1} ${musical.title}</li></a>
  </c:forEach>
  </ul>
</div>

<!--커뮤니티 인기글 3개씩 총 9개 출력-->
<h1 class="title">커뮤니티 인기글 🔥</h1>
<div class="popular_community">
		    <div class="swiper-container">
		        <div class="swiper-wrapper">
		            <div class="swiper-slide">
		                <div class="card-container">
		                    <c:forEach var="card" items="${bestPost}" varStatus="status">
		                        <c:if test="${status.index < 3}">
		                            <a href="/community/${card.id}">
		                                <div class="card">
		                                    <div class="card-header">
		                                        <h3 class="card-title">${card.title}</h3>
		                                    </div>
		                                    <div class="card-footer">
		                                        <span class="author">${card.user.nickname}</span>
		                                    </div>
		                                </div>
		                            </a>
		                        </c:if>
		                    </c:forEach>
		                </div>
		            </div>
		            <div class="swiper-slide">
		                <div class="card-container">
		                    <c:forEach var="card" items="${bestPost}" varStatus="status">
		                        <c:if test="${status.index >= 3 and status.index < 6}">
		                            <a href="/community/${card.id}">
		                                <div class="card">
		                                    <div class="card-header">
		                                        <h3 class="card-title">${card.title}</h3>
		                                    </div>
		                                    <div class="card-footer">
		                                        <span class="author">${card.user.nickname}</span>
		                                    </div>
		                                </div>
		                            </a>
		                        </c:if>
		                    </c:forEach>
		                </div>
		            </div>
		            <div class="swiper-slide">
		                <div class="card-container">
		                    <c:forEach var="card" items="${bestPost}" varStatus="status">
		                        <c:if test="${status.index >= 6}">
		                            <a href="/community/${card.id}">
		                                <div class="card">
		                                    <div class="card-header">
		                                        <h3 class="card-title">${card.title}</h3>
		                                    </div>
		                                    <div class="card-footer">
		                                        <span class="author">${card.user.nickname}</span>
		                                    </div>
		                                </div>
		                            </a>
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

<!--오늘의 배우 2개씩 총 8개 출력-->
<h1 class="title">추천 배우✨</h1>
<div class="today_actor" style="max-height: 180px">
  <div class="actor_img">
    <div>
    	<a id="actorInfo" href="/actorDetail/${randomActor.id}">
    		<c:if test="${not empty randomActor.profileImage}">
    		<img id="actorImage" src="${randomActor.profileImage}" style="border-radius: 50%; width:180px; height: 180px;">
    		</c:if>
		   	<span class="actor-name">${randomActor.name}</span>
    	</a>
    </div>
  </div>
  <div class="actor-details" style="max-height: 180px;">
    <div class="swiper-container">
      <div class="swiper-wrapper">
       	<c:forEach items="${limitedMusicalList}" var="limitedMusical" varStatus="status">
       		<c:if test="${status.index % 2 == 0}">
       			<div class="swiper-slide">
	          		<div class="poster-container"> 
       		</c:if>
	            <div class="poster">
	            <a href="/musical-detail/${limitedMusical.getId()}"><img src="${limitedMusical.getPosterImage()}" alt="${limitedMusical.getTitle()}"></a>
	            </div>
               <c:if test="${status.index % 2 == 1 or status.last}">
		           </div>
		       </div>
               </c:if>
    		</c:forEach>
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
          <a href="/musical-detail/${performingMusical.getId()}"><img src="${performingMusical.getPosterImage()}" alt="image ${status.index + 1}"></a>
        </div>
        <c:if test="${status.index % 4 == 3 or status.last}">
            </div>
          </div>
        </c:if>
      </c:forEach>
    </div>
     <!-- Add Pagination -->	
    <div class="swiper-pagination"></div>
    <!-- Add Navigation -->
    <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div>
  </div>
</div>


<nav>
  <a href="/main"><i class="bi bi-house-door-fill"></i>
    <div>HOME</div>
  </a>
  <a href="/community"><i class="bi bi-chat-dots-fill"></i>
    <div>COMMUNITY</div>
  </a>
  <a href="/mypage/myinfo"><i class="bi bi-person-fill"></i>
    <div>MY PAGE</div>
  </a>
</nav>

<footer>Copyright Weiver 2023 All Rights Reserved</footer>



<script type="text/javascript">
	const actorInfo = document.getElementById('actorInfo');
	const actorImage = document.getElementById('actorImage');
	const spanElement = document.createElement('span');
	
	actorImage.onerror = () => { 
		actorImage.remove();
		spanElement.textContent = '배우 이미지가 없습니다.';
		actorInfo.appendChild(spanElement);
	}
</script>
</body>

</html>
