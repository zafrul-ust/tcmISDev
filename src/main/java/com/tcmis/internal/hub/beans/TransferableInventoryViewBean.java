package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TransferableInventoryViewBean <br>
 * @version: 1.0, Nov 9, 2006 <br>
 *****************************************************************************/

public class TransferableInventoryViewBean
    extends BaseDataBean {

  private String inventoryGroup;
  private String inventoryGroupName;
  private String hub;
  private BigDecimal itemId;
  private String itemDesc;
  private String xferSourceOriginate;
  private String packaging;
  private BigDecimal onHand;
  private BigDecimal allocatable;
  private BigDecimal transferQuantity;
  private Date transferDate;
  private String searchString;
  private String specList;
  private String opsEntityId;
  private String opsCompanyId;
  private String destInventoryGroup;
  private String companyId;
  private String catalogCompanyId;
  private String catalogId;
  private String catPartNo;
  private BigDecimal partGroupNo;
  private String ok;
  private BigDecimal transferRequestId;
  private String comments;
  private String destInvGroupName;
  private String distCustomerPartList;
  private String shippingReference;

//constructor
  public TransferableInventoryViewBean() {
  }

  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }
  
  

  public String getInventoryGroupName() {
	return inventoryGroupName;
}

public void setInventoryGroupName(String inventoryGroupName) {
	this.inventoryGroupName = inventoryGroupName;
}

public void setHub(String hub) {
    this.hub = hub;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setItemDesc(String itemDesc) {
    this.itemDesc = itemDesc;
  }

  public void setXferSourceOriginate(String xferSourceOriginate) {
    this.xferSourceOriginate = xferSourceOriginate;
  }

  public void setPackaging(String packaging) {
    this.packaging = packaging;
  }

  public void setSearchString(String searchString) {
    this.searchString = searchString;
  }

   //getters
  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getHub() {
    return hub;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public String getItemDesc() {
    return itemDesc;
  }

  public String getXferSourceOriginate() {
    return xferSourceOriginate;
  }

  public String getPackaging() {
    return packaging;
  }

   public String getSearchString() {
    return searchString;
  }

  
	public String getSpecList() {
		return specList;
	}
	
	public void setSpecList(String specList) {
		this.specList = specList;
	}
	
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	
	public String getOpsEntityId() {
		return opsEntityId;
	}
	
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	
	public String getDestInventoryGroup() {
		return destInventoryGroup;
	}
	
	public void setDestInventoryGroup(String destInventoryGroup) {
		this.destInventoryGroup = destInventoryGroup;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	
	public String getCatalogId() {
		return catalogId;
	}
	
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	
	public String getCatPartNo() {
		return catPartNo;
	}
	
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	
	public BigDecimal getTransferQuantity() {
		return transferQuantity;
	}
	
	public void setTransferQuantity(BigDecimal transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	
	public String getOk() {
		return ok;
	}
	
	public void setOk(String ok) {
		this.ok = ok;
	}
	
	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}
	
	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}
	
	public Date getTransferDate() {
		return transferDate;
	}
	
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigDecimal getOnHand() {
		return onHand;
	}

	public void setOnHand(BigDecimal onHand) {
		this.onHand = onHand;
	}

	public BigDecimal getAllocatable() {
		return allocatable;
	}

	public void setAllocatable(BigDecimal allocatable) {
		this.allocatable = allocatable;
	}

	public String getDestInvGroupName() {
		return destInvGroupName;
	}

	public void setDestInvGroupName(String destInvGroupName) {
		this.destInvGroupName = destInvGroupName;
	}

	public String getDistCustomerPartList() {
		return distCustomerPartList;
	}

	public void setDistCustomerPartList(String distCustomerPartList) {
		this.distCustomerPartList = distCustomerPartList;
	}
	
	public String getShippingReference() {
		return shippingReference;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}
	
	
	
}