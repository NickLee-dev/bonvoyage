<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bon voyage</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.7.1.min.js"></script>
<%-- 지도 api용 스크립트 --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6c21cf4a0f3f4a8aa848524ff9fe27db"></script>
<%-- 지도 실제구현 --%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/mainMarkerMap.js"></script>
<%-- 공지사항 css --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

<script type="text/javascript">

	//공지사항 top10
	$(function(){
		$.ajax({
			url:'stnotice.do',
			type:'post',
			dataType:'json',
			success:function(data){
				console.log('success : '+data);
				
				var str = JSON.stringify(data);
				
				var json = JSON.parse(str);
				
				values = '';
				for(var i in json.nlist){
					
/* 					values += '<tr><td>' + decodeURIComponent(json.nlist[i].noticeId).substring(7)
					+'</td><td align="left">&nbsp;<a class="truncate" href="msnotice.do?noticeId='+ json.nlist[i].noticeId +'">'
					+ decodeURIComponent(json.nlist[i].title).replace(/\+/gi,' ')
					+'</a></td><td class="truncate">'+json.nlist[i].createAt + '</td></tr>'; */
					
					var noticeHTML = '<p>' + decodeURIComponent(json.nlist[i].noticeId).substring(7)
									+'</p><p><a class="truncate" style="text-decoration:none;color:black;font-weight: normal;" href="msnotice.do?noticeId='+ json.nlist[i].noticeId +'">'
									+ decodeURIComponent(json.nlist[i].title).replace(/\+/gi,' ')
									+ '</a></p><p>' + decodeURIComponent(json.nlist[i].createAt).replace(/\+/gi,' ') + '</p>';
					$('.main_notice .swiper-slide:nth-child(' + (parseInt(i) + 1) + ') .new_notice').html(noticeHTML);
				}
				
				$('#topnotice').html($('#topnotice').html()+values);
			},
			error:function(jqXHR, textStatus, errorThrown){
				console.log('error : ' + jqXHR + ', ' + textStatus + ', ' + errorThrown);
			}
		});
		
		let mainNoticeSwiper = new Swiper('.main_notice .swiper-container', {
	        direction: 'vertical',
	         autoplay: {
	            delay: 3500,
	            disableOnInteraction: false,
	        },
	        speed: 700,
	        loop: true, 
	    });
	});
	
	
	
	//경로추천 인기 게시글 3개 (top-N) 전송받아서 출력 처리
	$.ajax({
	    url: 'rtop3.do',  // 서버의 데이터 요청 URL
	    type: 'post',
	    dataType: 'json',
	    success: function(data) {
	        console.log(data);  // 응답 데이터 확인
	        if (data.rlist && data.rlist.length > 0) {
	            // 첫 번째 슬라이드 이미지와 링크 설정
	            $('#carouselExampleIndicators2 .carousel-item:nth-child(1) a').attr('href', '${pageContext.servletContext.contextPath}/routedetail.do?no=' + data.rlist[0].routeBoardId);
	            if(data.rlist[0].rFile1 != ""){
	            	$('#carouselExampleIndicators2 .carousel-item:nth-child(1) img').attr('src', '${pageContext.servletContext.contextPath}/resources/route_upfiles/' + data.rlist[0].rFile1);
	        	};		
	            $('.carousel-item:nth-child(1) .carousel-caption h4').text(decodeURIComponent(data.rlist[0].title).replace(/\+/g, ' '));
	            $('.carousel-item:nth-child(1) .carousel-caption h6').text(decodeURIComponent(data.rlist[0].transport).replace(/\+/g, ' '));
	            console.log('첫 번째 포스트 제목:', data.rlist[0].title);  // 첫 번째 포스트의 제목 확인
	            console.log('첫 번째 포스트 지역:', data.rlist[0].transport);  

	            // 두 번째 슬라이드 이미지와 링크 설정
	            $('#carouselExampleIndicators2 .carousel-item:nth-child(2) a').attr('href', '${pageContext.servletContext.contextPath}/routedetail.do?no=' + data.rlist[1].routeBoardId);
	            if(data.rlist[1].rFile1 != ""){
	            	$('#carouselExampleIndicators2 .carousel-item:nth-child(2) img').attr('src', '${pageContext.servletContext.contextPath}/resources/route_upfiles/' + data.rlist[1].rFile1);
	            };
	            $('.carousel-item:nth-child(2) .carousel-caption h4').text(decodeURIComponent(data.rlist[1].title).replace(/\+/g, ' '));
	            $('.carousel-item:nth-child(2) .carousel-caption h6').text(decodeURIComponent(data.rlist[1].transport).replace(/\+/g, ' '));

	            // 세 번째 슬라이드 이미지와 링크 설정
	            $('#carouselExampleIndicators2 .carousel-item:nth-child(3) a').attr('href', '${pageContext.servletContext.contextPath}/routedetail.do?no=' + data.rlist[2].routeBoardId);
	            if(data.rlist[1].rFile1 != ""){
	            	$('#carouselExampleIndicators2 .carousel-item:nth-child(3) img').attr('src', '${pageContext.servletContext.contextPath}/resources/route_upfiles/' + data.rlist[2].rFile1);
	            };
	            $('.carousel-item:nth-child(3) .carousel-caption h4').text(decodeURIComponent(data.rlist[2].title).replace(/\+/g, ' '));
	            $('.carousel-item:nth-child(3) .carousel-caption h6').text(decodeURIComponent(data.rlist[2].transport).replace(/\+/g, ' '));
	        }
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	        console.error('Error loading top blogs: ' + textStatus + ', ' + errorThrown);
	    }
	});
	
	
	
	
	//가이드게시판 인기 게시글 3개 (top-N) 전송받아서 출력 처리
