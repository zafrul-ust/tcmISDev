package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: CatPartQplViewBean <br>
 * @version: 1.0, May 14, 2010 <br>
 *****************************************************************************/

public class CatPartQplViewBean extends BaseDataBean {

	private BigDecimal sortingOrder;
	private BigDecimal lineItem;
	private BigDecimal itemId;
	private String status;
	private BigDecimal partId;
	private String materialDesc;
	private String packaging;
	private String mfgDesc;
	private BigDecimal materialId;
	private String mfgPartNo;
	private String msdsOnLine;
	private String dataSource;
	private BigDecimal displayLineItem;
	private BigDecimal startingView;
	private String displayStatus;

	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
	private BigDecimal minStorageTemp;
	private BigDecimal maxStorageTemp;
	private String storageTempUnit;
	private String grade;
	private String customerMsdsNumber;
	private String customerTemperatureId;
	private String customerMixtureNumber;
	private BigDecimal roomTempOutTime;
	private String itemIsKit;
    private String approvalLetterContent;
    private String replacesMsds;
    private String aerosolContainer;
    private String radioactive;
    private String nanomaterial;
    private String msdsApprovalCode;
    private String kitApprovalCode;
    private String approvalCode;
    private String hetUsageRecording;
    private String labelColor;
    private String mixtureDesc;
    private String roomTempOutTimeUnit;
    private String vocetStatusId;
    private String vocetCoatCategoryId;
    private BigDecimal mixRatioAmount;
    private String mixRatioSizeUnit; 
    private String mixRatioCalculated;
    private String calcMixRatioSizeUnit;
    private BigDecimal calcMixRatioAmount;
    private String approvedCustMsdsNumber;
    private String approvedCustMixtureNumber;
    private BigDecimal netwt;
    private String netwtUnit;
    private BigDecimal mfgId;
    private String customerMfgId;
	private String customerMfgIdDisplay;
    
    //constructor
 	public CatPartQplViewBean() {
 	}

 	//setters
	public void setSortingOrder(BigDecimal sortingOrder) {
		this.sortingOrder = sortingOrder;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}

