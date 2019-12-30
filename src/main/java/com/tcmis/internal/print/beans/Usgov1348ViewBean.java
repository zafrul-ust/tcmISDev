package com.tcmis.internal.print.beans;

import java.util.Date;
import java.math.BigDecimal;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Usgov1348ViewBean <br>
 * @version: 1.0, Feb 13, 2008 <br>
 *****************************************************************************/


public class Usgov1348ViewBean extends BaseDataBean {

	private BigDecimal packingGroupId;
	private BigDecimal box10;
	private BigDecimal prNumber;
	private String lineItem;
	private String box2;
	private String box4;
	private String box3;
	private String box27Line1;
	private String priorityRating;
	private String firstRow1348;
	private String box9;
	private String box27Line3;
	private BigDecimal catalogPrice;
	private String facPartNo;
	private String box15;
	private String box17;
	private String box26Line1;
	private String box26Line2;
	private String box26Line3;
	private String box26Line4;
	private String box26Line5;
	private String box26Line6;
	private String box25;
	private String box24;
	private BigDecimal box12;
	private BigDecimal box13;
	private String printedFirstRow;
	private String box1Dollars;
	private String box1Cents;
	private String box5;
  private String fms;
	private String requiredDeliveryDate;
	private String supplementaryAddress;
	private String distributionCode;
	private String unitOfIssue;
	private BigDecimal quantity;
	private String partNumber;
	private String tcn;
	private String cageCode;
	private String projectCode;
	private String box28;
	private String box29;
  private String transportationPriority;
	private String fmsCaseNum;
	private String releaseNum;

  //constructor
	public Usgov1348ViewBean() {
	}

	//setters
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setBox10(BigDecimal box10) {
		this.box10 = box10;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setBox2(String box2) {
		this.box2 = box2;
	}
	public void setBox4(String box4) {
		this.box4 = box4;
	}
	public void setBox3(String box3) {
		this.box3 = box3;
	}
	public void setBox27Line1(String box27Line1) {
		this.box27Line1 = box27Line1;
	}
	public void setPriorityRating(String priorityRating) {
		this.priorityRating = priorityRating;
	}
	public void setFirstRow1348(String firstRow1348) {
		this.firstRow1348 = firstRow1348;
	}
	public void setBox9(String box9) {
		this.box9 = box9;
	}
	public void setBox27Line3(String box27Line3) {
		this.box27Line3 = box27Line3;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setBox15(String box15) {
		this.box15 = box15;
	}
	public void setBox17(String box17) {
		this.box17 = box17;
	}
	public void setBox26Line1(String box26Line1) {
		this.box26Line1 = box26Line1;
	}
	public void setBox26Line2(String box26Line2) {
		this.box26Line2 = box26Line2;
	}
	public void setBox26Line3(String box26Line3) {
		this.box26Line3 = box26Line3;
	}
	public void setBox26Line4(String box26Line4) {
		this.box26Line4 = box26Line4;
	}
	public void setBox26Line5(String box26Line5) {
		this.box26Line5 = box26Line5;
	}
	public void setBox26Line6(String box26Line6) {
		this.box26Line6 = box26Line6;
	}
	public void setBox25(String box25) {
		this.box25 = box25;
	}
	public void setBox24(String box24) {
		this.box24 = box24;
	}
  public void setBox12(BigDecimal box12) {
    this.box12 = box12;
  }
  public void setBox13(BigDecimal box13) {
    this.box13 = box13;
  }
	public void setPrintedFirstRow(String printedFirstRow) {
		this.printedFirstRow = printedFirstRow;
	}
	public void setBox1Dollars(String box1Dollars) {
		this.box1Dollars = box1Dollars;
	}
	public void setBox1Cents(String box1Cents) {
		this.box1Cents = box1Cents;
	}
	public void setBox5(String box5) {
		this.box5 = box5;
	}
	public void setFms(String fms) {
		this.fms = fms;
	}
	public void setRequiredDeliveryDate(String requiredDeliveryDate) {
		this.requiredDeliveryDate = requiredDeliveryDate;
	}
  public void setSupplementaryAddress(String supplementaryAddress) {
    this.supplementaryAddress = supplementaryAddress;
  }
  public void setDistributionCode(String distributionCode) {
    this.distributionCode = distributionCode;
  }
  public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setTcn(String tcn) {
		this.tcn = tcn;
	}
	public void setCageCode(String cageCode) {
		this.cageCode = cageCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public void setBox28(String box28) {
		this.box28 = box28;
	}
	public void setBox29(String box29) {
		this.box29 = box29;
	}
	public void setTransportationPriority(String transportationPriority) {
		this.transportationPriority = transportationPriority;
	}
	public void setFmsCaseNum(String fmsCaseNum) {
		this.fmsCaseNum = fmsCaseNum;
	}
    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum;
    }

  //getters
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public BigDecimal getBox10() {
		return box10;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getBox2() {
		return box2;
	}
	public String getBox4() {
		return box4;
	}
	public String getBox3() {
		return box3;
	}
	public String getBox27Line1() {
		return box27Line1;
	}
	public String getPriorityRating() {
		return priorityRating;
	}
	public String getFirstRow1348() {
		return firstRow1348;
	}
	public String getBox9() {
		return box9;
	}
	public String getBox27Line3() {
		return box27Line3;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getBox15() {
		return box15;
	}
	public String getBox17() {
		return box17;
	}
	public String getBox26Line1() {
		return box26Line1;
	}
	public String getBox26Line2() {
		return box26Line2;
	}
	public String getBox26Line3() {
		return box26Line3;
	}
	public String getBox26Line4() {
		return box26Line4;
	}
	public String getBox26Line5() {
		return box26Line5;
	}
	public String getBox26Line6() {
		return box26Line6;
	}
	public String getBox25() {
		return box25;
	}
	public String getBox24() {
		return box24;
	}
	public BigDecimal getBox12() {
		return box12;
	}
	public BigDecimal getBox13() {
		return box13;
	}
  public String getPrintedFirstRow() {
    return printedFirstRow;
  }
  public String getBox1Dollars() {
    return box1Dollars;
  }
  public String getBox1Cents() {
    return box1Cents;
  }
  public String getBox5() {
    return box5;
  }
	public String getFms() {
		return fms;
	}
  public String getRequiredDeliveryDate() {
    return requiredDeliveryDate;
  }
  public String getSupplementaryAddress() {
    return StringHandler.emptyIfNull(supplementaryAddress);
  }
  public String getDistributionCode() {
    return distributionCode;
  }
  public String getUnitOfIssue() {
    return unitOfIssue;
  }
  public BigDecimal getQuantity() {
    return quantity;
  }
  public String getPartNumber() {
    return partNumber;
  }
  public String getTcn() {
    return tcn;
  }
  public String getCageCode() {
    return cageCode;
  }
	public String getProjectCode() {
		return projectCode;
	}
	public String getBox28() {
		return box28;
	}
	public String getBox29() {
		return box29;
	}
	public String getTransportationPriority() {
		return transportationPriority;
	}
	public String getFmsCaseNum() {
		return fmsCaseNum;
	}
    public String getReleaseNum() {
        return releaseNum;
    }    
}