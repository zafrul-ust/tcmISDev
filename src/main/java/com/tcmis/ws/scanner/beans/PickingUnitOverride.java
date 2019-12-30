package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;

public class PickingUnitOverride extends BaseDataBean {

	private boolean	labelOverride;
	private boolean	packageOverride;
	private boolean	packerOverride;
	private boolean	packerQtyOverride;
	private boolean	qtyOverride;
	private boolean	ridOverride;
	private String	shippingOverride;

	public PickingUnitOverride() {

	}

	public boolean isLabelOverride() {
		return this.labelOverride;
	}

	public boolean isPackageOverride() {
		return this.packageOverride;
	}

	public boolean isPackerOverride() {
		return this.packerOverride;
	}

	public boolean isPackerQtyOverride() {
		return packerQtyOverride;
	}

	public boolean isQtyOverride() {
		return this.qtyOverride;
	}

	public boolean isRidOverride() {
		return ridOverride;
	}

	public String getShippingOverride() {
		return this.shippingOverride;
	}

	public void setLabelOverride(boolean labelOverride) {
		this.labelOverride = labelOverride;
	}

	public void setPackageOverride(boolean packageOverride) {
		this.packageOverride = packageOverride;
	}

	public void setPackerOverride(boolean packerOverride) {
		this.packerOverride = packerOverride;
	}

	public void setPackerQtyOverride(boolean packerQtyOverride) {
		this.packerQtyOverride = packerQtyOverride;
	}

	public void setQtyOverride(boolean qtyOverride) {
		this.qtyOverride = qtyOverride;
	}

	public void setRidOverride(boolean ridOverride) {
		this.ridOverride = ridOverride;
	}

	public void setShippingOverride(String shippingOverride) {
		this.shippingOverride = shippingOverride;
	}
}
