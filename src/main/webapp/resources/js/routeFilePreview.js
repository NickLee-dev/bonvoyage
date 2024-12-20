window.onload = function() {
    // 파일 입력 필드와 이미지 미리보기 요소들을 매핑
    var files = [
        {input: "routePlacePhoto1", image: "photo1"},
        {input: "routePlacePhoto2", image: "photo2"},
        {input: "routePlacePhoto3", image: "photo3"},
        {input: "routePlacePhoto4", image: "photo4"},
        {input: "routePlacePhoto5", image: "photo5"}
    ];

    files.forEach(function(file) {
        var photofile = document.getElementById(file.input);
        var myphoto = document.getElementById(file.image);

        // 기본 이미지 설정
        const defaultImageSrc = '${ pageContext.servletContext.contextPath }/resources/images/noPhoto.jpg';
        myphoto.src = defaultImageSrc;

        // 파일이 변경될 때 미리보기 이미지 업데이트
        photofile.addEventListener('change', function(event) {
            const fileData = event.target.files[0];
            if (fileData) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    myphoto.src = e.target.result;
                };
                reader.readAsDataURL(fileData);
            } else {
                // 파일이 없을 경우 기본 이미지로 복구
                myphoto.src = defaultImageSrc;
            }
        });
    });
};