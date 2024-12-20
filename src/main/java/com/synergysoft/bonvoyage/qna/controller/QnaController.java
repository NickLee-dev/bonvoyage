package com.synergysoft.bonvoyage.qna.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.synergysoft.bonvoyage.common.FileNameChange;
import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.member.model.dto.Member;
import com.synergysoft.bonvoyage.notice.model.dto.Notice;
import com.synergysoft.bonvoyage.qna.model.dto.Qna;
import com.synergysoft.bonvoyage.qna.model.service.QnaService;

@Controller
public class QnaController {

	//로그 객체 생성
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	
	@Autowired
	private QnaService qnaService;
	
	/*
	 * //최근 등록글 10 출력
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value="stqna.do", method=RequestMethod.POST) public String
	 * selectTopQna( HttpServletResponse response ) throws
	 * UnsupportedEncodingException { // 최근 등록된 공지글 10개 조회 ArrayList<Qna> list =
	 * qnaService.selectTopQna(); logger.info("list : " + list);
	 * response.setContentType("application/json; charset=utf-8");
	 * 
	 * JSONArray jarr=new JSONArray(); logger.info("list : " + list); for(Qna qna :
	 * list) { JSONObject job = new JSONObject(); job.put("qnaId",qna.getQnaId());
	 * job.put("title", URLEncoder.encode(qna.getTitle(),"utf-8"));
	 * job.put("qCreatedAt",qna.getqCreatedAt().toString());
	 * 
	 * jarr.add(job); } JSONObject sendJson = new JSONObject();
	 * sendJson.put("qlist", jarr); logger.info("QNA sendJson : " +
	 * sendJson.toJSONString()); return sendJson.toJSONString(); }
	 */
	
	// 전체리스트 출력
	@RequestMapping("saqna.do")
	public String selectAllQna(
			Model model,
			@RequestParam(name="page", required=false) String page,
			@RequestParam(name="limit", required=false) String slimit,
			@RequestParam(name="groupLimit", required=false) String glimit
			) {
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
		int listCount = qnaService.selectListCount();
		logger.info("QnA 총 갯수 : " +listCount);			
		
		// 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "saqna.do", groupLimit);
		paging.calculate();
		//paging 세팅---------------------------------------------------------------------------
	    // 서비스를 목록 조회 요청하고 결과 받기(페이징 처리)
	    ArrayList<Qna> list = qnaService.selectAllQna(paging);  // 전체목록 받아오기
	    
	    logger.info("list : " + list);
	    if(list != null && list.size() > 0) {
	        model.addAttribute("list", list);
			model.addAttribute("paging",paging);
			model.addAttribute("currentPage",currentPage);
	        return "qna/qnaListView";  // 뷰의 이름을 반환
	    } else {
	        model.addAttribute("message", "목록 조회 실패!");
	        return "common/error";  // 에러 페이지 뷰의 이름 반환
	    }	    
	} // "saqna.do"
	
	// 검색 출력
	@RequestMapping("ssqna.do")
	public String selectSearchQna(
			Model model,
			@RequestParam("action") String action,
			@RequestParam("keyword") String keyword,
			@RequestParam(name="page", required=false) String page,
			@RequestParam(name="limit", required=false) String slimit,
			@RequestParam(name="groupLimit", required=false) String glimit
			) {
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
		int listCount = 0;
	    if(action.equals("title")) {
	    	listCount = qnaService.selectSearchTitleListCount(keyword);
	    } else if(action.equals("userContent")) {
	    	listCount = qnaService.selectSearchUserContentListCount(keyword);
	    } else if(action.equals("userId")) {
	    	listCount = qnaService.selectSearchUserIdListCount(keyword);
	    }
		
		
		// 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "saqna.do", groupLimit);
		paging.calculate();
		//paging 세팅---------------------------------------------------------------------------
	    // 서비스를 목록 조회 요청하고 결과 받기(페이징 처리)
	    Search search = new Search();
	    search.setKeyword(keyword);
	    search.setStartRow(paging.getStartRow());
	    search.setEndRow(paging.getEndRow());		
	    ArrayList<Qna> list = null;
	    
	    qnaService.selectAllQna(paging);  // 전체목록 받아오기
	    // 서비스를 목록 조회 요청하고 결과 받기(페이징 처리)
	    if(action.equals("title")) {
	    	list = qnaService.selectSearchTitleQna(search);
	    } else if(action.equals("userContent")) {
	    	list = qnaService.selectSearchUserContentQna(search);
	    } else if(action.equals("userId")) {
	    	list = qnaService.selectSearchUserIdQna(search);
	    }
	    
	    logger.info("list : " + list);
	    if(list != null && list.size() > 0) {
	        model.addAttribute("list", list);
			model.addAttribute("paging",paging);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("action",action);
			model.addAttribute("keyword",keyword);			
			
	        return "qna/qnaListView";  // 뷰의 이름을 반환
	    } else {
	        model.addAttribute("message", action + "에 대한 " + keyword + "검색결과가 존재하지 않습니다.");
	        return "common/error";  // 에러 페이지 뷰의 이름 반환
	    }	    
	} // "saqna.do"
	
