package com.synergysoft.bonvoyage.routedata.model.dto;

public class RouteData implements java.io.Serializable {

	private static final long serialVersionUID = 3239049264134407620L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String routeId;	// ROUTE_ID	VARCHAR2(100 BYTE)
	private String routeBoardId;	// ROUTE_BOARD_ID	VARCHAR2(255 BYTE)
	private String routePlaceId;	// ROUTE_PLACE_ID	VARCHAR2(255 BYTE)
	private int routePlaceOrderNo;	// ROUTE_PLACE_ORDER_NO	NUMBER
	
	public RouteData() {
		super();
	}

	public RouteData(String routeId, String routeBoardId, String routePlaceId, int routePlaceOrderNo) {
		super();
		this.routeId = routeId;
		this.routeBoardId = routeBoardId;
		this.routePlaceId = routePlaceId;
		this.routePlaceOrderNo = routePlaceOrderNo;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRouteBoardId() {
		return routeBoardId;
	}

	public void setRouteBoardId(String routeBoardId) {
		this.routeBoardId = routeBoardId;
	}

	public String getRoutePlaceId() {
		return routePlaceId;
	}

	public void setRoutePlaceId(String routePlaceId) {
		this.routePlaceId = routePlaceId;
	}

	public int getRoutePlaceOrderNo() {
		return routePlaceOrderNo;
	}

	public void setRoutePlaceOrderNo(int routePlaceOrderNo) {
		this.routePlaceOrderNo = routePlaceOrderNo;
	}

	@Override
	public String toString() {
		return "RouteData [routeId=" + routeId + ", routeBoardId=" + routeBoardId + ", routePlaceId=" + routePlaceId
				+ ", routePlaceOrderNo=" + routePlaceOrderNo + "]";
	}
	
	
}
