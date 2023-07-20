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

	<!-- 전체 컨테이너 -->
    <div class="backgroudBox">

        <!-- 헤더 -->
        <header>
            <img src="/img/logo.png" alt="logo" height="70" width="300" />
        </header>

        <!-- 뒤로가기 버튼 -->
        <div class="backBtn">
            <a href="/community">
                <i class="bi-chevron-left"></i>
            </a>
			<div class="nameTag">
				<h1>글 작성하기</h1>
			</div>
        </div>

	    <br>

		<form action="/community/board" method="post" enctype="multipart/form-data">
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
						<textarea class="title" name="reviewPerformance" id="reviewPerformance" oninput="searchPerformance()" required></textarea>
					</div>
				</div>

				<br>

				<!-- 내용 작성칸 -->
				<span>내용</span>

					<!-- 내용 작성칸(편집 버튼: 굵기, 이탤릭, 언더라인 등) -->
					<div class="editor-menu">
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
					</div>

						<!-- 내용칸 -->
					<div>
						<textarea name="content" type="text" class="content" id="editor"></textarea>
					</div>
				
				<br>
					<!-- 작성하기 버튼 -->
					<div class="nameTag">
					<button>
						<input type="submit" value="작성하기" class="submit-btn">
					</button>
					</div>
			</form>

<footer>Copyright Weiver 2023 All Rights Reserved</footer>

<nav>
    <a href="#"><i class="bi bi-house-door-fill"></i>
      <div>HOME</div>
    </a>
    <a href="/community"><i class="bi bi-chat-dots-fill"></i>
      <div>COMMUNITY</div>
    </a>
    <a href="#"><i class="bi bi-person-fill"></i>
      <div>MY PAGE</div>
    </a>
  </nav>

</div>
</body>

<script type="text/javascript">
	
function postTypeChange(){
	  let selectFormType = document.getElementById("selectFormType");
	  
	  let formType = selectFormType.options[selectFormType.selectedIndex].value;
	  if(formType == 'Review'){
		  document.getElementById("reviewForm").style.display="";
		  document.getElementById("reviewPerformance").required="required";

		  console.log("Review");
		  
	  }else if(formType == 'Chat'){
		  document.getElementById("reviewForm").style.display="none";
		  document.getElementById("reviewPerformance").required="";

		  console.log("Chat");
	  }
	  
}

function searchPerformance(){
	let performanceTitle = document.getElementById("reviewPerformance");
	
	axios
}

	// 테스트
	const editor = document.getElementById('editor');
    const btnBold = document.getElementById('btn-bold');
    const btnItalic = document.getElementById('btn-italic');
    const btnUnderline = document.getElementById('btn-underline');
    const btnStrike = document.getElementById('btn-strike');
    const btnOrderedList = document.getElementById('btn-ordered-list');
    const btnUnorderedList = document.getElementById('btn-unordered-list');

    btnBold.addEventListener('click', function () {
        setStyle('bold');
    });

    btnItalic.addEventListener('click', function () {
        setStyle('italic');
    });

    btnUnderline.addEventListener('click', function () {
        setStyle('underline');
    });

    btnStrike.addEventListener('click', function () {
        setStyle('strikeThrough')
    });

    btnOrderedList.addEventListener('click', function () {
        setStyle('insertOrderedList');
    });

    btnUnorderedList.addEventListener('click', function () {
        setStyle('insertUnorderedList');
    });

    function setStyle(style) {
        document.execCommand(style);
        focusEditor();
    }

    // 버튼 클릭 시 에디터가 포커스를 잃기 때문에 다시 에디터에 포커스를 해줌
    function focusEditor() {
        editor.focus({ preventScroll: true });
    }

    const btnImage = document.getElementById('btn-image');
    const imageSelector = document.getElementById('img-selector');


    btnImage.addEventListener('click', function () {
        imageSelector.click();
    });

    imageSelector.addEventListener('change', function (e) {
        const files = e.target.files;
        if (!!files) {
            insertImageDate(files[0]);
        }
    });

    function insertImageDate(file) {
        const reader = new FileReader();
        reader.addEventListener('load', function (e) {
            focusEditor();
            document.execCommand('insertImage', false, `${reader.result}`);
        });
        reader.readAsDataURL(file);
    }

</script>

</html>
