package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ConsolidatedBolDetailViewBean <br>
 * @version: 1.0, Mar 9, 2006 <br>
 *****************************************************************************/


public class ConsolidatedBolDetailViewBean extends BaseDataBean {

	private BigDecimal shipmentId;
	private String hazardous;
	private String dot;
	private BigDecimal netWeightLb;
        private String mrLine;
        private BigDecimal totalNetWeightLb;
        private String majorHazardClass;
        private String totalNetWtForMhc;

	//constructor
	public ConsolidatedBolDetailViewBean() {
	}

	//setters
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}
	public void setDot(String dot) {
		this.dot = dot;
	}
	public void setNetWeightLb(BigDecimal netWeightLb) {
		this.netWeightLb = netWeightLb;
	}
        public void setMrLine(String mrLine) {
                this.mrLine = mrLine;
        }
        public void setTotalNetWeightLb(BigDecimal totalNetWeightLb) {
                this.totalNetWeightLb = totalNetWeightLb;
        }
        public void setMajorHazardClass(String majorHazardClass) {
                this.majorHazardClass = majorHazardClass;
        }
        public void setTotalNetWtForMhc(String totalNetWtForMhc) {
                this.totalNetWtForMhc = totalNetWtForMhc;
        }


	//getters
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getHazardous() {
		return hazardous;
	}
	public String getDot() {
		return dot;
	}
	public BigDecimal getNetWeightLb() {
		return netWeightLb;
	}

        public String getMrLine() {
                return mrLine;
        }

        public BigDecimal getTotalNetWeightLb() {
                return totalNetWeightLb;
        }

        public String getMajorHazardClass() {
                return majorHazardClass;
        }

        public String getTotalNetWtForMhc() {
                return totalNetWtForMhc;
        }
}