	// QnA작성페이지 이동
	@RequestMapping("miqna.do")
	public String moveInsertQna() {
		return "qna/insertQnaView";
	}
	
	//QnA 등록
	@RequestMapping(value="iqna.do", method=RequestMethod.POST)
	public String insertQna(
			Qna qna, // 전송보낼 객체
			Model model, // 오류시 view 전송할 객체
			HttpServletRequest request, // session활용 파일 저장경로 설정
			@RequestParam(name="insertFile", required=false) MultipartFile[] insertFile
			) {
		// 로그 확인
		logger.info("insert qna : " + qna);
		logger.info("insertFile : " + insertFile.length );
		
		// 세션정보확인
		HttpSession session = request.getSession();
		// 로그인 정보저장
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		// 세션 확인후 로그아웃이면 에러 출력
		if(loginUser == null) {
			model.addAttribute("message","로그인 정보가 없습니다.");
			return "common/error";
		}
		// 파일 저장 경로 설정
		String savePath = request.getSession().getServletContext().getRealPath("resources/qna_upfiles");
		
		// 파일명을 저장시킬 변수 생성
		String[] ofiles = new String[5];
		String[] rfiles = new String[5];
		
		// 배열에 각 파일 처리
		if (insertFile != null) { // insertFile이 Null이 아니면
			for (int i = 0; i < insertFile.length; i++) {  // insertFile 갯수만큼 반복문 진행
				if(!insertFile[i].isEmpty()) {  // insetFile[i] 의 null 여부확인
					// 원래 파일명 저장 변수 선언
					String fileName = insertFile[i].getOriginalFilename();
					// 변경 파일명 저장 변수 선언
					String renameFileName = null;
					// 기존파일명이 null아니면 리네임 + 파일저장 실행
					if(fileName != null && fileName.length()>0) {
						// 리네임 파일명 생성
						renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
						
						try {
							// 위에 선언한 경로에 리네임 파일명으로 파일저장
							insertFile[i].transferTo( new File(savePath+ File.separator + renameFileName));
						} catch (Exception e) {
							e.printStackTrace();
							model.addAttribute("message", "첨부파일 저장 실패!");
							return "common/error";
						} 
						
						//미리 준비한 배열에 원본파일명과 변경파일명 저장
						ofiles[i] = fileName;
						rfiles[i] = renameFileName;
					}
				}
			}
		}
		// 파일명 set
		qna.setoFile1(ofiles[0]);
		qna.setoFile2(ofiles[1]);
		qna.setoFile3(ofiles[2]);
		qna.setoFile4(ofiles[3]);
		qna.setoFile5(ofiles[4]);
		
		qna.setrFile1(rfiles[0]);
		qna.setrFile2(rfiles[1]);
		qna.setrFile3(rfiles[2]);
		qna.setrFile4(rfiles[3]);
		qna.setrFile5(rfiles[4]);
		logger.info("전송될 qna : " + qna);
		// 쿼리문 실행
		if (qnaService.insertQna(qna)>0) {
			return "redirect:saqna.do";
		} else {
			model.addAttribute("message", "Q&A 작성 실패");
			return "common/error";
		}
	}
	
	// 상세보기 페이지 이동
	@RequestMapping("msqna.do")
	public String moveSelectQna(
			Model model,		// view로 전송할 객체
			@RequestParam("qnaId") String qnaId,  // 조회해올 매개변수
			HttpSession session  // 세션별 페이지 구분용 객체생성
			) {
		logger.info("상세보기할 qnaId : " + qnaId);
		Qna qna = qnaService.moveSelectQna(qnaId); // 상세보기할 qna조회
		logger.info("불러온 Qna : " + qna);
		
		// memtype에 따라 뷰 구분
		if(qna != null) {
			model.addAttribute("qna",qna);			
			Member loginUser = (Member)session.getAttribute("loginUser");
			if(loginUser != null && loginUser.getMemType().equals("ADMIN")) {
				return "qna/moveSelectAdminQna";
			} else {
				return "qna/moveSelectUserQna";
			}
		}else {
			model.addAttribute("message","선택하신 QnA글 조회실패");
			return "common/error";
		}
		
	}
	
