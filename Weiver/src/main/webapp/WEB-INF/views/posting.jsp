
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>글 작성 페이지</title>
<link href="../css/layout.css" rel="stylesheet" type="text/css" />
</head>

<header><img src="../img/logo.png" alt="logo" height="70" width="300"></header>

<body>
<div>
    <input type="button" value="뒤로가기" id="back" ><td>글 작성하기</td>
    <form>
        <select name="sort">
            <option value="select">글 종류</option>
            <option value="리뷰">리뷰</option>
            <option value="잡담">잡담</option>
        </select>
    </form>
    
    <form>
        <!-- 잡담 -->
        <c:if test="${true } }" >
	        <table>
	            <th align="left">제목</th>
	            <tr>
	                <td><input type="text" id="talkTitle"></td>
	            </tr>
	            <th align="left" >내용</th>
	            <tr>
	                <td><input type="text" id="talkContent"></td>
	            </tr>
	        </table>
        </c:if>
        
        <!-- 리뷰 -->
		<c:if test="${false }">
	        <table>
	            <th align="left">제목</th>
	            <tr>
	                <td><input type="text" id="reviewTitle"></td>
	            </tr>
	            <th align="left" >작품명</th>
	            <tr>
	                <td><input type="text" id="reviewPerformance"></td>
	            </tr>
	            <th align="left">내용</th>
	            <tr>
	                <td><input type="text" id="reviewContent"></td>
	            </tr>
	        </table>
		</c:if>
		
        <input type="submit" value="작성하기">
    </form>
</div>

<footer>Copyright 시난과 고련  2023 All Rights Reserved</footer>

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