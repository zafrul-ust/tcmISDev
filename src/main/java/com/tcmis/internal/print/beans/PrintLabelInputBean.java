package com.tcmis.internal.print.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 8, 2008
 * Time: 1:59:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrintLabelInputBean extends BaseDataBean{

	private String labelType;
	private BigDecimal packingGroupId;
	private String boxId;
    private String printerName;
    private String shipmentId;
    private String facilityId;

    //constructor
	public PrintLabelInputBean() {
	}

	public void setLabelType(String s) {
		this.labelType = s;
	}

    public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}

    public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public void setPrinterName(String s) {
		this.printerName = s;
	}

	public void setShipmentId(String s) {
		this.shipmentId = s;
	}
    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    //getters
	public String getLabelType() {
		return labelType;
	}

    public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}

    public String getBoxId() {
		return boxId;
	}

	public String getPrinterName() {
		return printerName;
	}

	public String getShipmentId() {
		return shipmentId;
	}

    public String getFacilityId() {
        return facilityId;
    }
}
