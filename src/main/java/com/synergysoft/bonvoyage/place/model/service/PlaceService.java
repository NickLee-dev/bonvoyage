package com.synergysoft.bonvoyage.place.model.service;

import java.util.ArrayList;

import com.synergysoft.bonvoyage.place.model.dto.Place;

public interface PlaceService {
	int insertPlace(Place place);
	ArrayList<Place> selectPlace(String routeBoardId);
	int placeCount(String si);

}