$.ajax({
    url: 'gtop3.do',  // 서버의 데이터 요청 URL
    type: 'post',
    dataType: 'json',
    success: function(data) {
        console.log(data);  // 응답 데이터 확인
        if (data.glist && data.glist.length > 0) {
            // 첫 번째 슬라이드 이미지와 링크 설정
            $('#carouselExampleIndicators .carousel-item:nth-child(1) a').attr('href', '${pageContext.servletContext.contextPath}/gdetail.do?guidepostId=' + data.glist[0].guidepostId);
            $('#carouselExampleIndicators .carousel-item:nth-child(1) img').attr('src', '${pageContext.servletContext.contextPath}/resources/guide_upfiles/' + data.glist[0].rFile1);
            $('.carousel-item:nth-child(1) .carousel-caption h5').text(decodeURIComponent(data.glist[0].gtitle).replace(/\+/g, ' '));
            $('.carousel-item:nth-child(1) .carousel-caption p').text(decodeURIComponent(data.glist[0].glocation).replace(/\+/g, ' '));
            console.log('첫 번째 포스트 제목:', data.glist[0].gtitle);  // 첫 번째 포스트의 제목 확인
            console.log('첫 번째 포스트 지역:', data.glist[0].location);  

            // 두 번째 슬라이드 이미지와 링크 설정
            $('#carouselExampleIndicators .carousel-item:nth-child(2) a').attr('href', '${pageContext.servletContext.contextPath}/gdetail.do?guidepostId=' + data.glist[1].guidepostId);
            $('#carouselExampleIndicators .carousel-item:nth-child(2) img').attr('src', '${pageContext.servletContext.contextPath}/resources/guide_upfiles/' + data.glist[1].rFile1);
            $('.carousel-item:nth-child(2) .carousel-caption h5').text(decodeURIComponent(data.glist[1].gtitle).replace(/\+/g, ' '));
            $('.carousel-item:nth-child(2) .carousel-caption p').text(decodeURIComponent(data.glist[1].glocation).replace(/\+/g, ' '));

            // 세 번째 슬라이드 이미지와 링크 설정
            $('#carouselExampleIndicators .carousel-item:nth-child(3) a').attr('href', '${pageContext.servletContext.contextPath}/gdetail.do?guidepostId=' + data.glist[2].guidepostId);
            $('#carouselExampleIndicators .carousel-item:nth-child(3) img').attr('src', '${pageContext.servletContext.contextPath}/resources/guide_upfiles/' + data.glist[2].rFile1);
            $('.carousel-item:nth-child(3) .carousel-caption h5').text(decodeURIComponent(data.glist[2].gtitle).replace(/\+/g, ' '));
            $('.carousel-item:nth-child(3) .carousel-caption p').text(decodeURIComponent(data.glist[2].glocation).replace(/\+/g, ' '));
        }
    },
    error: function(jqXHR, textStatus, errorThrown) {
        console.error('Error loading top blogs: ' + textStatus + ', ' + errorThrown);
    }
});