	public void setMsdsOnLine(String msdsOnLine) {
		this.msdsOnLine = msdsOnLine;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setDisplayLineItem(BigDecimal displayLineItem) {
		this.displayLineItem = displayLineItem;
	}

	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public void setMinStorageTemp(BigDecimal minStorageTemp) {
		this.minStorageTemp = minStorageTemp;
	}

	public void setMaxStorageTemp(BigDecimal maxStorageTemp) {
		this.maxStorageTemp = maxStorageTemp;
	}

	public void setStorageTempUnit(String storageTempUnit) {
		this.storageTempUnit = storageTempUnit;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setMixRatioAmount(BigDecimal mixRatioAmount) {
		this.mixRatioAmount = mixRatioAmount;
	}
	public void setMixRatioSizeUnit(String mixRatioSizeUnit) {
		this.mixRatioSizeUnit = mixRatioSizeUnit;
	}
	public void setMixRatioCalculated(String mixRatioCalculated) {
		this.mixRatioCalculated = mixRatioCalculated;
	}
	public void setCalcMixRatioSizeUnit(String calcMixRatioSizeUnit) {
		this.calcMixRatioSizeUnit = calcMixRatioSizeUnit;
	}
	public void setCalcMixRatioAmount(BigDecimal calcMixRatioAmount) {
		this.calcMixRatioAmount = calcMixRatioAmount;
	}
	public void setApprovedCustMsdsNumber(String approvedCustMsdsNumber) {
		this.approvedCustMsdsNumber = approvedCustMsdsNumber;
	}
	public void setApprovedCustMixtureNumber(String approvedCustMixtureNumber) {
		this.approvedCustMixtureNumber = approvedCustMixtureNumber;
	}
    
	//getters
	public String getApprovedCustMsdsNumber() {
		return approvedCustMsdsNumber;
	}
	public String getApprovedCustMixtureNumber() {
		return approvedCustMixtureNumber;
	}
	public String getCalcMixRatioSizeUnit() {
		return calcMixRatioSizeUnit;
	}
	public BigDecimal getCalcMixRatioAmount() {
		return calcMixRatioAmount;
	}
	public BigDecimal getMixRatioAmount() {
		return mixRatioAmount;
	}
	public String getMixRatioSizeUnit() {
		return mixRatioSizeUnit;
	}
	public String getMixRatioCalculated() {
		return mixRatioCalculated;
	}
 
	public BigDecimal getSortingOrder() {
		return sortingOrder;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getStatus() {
		return status;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getMfgDesc() {
		return mfgDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMfgPartNo() {
		return mfgPartNo;
	}

	public String getMsdsOnLine() {
		return msdsOnLine;
	}

	public String getDataSource() {
		return dataSource;
	}

	public BigDecimal getDisplayLineItem() {
		return displayLineItem;
	}

	public BigDecimal getStartingView() {
		return startingView;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}

	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}

	public BigDecimal getMinStorageTemp() {
		return minStorageTemp;
	}

	public BigDecimal getMaxStorageTemp() {
		return maxStorageTemp;
	}

	public String getStorageTempUnit() {
		return storageTempUnit;
	}

	public String getGrade() {
		return grade;
	}

	public String getCustomerMsdsNumber() {
		return customerMsdsNumber;
	}

	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.customerMsdsNumber = customerMsdsNumber;
	}

	public String getCustomerTemperatureId() {
		return customerTemperatureId;
	}

	public void setCustomerTemperatureId(String customerTemperatureId) {
		this.customerTemperatureId = customerTemperatureId;
	}

    public String getCustomerMixtureNumber() {
        return customerMixtureNumber;
    }

    public void setCustomerMixtureNumber(String customerMixtureNumber) {
        this.customerMixtureNumber = customerMixtureNumber;
    }

    public BigDecimal getRoomTempOutTime() {
		return roomTempOutTime;
	}

	public void setRoomTempOutTime(BigDecimal roomTempOutTime) {
		this.roomTempOutTime = roomTempOutTime;
	}

	public String getItemIsKit() {
		return itemIsKit;
	}

	public void setItemIsKit(String itemIsKit) {
		this.itemIsKit = itemIsKit;
	}

    public String getApprovalLetterContent() {
        return approvalLetterContent;
    }

    public void setApprovalLetterContent(String approvalLetterContent) {
        this.approvalLetterContent = approvalLetterContent;
    }

    public String getReplacesMsds() {
        return replacesMsds;
    }

    public void setReplacesMsds(String replacesMsds) {
        this.replacesMsds = replacesMsds;
    }

    public String getAerosolContainer() {
        return aerosolContainer;
    }

    public void setAerosolContainer(String aerosolContainer) {
        this.aerosolContainer = aerosolContainer;
    }

    public String getRadioactive() {
        return radioactive;
    }

    public void setRadioactive(String radioactive) {
        this.radioactive = radioactive;
    }

    public String getNanomaterial() {
        return nanomaterial;
    }

    public void setNanomaterial(String nanomaterial) {
        this.nanomaterial = nanomaterial;
    }

    public String getMsdsApprovalCode() {
        return msdsApprovalCode;
    }

    public void setMsdsApprovalCode(String msdsApprovalCode) {
        this.msdsApprovalCode = msdsApprovalCode;
    }
       
    public String getKitApprovalCode() {
        return kitApprovalCode;
    }

    public void setKitApprovalCode(String kitApprovalCode) {
        this.kitApprovalCode = kitApprovalCode;
    }
    
    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
    
    public String getHetUsageRecording() {
        return hetUsageRecording;
    }

    public void setHetUsageRecording(String hetUsageRecording) {
        this.hetUsageRecording = hetUsageRecording;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getMixtureDesc() {
        return mixtureDesc;
    }

    public void setMixtureDesc(String mixtureDesc) {
        this.mixtureDesc = mixtureDesc;
    }

    public String getRoomTempOutTimeUnit() {
        return roomTempOutTimeUnit;
    }

    public void setRoomTempOutTimeUnit(String roomTempOutTimeUnit) {
        this.roomTempOutTimeUnit = roomTempOutTimeUnit;
    }

    public String getVocetStatusId() {
        return vocetStatusId;
    }

    public void setVocetStatusId(String vocetStatusId) {
        this.vocetStatusId = vocetStatusId;
    }

    public String getVocetCoatCategoryId() {
        return vocetCoatCategoryId;
    }

    public void setVocetCoatCategoryId(String vocetCoatCategoryId) {
        this.vocetCoatCategoryId = vocetCoatCategoryId;
    }

    public BigDecimal getNetwt() {
        return netwt;
    }

    public void setNetwt(BigDecimal netwt) {
        this.netwt = netwt;
    }

    public String getNetwtUnit() {
        return netwtUnit;
    }

    public void setNetwtUnit(String netwtUnit) {
        this.netwtUnit = netwtUnit;
    }

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}

	public String getCustomerMfgId() {
		return customerMfgId;
	}

	public void setCustomerMfgId(String customerMfgId) {
		this.customerMfgId = customerMfgId;
	}

	public String getCustomerMfgIdDisplay() {
		return customerMfgIdDisplay;
	}

	public void setCustomerMfgIdDisplay(String customerMfgIdDisplay) {
		this.customerMfgIdDisplay = customerMfgIdDisplay;
	}

} //end of class