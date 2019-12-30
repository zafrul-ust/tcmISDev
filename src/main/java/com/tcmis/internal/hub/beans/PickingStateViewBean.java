package com.tcmis.internal.hub.beans;

public class PickingStateViewBean {

	private String pickingState;
	private String pickingStateDesc;
	private boolean pickingStatusPgAssignable;
	
	public String getPickingState() {
		return pickingState;
	}

	public void setPickingState(String pickingState) {
		this.pickingState = pickingState;
	}

	public String getPickingStateDesc() {
		return pickingStateDesc;
	}

	public void setPickingStateDesc(String pickingStateDesc) {
		this.pickingStateDesc = pickingStateDesc;
	}
	
	public boolean isPickingStatusPgAssignable() {
		return pickingStatusPgAssignable;
	}

	public void setPickingStatusPgAssignable(boolean pickingStatusPgAssignable) {
		this.pickingStatusPgAssignable = pickingStatusPgAssignable;
	}
}
