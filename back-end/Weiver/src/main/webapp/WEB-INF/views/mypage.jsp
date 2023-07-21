<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Page</title>

    <!--css 연결-->
    <link rel="stylesheet" href="/css/user.css">
    <link rel="stylesheet" href="/css/public.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body>
    
    <div class="page">
        <!-- header -->
        <header><img src="/img/logo.png" alt="logo" height="70" width="300"></header>

        <!-- 설정 -->
        <div class="setting">
            <a href="profileUpdate"><i class="bi bi-person-lines-fill"></i></a>
            <a href="setting"><i class="bi bi-gear-wide"></i></a>
        </div>
    
        <!-- 프로필 -->
        <div class="profile">
            <img src="${requestScope.userInfo.profileImg}" alt="profile" height="150" width="150">
            <div>${requestScope.userInfo.nickname}</div>
        </div>
    
        <!-- 찜했어요/ 봤어요 -->
        <div class="subscribe">
            <a href="mySubscribe">
                <div>${requestScope.userInfo.countJjim}</div>
                찜했어요
            </a>
        
            <div class="line"></div>
                
            <a href="mySubscribe">
                <div>${requestScope.userInfo.countIsWatched}</div>
                봤어요
            </a>
            
        </div>
    
        <!-- 내가 쓴 글/ 내가 쓴 댓글/ 좋아요한 글 -->
        <div class="board">
            
            <a href="myBoard">
                <div>내가 쓴 글</div>
                <div>
                    ${requestScope.userInfo.countPosts}
                    <i class="bi bi-chevron-right"></i>
                </div>
            </a>
            
            <hr>
            
            <a href="myComment">
                <div>내가 쓴 댓글</div>
                <div>
                    ${requestScope.userInfo.countReplies}
                    <i class="bi bi-chevron-right"></i>
                </div>
            </a>
            
            <hr>
            
            <a href="myLike">
                <div>좋아요한 글</div>
                <div>
                    ${requestScope.userInfo.countPostLikes}
                    <i class="bi bi-chevron-right"></i>
                </div>
            </a>
            
        </div>

    </div>

    <!-- footer -->
    <footer>Copyright Weiver 2023 All Rights Reserved</footer>

    <!-- navibar -->
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
</body>
</html>