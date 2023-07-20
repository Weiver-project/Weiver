<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WIEVER - Muscial Detail</title>

     <!-- SWIPER 외부 라이브러리 연결-->
     <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
     <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>

    <!--css, js 연결-->
    <link rel="stylesheet" href="/css/musical.css">
    <link rel="stylesheet" href="/css/actor.css">
    <link rel="stylesheet" href="/css/swiper.css">
    <link rel="stylesheet" href="/css/public.css">
    <script src="/js/swiper.js"></script>

    <!--부트스트랩 아이콘 연결-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    
    <!-- jquery -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>

<header>
  <img src="../img/logo.png" alt="logo" height="70" width="300">
  <div class="title-container">
    <a href="javascript:history.back();" class="back-button">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
      </svg>
    </a>
    <h1 class="musical-title">${musical.getTitle()}</h1>
  </div>
</header>

<body>

      <div class="musical-info">
        <img src="${musical.getPosterImage()}" alt="Musical Poster" width="180" height="270">
        <table>
        <c:if test="${musical.getTheater() != null}">
       	  <tr>
            <td>장소</td>
            <td>${musical.getTheater()}</td>
          </tr>
        </c:if>
        <c:if test="${musical.getStDate() != null && musical.getEdDate() != null}">
       	  <tr>
            <td>공연 기간</td>
            <td>
            	<fmt:formatDate value="${musical.stDate}" pattern="yyyy-MM-dd" /> ~
  				<fmt:formatDate value="${musical.edDate}" pattern="yyyy-MM-dd" />
			</td>
          </tr>
        </c:if> 
        <c:if test="${musical.getRunningTime() != null}">
       	  <tr>
            <td>공연 시간</td>
            <td>${musical.getRunningTime()}</td>
          </tr>
        </c:if>
        <c:if test="${musical.getViewAge() != null}">
       	   <tr>
            <td>관람 연령</td>
            <td>${musical.getViewAge()}</td>
          </tr>
        </c:if>
        </table>
      </div>
      
      <div class="like-jjim-btn">
        <div class="button">
          <button onclick="addSubscirbe(${musical.getId()}, '찜했어요')">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmarks-fill" viewBox="0 0 16 16">
              <path d="M2 4a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v11.5a.5.5 0 0 1-.777.416L7 13.101l-4.223 2.815A.5.5 0 0 1 2 15.5V4z"/>
              <path d="M4.268 1A2 2 0 0 1 6 0h6a2 2 0 0 1 2 2v11.5a.5.5 0 0 1-.777.416L13 13.768V2a1 1 0 0 0-1-1H4.268z"/>
            </svg>
          </button>
          <span>찜하기</span>

        </div>
        <div class="button">
          <button onclick="addSubscirbe(${musical.getId()}, '봤어요')">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check2-all" viewBox="0 0 16 16">
              <path d="M12.354 4.354a.5.5 0 0 0-.708-.708L5 10.293 1.854 7.146a.5.5 0 1 0-.708.708l3.5 3.5a.5.5 0 0 0 .708 0l7-7zm-4.208 7-.896-.897.707-.707.543.543 6.646-6.647a.5.5 0 0 1 .708.708l-7 7a.5.5 0 0 1-.708 0z"/>
              <path d="m5.354 7.146.896.897-.707.707-.897-.896a.5.5 0 1 1 .708-.708z"/>
            </svg>
          </button>
          <span>봤어요</span>
        </div>
      </div>
      
      <h1 class="title">출연진 정보✨</h1>
      <div class="casting">
        <div class="swiper-container">
            <div class="swiper-wrapper">
               	<c:forEach items="${castingList}" var="casting"  varStatus="status">
	               	<c:if test="${status.index % 6 == 0}">
		                <div class="swiper-slide">
		                    <div class="profile-container">
                    </c:if>
		                        <div class="casting-actor" style="max-width:130px; margin-right: 20px; display: flex; flex-direction: column;">
		                            <a href="/actorDetail/${casting.id }"><img src="${casting.profileImage}" style="max-width: 90px; max-height: 90px;"></a>
		                            <span style="font-size: 8px; text-align: center;">${casting.name}</span>
		                            <span style="font-size: 8px; text-align: center;">${casting.role}</span>
		                        </div>
         			<c:if test="${status.index % 6 == 5 or status.last}">
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
    </div>

    <h1 class="title">유튜브 클립</h1>
    <div class="youtube-api">
      <div class="swiper-container">
          <div class="swiper-wrapper">
          	<c:forEach var="clip" items="${clips }" begin="0" end="3" step="1">
              <div class="swiper-slide">
                  <iframe id="ytplayer" type="text/html" width="720" height="405"
						src="https://www.youtube.com/embed/${clip}"
						frameborder="0" allowfullscreen></iframe>
              </div>
          	</c:forEach>
          </div>
      <!-- Add Pagination -->
      <div class="swiper-pagination"></div>
      <!-- Add Navigation -->
      <div class="swiper-button-next"></div>
      <div class="swiper-button-prev"></div>
    </div>
  </div>  
      
  <footer>Copyright Weiver 2023 All Rights Reserved</footer>

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

<script>
function addSubscirbe(musicalId, type){

    // 서버에 데이터 전송 (AJAX 사용)
    $.ajax({
        type: 'GET',
        url: '/addSubscribe/' + musicalId + "/" + type, // 찜 처리를 담당하는 컨트롤러 URL
        contentType: 'application/json'
        
    });
    
}

</script>

</body>
</html>
