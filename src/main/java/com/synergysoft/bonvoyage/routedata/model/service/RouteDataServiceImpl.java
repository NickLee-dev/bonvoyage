package com.synergysoft.bonvoyage.routedata.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.routedata.model.dao.RouteDataDao;
import com.synergysoft.bonvoyage.routedata.model.dto.RouteData;

@Service("routeDataService")
public class RouteDataServiceImpl implements RouteDataService {

	@Autowired
	private RouteDataDao routeDataDao;
	
	@Override
	public int insertRouteData(RouteData routeData) {
		return routeDataDao.insertRouteData(routeData);
	}

}
