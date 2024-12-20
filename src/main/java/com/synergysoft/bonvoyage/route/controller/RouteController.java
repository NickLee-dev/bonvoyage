package com.synergysoft.bonvoyage.route.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.synergysoft.bonvoyage.comment.model.dto.Comment;
import com.synergysoft.bonvoyage.comment.model.service.CommentService;
import com.synergysoft.bonvoyage.common.FileNameChange;
import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.place.model.dto.Place;
import com.synergysoft.bonvoyage.place.model.service.PlaceService;
import com.synergysoft.bonvoyage.route.model.dto.Route;
import com.synergysoft.bonvoyage.route.model.service.RouteService;
import com.synergysoft.bonvoyage.routedata.model.dto.RouteData;
import com.synergysoft.bonvoyage.routedata.model.service.RouteDataService;

@Controller
public class RouteController {
	private static final Logger logger = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private RouteDataService routeDataService;
	
	@Autowired
	private CommentService commentService;
	
	// 뷰 페이지 이동 메소드
	@RequestMapping("moveWriteRoute.do")
	public String moveWritePage() {
		return "route/routeWritePage";
	}
	
	// 경로추천게시판 수정페이지 이동 메소드 : moveUpdateRoute.do
	@RequestMapping("moveUpdateRoute.do")
	public ModelAndView moveUpdatePage(ModelAndView mv, @RequestParam("no") String routeBoardId) {
		Route route = routeService.selectRoute(routeBoardId);
		ArrayList<Place> place = placeService.selectPlace(routeBoardId);
		
		if(route != null) {
			mv.addObject("route", route);
			mv.addObject("place", place);
			mv.setViewName("route/routeUpdateView");
		}else {
			mv.addObject("message", "수정페이지 이동 실패");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	// 신고글 작성페이지 이동 메소드 : routeReport.do
	@RequestMapping("routeReport.do")
	public ModelAndView moveRouteReportPage(ModelAndView mv,
																		@RequestParam("routeBoardId") String routeBoardId,
																		@RequestParam("userId") String userId,
																		@RequestParam("title") String title) {
		// 전송보낼 데이터를 담기 위한 객체 생성
		Route route = new Route();
		route.setRouteBoardId(routeBoardId);
		route.setUserId(userId);
		route.setTitle(title);
		
		// 전송보낼 데이터를 담은 객체 mv 에 저장
		mv.addObject(route);
		mv.setViewName("report/reportWriteView");
		
		return mv;
	}
	
	// 경로추천게시판 전체 출력 메소드 : routeall.do
	@RequestMapping("routeall.do")
	public String selectAllRouteMethod(Model model,
														@RequestParam(name="page", required=false) String page,
														@RequestParam(name="limit", required=false) String slimit,
														@RequestParam(name="groupLimit", required=false) String glimit) {
		
		//paging 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage=1;
		if(page!=null) {
			currentPage = Integer.parseInt(page);
		}
		// 한페이지에 출력할 공지글 갯수 (기본값 10개 세팅)
		int limit =10;
		if(slimit !=null) {
			limit= Integer.parseInt(slimit);
		}
		// 페이징 그룹 갯수 (기본값 5개 세팅)
		int groupLimit =5;
		if(glimit!=null) {
			groupLimit=Integer.parseInt(glimit);
		}
		// 총 목록 갯수 조회
		int listCount = routeService.selectListCount();
		logger.info("공지사항 총 갯수 : " +listCount);
		
		// 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "routeall.do", groupLimit);
		paging.calculate();
		//paging 세팅---------------------------------------------------------------------------
	    // 서비스를 목록 조회 요청하고 결과 받기(페이징 처리)
	    ArrayList<Route> list = routeService.selectAllRoute(paging);  // 전체목록 받아오기
	    logger.info("list : " + list);
	    if(list != null && list.size() > 0) {
	        model.addAttribute("list", list);
			model.addAttribute("paging",paging);
			model.addAttribute("currentPage",currentPage);
			return "route/routeall";
	    }else {
	    	model.addAttribute("message", "목록출력실패");
	    	return "common/error";
	    }
	}//selectAllRouteMethod()
	
	// 경로추천게시판 등록 메소드 : inroute.do
	@RequestMapping(value="inroute.do", method=RequestMethod.POST)
	public String insertRouteMethod(Route route, Model model, Place place,
													@RequestParam(name="ofile11", required=false) MultipartFile mfile1, 
													@RequestParam(name="ofile22", required=false) MultipartFile mfile2,
													@RequestParam(name="ofile33", required=false) MultipartFile mfile3,
													@RequestParam(name="ofile44", required=false) MultipartFile mfile4,
													@RequestParam(name="ofile55", required=false) MultipartFile mfile5,
													@RequestParam("routePlaceAddress[]") String[] rpaddress,
													@RequestParam("routePlaceName[]") String[] rpname,
													@RequestParam("routePlaceContent[]") String[] rpcontent,
													@RequestParam(name="transport", required=false) String transport,
													HttpServletRequest request) {
		
		logger.info("inroute.do : " + route);
		
		// 첨부파일 저장폴더 경로 지정
		String savePath = request.getSession().getServletContext().getRealPath("resources/route_upfiles");
		
		// 첨부파일이 있을경우 : ofile1
		if(!mfile1.isEmpty()) {
			// 전송온 파일이름 추출
			String fileName1 = mfile1.getOriginalFilename();
			String renameFileName1 = null;
			
			// 저장폴더에 변경이름으로 저장
			// 파일이름 바꾸기 : 년월일시분초.확장자
			if(fileName1 != null && fileName1.length() > 0) {
				// 바꿀 파일명에 대한 문자열
				renameFileName1 = FileNameChange.change(fileName1, "yyyyMMddHHmmssSSS");
				// 바뀐 파일명 확인
				logger.info("첨부파일명 확인 : " + renameFileName1);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					mfile1.transferTo(new File(savePath + File.separator + renameFileName1));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패");
					return "common/error";
				}
			}//파일명 바꾸기
			
			// 객체에 첨부파일 정보 저장
			route.setOfile1(fileName1);
			route.setRfile1(renameFileName1);
		}// ofile1
		
		// 첨부파일이 있을경우 : ofile2
		if(!mfile2.isEmpty()) {
			// 전송온 파일이름 추출
			String fileName2 = mfile2.getOriginalFilename();
			String renameFileName2 = null;
			
			// 저장폴더에 변경이름으로 저장
			// 파일이름 바꾸기 : 년월일시분초.확장자
			if(fileName2 != null && fileName2.length() > 0) {
				// 바꿀 파일명에 대한 문자열
				renameFileName2 = FileNameChange.change(fileName2, "yyyyMMddHHmmssSSS");
				// 바뀐 파일명 확인
				logger.info("첨부파일명 확인 : " + renameFileName2);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					mfile2.transferTo(new File(savePath + File.separator + renameFileName2));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패");
					return "common/error";
				}
			}//파일명 바꾸기
			
			// 객체에 첨부파일 정보 저장
			route.setOfile2(fileName2);
			route.setRfile2(renameFileName2);
		}// ofile2
		
		// 첨부파일이 있을경우 : ofile3
		if(!mfile3.isEmpty()) {
			// 전송온 파일이름 추출
			String fileName3 = mfile3.getOriginalFilename();
			String renameFileName3 = null;
			
			// 저장폴더에 변경이름으로 저장
			// 파일이름 바꾸기 : 년월일시분초.확장자
			if(fileName3 != null && fileName3.length() > 0) {
				// 바꿀 파일명에 대한 문자열
				renameFileName3 = FileNameChange.change(fileName3, "yyyyMMddHHmmssSSS");
				// 바뀐 파일명 확인
				logger.info("첨부파일명 확인 : " + renameFileName3);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					mfile3.transferTo(new File(savePath + File.separator + renameFileName3));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패");
					return "common/error";
				}
			}//파일명 바꾸기
			
			// 객체에 첨부파일 정보 저장
			route.setOfile3(fileName3);
			route.setRfile3(renameFileName3);
		}// ofile3
		
