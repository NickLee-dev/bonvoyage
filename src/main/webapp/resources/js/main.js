// 웹소켓 테스트
// 1. SockJS라이브러리 추가 -> main.jsp 추가 

//2. SockJS를 이용해서 클라이언트용 웹소켓 객체 생성
let testSock = new SockJS("/testSock/");

function sendMessage(name,str){
    //매개변수 JS객체에 저장 
    let obj = {}; // 비어있는 객체 

    obj.name = name;  // 객체에 일치하는 key가 없다면 자동으로 추가 
    obj.str = str;

    //console.log(obj);

    // 웹 소켓 연결된 곳으로 메세지를 보냄
    testSock.send(JSON.stringify(obj));
                // JS객체 -> JSON으로 보냄! 

            
}
// 웹소켓 객체(testSock)가 서버로 부터 전달 받은 메세지가 있을경우
testSock.onmessage = e => {
    // e: 이벤트 객체
    // onmessage: 이벤트가 오는걸 감지
    // e.data : 전달 받은 메세지(JSON)
    let obj = JSON.parse(e.data); // JSON -> JS객체 
    console.log(`보낸사람 : ${obj.name} / ${obj.str}`);

}