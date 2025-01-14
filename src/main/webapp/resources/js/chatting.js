
const addTarget = document.querySelector("#addTarget"); // 추가 버튼

const addTargetPopupLayer = document.querySelector("#addTargetPopupLayer"); // 팝업 레이어

const closeBtn = document.querySelector("#closeBtn"); // 닫기 버튼

const targetInput = document.querySelector("#targetInput"); // 사용자 검색

const resultArea = document.querySelector("#resultArea"); // 검색 결과

let selectChattingNo; // 선택한 채팅방 번호
let selectTargetId; // 대상의 이름
let selectTargetProfile; // 대상의 프로필


// 검색 팝업 레이어 열기
addTarget.addEventListener("click", e => {
   addTargetPopupLayer.classList.toggle("popup-layer-close");
   targetInput.focus();
});


// 검색 팝업 레이어 닫기
closeBtn.addEventListener("click", e => {
   addTargetPopupLayer.classList.toggle("popup-layer-close");
   resultArea.innerHTML = "";
});


// 사용자 검색(ajax)
targetInput.addEventListener("input", e => {

   const query = e.target.value.trim();

   // 입력된 값이 없을 때
   if(query.length == 0){
      resultArea.innerHTML = ""; // 이전 검색 결과 비우기
      return;
   }


   // 입력된 값이 있을 때
   if(query.length > 0){
      fetch("selectTarget.do?query="+query)
      .then(resp => resp.json())
      .then(list => {
         //console.log(list);

         resultArea.innerHTML = ""; // 이전 검색 결과 비우기

         if(list.length == 0){
            const li = document.createElement("li");
            li.classList.add("result-row");
            li.innerText = "일치하는 회원이 없습니다";
            resultArea.append(li);
         }

         for(let member of list){
            // li요소 생성(한 행을 감싸는 요소)
            const li = document.createElement("li");
            li.classList.add("result-row");
            li.setAttribute("data-id", member.memberID);

            // 프로필 이미지 요소
            const img = document.createElement("img");
            img.classList.add("result-row-img");
            
            // 프로필 이미지 여부에 따른 src 속성 선택
            if(member.photoFilename == null) img.setAttribute("src", "resources/images/user.png");
            else   img.setAttribute("src", member.photoFilename);

            let memberName = member.memberID;
            let email = member.memberEmail;

            const span = document.createElement("span");
            span.innerHTML = `${memberName} ${email}`.replace(query, `<mark>${query}</mark>`);

            // 요소 조립(화면에 추가)
            li.append(img, span);
            resultArea.append(li);

            // li요소에 클릭 시 채팅방에 입장하는 이벤트 추가
            li.addEventListener('click', chattingEnter);
         }

      })
      .catch(err => console.log(err) );
   }
});


// 채팅방 입장 또는 선택 함수
function chattingEnter(e){
   console.log(e.target); // 실제 클릭된 요소
   console.log(e.currentTarget); // 이벤트 리스트가 설정된 요소

   const targetId = e.currentTarget.getAttribute("data-id");

   fetch("chattingEnter.do?targetId="+targetId)
   .then(resp => resp.text())
   .then(chattingNo => {
      console.log(chattingNo);
      
      selectRoomList(); // 채팅방 목록 조회
      
      setTimeout(()=>{ 
         // 만약 채팅방 목록 중 이미 존재하는 채팅방이 있으면 클릭해서 입장
         const itemList = document.querySelectorAll(".chatting-item")
         
         for(let item of itemList) {      
            if(item.getAttribute("chat-no") == chattingNo){
               item.focus();
               item.click();
               addTargetPopupLayer.classList.toggle("popup-layer-close");
               targetInput.value = "";
               resultArea.innerHTML = "";
               return;
            }
         }

      }, 200);
     // 0.2초마다 채팅방 목록 다시 불러오기

   })
   .catch(err => console.log(err));
}


