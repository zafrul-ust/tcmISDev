package com.tcmis.client.fedex.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Oct 1, 2008
 * Time: 4:34:39 PM
 * To change this template use File | Settings | File Templates.
 */
import com.tcmis.common.framework.BaseDataBean;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jan 21, 2008
 * Time: 3:53:40 PM
 * To change this template use File | Settings | File Templates.
 */

public class PackageDetailBean extends BaseDataBean{

	private String trackingNumber;
  private String labelImageFormat;
  private String labelImage;
  //private BigDecimal serviceOptionsCharges;
  //private String graphicImage;
  //private String internationalSignatureGraphicImage;
  private String labelFilePath;
  //private String boxId;
  private File file;
  private String pickupDate;
  private BigDecimal billingWeight;
  private BigDecimal baseCharge;
  private BigDecimal totalFreightDiscounts;
  private BigDecimal netFreight;
  private BigDecimal totalSurcharges;
  private BigDecimal netCharge;

  public BigDecimal getBillingWeight() {
    return billingWeight;
  }

  public void setBillingWeight(BigDecimal billingWeight) {
    this.billingWeight = billingWeight;
  }

  public BigDecimal getBaseCharge() {
    return baseCharge;
  }

  public void setBaseCharge(BigDecimal baseCharge) {
    this.baseCharge = baseCharge;
  }

  public BigDecimal getTotalFreightDiscounts() {
    return totalFreightDiscounts;
  }

  public void setTotalFreightDiscounts(BigDecimal totalFreightDiscounts) {
    this.totalFreightDiscounts = totalFreightDiscounts;
  }

  public BigDecimal getNetFreight() {
    return netFreight;
  }

  public void setNetFreight(BigDecimal netFreight) {
    this.netFreight = netFreight;
  }

  public BigDecimal getTotalSurcharges() {
    return totalSurcharges;
  }

  public void setTotalSurcharges(BigDecimal totalSurcharges) {
    this.totalSurcharges = totalSurcharges;
  }

  public BigDecimal getNetCharge() {
    return netCharge;
  }

  public void setNetCharge(BigDecimal netCharge) {
    this.netCharge = netCharge;
  }

  //constructor
	public PackageDetailBean() {
	}

	//setters
	public void setTrackingNumber(String s) {
		this.trackingNumber = s;
	}

	public void setLabelImageFormat(String s) {
		this.labelImageFormat = s;
	}

	public void setLabelImage(String s) {
		this.labelImage = s;
	}
/*
	public void setServiceOptionsCharges(BigDecimal b) {
		this.serviceOptionsCharges = b;
	}

    public void setGraphicImage(String s) {
		this.graphicImage = s;
	}

    public void setInternationalSignatureGraphicImage(String s) {
		this.internationalSignatureGraphicImage = s;
	}
*/
	public void setLabelFilePath(String s) {
		this.labelFilePath = s;
	}
/*
	public void setBoxId(String s) {
		this.boxId = s;
	}
*/
	public void setFile(File f) {
		this.file = f;
	}

    public void setPickupDate(String s) {
		this.pickupDate = s;
	}
    //getters
	public String getTrackingNumber() {
		return this.trackingNumber;
	}

	public String getLabelImageFormat() {
		return this.labelImageFormat;
	}

	public String getLabelImage() {
		return this.labelImage;
	}
/*
    public BigDecimal getServiceOptionsCharges() {
        return this.serviceOptionsCharges;
    }

    public String getGraphicImage() {
		return this.graphicImage;
	}

    public String getInternationalSignatureGraphicImage() {
		return this.internationalSignatureGraphicImage;
	}
*/
	public String getLabelFilePath() {
		return this.labelFilePath;
	}
/*
	public String getBoxId() {
		return this.boxId;
	}
*/
	public File getFile() {
		return this.file;
	}

    public String getPickupDate() {
		return this.pickupDate;
	}
}
