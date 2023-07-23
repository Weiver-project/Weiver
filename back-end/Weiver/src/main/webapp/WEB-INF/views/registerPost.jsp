<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>글 작성 페이지</title>
	
	<!--css 연결-->
	<link rel="stylesheet" href="/css/posting.css">
    <link rel="stylesheet" href="/css/public.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body>
<script type="text/javascript">

var musicalsData = [
    <c:forEach var="musical" items="${musicals}">
        {
            id: '${musical.id}',
            title: `${musical.title.replace('\\',"")}`,
            theater: '${musical.theater}',
            posterImage: '${musical.posterImage}',
            stDate: '${musical.stDate}',
            edDate: '${musical.edDate}'
        },
    </c:forEach>
];

function handleMusicalItemClick(musicalId) {
    console.log("Clicked on musical with ID:", musicalId);

    // Hide the search box and musical list container
    var searchBox = document.getElementById("searchBox");
    searchBox.style.display = "none";

    var musicalInfoContainer = document.getElementById("musicalInfoContainer");
    musicalInfoContainer.style.display = "none";

    // Show the musical information container
    var musicalDetailContainer = document.getElementById("musicalDetailContainer");
    musicalDetailContainer.style.display = "block";
    musicalDetailContainer.style.backgroundColor = "#172036";
    musicalDetailContainer.style.padding = "20px";
    musicalDetailContainer.style.borderRadius = "10px";
    musicalDetailContainer.style.marginTop = "10px";
    musicalDetailContainer.style.color = "#fff";
    musicalDetailContainer.style.fontSize = "18px";
    musicalDetailContainer.style.textAlign = "center";

    // Find the selected musical by its ID in the musicalsData array
    var selectedMusical = musicalsData.find(function (musical) {
        return musical.id === musicalId;
    });

    console.log("selectedMusical: ", selectedMusical);

    // Populate the musical information container with the details
    if (selectedMusical) {
    
        var musicalPosterElement = document.createElement("img");
        musicalPosterElement.src = selectedMusical.posterImage;
        musicalPosterElement.alt = "Musical Poster";
        musicalPosterElement.style.height = "120px";
        musicalPosterElement.style.width = "85px";

        var musicalTitleElement = document.createElement("h2");
        musicalTitleElement.textContent = selectedMusical.title;
        musicalTitleElement.style.fontSize = "25px";
        musicalTitleElement.style.backgroundColor = "#172036";
        musicalTitleElement.style.textAlign = "center";
        musicalTitleElement.style.marginTop = "-5px";

        var musicalTheaterElement = document.createElement("p");
        musicalTheaterElement.textContent = selectedMusical.theater;
        musicalTheaterElement.style.backgroundColor = "#172036";
        musicalTheaterElement.style.fontSize = "15px";
        musicalTheaterElement.style.textAlign = "center";

        // Append the elements to the musicalDetailContainer
        musicalDetailContainer.innerHTML = ""; // Clear previous content
        musicalDetailContainer.appendChild(musicalTitleElement);
        musicalDetailContainer.appendChild(musicalTheaterElement);
        musicalDetailContainer.appendChild(musicalPosterElement);

        // Set the selected musical's ID to the hidden input
        var musicalIdInput = document.getElementById("musicalIdInput");
        musicalIdInput.value = musicalId;
    }
}


    function handleSearchBoxChange() {
        var searchTerm = document.getElementById("searchBox").value;

        // 뮤지컬 키워드에 해당하는 뮤지컬 리스트 필터링
        var filteredMusicals = musicalsData.filter(function (musical) {
            return musical.title.toLowerCase().includes(searchTerm.toLowerCase());
        });

        // 검색 리스트 초기화 후 새로운 검색 결과로 다시 채우기
        var musicalInfoContainer = document.getElementById("musicalInfoContainer");
        musicalInfoContainer.innerHTML = "";

        filteredMusicals.forEach(function (musical) {
            var musicalElement = document.createElement("li");

            var imgElement = document.createElement("img");
            imgElement.src = musical.posterImage;
            imgElement.alt = "poster";
            imgElement.style.marginRight = "20px";
            imgElement.style.height = "120px";
            imgElement.style.width = "85px";
            imgElement.style.float = "left";

            var h2Element = document.createElement("h2");
            h2Element.textContent = musical.title;
            h2Element.style.padding = "0";
            h2Element.style.marginTop = "10px";
            h2Element.style.textAlign = "left";
            h2Element.style.fontSize = "20px";
            h2Element.style.backgroundColor = "#172036";

            var spanElement = document.createElement("span");
            spanElement.textContent = musical.stDate + " ~ " + musical.edDate;
            spanElement.style.backgroundColor = "#172036";
            spanElement.style.fontSize = "15px";

            musicalElement.appendChild(imgElement);
            musicalElement.appendChild(h2Element);
            musicalElement.appendChild(spanElement);

            musicalElement.style.backgroundColor = "#172036";
            musicalElement.style.height = "120px";
            musicalElement.style.padding = "10px";
            musicalElement.style.borderRadius = "10px";
            musicalElement.style.marginBottom = "10px";
            musicalElement.style.marginTop = "10px";

            musicalElement.addEventListener("click", function () {
                handleMusicalItemClick(musical.id);
            });

            musicalInfoContainer.appendChild(musicalElement);
        });

        musicalInfoContainer.style.display = "block";
    }

    function postTypeChange() {
        let selectFormType = document.getElementById("selectFormType");

        let formType = selectFormType.options[selectFormType.selectedIndex].value;
        if (formType === 'Review') {
            document.getElementById("reviewForm").style.display = "";
            document.getElementById("reviewPerformance").required = "required";
            console.log("Review");
        } else if (formType === 'Chat') {
            document.getElementById("reviewForm").style.display = "none";
            document.getElementById("reviewPerformance").required = "";
            console.log("Chat");
        }
    }

   
