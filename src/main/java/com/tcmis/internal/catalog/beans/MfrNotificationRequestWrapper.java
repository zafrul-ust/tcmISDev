package com.tcmis.internal.catalog.beans;

import java.util.Collection;
import java.util.List;

public class MfrNotificationRequestWrapper {

	private MfrAffectedNotification mfr;
	private MfrAffectedNotification currentMfr;
	private List<MaterialAffectedNotification> materials;
	private Collection<MaterialAffectedNotification> currentMaterials;
	private List<ItemAffectedNotification> items;
	private Collection<ItemAffectedNotification> currentItems;
	public MfrAffectedNotification getMfr() {
		return mfr;
	}
	public void setMfr(MfrAffectedNotification mfr) {
		this.mfr = mfr;
	}
	public List<MaterialAffectedNotification> getMaterials() {
		return materials;
	}
	public void setMaterials(List<MaterialAffectedNotification> materials) {
		this.materials = materials;
	}
	public List<ItemAffectedNotification> getItems() {
		return items;
	}
	public void setItems(List<ItemAffectedNotification> items) {
		this.items = items;
	}
	public MfrAffectedNotification getCurrentMfr() {
		return currentMfr;
	}
	public void setCurrentMfr(MfrAffectedNotification currentMfr) {
		this.currentMfr = currentMfr;
	}
	public Collection<MaterialAffectedNotification> getCurrentMaterials() {
		return currentMaterials;
	}
	public void setCurrentMaterials(Collection<MaterialAffectedNotification> currentMaterials) {
		this.currentMaterials = currentMaterials;
	}
	public Collection<ItemAffectedNotification> getCurrentItems() {
		return currentItems;
	}
	public void setCurrentItems(Collection<ItemAffectedNotification> currentItems) {
		this.currentItems = currentItems;
	}
}
