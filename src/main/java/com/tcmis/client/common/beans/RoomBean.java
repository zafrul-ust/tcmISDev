package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: RoomBean <br>
 * @version: 1.0, Jan 11, 2011 <br>
 *****************************************************************************/

public class RoomBean extends BaseDataBean {

	private String floorId;
	private String companyId;
	private boolean interior = true;
	private String map;
	private String mapGrid;
	private String roomDescription;
	private String roomId;
	private String roomName;
	private String sprinkler;
	private BigDecimal unidocsControlArea;

	//constructor
	public RoomBean() {
	}

	public String getFloorId() {
		return floorId;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}


	public String getMap() {
		return map;
	}

	public String getMapGrid() {
		return mapGrid;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public String getRoomId() {
		return roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getSprinkler() {
		return sprinkler;
	}

	public BigDecimal getUnidocsControlArea() {
		return unidocsControlArea;
	}

	public boolean hasDescription() {
		return !StringHandler.isBlankString(roomDescription);
	}


	public boolean hasName() {
		return !StringHandler.isBlankString(roomName);
	}

	public boolean isInterior() {
		return interior;
	}

	public boolean isNewRoom() {
		return StringHandler.isBlankString(roomId);
	}

	public void setFloorId(String buildingId) {
		this.floorId = buildingId;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setInterior(boolean interior) {
		this.interior = interior;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public void setMapGrid(String mapGrid) {
		this.mapGrid = mapGrid;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setSprinkler(String sprinkler) {
		this.sprinkler = sprinkler;
	}

	public void setUnidocsControlArea(BigDecimal unidocsControlArea) {
		this.unidocsControlArea = unidocsControlArea;
	}
}