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
                <a href="/login"><i class="bi bi-chevron-left"></i></a>
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
                            본 서비스 약관을 확인하는 것이 번거로울 수 있다는 점은 이해하지만, 귀하가 WEIVER 서비스를 사용하면서 WEIVER에 기대할 수 있는 부분과
                            WEIVER가 귀하에게 기대하는 부분을 명확히 해 두는것은 중요합니다.<br><br>
                            본 서비스 약관에는 WEIVER의 사업 운영 방식, WEIVER에 적용되는 법률, WEIVER이 항상 진실이라고 여기는 특정 내용이 반영되어 있습니다. 따라서
                            귀하가 WEIVER 서비스와 상호작용하면 본
                            서비스 약관을 근거로 WEIVER과의 관계가 정의됩니다. 예를 들어, 약관에는 다음과 같은 제목의 주제들이 포함됩니다.<br><br>

                            WEIVER는 귀하에게 기대하는 사항, 서비스 사용과 관련된 일정한 규칙들을 정합니다.<br>
                            WEIVER 서비스에서 찾을 수 있는 콘텐츠의 지적 재산권에 대해 설명하며, 이러한 콘텐츠의 소유권이 귀하, WEIVER 또는 다른사람에게 있는지 설명합니다.<br><br>
                            문제 또는 의견 충돌이 있는 경우. 귀하가 갖는 기타 법적 권리와 타인이 본 약관을 위반했을 때를 대비해 알아 두어야 할 사항을 설명합니다.<br>
                            WEIVER 서비스를 이용함으로써 귀하는 본 약관에 동의하게 되므로 본 약관을 숙지하는 것이 중요합니다.<br>

                            본 약관 외에도 WEIVER은 개인정보처리방침을 게시합니다.<br><br>
                            개인정보처리방침은 본 약관에 포함되지는 않지만 정보의 업데이트, 관리, 내보내기 및 삭제 방법을
                            더 잘 이해하기 위해 읽어 볼 것을 권장합니다.<br><br><br>
                            
                            연령 요건<br>
                            WEIVER 계정을 직접 관리할 수 있는 연령 미만인 경우 WEIVER 계정을 사용하기 위해서는 부모 또는 법정대리인의 허락이 필요합니다.<br>
                            본 약관을 부모 또는 법정대리인과 함께 읽으시기 바랍니다.<br><br>
                            귀하가 부모 또는 법정대리인으로서 아동이 서비스를 사용하도록 허락하는 경우,<br>
                            본 약관은 귀하에게 적용되며 서비스 내 아동의 활동에 대해 귀하가 책임을집니다.<br><br>

                            일부 WEIVER 서비스에는 서비스별 추가 약관 및 정책에 명시된 추가적인 연령 요건이 적용됩니다.
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
                        <p style="line-height: 1.3;">본 약관에서 다루는 내용<br><br>
                        	수집하는 개인정보의 항목 및 목적<br>
                        	저희 WEIVER 서비스는 서비스 제공과 관련하여 필요한 최소한의 개인정보를 수집합니다.<br>
                        	수집되는 개인정보는 이용 목적에 맞게 제한적으로 사용됩니다.<br>
                        	주로 수집되는 개인정보는 이메일 주소, 닉네임 등이며, 이용 목적은 서비스 이용에 따른 회원 식별과 서비스 제공, 공지사항 및 이벤트 안내 등을 위함입니다.<br><br><br>
                            서비스 이용과정에서 기기정보, IP주소, 쿠키, 서비스 이용기록이 자동으로 수집될 수 있습니다.<br>
							이용자는 개인정보의 수집 및 이용 동의를 거부할 권리가 있습니다.<br><br><br>
							개인정보의 보유 및 이용 기간<br>
							수집한 개인정보는 회원탈퇴 시 즉시 파기되며, 별도의 동의가 없는 한 다른 용도로 사용하지 않습니다. 다만, 관련 법령에 따라 일정 기간 동안 보존해야 할 필요가 있는 경우는 예외로 합니다.<br><br>
							회원가입 시 수집하는 최소한의 개인정보, 즉, 필수 항목에 대한 수집 및 이용 동의를 거부하실 경우, 회원가입이 어려울 수 있습니다.</p>
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
                        <p style="line-height: 1.3;">본 약관에서 다루는 내용<br><br>
                            해당 이용약관은 본인(사용자)이 만 14세 이상이 아니라면 법적 책임을 질 수 있습니다<br><br>
							WEIVER 서비스는 만 14세 이상의 사용자를 대상으로 합니다. 서비스를 이용하기 위해서는 반드시 만 14세 이상이어야 합니다.<br>
							만일 만 14세 미만인 경우, 법적인 제한 사항이 존재할 수 있으며, 이로 인해 발생하는 책임은 본인에게 있을 수 있습니다.<br><br><br>
							
							만 14세 이상이 아닐 경우 회원 효력이 정지되거나 말소될 수 있습니다<br>
							서비스 이용 약관을 위반하여 만 14세 이상의 사용자가 아닌 경우, 회원 효력이 일시적으로 정지되거나 영구적으로 말소될 수 있습니다.<br>
							이로 인해 일부 혜택이 제한될 수 있으며, 서비스 이용에 제한이 생길 수 있습니다.<br><br><br>
							
							만 14세 이상이 아닐 경우 이로부터 발생하는 문제의 책임은 본인이 될 수 있습니다<br>
							만일 만 14세 이상이 아닌 상태에서 저희 서비스를 이용하여 발생하는 모든 문제와 책임은 본인에게 있을 수 있습니다.<br>
							부정한 방법으로 정보를 제공하는 행위와 타인의 정보를 도용하는 행위는 엄격히 금지되며, 이러한 위반 사항이 발견될 경우 서비스 이용이 중단될 수 있습니다.<br><br><br>
							
							서비스 이용 약관을 숙지하시고 본인이 만 14세 이상인 경우에만 서비스를 이용해 주시기 바랍니다.<br>
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