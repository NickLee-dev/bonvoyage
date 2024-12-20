document.addEventListener('DOMContentLoaded', function () {
    var ps = new kakao.maps.services.Places();

    // 엔터키 감지 함수
    function handleKeyPress(event, inputId, suggestionsId, nameInputId) {
        console.log("엔터키 감지");
        if (event.keyCode === 13) { // 엔터키 감지
            event.preventDefault(); // 기본 폼 제출 동작을 막음
            searchPlaces(inputId, suggestionsId, nameInputId); // 장소 검색 함수 실행
            return false; // 폼 제출 방지
        }
    }

    // 장소 검색 함수
    function searchPlaces(inputId, suggestionsId, nameInputId) {
        var keyword = document.getElementById(inputId).value;

        // 검색어가 공백일 경우 리턴
        if (!keyword.trim()) {
            return;
        }

        // 카카오 API로 장소 검색 요청
        ps.keywordSearch(keyword, function (data, status) {
            if (status === kakao.maps.services.Status.OK) {
                displaySuggestions(data, suggestionsId, nameInputId, inputId);
            }
        });
    }

    // 검색 결과를 드롭다운 형식으로 표시
    function displaySuggestions(places, suggestionsId, nameInputId, inputId) {
        var suggestions = document.getElementById(suggestionsId);
        suggestions.innerHTML = ''; // 기존 검색 결과 삭제

        var inputElement = document.getElementById(inputId);
        var rect = inputElement.getBoundingClientRect(); // 입력 필드의 화면 좌표 구하기

        // 드롭다운의 위치를 입력 필드 아래로 설정
        suggestions.style.top = (rect.bottom + window.scrollY) + 'px';
        suggestions.style.left = (rect.left + window.scrollX) + 'px';

        places.forEach(function (place) {
            var listItem = document.createElement('li');
            listItem.textContent = place.place_name;
            listItem.style.cursor = 'pointer';

            // 리스트 아이템 클릭 시 이벤트
            listItem.onclick = function () {
                document.getElementById(nameInputId).value = place.place_name; // 선택된 장소를 검색창에 입력
                document.getElementById(inputId).value = place.address_name;
                closeSuggestions(suggestionsId); // 드롭다운 숨기기
            };

            suggestions.appendChild(listItem);
        });
        suggestions.classList.add('show'); // 드롭다운 표시
    }

    function closeSuggestions(suggestionsId) {
        var suggestions = document.getElementById(suggestionsId);
        suggestions.classList.remove('show'); // 드롭다운 숨기기
    }

    // 엔터키 감지 및 장소 검색 이벤트 리스너 등록
    document.getElementById('routePlaceAddress1').addEventListener('keyup', function (event) {
        handleKeyPress(event, 'routePlaceAddress1', 'suggestions1', 'routePlaceName1');
    });
    document.getElementById('routePlaceAddress2').addEventListener('keyup', function (event) {
        handleKeyPress(event, 'routePlaceAddress2', 'suggestions2', 'routePlaceName2');
    });
    document.getElementById('routePlaceAddress3').addEventListener('keyup', function (event) {
        handleKeyPress(event, 'routePlaceAddress3', 'suggestions3', 'routePlaceName3');
    });
    document.getElementById('routePlaceAddress4').addEventListener('keyup', function (event) {
        handleKeyPress(event, 'routePlaceAddress4', 'suggestions4', 'routePlaceName4');
    });
    document.getElementById('routePlaceAddress5').addEventListener('keyup', function (event) {
        handleKeyPress(event, 'routePlaceAddress5', 'suggestions5', 'routePlaceName5');
    });

    // 외부 클릭 시 드롭다운 숨기기 이벤트 리스너 등록
    document.addEventListener('click', function (event) {
        ['routePlaceAddress1', 'routePlaceAddress2', 'routePlaceAddress3', 'routePlaceAddress4', 'routePlaceAddress5'].forEach(function (inputId, index) {
            var searchInput = document.getElementById(inputId);
            var suggestionsId = 'suggestions' + (index + 1);
            var suggestions = document.getElementById(suggestionsId);

            if (!searchInput.contains(event.target) && !suggestions.contains(event.target)) {
                closeSuggestions(suggestionsId); // 드롭다운 숨기기
            }
        });
    });
});
