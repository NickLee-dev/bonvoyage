package com.synergysoft.bonvoyage.routedata.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.routedata.model.dto.RouteData;

@Repository("routeDataDao")
public class RouteDataDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int insertRouteData(RouteData routeData) {
		return sqlSessionTemplate.insert("routedataMapper.insertRouteData", routeData);
	}

}