		// 첨부파일이 있을경우 : ofile4
		if(!mfile4.isEmpty()) {
			// 전송온 파일이름 추출
			String fileName4 = mfile4.getOriginalFilename();
			String renameFileName4 = null;
			
			// 저장폴더에 변경이름으로 저장
			// 파일이름 바꾸기 : 년월일시분초.확장자
			if(fileName4 != null && fileName4.length() > 0) {
				// 바꿀 파일명에 대한 문자열
				renameFileName4 = FileNameChange.change(fileName4, "yyyyMMddHHmmssSSS");
				// 바뀐 파일명 확인
				logger.info("첨부파일명 확인 : " + renameFileName4);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					mfile4.transferTo(new File(savePath + File.separator + renameFileName4));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패");
					return "common/error";
				}
			}//파일명 바꾸기
			
			// 객체에 첨부파일 정보 저장
			route.setOfile4(fileName4);
			route.setRfile4(renameFileName4);
		}// ofile4
		
		// 첨부파일이 있을경우 : ofile5
		if(!mfile5.isEmpty()) {
			// 전송온 파일이름 추출
			String fileName5 = mfile5.getOriginalFilename();
			String renameFileName5 = null;
			
			// 저장폴더에 변경이름으로 저장
			// 파일이름 바꾸기 : 년월일시분초.확장자
			if(fileName5 != null && fileName5.length() > 0) {
				// 바꿀 파일명에 대한 문자열
				renameFileName5 = FileNameChange.change(fileName5, "yyyyMMddHHmmssSSS");
				// 바뀐 파일명 확인
				logger.info("첨부파일명 확인 : " + renameFileName5);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					mfile5.transferTo(new File(savePath + File.separator + renameFileName5));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패");
					return "common/error";
				}
			}//파일명 바꾸기
			
			// 객체에 첨부파일 정보 저장
			route.setOfile5(fileName5);
			route.setRfile5(renameFileName5);
		}// ofile5
		
		if(transport.equals("publicTransport")) {
			route.setTransport(transport);
		} else if(transport.equals("bicycle")) {
			route.setTransport(transport);
		} else if(transport.equals("car")) {
			route.setTransport(transport);
		} else if(transport.equals("walking")) {
			route.setTransport(transport);
		} else if(transport.equals("train")) {
			route.setTransport(transport);
		}
		
		if(routeService.insertRoute(route) > 0) {
			for(int i = 0; i < 5; i++) {
				place.setAddress(rpaddress[i]);
				place.setPlaceName(rpname[i]);
				place.setPlaceContent(rpcontent[i]);
				
				place.setPlaceRouteBoardId(route.getRouteBoardId());
				
				placeService.insertPlace(place);
				
				RouteData routeData = new RouteData();
				routeData.setRouteBoardId(route.getRouteBoardId());
				routeData.setRoutePlaceId(place.getPlaceId());
				routeData.setRoutePlaceOrderNo(i+1);
				
				routeDataService.insertRouteData(routeData);
			}
			
			return "redirect:routeall.do?page=1";
		}else {
			model.addAttribute("message", "등록실패");
			return "common/error";
		}
		
	}
	
	// 경로추천게시판 상세페이지 출력 메소드 : routedetail.do
	@RequestMapping("routedetail.do")
	public ModelAndView routeDetailMethod(@RequestParam("no") String routeBoardId,
																ModelAndView mv, HttpSession session) {
		
		logger.info("routedetail.do : " + routeBoardId);
		
		routeService.updateRouteReadCount(routeBoardId);
		
		Route route = routeService.selectRoute(routeBoardId);
		ArrayList<Place> place = placeService.selectPlace(routeBoardId);
		ArrayList<Comment> clist = commentService.selectComment(routeBoardId);
		
		if(route != null) {
			mv.addObject("route", route);
			mv.addObject("place", place);
			mv.addObject("clist", clist);
			logger.info("route : " + route);
			mv.setViewName("route/routeDetailView");

		}else {
			mv.addObject("message", routeBoardId + "번 게시글 상세보기요청 실패");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	// 경로추천게시판 수정 요청 처리 메소드 : uroute.do
	@RequestMapping(value="uroute.do", method=RequestMethod.POST)
	public String routeUpdateMethod(Route route, Model model, HttpServletRequest request,
													@RequestParam(name="ofile11", required=false) MultipartFile ofile1,
													@RequestParam(name="ofile22", required=false) MultipartFile ofile2,
													@RequestParam(name="ofile33", required=false) MultipartFile ofile3,
													@RequestParam(name="ofile44", required=false) MultipartFile ofile4,
													@RequestParam(name="ofile55", required=false) MultipartFile ofile5,
													@RequestParam("transport") String transport) {
		// 전송확인
		logger.info("uroute.do : " + route );
		
		String savePath = request.getSession().getServletContext().getRealPath("resources/route_upfiles");
		
		// 첨부파일 삭제
		if(!ofile1.isEmpty()) {
			// 저장폴더에서 이전파일 삭제
			new File(savePath + File.separator + route.getRfile1()).delete();
			
			// route 안의 파일정보 삭제
			route.setOfile1(null);
			route.setRfile1(null);
			
			// 새로운 첨부파일 저장처리
			
			// 전송온 파일이름 추출
			String fileName = ofile1.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에 변경된 이름으로 저장처리
			// 파일이름 변경 : 년월일시분초.확장자
			if(fileName != null && fileName.length() >0 ) {
				
				// 바꿀 파일명 문자열
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				
				logger.info("첨부파일명 확인 : " + renameFileName);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					ofile1.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일저장 실패");
					return "common/error";
				}
			}
			
			route.setOfile1(fileName);
			route.setRfile1(renameFileName);
		}

		if(!ofile2.isEmpty()) {
			// 저장폴더에서 이전파일 삭제
			new File(savePath + File.separator + route.getRfile2()).delete();
			
			// route 안의 파일정보 삭제
			route.setOfile2(null);
			route.setRfile2(null);
			
			// 새로운 첨부파일 저장처리
			
			// 전송온 파일이름 추출
			String fileName = ofile2.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에 변경된 이름으로 저장처리
			// 파일이름 변경 : 년월일시분초.확장자
			if(fileName != null && fileName.length() >0 ) {
				
				// 바꿀 파일명 문자열
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				
				logger.info("첨부파일명 확인 : " + renameFileName);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					ofile2.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일저장 실패");
					return "common/error";
				}
			}
			
			route.setOfile2(fileName);
			route.setRfile2(renameFileName);
		}
		
		
		if(!ofile3.isEmpty()) {
			// 저장폴더에서 이전파일 삭제
			new File(savePath + File.separator + route.getRfile3()).delete();
			
			// route 안의 파일정보 삭제
			route.setOfile3(null);
			route.setRfile3(null);
			
			// 새로운 첨부파일 저장처리
			
			// 전송온 파일이름 추출
			String fileName = ofile3.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에 변경된 이름으로 저장처리
			// 파일이름 변경 : 년월일시분초.확장자
			if(fileName != null && fileName.length() >0 ) {
				
				// 바꿀 파일명 문자열
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				
				logger.info("첨부파일명 확인 : " + renameFileName);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					ofile3.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일저장 실패");
					return "common/error";
				}
			}
			
			route.setOfile3(fileName);
			route.setRfile3(renameFileName);
		}
		
		
		if(!ofile4.isEmpty()) {
			// 저장폴더에서 이전파일 삭제
			new File(savePath + File.separator + route.getRfile4()).delete();
			
			// route 안의 파일정보 삭제
			route.setOfile4(null);
			route.setRfile4(null);
			
			// 새로운 첨부파일 저장처리
			
			// 전송온 파일이름 추출
			String fileName = ofile4.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에 변경된 이름으로 저장처리
			// 파일이름 변경 : 년월일시분초.확장자
			if(fileName != null && fileName.length() >0 ) {
				
				// 바꿀 파일명 문자열
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				
				logger.info("첨부파일명 확인 : " + renameFileName);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					ofile4.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일저장 실패");
					return "common/error";
				}
			}
			
			route.setOfile4(fileName);
			route.setRfile4(renameFileName);
		}
		
		
		if(!ofile5.isEmpty()) {
			// 저장폴더에서 이전파일 삭제
			new File(savePath + File.separator + route.getRfile5()).delete();
			
			// route 안의 파일정보 삭제
			route.setOfile5(null);
			route.setRfile5(null);
			
			// 새로운 첨부파일 저장처리
			
			// 전송온 파일이름 추출
			String fileName = ofile5.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에 변경된 이름으로 저장처리
			// 파일이름 변경 : 년월일시분초.확장자
			if(fileName != null && fileName.length() >0 ) {
				
				// 바꿀 파일명 문자열
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				
				logger.info("첨부파일명 확인 : " + renameFileName);
				
				try {
					// 저장폴더에 바뀐파일명으로 저장
					ofile5.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일저장 실패");
					return "common/error";
				}
			}
			
			route.setOfile5(fileName);
			route.setRfile5(renameFileName);
		}
		
		if(transport.equals("publicTransport")) {
			route.setTransport(transport);
		} else if(transport.equals("bicycle")) {
			route.setTransport(transport);
		} else if(transport.equals("car")) {
			route.setTransport(transport);
		} else if(transport.equals("walking")) {
			route.setTransport(transport);
		} else if(transport.equals("train")) {
			route.setTransport(transport);
		}
		
		if (routeService.updateRoute(route) > 0) {
			return "redirect:routedetail.do?no=" + route.getRouteBoardId();
		}else {
			model.addAttribute("message", "수정 실패");
			return "common/error";
		}
		
		
		
		
		
		
		
	}
	
	// 경로추천게시판 삭제 요청 처리 메소드 : droute.do
	@RequestMapping("droute.do")
	public String routeDeleteMethod(Model model, HttpServletRequest request,
													@RequestParam("no") String routeBoardId,
													@RequestParam(name="rfile11", required=false) String renameFileName1,
													@RequestParam(name="rfile22", required=false) String renameFileName2,
													@RequestParam(name="rfile33", required=false) String renameFileName3,
													@RequestParam(name="rfile44", required=false) String renameFileName4,
													@RequestParam(name="rfile55", required=false) String renameFileName5) {
		
		logger.info("droute.do : " + renameFileName1);
		
		String savePath = request.getSession().getServletContext().getRealPath("resources/route_upfiles");
		if(routeService.deleteRoute(routeBoardId) > 0 ) {
			if (renameFileName1 != null && renameFileName1.length() > 0) {
				
				new File(savePath + File.separator + renameFileName1).delete();
			}
			
			if (renameFileName2 != null && renameFileName2.length() > 0) {
				new File(savePath + File.separator + renameFileName2).delete();
			}
			
			if (renameFileName3 != null && renameFileName3.length() > 0) {
				new File(savePath + File.separator + renameFileName3).delete();
			}
			
			if (renameFileName4 != null && renameFileName4.length() > 0) {
				new File(savePath + File.separator + renameFileName4).delete();
			}
			
			if (renameFileName5 != null && renameFileName5.length() > 0) {
				new File(savePath + File.separator + renameFileName5).delete();
			}
			
			return "redirect:routeall.do?page=1";
		}else {
			model.addAttribute("message", "게시글 삭제 실패");
			return "common/error";
		}
	}
	
	// 경로추천게시판 검색 처리 메소드 : sroute.do
	@RequestMapping("sroute.do")
	public String selectSearchRoute(Model model,
													@RequestParam("action") String action,
													@RequestParam("keyword") String keyword,
													@RequestParam(name="page", required=false) String page,
													@RequestParam(name="limit", required=false) String slimit,
													@RequestParam(name="groupLimit", required=false) String glimit) {
		
		//paging 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage=1;
		if(page!=null) {
			currentPage = Integer.parseInt(page);
		}
		
		// 한페이지에 출력할 공지글 갯수 (기본값 10개 세팅)
		int limit =10;
		if(slimit !=null) {
			limit= Integer.parseInt(slimit);
		}
		// 페이징 그룹 갯수 (기본값 5개 세팅)
		int groupLimit =5;
		if(glimit!=null) {
			groupLimit=Integer.parseInt(glimit);
		}
		
		int listCount = 0;
		// 총 목록 갯수 조회
	    if(action.equals("title")) {
	    	listCount = routeService.selectSearchTitleListCount(keyword);
	    } else if(action.equals("content")) {
	    	listCount = routeService.selectSearchContentListCount(keyword);
	    } else if(action.equals("userId")) {
	    	listCount = routeService.selectSearchUserIdListCount(keyword);
	    }
	    
	 // 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "sroute.do", groupLimit);
		paging.calculate();
		
		// 검색시 사용할 값 전송 객체생성 및 값 입력
	    Search search = new Search();
	    search.setKeyword(keyword);
	    search.setStartRow(paging.getStartRow());
	    search.setEndRow(paging.getEndRow());
	    ArrayList<Route> list =null;
	    
	    // 서비스를 목록 조회 요청하고 결과 받기(페이징 처리)
	    if(action.equals("title")) {
	    	list = routeService.selectSearchTitleRoute(search);
	    } else if(action.equals("content")) {
	    	list = routeService.selectSearchContentRoute(search);
	    } else if(action.equals("userId")) {
	    	list = routeService.selectSearchUserIdRoute(search);
	    }
	    
	    logger.info("list : " + list);
	    if(list != null && list.size() > 0) {
	        model.addAttribute("list", list);
			model.addAttribute("paging",paging);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("action",action);
			model.addAttribute("keyword",keyword);
			
	        return "route/routeall";  // 뷰의 이름을 반환
	    } else {
	        model.addAttribute("message", action + "에 대한 " + keyword + "검색결과가 존재하지 않습니다.");
	        return "common/error";  // 에러 페이지 뷰의 이름 반환
	    }
	}
	
	// 추천수 많은 인기 게시글 top-3 요청 처리용 : rtop3.do
	@RequestMapping(value = "rtop3.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardTop3Method(
			HttpServletResponse response
			) throws UnsupportedEncodingException {
		
		// 조회수 많은 게시글 3개 조회 요청
		ArrayList<Route> list = routeService.selectTop3();
		
		// 내보낼 값에 대해 response 에 mime타입 설정
		response.setContentType("application/json; charset=utf-8");
		
		// 리턴된 list를 json 배열에 옴겨 기록하기
		JSONArray jarr = new JSONArray();
		
		for(Route route : list) {
			JSONObject job = new JSONObject();
			// 게시번호
			job.put("routeBoardId", URLEncoder.encode(route.getRouteBoardId(),"utf-8"));
			// 제목
			job.put("title", URLEncoder.encode(route.getTitle(),"utf-8"));
			// 교통수단
			job.put("transport", URLEncoder.encode(route.getTransport(),"utf-8"));
			
			// 이미지
			if (route.getRfile1() != null && !route.getRfile1().isEmpty()) {
	            job.put("rFile1", URLEncoder.encode(route.getRfile1(),"utf-8"));
	        } else {
	            job.put("rFile1", "");  // 이미지가 없는 경우 빈 문자열
	        }
			
			jarr.add(job); // 배열에 Json 객체 추가
		}
		
		// 전송용 JSON 객체 생성
		JSONObject sendJson = new JSONObject();
		sendJson.put("rlist", jarr);
		
		// 최종적으로 JSON 문자열로 변환하여 반환
		return sendJson.toJSONString();
	}












}








