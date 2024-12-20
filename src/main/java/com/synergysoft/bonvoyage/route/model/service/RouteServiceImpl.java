package com.synergysoft.bonvoyage.route.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.route.model.dao.RouteDao;
import com.synergysoft.bonvoyage.route.model.dto.Route;

@Service("routeService")
public class RouteServiceImpl implements RouteService {
	
	@Autowired
	private RouteDao routeDao;
	
	@Override
	public ArrayList<Route> selectAllRoute(Paging paging) {
		return routeDao.selectAllRoute(paging);
	}

	@Override
	public int insertRoute(Route route) {
		return routeDao.insertRoute(route);
	}

	@Override
	public Route selectRoute(String routeBoardId) {
		return routeDao.selectRoute(routeBoardId);
	}

	@Override
	public int selectListCount() {
		return routeDao.selectListCount();
	}

	@Override
	public int updateRoute(Route route) {
		return routeDao.updateRoute(route);
	}

	@Override
	public int deleteRoute(String routeBoardId) {
		return routeDao.deleteRoute(routeBoardId);
	}

	@Override
	public int updateRouteLikeCount(String postId) {
		return routeDao.updateRouteLikeCount(postId);
	}

	@Override
	public int updateRouteReadCount(String routeBoardId) {
		return routeDao.updateRouteReadCount(routeBoardId);
	}

	@Override
	public int selectSearchTitleListCount(String keyword) {
		return routeDao.selectSearchTitleListCount(keyword);
	}

	@Override
	public int selectSearchContentListCount(String keyword) {
		return routeDao.selectSearchContentListCount(keyword);
	}

	@Override
	public int selectSearchUserIdListCount(String keyword) {
		return routeDao.selectSearchUserIdListCount(keyword);
	}

	@Override
	public ArrayList<Route> selectSearchTitleRoute(Search search) {
		return routeDao.selectSearchTitleRoute(search);
	}

	@Override
	public ArrayList<Route> selectSearchContentRoute(Search search) {
		return routeDao.selectSearchContentRoute(search);
	}

	@Override
	public ArrayList<Route> selectSearchUserIdRoute(Search search) {
		return routeDao.selectSearchUserIdRoute(search);
	}

	@Override
	public Route top1Route(String si) {
		return routeDao.top1Route(si);
		
	}

	@Override
	public ArrayList<Route> selectTop3() {
		return routeDao.selectTop3();
	}
}
