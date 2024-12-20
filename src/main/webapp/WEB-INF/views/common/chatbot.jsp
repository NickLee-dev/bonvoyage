<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	// 검색어 입력받기
	inputForm.addEventListener('submit',function(event) {
						// 폼처리시 이동방지(이벤트 전파 방지)
						event.preventDefault();

						// 입력값 가져오기
						const input = inputField.value;

						// 입력필드 초기화
						inputField.value = '';
						
						// 현재시간 가져오기
						const currentTime = new Date()
								.toLocaleTimeString([], {
									hour : '2-digit',
									minute : "2-digit"
								});

						// 대화창에 메세지 추가
						let message = document.createElement('div');
						message.classList.add('chatbot-message','user-message');
						message.innerHTML = `<p class="chatbot-text" sentTime="${currentTime}">${input}</p>`;
						conversation.appendChild(message);

						// 챗봇 응답 생성
						const response = generateResponse(input);

						// 챗봇 응답을 대화에 추가
						message = document.createElement('div');
						message.classList.add('chatbot-message', 'chatbot');
						message.innerHTML = `<p class="chatbot-text" sentTime="${currentTime}">${response}</p>`;
						conversation.appendChild(message);
						message.scrollIntoView({behavior : "smooth"});
						
	});
</script>	


<title>Bon voyage</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	color: #000;
	font-family: 'ELAND_Choice_M';
	position: relative; /* 다른 요소들과의 상대적인 위치 설정을 위해 추가 */
}

@font-face {
	font-family: 'SF_IceLemon';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2106@1.1/SF_IceLemon.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}

@font-face {
	font-family: 'ELAND_Choice_M';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/ELAND_Choice_M.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}
#board>h3 {
	font-size: 24px;
	margin-bottom: 12px;
	text-align:center;
}
#board {
	height: 600;
	width: 95%;
	margin: 30px auto;
}

.chatbot-container {
    width: 475px;
    margin: 0 auto;
    background-color: #f5f5f5;
    border: 1px solid #cccccc;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
#chatbot {
    background-color: #f5f5f5;
    border: 1px solid #eef1f5;
    box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.1);
    border-radius: 4px;
}
#conversation {
    height: 470px;
    overflow-y: auto;
    padding: 20px;
    display: flex;
    flex-direction: column;
}
.chatbot-message {
    display: flex;
    align-items: flex-start;
    position: relative;
    font-size: 16px;
    line-height: 20px;
    border-radius: 20px;
    word-wrap: break-word;
    white-space: pre-wrap;
    max-width: 100%;
    padding: 0 15px;
}
.chatbot-text {
    background-color: white;
    color: #333333;
    font-size: 1.1em;
    padding: 15px;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
#input-form {
    display: flex;
    align-items: center;
    border-top: 1px solid #eef1f5;
}
#input-field {
    flex: 1;
    height: 60px;
    border: 1px solid #eef1f5;
    border-radius: 4px;
    padding: 0 10px;
    font-size: 14px;
    transition: border-color 0.3s;
    background: #ffffff;
    color: #333333;
    border: none;
}
message-container {
    background: #ffffff;
    width: 100%;
    display: flex;
    align-items: center;
}
button#blueBtn {
	font-weight: bold;
	border: 1px solid #15bef5;
	background-color: #15bef5;
	color: #fff;
	border-radius: 7px;
	padding: 10px 22px;
	cursor: pointer;
	font-size: 15px;
	transition: .5s;
	margin-right:5px;
}
button#blueBtn:hover {
	background-color: #fff;
	color: #15bef5;
}
.user-message {
    justify-content: flex-end;
    margin:2px;
}
.chatbot-message {
    display: flex;
    align-items: flex-start;
    position: relative;
    font-size: 16px;
    line-height: 20px;
    border-radius: 20px;
    word-wrap: break-word;
    white-space: pre-wrap;
    max-width: 100%;
    padding: 0 15px;
    margin:2px;
}
</style>
</head>

<body>
	<section id="board">
		<h2 style="font-family: Arial, sans-serif; font-size:30px; font-weight: bold; color: #a0c9bb; text-decoration: none; text-shadow:2px 2px 2px #4ba483; text-align:center;margin:10px;">
		Bon Voyage
    	</h2>
		<h3>빠른 메뉴이동을 도와드려요!</h3>
		<div id="setting">
			<div class="chatbot-container">
				<div id="chatbot">
					<div id="conversation">
						<div class="chatbot-message">	
							<p class="chatbot-text">Bon voyage에 오신것을 환영합니다.!<br><br>원하는 메뉴 이름을 입력해주세요.</p>
						</div>
					</div>
					<form id="input-form">
						<message-container> <input id="input-field" type="text" placeholder="ex)경로추천, 가이드게시판, 공지사항">
							<button id="blueBtn" type="submit">입력</button>
						</message-container>
					</form>
				</div>
			</div>
		</div>		
	</section>

	<script src="resources/js/chatbot.js"></script>
</body>
</html>