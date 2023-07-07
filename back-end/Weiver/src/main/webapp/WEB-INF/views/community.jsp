<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WEIVER - 커뮤니티 페이지</title>

    <!-- CSS 연결 -->
    <link rel="stylesheet" href="/css/community.css">
    <link rel="stylesheet" href="/css/searchAll.css">
    <link rel="stylesheet" href="/css/swiper.css">
    <link rel="stylesheet" href="/css/public.css">

    <!-- JavaScript 및 라이브러리 연결 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"
        integrity="sha384-fbbOQedDUMZZ5KreZpsbe1LCZPVmfTnH7ois6mU1QK+m14rQ1l2bGBq41eYeM/fS"
        crossorigin="anonymous"></script>
    <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
    <script src="../js/swiper.js"></script>
</head>

<body>
    <div class="backgroudBox">
        <header>
            <img src="/img/logo.png" alt="logo" height="70" width="300" />
        </header>
        <div class="pageName" style="margin-bottom: 15px;">커뮤니티</div>
        <div>
            <form class="communittySearch" action="communittySearch" method="post">
                <input type="text">
                <button type="submit">검색</button>
            </form>
        </div>
        <p style="font-weight:bold; font-size: 17px;">인기글🔥</p>
        <div class="popular_community">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <div class="swiper-slide">
                        <div class="card-container">
                            <c:forEach var="card" items="${cards}">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">${card.title}</h3>
                                    </div>
                                    <div class="card-footer">
                                        <span class="author">${card.author}</span>
                                    </div>
                                </div>
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

        <hr style="margin-top: 22px; margin-bottom: 22px; color: #D4D9E1;">

        <p style="font-weight:bold; font-size: 17px; margin-bottom: 12px;">카테고리</p>
        <div>
            <div class="btnGroup">
                <div class="categoryGroup">
                    <button style="background-color: #4263EB;">전체</button>
                    <button style="background-color: #466093;">리뷰</button>
                    <button style="background-color: #466093;">잡담</button>
                </div>
                <button class="writeBtn">글 작성하기</button>
            </div>
            <div class="postAndUserInfo">
                <div class="postList">
                    <c:forEach var="post" items="${posts}">
                        <div class="postWrap-main">
                            <p>${post.author}</p>
                            <h2>${post.title}</h2>
                            <p>${post.content}</p>
                            <img src="${post.image}" alt="게시글 이미지">
                            <div class="iconGroup">
                                <div>
                                    <i class="bi-eye"></i>
                                    <span>${post.views}</span>
                                </div>
                                <div>
                                    <i class="bi-chat"></i>
                                    <span>${post.comments}</span>
                                </div>
                                <div>
                                    <i class="bi-suit-heart"></i>
                                    <span>${post.likes}</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="sideGroup">
                    <div class="userInfoAndLoginBtn">
                        <div class="userInfo">
                            <p class="userInfoID">${userInfo.id}</p>
                            <div class="myWrited">
                                <div class="myPost">
                                    <p>내가 쓴 글</p>
                                    <p><a href="" style="text-decoration: none;">${userInfo.posts}</a></p>
                                </div>
                                <div class="myComment">
                                    <p>내가 쓴 댓글</p>
                                    <p><a href="" style="text-decoration: none;">${userInfo.comments}</a></p>
                                </div>
                            </div>
                        </div>
                        <button class="loginBtn">로그인 버튼</button>
                    </div>
                    <i class="bi bi-arrow-up-circle"></i> 
                </div>
            </div>
        </div>
    </div>

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