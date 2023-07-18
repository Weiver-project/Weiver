<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>passwordUpdate</title>

    <!--css 연결-->
    <link rel="stylesheet" href="/css/signup.css">
    <link rel="stylesheet" href="/css/public.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <!-- axios -->
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

</head>

<body>
    <!-- header -->
    <header>
        <img src="/img/logo.png" alt="logo" height="70" width="300">

        <!-- 타이틀 -->
        <div class="title">
            <div class="back">
                <a href="#"><i class="bi bi-chevron-left"></i></a>
            </div>
        </div>
    </header>

    <div class="page">
        <div class="signupText">
            <h1>BECOME A WEIVER MEMBER</h1>
            <p>환영합니다!</p>
            <p>회원 가입을 통해 WEIVER의 모든 기능을 이용해보세요</p>
        </div>

        <!-- 회원가입 form -->
        <form id="signupForm" action="/signupTest" method="post">
            <!-- ID -->
            <div class="ID">
                <input id="userId" class="info_input" type="email" name="userId" placeholder="이메일을 입력해주세요" required style="text-transform: lowercase">
            </div>

            <!-- 닉네임 -->
            <div class="data_input">
                <input class="info_input" type="password" name="userPw" placeholder="비밀번호를 입력주세요" required>
            </div>

            <div class="data_input">
                <input class="info_input" type="password" name="userPwCheck" placeholder="비밀번호를 다시 입력해주세요" required>
            </div>
            <div class="data_input">
                <input class="info_input" type="text" name="userNickname" placeholder="닉네임을 입력해주세요" required>
            </div>
        </form>

        <!-- 약관 체크 박스 -->
        <div class="clauseBox">
            <div class="clauseWrap">
                <div>
                    <input type="checkbox" class="signupCheckBox">
                    <span>[필수] 이용약관에 동의합니다.</span>
                </div>
                <button class="openBtn">약관보기</button>
                <div class="modal hidden">
                    <div class="bg"></div>
                    <div class="modalBox">
                        <h1 style="text-align: center;">이용약관</h1>
                        <p style="line-height: 1.3;">본 약관에서 다루는 내용<br>
                            본 서비스 약관을 확인하는 것이 번거로울 수 있다는 점은 이해하지만, 귀하가 Google 서비스를 사용하면서 Google에 기대할 수 있는 부분과
                            Google이
                            귀하에게 기대하는 부분을 명확히 해 두는
                            것은 중요합니다.<br>
                            본 서비스 약관에는 Google의 사업 운영 방식, Google에 적용되는 법률, Google이 항상 진실이라고 여기는 특정 내용이 반영되어 있습니다. 따라서
                            귀하가
                            Google 서비스와 상호작용하면 본
                            서비스 약관을 근거로 Google과의 관계가 정의됩니다. 예를 들어, 약관에는 다음과 같은 제목의 주제들이 포함됩니다.<br><br>

                            Google에 기대할 수 있는 사항. Google이 서비스를 제공 및 개발하는 방법을 설명합니다.<br>
                            Google이 귀하에게 기대하는 사항. Google 서비스 사용과 관련된 일정한 규칙들을 정합니다.<br>
                            Google 서비스 내 콘텐츠. Google 서비스에서 찾을 수 있는 콘텐츠의 지적 재산권에 대해 설명하며, 이러한 콘텐츠의 소유권이 귀하, Google 또는
                            다른
                            사람에게 있는지 설명합니다.<br>
                            문제 또는 의견 충돌이 있는 경우. 귀하가 갖는 기타 법적 권리와 타인이 본 약관을 위반했을 때를 대비해 알아 두어야 할 사항을 설명합니다.<br>
                            Google 서비스를 이용함으로써 귀하는 본 약관에 동의하게 되므로 본 약관을 숙지하는 것이 중요합니다.<br>

                            본 약관 외에도 Google은 개인정보처리방침을 게시합니다. 개인정보처리방침은 본 약관에 포함되지는 않지만 정보의 업데이트, 관리, 내보내기 및 삭제 방법을
                            더 잘
                            이해하기 위해 읽어 볼 것을 권장합니다.<br>
                            <br>
                            약관<br>
                            서비스 제공자<br>
                            Google 서비스를 제공하고 귀하와 계약을 체결하는 주체는 다음과 같습니다.<br>
                            Google LLC<br>
                            미국 델라웨어주 법률에 따라 설립되고 미국법에 따라 운영됨<br>
                            1600 Amphitheatre Parkway
                            Mountain View, California 94043
                            USA<br><br>
                            연령 요건<br>
                            Google 계정을 직접 관리할 수 있는 연령 미만인 경우 Google 계정을 사용하기 위해서는 부모 또는 법정대리인의 허락이 필요합니다. 본 약관을 부모
                            또는
                            법정대리인과 함께 읽으시기 바랍니다.<br>

                            귀하가 부모 또는 법정대리인으로서 아동이 서비스를 사용하도록 허락하는 경우, 본 약관은 귀하에게 적용되며 서비스 내 아동의 활동에 대해 귀하가 책임을
                            집니다.<br>

                            일부 Google 서비스에는 서비스별 추가 약관 및 정책에 명시된 추가적인 연령 요건이 적용됩니다.
                        </p>
                        <button class="closeBtn">닫기</button>
                    </div>
                </div>

            </div>

            <div class="clauseWrap">
                <div>
                    <input type="checkbox" class="signupCheckBox">
                    <span>[필수] 개인정보 수집 및 이용에 동의합니다.</span>
                </div>
                <button class="openBtn">약관보기</button>
                <div class="modal hidden">
                    <div class="bg"></div>
                    <div class="modalBox">
                        <h1 style="text-align: center;">이용약관</h1>
                        <p style="line-height: 1.3;">본 약관에서 다루는 내용<br>
                            이용 약관 2가 들어갑니다.</p>
                        <button class="closeBtn">닫기</button>
                    </div>
                </div>

            </div>
            <div class="clauseWrap">
                <div>
                    <input type="checkbox" class="signupCheckBox">
                    <span>[필수] 본인 만 14세 이상 입니다.</span>
                </div>
                <button class="openBtn">약관보기</button>
                <div class="modal hidden">
                    <div class="bg"></div>
                    <div class="modalBox">
                        <h1 style="text-align: center;">이용약관</h1>
                        <p style="line-height: 1.3;">본 약관에서 다루는 내용<br>
                            사용자 본인 만 14세 이상임을 증명합니다.<br>
                            </p>
                        <button class="closeBtn">닫기</button>
                    </div>
                </div>

            </div>
        </div>

        <!-- 가입하기 버튼 -->
       	<div class="btn">
            <button class="signupBtn" type="submit" disabled>가입하기</button>
        </div>

        <!-- footer -->
        <footer>Copyright Weiver 2023 All Rights Reserved</footer>

        <!-- navibar -->
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

        <!-- script -->
        <script>
            // 모달 팝업
            // 이벤트가 발생한 후 다음 요소(빈 값)의 히든을 제거
            const open = (e) => {
                const modal = e.target.nextElementSibling;
                modal.classList.remove("hidden");
            }

            // 이벤트가 발생한 상위, 상위 태그를 modal로 특정 시킴
            const close = (e) => {
                const modal = e.target.parentElement.parentElement;
                modal.classList.add("hidden");
            }

            const openBtns = document.querySelectorAll(".openBtn");
            const closeBtns = document.querySelectorAll(".closeBtn");

            openBtns.forEach((btn) => {
                btn.addEventListener("click", open);
            });

            closeBtns.forEach((btn) => {
                btn.addEventListener("click", close);
            });

            /* 회원가입 */
			const signupButton = document.querySelector(".signupBtn");
			const signupForm = document.querySelector("#signupForm");
			const signupCheckBoxes = document.querySelectorAll(".signupCheckBox");
			const emailValue = document.querySelector("#userId")
			
			/* 체크박스 체크 확인 -> 버튼 활성화 */
			signupCheckBoxes.forEach((checkbox) => {
	            checkbox.addEventListener("change", () => {
	                signupButton.disabled = !signupCheckBoxes[0].checked || !signupCheckBoxes[1].checked || !signupCheckBoxes[2].checked;
	            });
	        });
			
			/* 소문자로 변형 */
			emailValue.addEventListener("input", (e) => {
				e.target.value = e.target.value.toLowerCase();
				console.log(e.target.value);
			})
			
			/* 회원가입 axios 요청 */
			signupButton.addEventListener("click", (event) => {
			    event.preventDefault(); // 기본 양식 제출을 방지
			
			    if (signupForm.checkValidity()) { // 양식이 유효한지 확인
			        const formData = new FormData(signupForm);
			
			        axios.post("/signupTest", formData)
			            .then(response => {
			                const data = response.data;
			                if (response.status === 200) {
		                        alert(data);
		                        window.location.href = "/login";
			                } 
			            })
			            .catch((error) => {
			            	const errorMessage = error.response.data;
			                alert(errorMessage);
			            });
			    } else {
			        signupForm.reportValidity(); // 양식이 유효하지 않으면 유효성 검사 메시지 표시
			    }
			});

        </script>
</body>

</html>