</script>
<style>
  td a {
    color: black;
    text-decoration: none;
  }
  .truncate {
  white-space: nowrap;        /* 한 줄로 표시 */
  overflow: hidden;           /* 넘치는 텍스트는 숨김 */
  text-overflow: ellipsis;    /* 넘치는 텍스트에 '...' 표시 */
  width: 100%;                /* 부모 요소의 크기에 맞춤 */
  }
  
  /* 캐러셀 컨테이너의 전체 크기를 조정 */
  .carousel {
    width: 90%;     /* 전체 너비의 60%로 설정 */
    margin: 0 auto; /* 중앙에 정렬 */
  }

  /* 슬라이드 이미지 크기 조정 */
  .carousel-inner img {
    width: 100%;    /* 슬라이드 이미지의 너비를 부모 요소의 너비에 맞춤 */
    height: 500px;  /* 이미지 높이를 300px로 고정 */
    object-fit: cover; /* 이미지 비율을 유지하면서 넘치는 부분을 잘라냄 */
  }

  /* 슬라이드 안에 제목과 지역 정보를 표시할 스타일 */
  .carousel-caption {
    position: absolute;
    bottom: 20px;
    left: 20px;
    color: white;
    background-color: rgba(0, 0, 0, 0.5); /* 배경을 반투명하게 */
    padding: 10px;
    border-radius: 5px;
	}
    #topnotice th:nth-child(1) {
        width: 10%;  /* No 열 */
    }
    #topnotice th:nth-child(2) {
        width: 60%;  /* 제목 열 */
    }
    #topnotice th:nth-child(3) {
        width: 25%;  /* 등록일 열 */
    }
    
    /* main_notice */
	.main_notice {width: 90%; max-width: 1280px;}
	.main_notice_top {width: 100%; box-sizing: border-box; padding: 0 10px; display: flex; justify-content: space-between; align-items: center;}
	.main_notice_top > p:nth-child(1) {font-weight: bold; font-size: 36px;}
	.main_notice_top > p > a {color: #ff5f2c; font-size: 16px; font-weight: bold;}
	.main_notice_top > p > a > span {position: relative; left: 0; transition: all 0.3s;}
	.main_notice_top > p > a:hover > span {left: 10px;}
	/* main_notice_top end */
	
	.main_notice .swiper-container {width: 100%; height: 41px;overflow:hidden; border-top: 2px solid #4ba483; border-bottom: 2px solid #4ba483;}
	.main_notice .swiper-wrapper {width: 100%;}
	.main_notice .swiper-slide {height: 37px !important; line-height: 37px;}
	
	.new_notice {width: 100%; height: 100%; display: flex; align-items: center;box-sizing:border-box;}
	.new_notice > p {text-align: center;margin-bottom:0px;}
	.new_notice > p:nth-child(1) {width: 10%;}
	.new_notice > p:nth-child(2) {width: 70%; font-weight: bold;}
	.new_notice > p:nth-child(2):hover {color: #ff5f2c;}
	.new_notice > p:nth-child(3) {width: 20%;}
	
	@media all and (max-width: 1280px) {
	    .main_notice_top > p:nth-child(1) {font-size: 24px;}
	    .new_notice > p {font-size: 14px;}
	    .new_notice > p:nth-child(2) {box-sizing: border-box; padding: 0 20px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;}
	}
</style>
</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp"/>	
<div class="container text-center">

<%-- 공지사항 --%>
  <div class="row my-3">
  	<div class="col">
	<!-- <div class="col-8">
		<div class="carousel">
			<div style="display: flex; justify-content: space-between; align-items: center; width: 100%;">
			    <div class="h4 float-start">&nbsp; ▶ 공지사항</div>
			    <div>
			        <a href="sanotice.do" style="text-decoration:none; color:black;">더보기</a>
			    </div>
			</div>
	      	<table class="table table-sm" id="topnotice" style="width: 100%;">
				<tr class="table-success">
					<th>No</th>
					<th>제목</th>
					<th>등록일</th>
				</tr>
			</table>
		</div>
    </div> -->
    
	    <div class="carousel main_notice">
	    	<div class="main_notice_top">
	    		<div class="h4 float-start">▶ 공지사항</div>
	    		<P>
	    			<a href="sanotice.do" style="text-decoration:none; color:black;">더보기</a>
	    		</P>
	    	</div>
	    	
	    	<div class="swiper-container">
	    		<div class="swiper-wrapper">
	    			<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice1">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice2">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice3">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice4">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice5">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice6">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice7">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice8">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice9">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
					<div class="swiper-slide">
	    				<div class="new_notice" id="new_notice10">
							<p></p><!-- 번호 -->
							<p></p><!-- 제목 -->
							<p></p><!-- 등록날짜 -->
						</div>
					</div>
				</div>
			</div>
	    </div>
  	</div>
  </div>

<%-- 경로추천 인기글 top3 --%>
  <div class="row mt-5 mb-5">
    <div class="col">
      <div id="carouselExampleIndicators2" class="carousel slide" data-bs-ride="carousel">
  		<div class="h4 float-start">&nbsp; ▶ 경로추천 인기글 TOP3</div>
  		<div class="carousel-indicators">
    		<button type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
    		<button type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide-to="1" aria-label="Slide 2"></button>
    		<button type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide-to="2" aria-label="Slide 3"></button>
  		</div>
  
  		<div class="carousel-inner">
    		<!-- 첫 번째 슬라이드 -->
	    	<div class="carousel-item active">
	      		<a href="#">
	        		<img src="https://via.placeholder.com/300x150" class="d-block w-100" alt="게시글 이미지">
	      		</a>
	      		<div class="carousel-caption">
	        		<h4>제목</h4>
	        		<h6>교통수단</h6>
	      		</div>
	    	</div>

		    <!-- 두 번째 슬라이드 -->
		    <div class="carousel-item">
		      <a href="#">
		        <img src="https://via.placeholder.com/300x150" class="d-block w-100" alt="게시글 이미지">
		      </a>
		      <div class="carousel-caption">
		        <h4>제목</h4>
		        <h6>교통수단</h6>
		      </div>
		    </div>

		    <!-- 세 번째 슬라이드 -->
		    <div class="carousel-item">
		      <a href="#">
		        <img src="https://via.placeholder.com/300x150" class="d-block w-100" alt="게시글 이미지">
		      </a>
		      <div class="carousel-caption">
		        <h4>제목</h4>
		        <h6>교통수단</h6>
		      </div>
		    </div>
  		</div>
  
	    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Previous</span>
		</button>
  
		<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators2" data-bs-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Next</span>
		</button>
	</div>
  </div>
</div>  
  
  
<%-- 지역소담이 인기글 top3 --%> 
 <div class="row my-5">
    <div class="col"> 
		<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
		  <div class="h4 float-start">&nbsp; ▶ 지역소담이 인기글 TOP 3</div>
		  <div class="carousel-indicators">
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
		    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
		  </div>
		  
		  <div class="carousel-inner">
		    <!-- 첫 번째 슬라이드 -->
		    <div class="carousel-item active">
		      <a href="#">
		        <img src="https://via.placeholder.com/300x150" class="d-block w-100" alt="블로그 이미지">
		      </a>
		      <div class="carousel-caption">
		        <h5>제목</h5>
		        <p>지역</p>
		      </div>
		    </div>
		
		    <!-- 두 번째 슬라이드 -->
		    <div class="carousel-item">
		      <a href="#">
		        <img src="https://via.placeholder.com/300x150" class="d-block w-100" alt="블로그 이미지">
		      </a>
		      <div class="carousel-caption">
		        <h5>제목</h5>
		        <p>지역</p>
		      </div>
		    </div>
		
		    <!-- 세 번째 슬라이드 -->
		    <div class="carousel-item">
		      <a href="#">
		        <img src="https://via.placeholder.com/300x150" class="d-block w-100" alt="블로그 이미지">
		      </a>
		      <div class="carousel-caption">
		        <h5>제목</h5>
		        <p>지역</p>
		      </div>
		    </div>
		  </div>
		  
		  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Previous</span>
		  </button>
		  
		  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Next</span>
		  </button>
		</div>
    </div>
  </div>
  
 <%-- 지역별 여행 등록수 현황 --%>
  
 <div class="row my-5">
 	<div class="col">
 		<div class="carousel">
	 		<div class="h4 float-start">&nbsp; ▶ 지역별 여행 등록수 현황</div>
	    	<div class="container" style="height: 500px;" id="map" style="border: 1px solid black;"></div>
    	</div>
    </div>
  </div>

</div>
<c:import url="/WEB-INF/views/common/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>