package com.synergysoft.bonvoyage.likecount.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergysoft.bonvoyage.guide.model.service.GuideService;
import com.synergysoft.bonvoyage.likecount.model.dto.LikeCount;
import com.synergysoft.bonvoyage.likecount.model.service.LikeCountService;
import com.synergysoft.bonvoyage.member.model.dto.Member;
import com.synergysoft.bonvoyage.route.controller.RouteController;
import com.synergysoft.bonvoyage.route.model.service.RouteService;

@Controller
public class LikeCountController {
	private static final Logger logger = LoggerFactory.getLogger(RouteController.class);

	@Autowired
	private LikeCountService likeCountService;
	
	@Autowired
	private RouteService routeService; 
	
	@Autowired
	private GuideService guideService;
	
	// 경로추천게시판 추천수 증가 메소드
	@RequestMapping("rlikecount.do")
	public String routeLikeCount(Model model,
												@RequestParam("postId") String postId,
												@RequestParam("userId") String userId) {
		
		
		// 게시글 식별코드와 로그인된 유저아이디를 보내기 위한 likecount 객체생성
		LikeCount likeCount = new LikeCount();
		// 생성한 객체에 전송된 식별코드와 아이디 저장
		likeCount.setPostId(postId);
		likeCount.setUserId(userId);
		
		logger.info("likeCount" + likeCount);
		
		// likecount 테이블에 전송된 게시글 식별코드와 아이디를 보내 추천한 이력이 있는지 확인
		LikeCount chkLikeCount = likeCountService.selectLikeCount(likeCount);
		
		// 추천 이력 여부에 따른 추천수 증가
		if(chkLikeCount == null) {
			likeCountService.insertLikeCount(likeCount);
			routeService.updateRouteLikeCount(postId);
			return "redirect:routedetail.do?no=" + postId;
		}else {
			model.addAttribute("message", "이미 추천한 게시글입니다.");
			return "common/error";
		}

	}
	
	// 가이드 게시판 좋아요수 증가 메소드
		@RequestMapping("glikecount.do")
		public String guideLikeCount(Model model, HttpSession session,
							@RequestParam("postId") String postId,
							@RequestParam("userId") String userId) {
			 // 로그인된 유저 정보 확인
		    Member loginUser = (Member) session.getAttribute("loginUser");
		    
		    // 로그인되지 않은 경우
		    if (loginUser == null) {
		        model.addAttribute("message", "로그인이 필요합니다. 로그인 후 다시 시도해주세요.");
		        return "common/error"; // 로그인 페이지나 에러 페이지로 이동
		    }
			
			// 게시글 식별코드와 로그인된 유저아이디를 보내기 위한 likecount 객체생성
			LikeCount likeCount = new LikeCount();
			// 생성한 객체에 전송된 식별코드와 아이디 저장
			likeCount.setPostId(postId);
			likeCount.setUserId(userId);
			
			logger.info("likeCount" + likeCount);
			
			// likecount 테이블에 전송된 게시글 식별코드와 아이디를 보내 추천한 이력이 있는지 확인
			LikeCount gchkLikeCount = likeCountService.gselectLikeCount(likeCount);
			
			// 추천 이력 여부에 따른 추천수 증가
			if(gchkLikeCount == null) {
				likeCountService.ginsertLikeCount(likeCount);
				guideService.updateGuideLikeCount(postId);
				return "redirect:gdetail.do?guidepostId=" + postId;
			}else {
				model.addAttribute("message", "이미 추천한 게시글입니다.");
				return "common/error";
			}

		}
	
	
	
	
}
