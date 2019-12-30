package com.tcmis.client.ups.beans;

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

public class PackageResultsBean extends BaseDataBean{

	private String trackingNumber;
    private String labelImageFormat;
    private String labelImageFormatDescription;
    private BigDecimal serviceOptionsCharges;
    private String graphicImage;
    private String internationalSignatureGraphicImage;
    private String labelFilePath;
    private String boxId;
    private File file;

    private String pickupDate;


    //constructor
	public PackageResultsBean() {
	}

	//setters
	public void setTrackingNumber(String s) {
		this.trackingNumber = s;
	}

	public void setLabelImageFormat(String s) {
		this.labelImageFormat = s;
	}

	public void setLabelImageFormatDescription(String s) {
		this.labelImageFormatDescription = s;
	}

	public void setServiceOptionsCharges(BigDecimal b) {
		this.serviceOptionsCharges = b;
	}

    public void setGraphicImage(String s) {
		this.graphicImage = s;
	}

    public void setInternationalSignatureGraphicImage(String s) {
		this.internationalSignatureGraphicImage = s;
	}

	public void setLabelFilePath(String s) {
		this.labelFilePath = s;
	}

	public void setBoxId(String s) {
		this.boxId = s;
	}

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

	public String getLabelImageFormatDescription() {
		return this.labelImageFormatDescription;
	}

    public BigDecimal getServiceOptionsCharges() {
        return this.serviceOptionsCharges;
    }

    public String getGraphicImage() {
		return this.graphicImage;
	}

    public String getInternationalSignatureGraphicImage() {
		return this.internationalSignatureGraphicImage;
	}

	public String getLabelFilePath() {
		return this.labelFilePath;
	}

	public String getBoxId() {
		return this.boxId;
	}

	public File getFile() {
		return this.file;
	}

    public String getPickupDate() {
		return this.pickupDate;
	}
}