	//ajax 요청으로 파일 다운로드 처리용
	//첨부파일 다운로드 요청 처리용 메소드
	//공통모듈로 작성된 fileDownloadView 클래스를 이용함 => 반드시 리턴타입이 ModelAndView이어야한다.
	@RequestMapping("qfdown.do")
	public ModelAndView filedownMethod(
			HttpServletRequest request, ModelAndView mv,
			@RequestParam("oFile") String originalFileName,
			@RequestParam("rFile") String renameFileName) {
		
		//공지사항 첨부파일 저장 폴더 경로 지정
		String savePath = request.getSession().getServletContext().getRealPath("resources/qna_upfiles");
		// 저장 폴더에서 읽을 파일에 대한 file 객체 생성
		File downFile = new File(savePath + File.separator + renameFileName);
		// 파일 다운시 브라우저로 내보낼 원래 파일에 대한 File객체 생성함
		File originFile = new File(originalFileName);
		
		// 파일 다운 처리용 뷰 클래스 id 명과 다운로드할 File 객체를 ModelAndView 에 담아서 리턴함
		mv.setViewName("filedown"); //뷰클래스의 id명 기입
		mv.addObject("originFile", originFile);
		mv.addObject("renameFile", downFile);
		
		return mv;
		
	} 
	
	// 관리자 답글 처리
	@RequestMapping(value="iaqna.do", method=RequestMethod.POST)
	public String updateAdminQna(
			HttpServletRequest request,
			Qna qna,
			Model model
			) {
		logger.info("qna : " + qna);
		
		// 세션정보확인
		HttpSession session = request.getSession();
		// 로그인 정보저장
				
		Member loginUser = (Member)session.getAttribute("loginUser");
				
		// 세션 확인후 로그아웃이면 에러 출력
		if(loginUser == null) {
			model.addAttribute("message","로그인 정보가 없습니다.");
			return "common/error";
		}
		
		if(qnaService.updateAdminQna(qna) > 0) {
			return "redirect:saqna.do";
		} else {
			model.addAttribute("message","답변 처리에 실패하였습니다.");
			return "common/error";
		}
		
		
	}
	
	// 사용자 수정 페이지 이동
	@RequestMapping("muqna.do")
	public String moveUpdateQna(
			@RequestParam("qnaId") String qnaId,
			Model model
			) {
		logger.info("수정할 qnaId : " + qnaId);
		Qna qna = qnaService.moveSelectQna(qnaId);
		logger.info("불러온 Qna : " + qna);
		
		if(qna != null) {
			model.addAttribute("qna",qna);
			return "qna/moveUpdateQna";
		} else {
			model.addAttribute("message","수정 페이지 이동실패");
			return "common/error";
		}
		
	}
	
