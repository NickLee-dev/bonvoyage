package com.synergysoft.bonvoyage.place.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synergysoft.bonvoyage.place.model.service.PlaceService;
import com.synergysoft.bonvoyage.route.model.dto.Route;
import com.synergysoft.bonvoyage.route.model.service.RouteService;

@Controller
public class PlaceController {

	private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
	
	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private RouteService routeService;
	
	@ResponseBody
	@RequestMapping(value= "mainmap.do", method=RequestMethod.POST)
	public String mainMap(
			Model model,
			HttpServletResponse response,
			@RequestParam(value="local", required = false) String local,
			@RequestParam(value="dosi", required = false) String dosi,
			@RequestParam(value="si", required = false) String si
			) throws UnsupportedEncodingException, JsonProcessingException {
		// 넘어 온 값 확인
		logger.info("local : "+local.toString());
		logger.info("local : "+dosi.toString());
		logger.info("local : "+si.toString());
		
		// 내보낼 값에 대해 response에 mimetype 설정
		response.setContentType("application/json; charset=utf-8");		
		
		// 전송할 json 객체생성
		JSONObject sendJson = new JSONObject();
		// 지역별 여행 장소수
		int placeCount = placeService.placeCount(si);
		logger.info("placeCount : " + placeCount);
		String error = "";
		sendJson.put("placeCount", placeCount);
		sendJson.put("local", URLEncoder.encode(local,"utf-8"));
		// 지역별 여행 장소 가 없으면 에러 보내기
		if(placeCount == 0) {
			error = "다녀온 여행정보가 없습니다.";
			logger.info("여행정보 "+error);
			sendJson.put("error", URLEncoder.encode(error,"utf-8"));
			return sendJson.toJSONString();
		}
		sendJson.put("error", URLEncoder.encode(error,"utf-8"));
		// 인기많은 1개 게시글 불러오기
		Route top1Route = routeService.top1Route(si);
		logger.info("top1Route : " + top1Route.toString());
		String context = (top1Route.getContent().length() > 100 ? (top1Route.getContent().substring(0, 100) + "...") : top1Route.getContent()) ;
		// 가져온 게시글 json put
		if (top1Route.getRouteBoardId() != null) {
		    sendJson.put("routeBoardId", URLEncoder.encode(top1Route.getRouteBoardId(), "utf-8"));
		}

		if (top1Route.getTitle() != null) {
		    sendJson.put("title", URLEncoder.encode(top1Route.getTitle(), "utf-8"));
		}

		/*
		 * if (top1Route.getContent() != null) { sendJson.put("content",
		 * URLEncoder.encode(context, "utf-8")); }
		 */

		if (top1Route.getTransport() != null) {
		    sendJson.put("transport", URLEncoder.encode(top1Route.getTransport(), "utf-8"));
		}

		if (top1Route.getOfile1() != null) {
		    sendJson.put("ofile1", URLEncoder.encode(top1Route.getOfile1(), "utf-8"));
		}

		if (top1Route.getRfile1() != null) {
		    sendJson.put("rfile1", URLEncoder.encode(top1Route.getRfile1(), "utf-8"));
		}
		
		logger.info("sendJson : " + sendJson.toJSONString());
		return sendJson.toJSONString();
	}
	
}