</script>


	<!-- 전체 컨테이너 -->
    <div class="backgroudBox">

        <!-- 헤더 -->
        <header>
            <img src="/img/logo.png" alt="logo" height="70" width="300" />
        </header>

        <!-- 뒤로가기 버튼 -->
        <div class="backBtn">
            <a href="http://3.36.252.181:8081/community">
                <i class="bi-chevron-left"></i>
            </a>
			<div class="nameTag">
				<h1>글 작성하기</h1>
			</div>
        </div>

	    <br>

		<form action="http://3.36.252.181:8081/community/board" method="post" enctype="multipart/form-data">
				<div class="selectbtn">	
					<!-- 셀렉트 버튼(리뷰, 잡담) -->
					<select id="selectFormType" name="type" onchange="postTypeChange()">
						<option value="select">글 종류</option>
                        <option value="Chat">잡담</option>
                        <option value="Review">리뷰</option>
					</select>
				</div>

				<br>
				
				<!-- 제목 작성 칸-->
				<span>제목</span>
				<div>
					<textarea class="title" name="title" id="title" required></textarea>
				</div>


				<!-- 작품 명 작성칸 -->
				<div align="left" id="reviewForm" style="display:none">
				    <br>
				    작품명
				    <div>
				        <input
				            type="text"
				            id="searchBox"
				            placeholder="작품명을 입력해주세요."
				            autocomplete="off"
				            oninput="handleSearchBoxChange()"
				        />
				    </div>
				    <div id="musicalInfoContainer">
				    </div>
				    <div id="musicalDetailContainer"  style="display: none;">
					</div>
				</div>
				
				
				<br>

				<!-- 내용 작성칸 -->
				<span>내용</span>
				
					<!-- 내용 작성칸(편집 버튼: 굵기, 이탤릭, 언더라인 등) -->
					<!--  <div class="editor-menu">
						<button id="btn-bold" name="reviewTool" type="button">
							<b>B</b>
						</button>
						<button id="btn-italic" name="reviewTool" type="button">
							<i>I</i>
						</button>
						<button id="btn-underline" name="reviewTool" type="button">
							<u>U</u>
						</button>
						<button id="btn-strike" name="reviewTool" type="button">
							<s>S</s>
						</button>
						<button id="btn-ordered-list" name="reviewTool" type="button">
							OL
						</button>
						<button id="btn-unordered-list" name="reviewTool" type="button">
							UL
						</button>
						<button id="btn-image" name="reviewTool" type="button">
							IMG
						</button>
					</div>-->

					
						<!-- 내용칸 -->
					<div>
						<textarea name="content" type="text" class="content" id="editor"></textarea>
					</div>
					
					<input type="hidden" name="musicalId" id="musicalIdInput" value="">
					
				
		    
				<br>
				<!-- 작성하기 버튼 -->
				<div class="nameTag">
				
					<input type="submit" value="작성하기" class="submit-btn">
				
				</div>
			</form>
			
			<!--  <form action="/upload" method="post" enctype="multipart/form-data">
		        <input type="file" id="imageUpload" name="file" required onchange="previewImage(event)">
		        <button type="submit">Upload</button>
		    </form>-->

			

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

</div>
</body>

</html>