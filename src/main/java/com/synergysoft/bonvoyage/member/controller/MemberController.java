package com.synergysoft.bonvoyage.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.member.model.dto.Member;
import com.synergysoft.bonvoyage.member.model.dto.MyComment;
import com.synergysoft.bonvoyage.member.model.service.GoogleLoginAuth;
import com.synergysoft.bonvoyage.member.model.service.MemberService;
import com.synergysoft.bonvoyage.member.model.service.NaverLoginAuth;

@Controller
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private final String setFrom = "tsoh03@beeu.co.kr";
	private String apiResult = null;

	@Autowired
	private MemberService memberService;

//	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	private NaverLoginAuth naverloginAuth;

	@Autowired
	private GoogleLoginAuth googleloginAuth;

	private void addAuthURLsMethod(Model model, HttpSession session) {
		String naverAuthURL = naverloginAuth.getAuthorizationUrl(session);
		String googleAuthURL = googleloginAuth.getAuthorizationUrl(session);

		model.addAttribute("naverurl", naverAuthURL);
		model.addAttribute("googleurl", googleAuthURL);
	}

	public MemberController() {
		super();
	}

	public MemberController(GoogleLoginAuth googleLoginAuth) {
		this.googleloginAuth = googleLoginAuth;
	}

	public MemberController(NaverLoginAuth naverLoginAuth) {
		this.naverloginAuth = naverLoginAuth;
	}
	
	@Autowired
    public MemberController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        if (this.mailSender == null) {
            System.out.println("mailSender is not initialized.");
        }
    }
	
	// 로그인 페이지 출력 | 2024. 09. 28 작성 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "loginPage.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String moveLoginPage(Member member, HttpSession session, SessionStatus status, HttpServletRequest request,
			Model model) {
		addAuthURLsMethod(model, session);
		logger.info("로그인 페이지 요청");
		return "member/loginPage";
	}

	// 소셜 로그인 구현(카카오) | 2024. 10. 07 수정 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "kakaoLogin.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String moveKakaoLoginPage(@RequestParam String code, HttpSession session, SessionStatus status)
			throws IOException {
		logger.info("code:: " + code);

		// 접속토큰 get
		String kakaoToken = memberService.getReturnAccessToken(code);

		// 접속자 정보 get
		Map<String, Object> result = memberService.getMemberInfo(kakaoToken);
		logger.info("result:: " + result);
		String memId = (String) result.get("email");
		String memName = (String) result.get("nickname");
		String memNickNm = (String) result.get("nickname");

		// 분기
		Member loginUser = new Member();
		// 일치하는 snsId 없을 시 회원가입
		if (memberService.selectSocialLogin(memId) == null) {
			logger.warn("카카오로 회원가입");
			loginUser.setMemId(memId);
			loginUser.setMemName(memName);
			loginUser.setMemNickNm(memNickNm);
			loginUser.setMemSocial("KAKAO");
			if (memberService.insertSocialMember(loginUser) > 0) {
				memberService.updateLoginLog(loginUser.getMemId());
				return "redirect:main.do";
			} else {
				return "common/error";
			}
		}

		loginUser = memberService.selectSocialLogin(memId);
		memberService.updateLoginLog(loginUser.getMemId());
		logger.info("loginUser : " + loginUser);
		session.setAttribute("loginUser", loginUser);
		status.setComplete();

		return "redirect:main.do";
	}

	// 소셜 로그인 구현(네이버) | 2024. 10. 08 수정 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "naverLogin.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String naverLogin(@RequestParam("code") String code, @RequestParam("state") String state, Model model,
			HttpSession session, SessionStatus status) throws Exception {

		logger.info("naverLogin.do 접근");

		logger.info("Code: {}\nState: {}\nSession : {}", code, state, session);
		// 1. 코드, 세션 및 상태를 사용하여 getAccessToken을 호출합니다.
		OAuth2AccessToken node = naverloginAuth.getAccessToken(session, code, state);
		logger.info("node = " + node);
		// 이제 accessToken을 사용하여 사용자 정보를 가져와서 JsonObject를 만들거나 다른 작업을 수행할 수 있습니다.
		if (node == null) {
			model.addAttribute("message", "Naver Token Error 브라우저 캐시를 지우고 다시 시도해 주세요");
			return "common/error";
		} else {
			// 2. accessToken에 사용자의 로그인한 모든 정보가 들어있음
			apiResult = naverloginAuth.getUserProfile(node);

			// 3. String형식인 apiResult를 json형태로 바꿈
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(apiResult);
			JSONObject jsonObj = (JSONObject) obj;

			// 4. 데이터 파싱
			// Top레벨 단계 _response 파싱
			JSONObject response_obj = (JSONObject) jsonObj.get("response");
			String nickname = (String) response_obj.get("nickname");
			String email = (String) response_obj.get("email");

			logger.info("nickname : " + nickname);
			logger.info("email : " + email);

			// 유저 테이블에서 회원 정보 조회해 오기
			Member loginUser = new Member();

			if (memberService.selectSocialLogin(email) == null) {
				loginUser.setMemId(email);
				loginUser.setMemName(nickname);
				loginUser.setMemNickNm(nickname);
				loginUser.setMemSocial("NAVER");
				if (memberService.insertSocialMember(loginUser) > 0) {
					if (memberService.updateLoginLog(loginUser.getMemId()) > 0) {
						session.setAttribute("loginUser", loginUser);
						status.setComplete();
						return "redirect:main.do";
					} else {
						return "common/error";
					}
				} else {
					return "common/error";
				}
			} else {
				loginUser = memberService.selectSocialLogin(email);
				session.setAttribute("loginUser", loginUser);
				status.setComplete();
				return "redirect:main.do";
			}
		}
	}

	// 소셜 로그인 구현(구글) | 2024. 10. 08 수정 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "googleLogin.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String moveGoogleLoginPage(@RequestParam String code, @RequestParam String state, Model model,
			HttpSession session, SessionStatus status) throws Exception {

		OAuth2AccessToken node = googleloginAuth.getAccessToken(session, code, state);

		if (node == null) {
			model.addAttribute("message", "google Token Error 브라우저 캐시를 지우고 다시 시도해 주세요");
			return "common/error";
		} else {
			apiResult = googleloginAuth.getUserProfile(node);

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(apiResult);
			JSONObject jsonObj = (JSONObject) obj;

			String name = (String) jsonObj.get("name");
			String email = (String) jsonObj.get("email");

			Member loginUser = new Member();

			if (memberService.selectSocialLogin(email) == null) {
				loginUser.setMemId(email);
				loginUser.setMemName(name);
				loginUser.setMemNickNm(name);
				loginUser.setMemSocial("GOOGLE");
				if (memberService.insertSocialMember(loginUser) > 0) {
					if (memberService.updateLoginLog(loginUser.getMemId()) > 0) {
						session.setAttribute("loginUser", loginUser);
						status.setComplete();
						return "redirect:main.do";
					} else {
						return "common/error";
					}
				} else {
					return "common/error";
				}
			} else {
				loginUser = memberService.selectSocialLogin(email);
				session.setAttribute("loginUser", loginUser);
				status.setComplete();
				return "redirect:main.do";
			}
		}
	}

	// 회원가입 페이지 출력 | 2024. 09. 28 작성 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping("enrollPage.do")
	public String moveEnrollPage(HttpSession session, SessionStatus status, Model model) {
		addAuthURLsMethod(model, session);
		logger.info("회원가입 페이지 요청");
		return "member/enrollPage";
	}

	// 아이디 찾기 페이지 출력 | 2024. 09. 28 작성 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "idSearchPage.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String moveIDSearchPage() {
		logger.info("아이디 찾기 페이지 요청");
		return "member/idSearch";
	}

	// 비밀번호 찾기 페이지 출력 | 2024. 09. 28 작성 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "pwSearchPage.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String movePWSearchPage() {
		logger.info("비밀번호 찾기 페이지 요청");
		return "member/pwSearch";
	}

	// 내 정보 조회 페이지 출력 | 2024. 09. 28 작성 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "myinfo.do")
	public String moveMyInfoPage(@RequestParam("memId") String memId, Model model) {

		logger.info("내 정보 조회 페이지 요청 : " + memId);

		Member member = memberService.selectMyinfo(memId);

		if (member != null) {
			model.addAttribute("member", member);
			return "member/myinfo/myinfo";
		} else {
			model.addAttribute("message", memId + " 님의 정보를 조회하는데 실패하였습니다.");
			return "common/error";
		}
	} // 내 정보 조회 페이지 출력

	// 내가 쓴 댓글 전체 조회 페이지 출력 | 2024. 10. 10 구현 완료
	// jmoh03 (오정민)
	@RequestMapping(value = "myAllComment.do")
	public String moveMyinfoCommentAll(@RequestParam("memId") String memId, Model model,
			@RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit) {
		logger.info("내가 쓴 댓글 전체 조회 페이지 요청");
		
		if(memId != null) {
			// paging
			// 기본세팅-----------------------------------------------------------------------
			// 출력할 페이지(기본값 1페이지)
			int currentPage = 1;
			if (page != null) {
				currentPage = Integer.parseInt(page);
			}
			// 한페이지에 출력할 댓글 수 (10개)
			int limit = 10;
			if (slimit != null) {
				limit = Integer.parseInt(slimit);
			}
			// 페이징 그룹 갯수 (기본값 5개 세팅)
			int groupLimit = 5;
			if (glimit != null) {
				groupLimit = Integer.parseInt(glimit);
			}
	
			int listCount = memberService.selectCommentAllCount(memId);
	
			// 페이징 처리 값생성
			Paging paging = new Paging(listCount, limit, currentPage, "myAllComment.do", groupLimit);
			paging.calculate();
	
			MyComment mycomment = new MyComment();
	
			mycomment.setMemId(memId);
			mycomment.setStartRow(paging.getStartRow());
			mycomment.setEndRow(paging.getEndRow());
	
			ArrayList<MyComment> commentList = memberService.selectCommentAll(mycomment);
	
			if (commentList != null && commentList.size() > 0) {
				model.addAttribute("commentList", commentList);
				model.addAttribute("paging", paging);
				model.addAttribute("currentPage", currentPage);
				return "member/myinfo/comment/all"; // 뷰의 이름을 반환
			} else {
				model.addAttribute("message", "댓글 작성 이력이 없습니다.");
				return "common/error"; // 에러 페이지 뷰의 이름 반환
			}
		} else {
			model.addAttribute("message", "로그인 후 다시 이용해주시기 바랍니다.");
			return "common/error"; // 에러 페이지 뷰의 이름 반환
		}
	} // 내가 쓴 댓글 전체 조회 페이지 출력

	// 내가 쓴 댓글(가이드게시판) 페이지 출력 | 2024. 10. 10 구현 완료
	// jmoh03 (오정민)
	@RequestMapping(value = "myGuideBoardComment.do")
	public String moveMyinfoCommentGuideBoard(@RequestParam("memId") String memId, Model model,
			@RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit) {
		logger.info("내가 쓴 댓글(가이드게시판) 페이지 요청");
		
		if(memId != null) {
			// paging
			// 기본세팅-----------------------------------------------------------------------
			// 출력할 페이지(기본값 1페이지)
			int currentPage = 1;
			if (page != null) {
				currentPage = Integer.parseInt(page);
			}
			// 한페이지에 출력할 댓글 수 (10개)
			int limit = 10;
			if (slimit != null) {
				limit = Integer.parseInt(slimit);
			}
			// 페이징 그룹 갯수 (기본값 5개 세팅)
			int groupLimit = 5;
			if (glimit != null) {
				groupLimit = Integer.parseInt(glimit);
			}
	
			int listCount = memberService.selectCommentGuideBoardCount(memId);
	
			// 페이징 처리 값생성
			Paging paging = new Paging(listCount, limit, currentPage, "myGuideBoardComment.do", groupLimit);
			paging.calculate();
	
			MyComment mycomment = new MyComment();
	
			mycomment.setMemId(memId);
			mycomment.setStartRow(paging.getStartRow());
			mycomment.setEndRow(paging.getEndRow());
	
			ArrayList<MyComment> commentList = memberService.selectCommentGuideBoard(mycomment);
	
			if (commentList != null && commentList.size() > 0) {
				model.addAttribute("commentList", commentList);
				model.addAttribute("paging", paging);
				model.addAttribute("currentPage", currentPage);
				return "member/myinfo/comment/guideBoard"; // 뷰의 이름을 반환
			} else {
				model.addAttribute("message", "댓글 작성 이력이 없습니다.");
				return "common/error"; // 에러 페이지 뷰의 이름 반환
			}
		} else {
			model.addAttribute("message", "로그인 후 다시 이용해주시기 바랍니다.");
			return "common/error"; // 에러 페이지 뷰의 이름 반환
		}
	} // 내가 쓴 댓글(가이드게시판) 페이지 출력

	// 내가 쓴 댓글(경로게시판) 페이지 출력 | 2024. 10. 10 구현 완료
	// jmoh03 (오정민)
	@RequestMapping(value = "myRouteBoardComment.do")
	public String moveMyinfoCommentRouteBoard(@RequestParam("memId") String memId, Model model,
			@RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit) {
		logger.info("내가 쓴 댓글(경로게시판) 페이지 요청");
		
		if(memId != null) {
			// paging
			// 기본세팅-----------------------------------------------------------------------
			// 출력할 페이지(기본값 1페이지)
			int currentPage = 1;
			if (page != null) {
				currentPage = Integer.parseInt(page);
			}
			// 한페이지에 출력할 댓글 수 (10개)
			int limit = 10;
			if (slimit != null) {
				limit = Integer.parseInt(slimit);
			}
			// 페이징 그룹 갯수 (기본값 5개 세팅)
			int groupLimit = 5;
			if (glimit != null) {
				groupLimit = Integer.parseInt(glimit);
			}
	
			int listCount = memberService.selectCommentRouteBoardCount(memId);
	
			// 페이징 처리 값생성
			Paging paging = new Paging(listCount, limit, currentPage, "myRouteBoardComment.do", groupLimit);
			paging.calculate();
	
			MyComment mycomment = new MyComment();
	
			mycomment.setMemId(memId);
			mycomment.setStartRow(paging.getStartRow());
			mycomment.setEndRow(paging.getEndRow());
	
			ArrayList<MyComment> commentList = memberService.selectCommentRouteBoard(mycomment);
	
			if (commentList != null && commentList.size() > 0) {
				model.addAttribute("commentList", commentList);
				model.addAttribute("paging", paging);
				model.addAttribute("currentPage", currentPage);
				return "member/myinfo/comment/routeBoard"; // 뷰의 이름을 반환
			} else {
				model.addAttribute("message", "댓글 작성 이력이 없습니다.");
				return "common/error"; // 에러 페이지 뷰의 이름 반환
			}
		} else {
			model.addAttribute("message", "로그인 후 다시 이용해주시기 바랍니다.");
			return "common/error"; // 에러 페이지 뷰의 이름 반환
		}
	} // 내가 쓴 댓글(경로게시판) 페이지 출력

	// 관리자 : 회원 목록 조회 페이지 출력 | 2024. 10. 07 구현 완료
	// ejjung02 (정은지) => tsoh03 (오정민)
	@RequestMapping(value = "memberList.do")
	public String moveMemberList(Model model, @RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit) {
		logger.info("회원 목록 조회 페이지 요청");

		// paging
		// 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		// 한페이지에 출력할 회운 수 (20명)
		int limit = 20;
		if (slimit != null) {
			limit = Integer.parseInt(slimit);
		}
		// 페이징 그룹 갯수 (기본값 5개 세팅)
		int groupLimit = 5;
		if (glimit != null) {
			groupLimit = Integer.parseInt(glimit);
		}
		// 총 회원 수 조회
		int listCount = memberService.selectMemberListCount();
		logger.info("회원 수 : " + listCount);

		// 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "memberList.do", groupLimit);
		paging.calculate();

		ArrayList<Member> memberList = memberService.selectMember(paging);

		logger.info("memberList : " + memberList);
		if (memberList != null && memberList.size() > 0) {
			model.addAttribute("memberList", memberList);
			model.addAttribute("paging", paging);
			model.addAttribute("currentPage", currentPage);
			return "member/admin/memberList"; // 뷰의 이름을 반환
		} else {
			model.addAttribute("message", "회원이 없습니다.");
			return "common/error"; // 에러 페이지 뷰의 이름 반환
		}
	} // 관리자 : 회원 목록 조회 페이지 출력

	// 내가 쓴 댓글 전체 검색 조회 페이지 출력 | 2024. 10. 11 구현 완료
	// jmoh03 (오정민)
	@RequestMapping(value = "myAllCommentSearch.do")
	public String moveMyinfoCommentAllSearch(@RequestParam("memId") String memId, Model model,
			@RequestParam("action") String action, // 검색 기준 (title, commentContent)
			@RequestParam("keyword") String keyword, @RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit) {
		logger.info("내가 쓴 댓글 전체 검색 조회 페이지 요청");

		// paging
		// 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		// 한페이지에 출력할 댓글 수 (10개)
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

		MyComment commentCount = new MyComment();

		if (action.equals("title")) {
			commentCount.setAction(action);
			commentCount.setKeyword(keyword);
			commentCount.setMemId(memId);
			listCount = memberService.selectCommentAllSearchCount(commentCount);
		} else if (action.equals("commentContent")) {
			commentCount.setAction(action);
			commentCount.setKeyword(keyword);
			commentCount.setMemId(memId);
			listCount = memberService.selectCommentAllSearchCount(commentCount);
		}

		// 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "myAllComment.do", groupLimit);
		paging.calculate();

		commentCount.setAction(action);
		commentCount.setKeyword(keyword);
		commentCount.setMemId(memId);
		commentCount.setStartRow(paging.getStartRow());
		commentCount.setEndRow(paging.getEndRow());

		ArrayList<MyComment> commentList = null;

		if (action.equals("title")) {
			commentList = memberService.selectCommentAllSearch(commentCount);
		} else if (action.equals("commentContent")) {
			commentList = memberService.selectCommentAllSearch(commentCount);
		}

		if (commentList != null && commentList.size() > 0) {
			model.addAttribute("commentList", commentList);
			model.addAttribute("paging", paging);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("memId", memId);
			model.addAttribute("action", action);
			model.addAttribute("keyword", keyword);
			return "member/myinfo/comment/all"; // 뷰의 이름을 반환
		} else {
			model.addAttribute("message", "검색 결과가 없습니다.");
			return "common/error"; // 에러 페이지 뷰의 이름 반환
		}
	} // 내가 쓴 댓글 전체 조회 페이지 출력

	// 내가 쓴 댓글(가이드게시판) 검색 페이지 출력 | 2024. 10. 11 구현 완료
	// jmoh03 (오정민)
	@RequestMapping(value = "myGuideBoardCommentSearch.do")
	public String moveMyinfoCommentGuideBoardSearch(@RequestParam("memId") String memId, Model model,
			@RequestParam("action") String action, // 검색 기준 (title, commentContent)
			@RequestParam("keyword") String keyword, @RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit) {
		logger.info("내가 쓴 댓글(가이드게시판) 검색 페이지 요청");

		// paging
		// 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		// 한페이지에 출력할 댓글 수 (10개)
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

		MyComment commentCount = new MyComment();

		if (action.equals("title")) {
			commentCount.setAction(action);
			commentCount.setKeyword(keyword);
			commentCount.setMemId(memId);
			listCount = memberService.selectCommentGuideBoardSearchCount(commentCount);
		} else if (action.equals("commentContent")) {
			commentCount.setAction(action);
			commentCount.setKeyword(keyword);
			commentCount.setMemId(memId);
			listCount = memberService.selectCommentGuideBoardSearchCount(commentCount);
		}

		// 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "myGuideBoardCommentSearch.do", groupLimit);
		paging.calculate();

		MyComment myComment = new MyComment();

		myComment.setAction(action);
		myComment.setKeyword(keyword);
		myComment.setMemId(memId);
		myComment.setStartRow(paging.getStartRow());
		myComment.setEndRow(paging.getEndRow());

		ArrayList<MyComment> commentList = null;

		if (action.equals("title")) {
			commentList = memberService.selectCommentGuideBoardSearch(myComment);
		} else if (action.equals("commentContent")) {
			commentList = memberService.selectCommentGuideBoardSearch(myComment);
		}

		if (commentList != null && commentList.size() > 0) {
			model.addAttribute("commentList", commentList);
			model.addAttribute("paging", paging);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("memId", memId);
			model.addAttribute("action", action);
			model.addAttribute("keyword", keyword);
			return "member/myinfo/comment/guideBoard"; // 뷰의 이름을 반환
		} else {
			model.addAttribute("message", "검색 결과가 없습니다.");
			return "common/error"; // 에러 페이지 뷰의 이름 반환
		}
	} // 내가 쓴 댓글(가이드게시판) 검색 페이지 출력

	// 내가 쓴 댓글(경로게시판) 검색 페이지 출력 | 2024. 10. 11 구현 완료
	// jmoh03 (오정민)
	@RequestMapping(value = "myRouteBoardCommentSearch.do")
	public String moveMyinfoCommentRouteBoardSearch(@RequestParam("memId") String memId, Model model,
			@RequestParam("action") String action, // 검색 기준 (title, commentContent)
			@RequestParam("keyword") String keyword, @RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit) {
		logger.info("내가 쓴 댓글(경로게시판) 검색 페이지 요청");

		// paging
		// 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		// 한페이지에 출력할 댓글 수 (10개)
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

		MyComment commentCount = new MyComment();

		if (action.equals("title")) {
			commentCount.setAction(action);
			commentCount.setKeyword(keyword);
			commentCount.setMemId(memId);
			listCount = memberService.selectCommentRouteBoardSearchCount(commentCount);
		} else if (action.equals("commentContent")) {
			commentCount.setAction(action);
			commentCount.setKeyword(keyword);
			commentCount.setMemId(memId);
			listCount = memberService.selectCommentRouteBoardSearchCount(commentCount);
		}

		// 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "myRouteBoardCommentSearch.do", groupLimit);
		paging.calculate();

		MyComment myComment = new MyComment();

		myComment.setAction(action);
		myComment.setKeyword(keyword);
		myComment.setMemId(memId);
		myComment.setStartRow(paging.getStartRow());
		myComment.setEndRow(paging.getEndRow());

		ArrayList<MyComment> commentList = null;

		if (action.equals("title")) {
			commentList = memberService.selectCommentRouteBoardSearch(myComment);
		} else if (action.equals("commentContent")) {
			commentList = memberService.selectCommentRouteBoardSearch(myComment);
		}

		if (commentList != null && commentList.size() > 0) {
			model.addAttribute("commentList", commentList);
			model.addAttribute("paging", paging);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("memId", memId);
			model.addAttribute("action", action);
			model.addAttribute("keyword", keyword);
			return "member/myinfo/comment/routeBoard"; // 뷰의 이름을 반환
		} else {
			model.addAttribute("message", "검색 결과가 없습니다.");
			return "common/error"; // 에러 페이지 뷰의 이름 반환
		}
	} // 내가 쓴 댓글(경로게시판) 검색 페이지 출력

	// 관리자 : 회원 목록 검색 조회 페이지 출력 | 2024. 10. 11 구현 완료
	// tsoh03 (오정민)
	@RequestMapping(value = "memberListSearch.do")
	public String moveMemberListSearch(Model model, 
			@RequestParam(name = "page", required = false) String page,
			@RequestParam("memStatus") String memStatus, // 검색 기준 (ACTIVE, BLOCKED, INACTIVE)
			@RequestParam("action") String action, // 검색 기준 (title, content)
			@RequestParam("keyword") String keyword, @RequestParam(name = "limit", required = false) String slimit,
			@RequestParam(name = "groupLimit", required = false) String glimit) {
		logger.info("회원 목록 검색 조회 페이지 요청");

		// paging
		// 기본세팅-----------------------------------------------------------------------
		// 출력할 페이지(기본값 1페이지)
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		// 한페이지에 출력할 회운 수 (20명)
		int limit = 20;
		if (slimit != null) {
			limit = Integer.parseInt(slimit);
		}
		// 페이징 그룹 갯수 (기본값 5개 세팅)
		int groupLimit = 5;
		if (glimit != null) {
			groupLimit = Integer.parseInt(glimit);
		}
		// 총 회원 수 조회
		int listCount = 0;
		
		// 검색시 사용할 값 전송 객체생성 및 값 입력
		Member searchCount = new Member();
	    
		if(
			(memStatus.equals("INACTIVE") || memStatus.equals("BLOCKED") || memStatus.equals("ACTIVE") || memStatus.equals("ALL")) 
				&& 
			(action.equals("memId") || action.equals("memNickNm") || action.equals("memName"))
			) {
			searchCount.setMemStatus(memStatus);
			searchCount.setAction(action);
			searchCount.setKeyword(keyword);
	    	listCount = memberService.selectMemberListSearchCount(searchCount);
		}
		
		// 페이징 처리 값생성
		Paging paging = new Paging(listCount, limit, currentPage, "memberListSearch.do", groupLimit);
		paging.calculate();
		
		Member search = new Member();
		search.setMemStatus(memStatus);
		search.setAction(action);
		search.setKeyword(keyword);
		search.setStartRow(paging.getStartRow());
	    search.setEndRow(paging.getEndRow());
		
	    ArrayList<Member> memberList = null;
	    
	    // 서비스를 목록 조회 요청하고 결과 받기(페이징 처리)
	    if((memStatus.equals("INACTIVE") || memStatus.equals("BLOCKED") || memStatus.equals("ACTIVE") || memStatus.equals("ALL")) 
				&& 
			(action.equals("memId") || action.equals("memNickNm") || action.equals("memName"))) {
	    	memberList = memberService.selectMemberSearch(search);
	    }

		if (memberList != null && memberList.size() > 0) {
			model.addAttribute("memberList", memberList);
			model.addAttribute("paging", paging);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("action", action);
			model.addAttribute("keyword", keyword);
			return "member/admin/memberList"; // 뷰의 이름을 반환
		} else {
			model.addAttribute("message", "해당 키워드에 일치하는 회원이 없습니다.");
			return "common/error"; // 에러 페이지 뷰의 이름 반환
		}
	} // 관리자 : 회원 목록 조회 검색 페이지 출력

	// 관리자 : 회원 상세 조회 페이지 출력 | 2024. 10. 07 수정 및 구현 완료
	// ejjung02 (정은지) => tsoh03 (오정민)
	@RequestMapping(value = "memberDetail.do")
	public ModelAndView moveMemberDetail(ModelAndView mv, @RequestParam("memId") String memId, HttpSession session) {
		logger.info("회원 상세 조회 페이지 요청");

		Member member = memberService.selectMemberDetail(memId);
		if (member != null) {
			mv.addObject("member", member);
			Member loginUser = (Member) session.getAttribute("loginUser");
			if (loginUser != null && loginUser.getMemType().equals("ADMIN")) {
				mv.setViewName("member/admin/memberDetail");
			} else {
				mv.setViewName("member/admin/memberDetail");
			}

		} else {
			mv.addObject("권한이 없습니다.");
			mv.setViewName("common/error");
		}
		return mv;
	} // 관리자 : 회원 상세 조회 페이지 출력

	// 로그인 기능 | 2024. 09. 28 작성 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String loginMethod(Member member, HttpSession session, SessionStatus status, Model model) {

		logger.info("로그인 요청 : " + member);

		Member loginUser = memberService.selectLogin(member.getMemId());

		if (loginUser != null && this.bcryptPasswordEncoder.matches(member.getMemPw(), loginUser.getMemPw())) {
			memberService.updateLoginLog(loginUser.getMemId());
			session.setAttribute("loginUser", loginUser);
			status.setComplete();

			logger.info("로그인 요청 : " + loginUser.getMemNickNm() + "(" + loginUser.getMemId() + ")" + " 님이 로그인 하였습니다.");
			return "redirect:main.do";
		} else {
			model.addAttribute("message", "로그인에 실패하였습니다. 아이디와 비밀번호를 확인 후 다시 시도해주시기 바랍니다.");
			return "common/error";
		}
	} // 로그인 기능

	// 로그아웃 기능 | 2024. 09. 28 작성 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping("logout.do")
	public String logoutMethod(Member member, HttpSession session, HttpServletRequest request, Model model) {

		member = (Member) session.getAttribute("loginUser");
		memberService.updateLogoutLog(member.getMemId());

		session = request.getSession(false);

		if (session != null) {
			session.invalidate();
			return "common/main";
		} else {
			model.addAttribute("message", "세션이 만료되어 로그아웃에 실패하였습니다. 메인페이지로 돌아가세요.");
			return "common/error";
		}
	} // 로그아웃 기능

	// 회원가입 기능 | 2024. 09. 28 작성 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "enroll.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String enrollMethod(Member member, Model model, HttpServletRequest request) {
		logger.info("enroll.do : " + member);

		member.setMemPw(bcryptPasswordEncoder.encode(member.getMemPw()));
		logger.info("after encode : " + member.getMemPw() + ", length : " + member.getMemPw().length());

		if (memberService.insertMember(member) > 0) {
			logger.info("회원가입 요청 : " + member.getMemNickNm() + "(" + member.getMemId() + ")" + "님이 회원가입에 성공하였습니다.");
			return "member/loginPage";
		} else {
			model.addAttribute("message", "회원가입에 실패하였습니다. 다시 시도해주시기 바랍니다.");
			return "common/error";
		}
	}

	// 아이디 중복 검사 기능 | 2024. 09. 28 수정 필요
	// jmoh03 (오정민)
	@RequestMapping(value = "idchk.do", method = RequestMethod.POST)
	@ResponseBody
	public void dupCheckIdMethod(@RequestParam("memId") String memId, HttpServletResponse response) throws IOException {
		int userIdCount = memberService.selectCheckId(memId);
		String returnStr = null;
		if (userIdCount == 0 && memId.length() > 0) {
			returnStr = "ok";
		} else {
			returnStr = "dup";
		}

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.append(returnStr);
		out.flush();
		out.close();
	} // 아이디 중복 검사 기능

	// 아이디 찾기 기능
	// jmoh03 (오정민)