// 비동기로 채팅방 목록 조회
function selectRoomList(){

   fetch("roomList.do")
   .then(resp => resp.json())
   .then(roomList => {
      console.log(roomList);

      // 채팅방 목록 출력 영역 선택
      const chattingList = document.querySelector(".chatting-list");

      // 채팅방 목록 지우기
      chattingList.innerHTML = "";

      // 조회한 채팅방 목록을 화면에 추가
      for(let room of roomList){
         const li = document.createElement("li");
         li.classList.add("chatting-item");
         li.setAttribute("chat-no", room.chattingNo);
         li.setAttribute("target-id", room.targetId);

         if(room.chattingNo == selectChattingNo){
            li.classList.add("select");
         }

         // item-header 부분
         const itemHeader = document.createElement("div");
         itemHeader.classList.add("item-header");

         const listProfile = document.createElement("img");
         listProfile.classList.add("list-profile");

         if(room.targetProfile == undefined)   
            listProfile.setAttribute("src", "resources/images/user.png");
         else                        
            listProfile.setAttribute("src", room.targetProfile);

         itemHeader.append(listProfile);

         // item-body 부분
         const itemBody = document.createElement("div");
         itemBody.classList.add("item-body");

         const p = document.createElement("p");

         const targetId = document.createElement("span");
         targetId.classList.add("target-name");
         targetId.innerText = room.targetId;
         
         const recentSendTime = document.createElement("span");
         recentSendTime.classList.add("recent-send-time");
         recentSendTime.innerText = room.sendTime;

         p.append(targetId, recentSendTime);
       
         const div = document.createElement("div");
         
         const recentMessage = document.createElement("p");
         recentMessage.classList.add("recent-message");

         if(room.lastMessage != undefined){
            recentMessage.innerHTML = room.lastMessage;
         }
         
         div.append(recentMessage);

         itemBody.append(p,div);

         // 현재 채팅방을 보고 있지 않고, 읽지 않은 메시지 개수가 0개 이상인 경우 -> 읽지 않은 메시지 개수 출력
         if(room.notReadCount > 0 && room.chattingNo != selectChattingNo ){
            const notReadCount = document.createElement("p");
            notReadCount.classList.add("not-read-count");
            notReadCount.innerText = room.notReadCount;
            div.append(notReadCount);
           
         }else{

            // 현재 채팅방을 보고 있는 경우
            // 비동기로 해당 채팅방 메시지를 읽음으로 표시
            fetch("updateReadFlag.do",{
               method : "PUT",
               headers : {"Content-Type": "application/json"},
               body : JSON.stringify({"chattingNo" : selectChattingNo, "memberID" : loginMemberID})
            })
            .then(resp => resp.text())
            .then(result => console.log(result))
            .catch(err => console.log(err));

         }

         li.append(itemHeader, itemBody);
         chattingList.append(li);
      }

      roomListAddEvent();
   })
   .catch(err => console.log(err));

}


// 채팅 메시지 영역
const display = document.getElementsByClassName("display-chatting")[0];


// 채팅방 목록에 이벤트를 추가하는 함수 
function roomListAddEvent(){
  
   const chattingItemList = document.getElementsByClassName("chatting-item");
   
   for(let item of chattingItemList){
      item.addEventListener("click", e => {
   
         // 클릭한 채팅방의 번호 얻어오기
         //const id = item.getAttribute("id");
         //const arr = id.split("-");
        
         // 전역변수에 채팅방 번호, 상대 번호, 상태 프로필, 상대 이름 저장
         selectChattingNo = item.getAttribute("chat-no");
         selectTargetId = item.getAttribute("target-id");

         selectTargetProfile = item.children[0].children[0].getAttribute("src");

         // 모든 채팅방에서 select 클래스를 제거
         for(let it of chattingItemList) it.classList.remove("select")
   
         // 현재 클릭한 채팅방에 select 클래스 추가
         item.classList.add("select");
   
         // 비동기로 메시지 목록을 조회하는 함수 호출
         selectChattingFn();
      });
   }
}


// 비동기로 메시지 목록을 조회하는 함수
function selectChattingFn() {

   fetch("selectMessage.do?"+`chattingNo=${selectChattingNo}&memberID=${loginMemberID}`)
   .then(resp => resp.json())
   .then(messageList => {
      console.log(messageList);

      // <ul class="display-chatting">
      const ul = document.querySelector(".display-chatting");

      ul.innerHTML = ""; // 이전 내용 지우기

      // 메시지 만들어서 출력하기
      for(let msg of messageList){
         //<li>,  <li class="my-chat">
         const li = document.createElement("li");

         // 보낸 시간
         const span = document.createElement("span");
         span.classList.add("chatDate");
         span.innerText = msg.sendTime;

         // 메시지 내용
         const p = document.createElement("p");
         p.classList.add("chat");
         p.innerHTML = msg.messageContent; // br 태그 해석을 위해 innerHTML

         // 내가 작성한 메시지인 경우
         if(loginMemberID == msg.senderId){ 
            li.classList.add("my-chat");
            
            li.append(span, p);
            
         }else{ // 상대가 작성한 메시지인 경우
            li.classList.add("target-chat");

            // 상대 프로필
            // <img src="/resources/images/user.png">
            const img = document.createElement("img");
            img.setAttribute("src", selectTargetProfile);
            
            const div = document.createElement("div");

            // 상대 이름
            const b = document.createElement("b");
            b.innerText = selectTargetId; // 전역변수

            const br = document.createElement("br");

            div.append(b, br, p, span);
            li.append(img,div);

         }

         ul.append(li);
        
         display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
        										   // 최근에 온 메시지는 아래로 계속 쌓이니까!
      }

   })
   .catch(err => console.log(err));

}


