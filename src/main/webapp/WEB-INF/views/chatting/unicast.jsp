<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅</title>
<c:import url="/WEB-INF/views/common/menubar.jsp" />

<link rel="stylesheet" type="text/css" href="resources/css/chat.css" />
    <script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.1.min.js"></script>
</head>
<body>

	<div class="chat">
	    <h2>채팅</h2>
    
	    <div class="startchat">
	    <input type="hidden" id="chat_id" value="${ loginUser.userId }"/>
	    <span>상대방 ID : </span><input type="text" id="recvUser"  style="width:100px;"/> &nbsp;
	    <button type="button" id="startBtn">채팅하기</button><br>
	    </div>
    <!-- 채팅 창 구현 부분 -->
	    <div style="display:none;" id="chatbox">
	        <fieldset>
	            <div id="messageWindow"></div><br>
	            <input type="text" id="inputMessage" onkeyup="enterKey()"/>
	            <input type="submit" value="보내기" onclick="send()">
	            <button type="button" id="endBtn">나가기</button>
	        </fieldset>
	    </div>
	</div>
<script>


$('#startBtn').on('click',function(){

    $.ajax({
    	url: "checkMember.do",
    	type: "POST",
    	data: {memberID: $('#recvUser').val()},
    	success: function(response) {
    		if(response == "exist"){
    			alert("연결 완료! 상대방이 접속할 때까지 기다려주십시오.");
   			    $('#chatbox').css('display', 'block');
    			$(this).css('display', 'none');
    			$('#startBtn').css('display', 'block');
    		    $.ajax({
    		    	url:"chatRequest.do",
    		    	type: "POST",
    		    	data: { textMessage : 'start', 
    		    			sender : $('#chat_id').val(),
    		    			receiver : $('#recvUser').val()}
    		    }); 
    			connection();
    		}else{
    			alert("없는 회원이거나 잘못된 입력입니다. 다시 입력해주세요.");
    			$('#recvUser').val("");
    		}
    	},
        error: function(xhr, status, error) {
            console.error("에러 : ", error);
        }
    });
})

$('#endBtn').on('click',function(){
    $('#chatbox').css('display', 'none');
    $('#startBtn').css('display', 'inline');
    webSocket.send($('#chat_id').val()
            +"|님이 채팅방을 퇴장하였습니다.");
    $.ajax({
    	url: "removeRequest.do",
    	type: "POST",
    	data: {receiver: $('#recvUser').val(),
    			sender: $('#chat_id').val()},
    	success: function(response) {
    		if(response == "exit"){
    			webSocket.close();
    		}
    	},
        error: function(xhr, status, error) {
            console.error("에러 : ", error);
        }
    });
    
});

var $textarea = $('#messageWindow');

var webSocket = null;

var $inputMessage = $('#inputMessage');

function connection(){
    webSocket = new WebSocket('ws://ktj0514.synology.me:8080'+
    '<%=request.getContextPath()%>/chat');
    // chat 는 자바측의 @ServerEndPoint("/chat") 로 등록 설정된 클래스를 의미함

    webSocket.onopen = function(event){
        webSocket.send($('#chat_id').val()+"|님이 입장하셨습니다.");
    };
    webSocket.onmessage = function(event){
        onMessage(event);
    }
    webSocket.onerror = function(event){
         console.log('닫힘 코드:', event.code);
    }
    webSocket.onclose = function(event){
         console.log('닫힘 코드:', event.code);
    }
}

function enterKey(){
    if(window.event.keyCode == 13)
        send();
}

function send(){
    if ($inputMessage.val() == ""){
        alert("메시지를 입력해 주세요!");
    } else {
        $textarea.html(
            $textarea.html()
            +"<p class='chat_content'>"
            +$inputMessage.val()+"</p><br>");
      
        webSocket.send($('#chat_id').val()+"|"+$inputMessage.val());
        
        $.ajax({
        	url:"chatRequest.do",
        	type: "POST",
        	data: { textMessage : $inputMessage.val(), 
        			sender : $('#chat_id').val(),
        			receiver : $('#recvUser').val()}
        }); 
        $inputMessage.val("");
    }
    
    $textarea.scrollTop($textarea.height());
}


function onMessage(event) {
    var message = event.data.split("|");
    var sender = message[0];
    var content = message[1];
    if(content == "" || !sender.match($('#recvUser').val())){
    } else {
        $textarea.html(
            $textarea.html()
           +"<p class='other-side'>"
           +sender+" : "
           +content
           +"</p><br>");
        $textarea.scrollTop($textarea.height());
    }
}

function onError(event) {
	event.code
    alert(event.data);
}

function onClose(event) {
	event.code
    alert(event);
}

</script>

</body>
</html>