//	@RequestMapping(value = "idSearch.do", method = RequestMethod.POST)
//	public String idSearchMethod(Member member, HttpSession session) {
//		Member findUser = memberService.searchIdByEmailByName(member);
//		logger.info(findUser.toString());
//		
//		if(findUser != null) {
//			session.setAttribute("findUser", findUser);
//			return "user/findUserId";
//		} else {
//			return "common/error";
//		}
//	} // 아이디 찾기 기능

	// 비밀번호 찾기 기능
	// jmoh03 (오정민)
	@RequestMapping(value = "pwSearch.do", method = RequestMethod.POST)
	@ResponseBody
	public String pwSearchMethod(Member member, Model model) {
		Member findUser = null;
		findUser = memberService.selectMemberByEmailId(member);

		if (findUser != null) {
			// 소문자, 대문자, 숫자
			final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+=";

			SecureRandom rm = new SecureRandom();
			StringBuilder newPw = new StringBuilder();

			for (int i = 0; i < 11; i++) {
				// 무작위로 문자열의 인덱스 반환
				int index = rm.nextInt(chars.length());
				// index의 위치한 랜덤값
				newPw.append(chars.charAt(index));
			}

			// 이메일 보낼 양식
			String toMail = member.getMemId();
			String title = "[본보야지] 임시 비밀번호 입니다.";
			String content = "임시 비밀번호는 " + newPw + " 입니다." + "<br>" + "해당 비밀번호로 로그인 해주세요.";

			try {
				MimeMessage message = mailSender.createMimeMessage(); // Spring에서 제공하는 mail API
				MimeMessageHelper mailHelper = new MimeMessageHelper(message, "utf-8");

				mailHelper.setFrom(setFrom);
				mailHelper.setTo(toMail);
				mailHelper.setSubject(title);
				mailHelper.setText(content, true);
				mailSender.send(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("임시 비밀번호 : " + newPw);

			member.setMemPw(bcryptPasswordEncoder.encode(newPw.toString()));

			if (memberService.updateMemberPw(member) <= 0) {
				model.addAttribute("message", "비밀번호 업데이트 오류");
				return "common/error";
			}

			return "no";
		} else {
			model.addAttribute("message", "일치하는 사람 없음");
			return "failed";
		}

	} // 비밀번호 찾기 기능

	// 내 정보 수정 기능 | 2024. 09. 30 작정 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "myinfo/update.do", method = RequestMethod.POST)
	public String myinfoUpdateMethod(Member member, Model model, HttpServletRequest request,
			@RequestParam("originalMemPw") String originalMemPw) {

		logger.info("내 정보 수정 요청 : " + member);

		// 내 정보 수정에서 회원이 비밀번호를 변경한 경우 변경한 요청에 따라 비밀번호를 변경하는 기능
		if (member.getMemPw() != null && member.getMemPw().length() > 0) {
			member.setMemPw(bcryptPasswordEncoder.encode(member.getMemPw()));
			logger.info("after encode : " + member.getMemPw() + ", length : " + member.getMemPw().length());
		} else {
			member.setMemPw(originalMemPw);
		}

		// 내 정보 수정 요청 성공여부
		if (memberService.updateMyinfo(member) > 0) {
			logger.info("내 정보 수정 요청 성공 : " + member);
			return "redirect:../main.do";
		} else {
			model.addAttribute("message", "회원정보 수정에 실패하였습니다.");
			logger.info("내 정보 수정 요청 실패 : " + member);
			return "common/error";
		}
	} // 내 정보 수정 기능

	// 회원 탈퇴 기능 | 2024. 09. 30 작정 및 테스트 성공
	// jmoh03 (오정민)
	@RequestMapping(value = "myinfo/left.do")
	public String memberLeftMethod(@RequestParam("memId") String memId, Model model, HttpSession session) {

		if (memberService.updateLeft(memId) > 0) {

			logger.info("회원탈퇴 요청 성공 : " + memId);
			model.addAttribute("msg", "회원 탈퇴 완료되었습니다. 안녕히가십시오.");
			model.addAttribute("url", "../main.do");
			session.invalidate();
			return "common/alert";
		} else {
			model.addAttribute("message", "회원탈퇴에 실패하였습니다.");
			logger.info("회원탈퇴 요청 실패 : " + memId);
			return "common/error";
		}
	} // 회원 탈퇴 기능
	
	// 이메일 인증 기능
	// jmoh03 (오정민)
	@RequestMapping(value = "emailAuth.do", method = RequestMethod.POST)
	@ResponseBody
	public String emailAuthMethod(@RequestParam("memId") String memId) {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;

		// 이메일 보낼 양식
		String toMail = memId;
		String title = "[본보야지] 회원가입 인증 이메일 입니다.";
		String content = "인증 코드는 " + checkNum + " 입니다." + "<br>" + "해당 인증 코드를 인증 코드 확인란에 기입하여 주세요.";

		try {
			MimeMessage message = mailSender.createMimeMessage(); // Spring에서 제공하는 mail API
			MimeMessageHelper mailHelper = new MimeMessageHelper(message, "utf-8");

			mailHelper.setFrom(setFrom);
			mailHelper.setTo(toMail);
			mailHelper.setSubject(title);
			mailHelper.setText(content, true);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("랜덤숫자 : " + checkNum);

		JSONObject jOb = new JSONObject();
		jOb.put("authCode", checkNum);

		return jOb.toJSONString();
	}

	// 관리자 : 회원 정보 수정 기능 | 2024. 10. 08 작정 및 테스트 성공
	// ejjung02 (정은지) => jmoh03 (오정민)
	@RequestMapping(value = "memberUpdate.do", method = RequestMethod.POST)
	public String memberUpdateMethod(Member member, Model model, HttpServletRequest request) {
		logger.info("memberUpdate.do 접근");
		if (memberService.updateMember(member) > 0) {
			logger.info(member.getMemName() + "님의 유저 정보 수정 완료.");
			return "redirect:memberDetail.do?memId=" + member.getMemId();
		} else {
			model.addAttribute("message", member.getMemName() + "님의 회원 정보 수정에 실패하였습니다.");
			return "common/error";
		}
	} // 관리자 : 회원 정보 수정 기능

	// 관리자 : 회원 계정 조치 기능 | 2024. 10. 08 작정 및 테스트 성공
	// ejjung02 (정은지) => jmoh03 (오정민)
	@RequestMapping(value = "memberAccountUpdate.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberAccountUpdateMethod(Member member, Model model, HttpServletRequest request) {

		if (memberService.updateMemberAccount(member.getMemId()) > 0) {
			return "redirect:memberDetail.do?memId=" + member.getMemId();
		} else {
			model.addAttribute("message", "계정조치에 실패하였습니다.");
			return "common/error";
		}
	} // 관리자 : 회원 계정 조치 기능
	
	// 관리자 : 회원 계정 조치 해제 기능 | 2024. 10. 12 작성 및 테스트 성공
	// tsoh03 (오정민)
	@RequestMapping(value = "memberAccountUpdateClear.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberAccountUpdateClearMethod(Member member, Model model, HttpServletRequest request) {
		logger.info("memberAccountUpdateClear.do 요청");
		if (memberService.updateMemberAccountClear(member.getMemId()) > 0) {
			return "redirect:memberDetail.do?memId=" + member.getMemId();
		} else {
			model.addAttribute("message", "계정정지 해제에 실패하였습니다.");
			return "common/error";
		}
	}

	// 관리자 : 회원 관리자 부여 기능 | 2024. 10. 08 작정 및 테스트 성공
	// ejjung02 (정은지) => jmoh03 (오정민)
	@RequestMapping(value = "memberAdmin.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberAdminMethod(Member member, Model model, HttpServletRequest request) {

		if (memberService.updateMemberAdmin(member.getMemId()) > 0) {
			return "redirect:memberDetail.do?memId=" + member.getMemId();
		} else {
			model.addAttribute("message", "관리자 부여에 실패하였습니다.");
			return "common/error";
		}
	} // 관리자 : 회원 관리자 부여 기능

	// 관리자 : 회원 관리자 박탈 기능 | 2024. 10. 08 작성 및 테스트 성공
	// tsoh03 (오정민)
	@RequestMapping(value = "memberAdminDelete.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberAdminDeleteMethod(Member member, Model model, HttpServletRequest request) {

		if (memberService.updateMemberAdminDelete(member.getMemId()) > 0) {
			return "redirect:memberDetail.do?memId=" + member.getMemId();
		} else {
			model.addAttribute("message", "관리자 박탈에 실패하였습니다.");
			return "common/error";
		}

	}
}