	// 사용자 수정 처리
	@RequestMapping(value="uqna.do", method=RequestMethod.POST)
	public String updateQna(
			Qna qna, // 전송온 객체
			Model model,  // 전송할 객체
			HttpServletRequest request,  // 파일경로 만들 객체
			@RequestParam(name="insertFile1", required=false) MultipartFile ifile1,
			@RequestParam(name="insertFile2", required=false) MultipartFile ifile2,
			@RequestParam(name="insertFile3", required=false) MultipartFile ifile3,
			@RequestParam(name="insertFile4", required=false) MultipartFile ifile4,
			@RequestParam(name="insertFile5", required=false) MultipartFile ifile5,
			@RequestParam(name="delete1", required=false) String delete1,
			@RequestParam(name="delete2", required=false) String delete2,
			@RequestParam(name="delete3", required=false) String delete3,
			@RequestParam(name="delete4", required=false) String delete4,
			@RequestParam(name="delete5", required=false) String delete5
			) {
		logger.info("수정한 Qna : " + qna);  //전송온값 확인
		
		// 로그인 상태확인 *********************************************************
		// 세션정보확인
		HttpSession session = request.getSession();
		// 로그인 정보저장
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		// 세션 확인후 로그아웃이면 에러 출력
		if(loginUser == null) {
			model.addAttribute("message","로그인 정보가 없습니다.");
			return "common/error";
		}
		// 로그인 상태확인 *********************************************************
		
		// 전송할 경로 String 생성
		String savePath = request.getSession().getServletContext().getRealPath("resources/qna_upfiles");
		
		// 파일1
		// 파일제거
		if((qna.getoFile1() != null //원래 첨부파일이 있는데 파일이 지워졋을때 또는 신규파일이 들어왔을때
				&& (delete1.equals("yes") && ifile1.isEmpty())) || !ifile1.isEmpty()
				) {    
			//저장폴더에서 이전 파일은 삭제함
			new File(savePath + File.separator + qna.getrFile1()).delete();
			
			//qna 안의 파일정보도 삭제함
			qna.setoFile1(null);
			qna.setrFile1(null);
		}
		
		// 파일 추가
		// 즉, upfile 이름으로 전송온 파일이 있따면
		if(!ifile1.isEmpty()) {  // mfile이 비어잇지 않다면 // 파일이 있다면
			// 전송온 파일이름 추출함
			String fileName = ifile1.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에는 변경된 이름을 저장 처리함
			// 파일 이름 바꾸기함 : 년월일시분초.확장자
			if(fileName != null && fileName.length() > 0) { // 파일이름이 null이 아니고 한글자 이상일때(""이 아닐때)
				// 바꿀 파일명에 대한 문자열 만들기
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				// 바뀔 파일명 확인
				logger.info("변경될 첨부파일명 확인 : "+renameFileName);
				
				try {
					// 저장 폴더에 파일명 바꾸어 저장하기
					ifile1.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message","첨부파일 저장 실패!!");
					return "common/error";
				}
			} // 파일명 바꾸기 블록		
			//qna 객체에 첨부파일 정보 저장 처리
			qna.setoFile1(fileName);
			qna.setrFile1(renameFileName);
			
		}// 첨부파일이 있을때
		
		// 파일2
		// 파일제거
		if((qna.getoFile2() != null //원래 첨부파일이 있는데 파일이 지워졋을때 또는 신규파일이 들어왔을때
				&& (delete2.equals("yes") && ifile2.isEmpty())) || !ifile2.isEmpty()
				) {    
			//저장폴더에서 이전 파일은 삭제함
			new File(savePath + File.separator + qna.getrFile2()).delete();
			
			//qna 안의 파일정보도 삭제함
			qna.setoFile2(null);
			qna.setrFile2(null);
		}
		
		// 파일 추가
		// 즉, upfile 이름으로 전송온 파일이 있따면
		if(!ifile2.isEmpty()) {  // mfile이 비어잇지 않다면 // 파일이 있다면
			// 전송온 파일이름 추출함
			String fileName = ifile2.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에는 변경된 이름을 저장 처리함
			// 파일 이름 바꾸기함 : 년월일시분초.확장자
			if(fileName != null && fileName.length() > 0) { // 파일이름이 null이 아니고 한글자 이상일때(""이 아닐때)
				// 바꿀 파일명에 대한 문자열 만들기
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				// 바뀔 파일명 확인
				logger.info("변경될 첨부파일명 확인 : "+renameFileName);
				
				try {
					// 저장 폴더에 파일명 바꾸어 저장하기
					ifile2.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message","첨부파일 저장 실패!!");
					return "common/error";
				}
			} // 파일명 바꾸기 블록		
			//qna 객체에 첨부파일 정보 저장 처리
			qna.setoFile2(fileName);
			qna.setrFile2(renameFileName);
			
		}// 첨부파일이 있을때		
		
		// 파일3
		// 파일제거
		if((qna.getoFile3() != null //원래 첨부파일이 있는데 파일이 지워졋을때 또는 신규파일이 들어왔을때
				&& (delete3.equals("yes") && ifile3.isEmpty())) || !ifile3.isEmpty()
			) { 
			//저장폴더에서 이전 파일은 삭제함
			new File(savePath + File.separator + qna.getrFile3()).delete();
			
			//qna 안의 파일정보도 삭제함
			qna.setoFile3(null);
			qna.setrFile3(null);
		}
		
		// 파일 추가
		// 즉, upfile 이름으로 전송온 파일이 있따면
		if(!ifile3.isEmpty()) {  // mfile이 비어잇지 않다면 // 파일이 있다면
			// 전송온 파일이름 추출함
			String fileName = ifile3.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에는 변경된 이름을 저장 처리함
			// 파일 이름 바꾸기함 : 년월일시분초.확장자
			if(fileName != null && fileName.length() > 0) { // 파일이름이 null이 아니고 한글자 이상일때(""이 아닐때)
				// 바꿀 파일명에 대한 문자열 만들기
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				// 바뀔 파일명 확인
				logger.info("변경될 첨부파일명 확인 : "+renameFileName);
				
				try {
					// 저장 폴더에 파일명 바꾸어 저장하기
					ifile3.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message","첨부파일 저장 실패!!");
					return "common/error";
				}
			} // 파일명 바꾸기 블록		
			//qna 객체에 첨부파일 정보 저장 처리
			qna.setoFile3(fileName);
			qna.setrFile3(renameFileName);
			
		}// 첨부파일이 있을때
		
		// 파일4
		// 파일제거
		if((qna.getoFile4() != null //원래 첨부파일이 있는데 파일이 지워졋을때 또는 신규파일이 들어왔을때
				&& (delete4.equals("yes") && ifile4.isEmpty())) || !ifile4.isEmpty()
			) { 
			//저장폴더에서 이전 파일은 삭제함
			new File(savePath + File.separator + qna.getrFile4()).delete();
			
			//qna 안의 파일정보도 삭제함
			qna.setoFile4(null);
			qna.setrFile4(null);
		}
		
		// 파일 추가
		// 즉, upfile 이름으로 전송온 파일이 있따면
		if(!ifile4.isEmpty()) {  // mfile이 비어잇지 않다면 // 파일이 있다면
			// 전송온 파일이름 추출함
			String fileName = ifile4.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에는 변경된 이름을 저장 처리함
			// 파일 이름 바꾸기함 : 년월일시분초.확장자
			if(fileName != null && fileName.length() > 0) { // 파일이름이 null이 아니고 한글자 이상일때(""이 아닐때)
				// 바꿀 파일명에 대한 문자열 만들기
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				// 바뀔 파일명 확인
				logger.info("변경될 첨부파일명 확인 : "+renameFileName);
				
				try {
					// 저장 폴더에 파일명 바꾸어 저장하기
					ifile4.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message","첨부파일 저장 실패!!");
					return "common/error";
				}
			} // 파일명 바꾸기 블록		
			//qna 객체에 첨부파일 정보 저장 처리
			qna.setoFile4(fileName);
			qna.setrFile4(renameFileName);
			
		}// 첨부파일이 있을때
		
		// 파일5
		// 파일제거
		if((qna.getoFile5() != null //원래 첨부파일이 있는데 파일이 지워졋을때 또는 신규파일이 들어왔을때
				&& (delete5.equals("yes") && ifile5.isEmpty())) || !ifile5.isEmpty()
			) { 
			//저장폴더에서 이전 파일은 삭제함
			new File(savePath + File.separator + qna.getrFile5()).delete();
			
			//qna 안의 파일정보도 삭제함
			qna.setoFile5(null);
			qna.setrFile5(null);
		}
		
		// 파일 추가
		// 즉, upfile 이름으로 전송온 파일이 있따면
		if(!ifile5.isEmpty()) {  // mfile이 비어잇지 않다면 // 파일이 있다면
			// 전송온 파일이름 추출함
			String fileName = ifile5.getOriginalFilename();
			String renameFileName = null;
			
			// 저장폴더에는 변경된 이름을 저장 처리함
			// 파일 이름 바꾸기함 : 년월일시분초.확장자
			if(fileName != null && fileName.length() > 0) { // 파일이름이 null이 아니고 한글자 이상일때(""이 아닐때)
				// 바꿀 파일명에 대한 문자열 만들기
				renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmssSSS");
				// 바뀔 파일명 확인
				logger.info("변경될 첨부파일명 확인 : "+renameFileName);
				
				try {
					// 저장 폴더에 파일명 바꾸어 저장하기
					ifile5.transferTo(new File(savePath + File.separator + renameFileName));
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message","첨부파일 저장 실패!!");
					return "common/error";
				}
			} // 파일명 바꾸기 블록		
			//qna 객체에 첨부파일 정보 저장 처리
			qna.setoFile5(fileName);
			qna.setrFile5(renameFileName);
			
		}// 첨부파일이 있을때
		
		logger.info("최종 인풋 데이터 : " + qna);
		
		if(qnaService.updateQna(qna)>0) {
			return "redirect:saqna.do";
		}else {
			model.addAttribute("message",qna.getQnaId()+ "번 Q&A 수정에 실패하였습니다.");
			return "common/error";
		}
		
	}
	
	// qna 삭제 처리
	@RequestMapping("dqna.do")
	public String deleteQna(
			@RequestParam("qnaId") String qnaId,
			Model model
			) {
		logger.info("삭제할 qna id: "+ qnaId);
		if(qnaService.deleteQna(qnaId)>0) {
			return "redirect:saqna.do";
		} else {
			model.addAttribute("message",qnaId+"번 게시글 삭제 실패");
			return "common/error";
		}
	}
}


