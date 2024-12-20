package com.synergysoft.bonvoyage.route.model.service;

import java.util.ArrayList;

import com.synergysoft.bonvoyage.common.Paging;
import com.synergysoft.bonvoyage.common.Search;
import com.synergysoft.bonvoyage.route.model.dto.Route;

public interface RouteService {
	ArrayList<Route> selectAllRoute(Paging paging);
	int insertRoute(Route route);
	Route selectRoute(String routeBoardId);
	int selectListCount();
	int updateRoute(Route route);
	int deleteRoute(String routeBoardId);
	int updateRouteLikeCount(String postId);
	int updateRouteReadCount(String routeBoardId);
	int selectSearchTitleListCount(String keyword);
	int selectSearchContentListCount(String keyword);
	int selectSearchUserIdListCount(String keyword);
	ArrayList<Route> selectSearchTitleRoute(Search search);
	ArrayList<Route> selectSearchContentRoute(Search search);
	ArrayList<Route> selectSearchUserIdRoute(Search search);
	Route top1Route(String si);
	ArrayList<Route> selectTop3();
	
}
