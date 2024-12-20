package com.synergysoft.bonvoyage.place.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synergysoft.bonvoyage.place.model.dto.Place;

@Repository("placeDao")
public class PlaceDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public int insertPlace(Place place) {
		return sqlSessionTemplate.insert("placeMapper.insertPlace", place);
	}

	public ArrayList<Place> selectPlace(String routeBoardId) {
		List<Place> list = sqlSessionTemplate.selectList("placeMapper.selectPlace", routeBoardId);
		return (ArrayList<Place>)list;
	}

	public int placeCount(String si) {
		return sqlSessionTemplate.selectOne("placeMapper.placeCount", si);
	}
}
