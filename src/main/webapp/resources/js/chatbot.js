// 챗봇 요소 가져오기
const chatbot = document.getElementById('chatbot');
const conversation = document.getElementById('conversation');
const inputForm = document.getElementById('input-form');
const inputField = document.getElementById('input-field');

// 입력 폼 제출 이벤트 리스너
inputForm.addEventListener('submit', function(event) {
  // 폼 제출 동작을 막고, js에서 입력을 처리
  event.preventDefault();

  // 사용자 입력값 가져오기
  const input = inputField.value;

  // 입력 필드 초기화
  inputField.value = '';
  
  // 현재 시간 가져오기
  const currentTime = new Date().toLocaleTimeString([], { hour: '2-digit', minute: "2-digit" });

  // 사용자 입력을 대화창에 추가
  let message = document.createElement('div');
  message.classList.add('chatbot-message', 'user-message');
  message.innerHTML = `<p class="chatbot-text" sentTime="${currentTime}">${input}</p>`;
  conversation.appendChild(message);

  // 챗봇 응답 생성
  const response = generateResponse(input);

  // 챗봇 대화창에 응답 추가
  message = document.createElement('div');
  message.classList.add('chatbot-message','chatbot');
  message.innerHTML = `<p class="chatbot-text" sentTime="${currentTime}">${response}</p>`;
  conversation.appendChild(message);
  message.scrollIntoView({behavior: "smooth"});
});

// 챗봇 응답 생성 함수
function generateResponse(input) {
    // 답변을 저장할 배열 생성
    let responses = [];

    // 조건에 맞는 응답을 배열에 추가
    if (input.includes("경로")) {
        responses.push(`경로추천 메뉴를 찾으시나요? <a href="routeall.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }
    if (input.includes("수단")) {
        responses.push(`경로추천 메뉴를 찾으시나요? <a href="routeall.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }
    if (input.includes("추천")) {
        responses.push(`경로추천 메뉴를 찾으시나요? <a href="routeall.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }    
    if (input.includes("지도")) {
        responses.push(`지도(경로검색) 메뉴를 찾으시나요? <a href="movebmap.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }
    if (input.includes("경로")) {
        responses.push(`지도(경로검색) 메뉴를 찾으시나요? <a href="movebmap.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }
    if (input.includes("검색")) {
        responses.push(`지도(경로검색) 메뉴를 찾으시나요? <a href="movebmap.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }    
    if (input.includes("가이드")) {
        responses.push(`지역 소담이(가이드) 게시판을 찾으시나요? <a href="sagBlog.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }
    if (input.includes("지역")) {
        responses.push(`지역 소담이(가이드) 게시판을 찾으시나요? <a href="sagBlog.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }    
    if (input.includes("소담이")) {
        responses.push(`지역 소담이(가이드) 게시판을 찾으시나요? <a href="sagBlog.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }      
    if (input.includes("공지사항")) {
        responses.push(`공지사항 게시판을 찾으시나요? <a href="sanotice.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }     
    if (input.includes("Q")) {
        responses.push(`QnA 게시판을 찾으시나요? <a href="saqna.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }    
    if (input.includes("q")) {
        responses.push(`QnA 게시판을 찾으시나요? <a href="saqna.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    } 
    if (input.includes("신고")) {
        responses.push(`신고 게시판을 찾으시나요? <a href="reportList.do" target="_blank">맞으시면 여기를 눌러주세요.</a>`);
    }

    // 응답이 없을 경우 기본 응답 추가
    if (responses.length === 0) {
        responses.push(`제공하지 않는 메뉴입니다.`);
    }

    // 여러 응답을 하나의 문자열로 결합해서 반환
    return responses.join('<br><br>'); // 줄바꿈으로 구분해서 표시
}