// ----------------------------------------------------------------------------------------------------------------

// sockjs를 이용한 WebSocket 구현

// 로그인이 되어 있을 경우에만
// /chattingSock 이라는 요청 주소로 통신할 수 있는 WebSocket 객체 생성

   const chattingSock = new WebSocket("ws://localhost:8080/bonvoyage/chat.do");

chattingSock.onopen = function() {
    console.log('WebSocket 연결이 열렸습니다.');
};

chattingSock.onmessage = function(event) {
    console.log('서버로부터 메시지를 수신했습니다:', event.data);
};

chattingSock.onclose = function() {
    console.log('WebSocket 연결이 닫혔습니다.');
};

chattingSock.onclose = function(event) {
    console.log('WebSocket 연결이 닫혔습니다. 이유:', event.reason);
    console.log('닫힘 코드:', event.code);
};

chattingSock.onerror = function(error) {
    console.error('WebSocket 오류 발생:', error);
};

// 채팅 입력
const send = document.getElementById("send");

const sendMessage = () => {
   const inputChatting = document.getElementById("inputChatting");

   if (inputChatting.value.trim().length == 0) {
      alert("채팅을 입력해주세요.");
      inputChatting.value = "";
     
   } else {
      var obj = {
         "senderId": loginMemberID,
         "targetId": selectTargetId,
         "chattingNo": selectChattingNo,
         "messageContent": inputChatting.value,
      };
     
      console.log(obj)

      // JSON.stringify() : 자바스크립트 객체를 JSON 문자열로 변환
      chattingSock.send(JSON.stringify(obj));

      inputChatting.value = "";
   }
}

// 엔터 == 제출
// 쉬프트 + 엔터 == 줄바꿈
inputChatting.addEventListener("keyup", e => {
   if(e.key == "Enter"){ 
      if (!e.shiftKey) {
         sendMessage();
      }
   }
})


// WebSocket 객체 chattingSock이 서버로부터 메시지를 통지받으면 자동으로 실행될 콜백 함수
chattingSock.onmessage = function(e) {
  
   // 메소드를 통해 전달받은 객체값을 JSON객체로 변환해서 obj 변수에 저장
   const msg = JSON.parse(e.data);
   console.log(msg);

   // 현재 채팅방을 보고있는 경우
   if(selectChattingNo == msg.chattingNo){

     const ul = document.querySelector(".display-chatting");
   
      // 메시지 만들어서 출력하기
      //<li>,  <li class="my-chat">
      const li = document.createElement("li");
   
      // 보낸 시간
      const span = document.createElement("span");
      span.classList.add("chatDate");
      span.innerText = msg.sendTime;
   
      // 메시지 내용
      const p = document.createElement("p");
      p.classList.add("chat");
      p.innerHTML = msg.messageContent; // br 태그 해석을 위해 innerHTML
   
      // 내가 작성한 메시지인 경우
      if(loginMemberID == msg.senderId){ 
         li.classList.add("my-chat");
         
         li.append(span, p);
         
      }else{ // 상대가 작성한 메시지인 경우
         li.classList.add("target-chat");
   
         // 상대 프로필
         // <img src="/resources/images/user.png">
         const img = document.createElement("img");
         img.setAttribute("src", selectTargetProfile);
         
         const div = document.createElement("div");
   
         // 상대 이름
         const b = document.createElement("b");
         b.innerText = selectTargetId; // 전역변수
   
         const br = document.createElement("br");
   
         div.append(b, br, p, span);
         li.append(img,div);
   
      }
   
      ul.append(li)
      display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
   }

   selectRoomList();
}


// 문서 로딩 완료 후 수행할 기능
document.addEventListener("DOMContentLoaded", ()=>{
   
   // 채팅방 목록에 클릭 이벤트 추가
   roomListAddEvent(); 

   // 보내기 버튼에 이벤트 추가
   send.addEventListener("click", sendMessage);
});