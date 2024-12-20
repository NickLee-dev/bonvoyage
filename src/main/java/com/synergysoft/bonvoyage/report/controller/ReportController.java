package com.synergysoft.bonvoyage.report.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.guide.model.dto.Guide;
import com.synergysoft.bonvoyage.guide.model.service.GuideService;
import com.synergysoft.bonvoyage.member.model.dto.Member;
import com.synergysoft.bonvoyage.member.model.service.MemberService;
import com.synergysoft.bonvoyage.report.model.dto.Report;
import com.synergysoft.bonvoyage.report.model.service.ReportService;
import com.synergysoft.bonvoyage.route.model.dto.Route;
import com.synergysoft.bonvoyage.route.model.service.RouteService;

@Controller
public class ReportController {

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private GuideService guideService;

	@Autowired
	private RouteService routeService;

	// 새 신고 등록 페이지 이동 처리
	@RequestMapping("reportWrite.do")
	public ModelAndView moveWritePage(ModelAndView mv, HttpSession session) {

		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser != null) {
			mv.setViewName("member/report/reportWriteView");
		} else {
			mv.addObject("message", "세션이 없습니다. 로그인 후 다시 이용해주시기 바랍니다.");
			mv.setViewName("common/error");
		}
		return mv;
	}

	// 요청 처리
	// 신고글 상세 내용 보기 요청 처리
	@RequestMapping("reportDetail.do")
	public ModelAndView reportDetailMethod(@RequestParam("reportId") String reportId, ModelAndView mv,
			HttpSession session) {

		// 관리자용 상세보기 페이지와 일반회원 상세보기 페이지를 구분해서 응답 처리

		logger.info("rdetail.do : " + reportId); // 전송받은 값 확인

		Report report = reportService.selectReportDetail(reportId);

		Member loginUser = (Member) session.getAttribute("loginUser");

		if ((report != null && report.getReportUserId().equals(loginUser.getMemId()))
				|| loginUser.getMemType().equals("ADMIN")) {
			mv.addObject("report", report);
			mv.setViewName("report/reportDetailView");
		} else {
			mv.addObject("message", reportId + "번 신고글 상세보기 요청 실패");
			mv.setViewName("common/error");
		}
		return mv;
	} // 신고글 상세정보 보기

	// 신고글 전체 목록 보기 (페이징/검색)
	@RequestMapping("reportList.do")
	public ModelAndView reportListMethod(
//			@RequestParam("action") String action,  // 검색 유형 (제목,내용)
//			@RequestParam("keyword") String keyword,  // 검색키워드값		
			@RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit, ModelAndView mv, HttpSession session,
			Model model) {

		// paging
		// 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}

		// 한페이지에 출력할 공지글 갯수 (기본값 10개 세팅)
		int limit = 10;
		if (slimit != null) {
			limit = Integer.parseInt(slimit);
		}
		// 페이징 그룹 갯수 (기본값 5개 세팅)
		int groupLimit = 5;
		if (glimit != null) {
			groupLimit = Integer.parseInt(glimit);
		}

		// paging
		// 세팅---------------------------------------------------------------------------
		// 서비스롤 목록 조회 요청하고 결과 받기
		// 관리자인지 일반회원인지 확인
		Member loginUser = (Member) session.getAttribute("loginUser");
//관리자이면
		int listCount = 0;
		if (loginUser != null && loginUser.getMemType().equals("ADMIN")) {
			// 전체 신고글 목록갯수 조회
			listCount = reportService.selectReportListCount();

		} else if (loginUser != null && loginUser.getMemType().equals("USER")) {
			listCount = reportService.selectReportUserListCount(loginUser.getMemId());
		}

		// 페이징 계산
		Paging paging = new Paging(listCount, limit, currentPage, "reportList.do", groupLimit);
		paging.calculate();

		ArrayList<Report> list = null;
		if (loginUser != null && loginUser.getMemType().equals("ADMIN")) {
			// 전체 신고글 목록 조회
			list = reportService.selectReport(paging);
			if (list != null && list.size() > 0) {
				mv.addObject("list", list);
				mv.addObject("paging", paging);
				mv.addObject("currentPage", currentPage);
				mv.setViewName("report/reportListView");
			} else {
				mv.addObject("message", "신고 이력이 없습니다.");
				mv.setViewName("common/error");
			}
		} else if (loginUser != null && loginUser.getMemType().equals("USER")) {
			// 회원이 등록한 목록 조회
			Report userReport = new Report();
			userReport.setReportUserId(loginUser.getMemId());
			userReport.setStartRow(paging.getStartRow());
			userReport.setEndRow(paging.getEndRow());
			list = reportService.selectReportMe(userReport);
			if (list != null && list.size() > 0) {
				mv.addObject("list", list);
				mv.addObject("paging", paging);
				mv.addObject("currentPage", currentPage);
				mv.setViewName("report/reportListView");
			} else {
				mv.addObject("message", "신고 이력이 없습니다.");
				mv.setViewName("common/error");
			}
		} else {
			mv.addObject("message", "세션이 없습니다. 로그인 후 다시 이용해주시기 바랍니다.");
			mv.setViewName("common/error");
		}
		return mv;
	} // 신고글 전체 목록 보기

	// 신고글 등록 기능
	@RequestMapping("reportInsert.do")
	public ModelAndView reportInsertMethod(Report report, ModelAndView mv, HttpSession session) {
		if (reportService.insertReport(report) > 0) {
			mv.addObject("report", report);
			mv.setViewName("common/main");
		} else {
			mv.addObject("message", "신고 등록 실패");
			mv.setViewName("common/error");
		}
		return mv;
	} // 신고글 등록 기능

	// 신고글 처리 기능
	@RequestMapping("reportUpdateProcess.do")
	public ModelAndView reportUpdateProcessMethod(Report report, ModelAndView mv, HttpSession session) {

		logger.info("report :: " + report);
		Member loginUser = (Member) session.getAttribute("loginUser");
		Report reportProcess = reportService.selectReportDetail(report.getReportId());

		if (loginUser != null && loginUser.getMemType().equals("ADMIN")) {
			Route route = routeService.selectRoute(reportProcess.getPostId()); // 문제의 게시글을 불러옴
			Guide guide = guideService.selectGuide(reportProcess.getPostId()); // 문제의 게시글을 불러옴

			String userId = null;

			// route가 null이 아닌 경우
			if (route != null) {
				userId = route.getUserId();
			}

			// guide가 null이 아닌 경우
			if (userId == null && guide != null) {
				userId = guide.getGuideUserId();
			}

			// userId가 null이 아닐 경우에만 업데이트 시도
			if (userId != null && memberService.updateMemberAccount(userId) > 0) {
				// 계정 정지 처리 후 게시글 삭제
				if (route != null && routeService.deleteRoute(reportProcess.getPostId()) > 0
						|| guide != null && guideService.deleteGuide(reportProcess.getPostId()) > 0) {
					// 신고 처리 완료
					if (reportService.updateReportProcess(report) > 0) {
						mv.addObject("report", reportProcess);
						mv.setViewName("redirect:reportList.do");
					} else {
						mv.addObject("message", "신고 처리 실패");
						mv.setViewName("common/error");
					}
				} else {
					mv.addObject("message", "신고 처리 실패");
					mv.setViewName("common/error");
				}
			} else {
				mv.addObject("message", "신고 처리 실패");
				mv.setViewName("common/error");
			}
		} else {
			mv.addObject("message", "권한이 없습니다.");
			mv.setViewName("common/error");
		}
		return mv;
	}

	// 신고글 처리 반려 기능
	@RequestMapping("reportUpdateReject.do")
	public ModelAndView reportUpdateRejectMethod(Report report, ModelAndView mv, HttpSession session) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser != null && loginUser.getMemType().equals("ADMIN")) {
			if (reportService.updateReportReject(report) > 0) {
				mv.addObject("report", report);
				mv.setViewName("redirect:reportList.do");
			} else {
				mv.addObject("message", "신고 처리 실패");
				mv.setViewName("common/error");
			}
		} else {
			mv.addObject("message", "권한이 없습니다.");
		}
		return mv;
	} // 신고글 처리 반려 기능

	// 신고글 삭제 기능
	@RequestMapping("reportDelete.do")
	public ModelAndView reportDeleteMethod(String reportId, ModelAndView mv, HttpSession session) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (reportService.deleteReport(reportId) > 0) {
			mv.addObject(loginUser);
			mv.setViewName("redirect:reportList.do");
		} else {
			mv.addObject("message", "신고 삭제 실패");
			mv.setViewName("common/error");
		}
		return mv;
	} // 신고글 삭제 기능

	// 신고글 검색 처리(제목/내용)
	@RequestMapping("reportSearch.do")
	public String selectSearchTitleReport(Model model, @RequestParam("action") String action, // 검색 유형 (제목,내용)
			@RequestParam("keyword") String keyword, // 검색키워드값
			@RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit, HttpSession session) {

		// paging
		// 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}

		// 한페이지에 출력할 공지글 갯수 (기본값 10개 세팅)
		int limit = 10;
		if (slimit != null) {
			limit = Integer.parseInt(slimit);
		}
		// 페이징 그룹 갯수 (기본값 5개 세팅)
		int groupLimit = 5;
		if (glimit != null) {
			groupLimit = Integer.parseInt(glimit);
		}

		int listCount = 0;
		// 총 목록 갯수 조회

		Member loginUser = (Member) session.getAttribute("loginUser");
		Report reportSearch = new Report();

		if (loginUser != null && loginUser.getMemType().equals("ADMIN")) {
			if (action.equals("reportingReason")) {
				reportSearch = new Report();
				reportSearch.setAction(action);
				reportSearch.setKeyword(keyword);
				listCount = reportService.selectReportSearchCount(reportSearch);
			} else if (action.equals("detail")) {
				reportSearch = new Report();
				reportSearch.setAction(action);
				reportSearch.setKeyword(keyword);
				listCount = reportService.selectReportSearchCount(reportSearch);
			}

			logger.info("신고글 총 갯수 : " + listCount);

			// 페이징 처리 값생성
			Paging paging = new Paging(listCount, limit, currentPage, "searchreport.do", groupLimit);
			paging.calculate();
			// paging
			// 세팅---------------------------------------------------------------------------

			// 검색시 사용할 값 전송 객체생성 및 값 입력
			reportSearch = new Report();
			reportSearch.setAction(action);
			reportSearch.setKeyword(keyword);
			reportSearch.setStartRow(paging.getStartRow());
			reportSearch.setEndRow(paging.getEndRow());
			ArrayList<Report> list = null;

			// 서비스를 목록 조회 요청하고 결과 받기(페이징 처리)
			if (action.equals("reportingReason")) {
				list = reportService.selectReportSearch(reportSearch);
			} else if (action.equals("detail")) {
				list = reportService.selectReportSearch(reportSearch);
			}

			logger.info("list : " + list);
			if (list != null && list.size() > 0) {
				model.addAttribute("list", list);
				model.addAttribute("paging", paging);
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("action", action);
				model.addAttribute("keyword", keyword);

				return "report/reportListView"; // 뷰의 이름을 반환
			} else {
				model.addAttribute("message", action + "에 대한 '" + keyword + "' 검색결과가 존재하지 않습니다.");
				return "common/error"; // 에러 페이지 뷰의 이름 반환
			}
		} else if (loginUser != null && loginUser.getMemType().equals("USER")) {
			if (action.equals("title")) {
				reportSearch = new Report();
				reportSearch.setAction(action);
				reportSearch.setKeyword(keyword);
				listCount = reportService.selectReportMeSearchCount(reportSearch);
			} else if (action.equals("content")) {
				reportSearch = new Report();
				reportSearch.setAction(action);
				reportSearch.setKeyword(keyword);
				listCount = reportService.selectReportMeSearchCount(reportSearch);
			}

			logger.info("신고글 총 갯수 : " + listCount);

			// 페이징 처리 값생성
			Paging paging = new Paging(listCount, limit, currentPage, "searchreport.do", groupLimit);
			paging.calculate();
			// paging
			// 세팅---------------------------------------------------------------------------

			// 검색시 사용할 값 전송 객체생성 및 값 입력
			reportSearch = new Report();
			reportSearch.setAction(action);
			reportSearch.setKeyword(keyword);
			reportSearch.setStartRow(paging.getStartRow());
			reportSearch.setEndRow(paging.getEndRow());
			ArrayList<Report> list = null;

			// 서비스를 목록 조회 요청하고 결과 받기(페이징 처리)
			if (action.equals("reportingReason")) {
				list = reportService.selectReportMeSearch(reportSearch);
			} else if (action.equals("detail")) {
				list = reportService.selectReportMeSearch(reportSearch);
			}

			logger.info("list : " + list);
			if (list != null && list.size() > 0) {
				model.addAttribute("list", list);
				model.addAttribute("paging", paging);
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("action", action);
				model.addAttribute("keyword", keyword);

				return "report/reportListView"; // 뷰의 이름을 반환
			} else {
				model.addAttribute("message", action + "에 대한 " + keyword + "검색결과가 존재하지 않습니다.");
				return "common/error"; // 에러 페이지 뷰의 이름 반환
			}
		} else {
			model.addAttribute("message", "세션이 없습니다. 로그인 후 다시 이용해주시기 바랍니다.");
			return "common/error";
		}
	}
}
