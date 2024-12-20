package com.synergysoft.bonvoyage.place.model.dto;

public class Place implements java.io.Serializable {

	private static final long serialVersionUID = 8072654861485651643L;
	
	private String placeId; 		// PLACE_ID	VARCHAR2(255 BYTE)
	private String placeName; // PLACE_NAME	VARCHAR2(255 BYTE)
	private String address;		// ADDRESS	VARCHAR2(255 BYTE)
	private double latitude;		// LATITUDE	NUMBER
	private double longitude;		// LONGITUDE	NUMBER
	private String placeContent;
	private String placeRouteBoardId;
	
	public Place() {
		super();
	}

	public Place(String placeId, String placeName, String address, int latitude, int longitude, String placeContent,
			String placeRouteBoardId) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.placeContent = placeContent;
		this.placeRouteBoardId = placeRouteBoardId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPlaceContent() {
		return placeContent;
	}



	public void setPlaceContent(String placeContent) {
		this.placeContent = placeContent;
	}



	public String getPlaceRouteBoardId() {
		return placeRouteBoardId;
	}

	public void setPlaceRouteBoardId(String placeRouteBoardId) {
		this.placeRouteBoardId = placeRouteBoardId;
	}

	@Override
	public String toString() {
		return "Place [placeId=" + placeId + ", placeName=" + placeName + ", address=" + address + ", latitude="
				+ latitude + ", longitude=" + longitude + ", placeContent=" + placeContent + ", placeRouteBoardId="
				+ placeRouteBoardId + "]";
	}

	
}
