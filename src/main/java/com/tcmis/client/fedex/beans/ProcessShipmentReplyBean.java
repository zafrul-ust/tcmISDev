package com.tcmis.client.fedex.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 29, 2008
 * Time: 2:59:30 PM
 * To change this template use File | Settings | File Templates.
 */
import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: ProcessShipmentReplyBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class ProcessShipmentReplyBean extends BaseDataBean {

	private String carrierCode;
    private String notification;
    private String transitTime;
    private String serviceDescription;
    private BigDecimal totalNetFreight;
    private BigDecimal totalSurcharges;
   private BigDecimal totalCharge;
   private BigDecimal totalRebate;
    private BigDecimal billingWeight;
    private String packagingDescription;
  private String severity;
  private String message;

    private Collection<PackageDetailBean> packageDetailBeanCollection = new Vector();

    //constructor
	public ProcessShipmentReplyBean() {
	}

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public BigDecimal getBillingWeight() {
    return billingWeight;
  }

  public void setBillingWeight(BigDecimal billingWeight) {
    this.billingWeight = billingWeight;
  }

  public BigDecimal getTotalNetFreight() {
    return totalNetFreight;
  }

  public void setTotalNetFreight(BigDecimal totalNetFreight) {
    this.totalNetFreight = totalNetFreight;
  }

  public BigDecimal getTotalSurcharges() {
    return totalSurcharges;
  }

  public void setTotalSurcharges(BigDecimal totalSurcharges) {
    this.totalSurcharges = totalSurcharges;
  }

  //setters
	public void setCarrierCode(String s) {
		this.carrierCode = s;
	}

	public void setNotification(String s) {
		this.notification = s;
	}

	public void setTransitTime(String s) {
		this.transitTime = s;
	}

	public void setServiceDescription(String s) {
		this.serviceDescription = s;
	}

	public void setTotalRebate(BigDecimal b) {
		this.totalRebate = b;
	}

	public void setTotalCharge(BigDecimal b) {
		this.totalCharge = b;
	}

	public void setPackageDetailBeanCollection(Collection c) {
		this.packageDetailBeanCollection = c;
	}

	public void addPackageDetailBean(PackageDetailBean bean) {
		this.packageDetailBeanCollection.add(bean);
	}

    public void setPackagingDescription(String s) {
		this.packagingDescription = s;
	}
    //getters
	public String getCarrierCode() {
		return this.carrierCode;
	}

	public String getNotification() {
		return this.notification;
	}

	public String getTransitTime() {
		return this.transitTime;
	}

	public String getServiceDescription() {
		return this.serviceDescription;
	}

    public BigDecimal getTotalRebate() {
        return this.totalRebate;
    }

    public BigDecimal getTotalCharge() {
        return this.totalCharge;
    }

    public String getPackagingDescription() {
		return this.packagingDescription;
	}

    public Collection<PackageDetailBean> getPackageDetailBeanCollection() {
		return this.packageDetailBeanCollection;
	}
}
