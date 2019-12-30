package com.tcmis.client.het.beans;

public class HetPermitMgmtInputBean extends HetPermitIdForMgmtBean {
	private boolean deleted = false;
	private boolean newPermit = false;
	private boolean newPermitRow = false;
	private String oldControlDevice;
	private String oldName;
	private String oldStatus;

	public String getOldName() {
		return oldName;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isNewPermit() {
		return newPermit;
	}

	public boolean isNewPermitRow() {
		return newPermitRow;
	}

	public String getOldControlDevice() {
		return oldControlDevice;
	}

	public boolean isPermitModified() {
		return !oldStatus.equals(getStatus()) || !oldName.equals(getPermitName()) || !oldControlDevice.equalsIgnoreCase(getControlDevice());
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setNewPermit(boolean newPermit) {
		this.newPermit = newPermit;
	}

	public void setNewPermitRow(boolean newPermitRow) {
		this.newPermitRow = newPermitRow;
	}

	public void setOldControlDevice(String oldControlDevice) {
		this.oldControlDevice = oldControlDevice;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

}
