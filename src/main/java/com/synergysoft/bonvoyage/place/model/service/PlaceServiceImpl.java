package com.synergysoft.bonvoyage.place.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergysoft.bonvoyage.place.model.dao.PlaceDao;
import com.synergysoft.bonvoyage.place.model.dto.Place;

@Service("placeService")
public class PlaceServiceImpl implements PlaceService {
	
	@Autowired
	public PlaceDao placeDao;
	
	@Override
	public int insertPlace(Place place) {
		return placeDao.insertPlace(place);
	}

	@Override
	public ArrayList<Place> selectPlace(String routeBoardId) {
		return placeDao.selectPlace(routeBoardId);
	}

	@Override
	public int placeCount(String si) {
		return placeDao.placeCount(si);
	}



}
