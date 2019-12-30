package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/**
 * @author spiros.petratos
 * 
 */
@SuppressWarnings("serial")
public class MrAllocationViewBean extends BaseDataBean
{

	private String		catalogCompanyId;
	private String		catalogId;
	private String		catPartNo;
	private String		companyId;
	private String		csrName;
	private String		customerName;
	private Date		dateCreated;
	private Date		dateToDeliver;
	private String		dedicated;
	private String		deleteAfterBuy;
	private BigDecimal	docNum;
	private String		docType;
	private Date		expireDatetime;
	private String		hub;
	private String		inventoryGroup;
	private String		itemDesc;
	private BigDecimal	itemId;
	private String		lineItem;
	private String		mfgLot;
	private String		mr;
	private BigDecimal	packingGroupId;
	private BigDecimal	partGroupNo;
	private BigDecimal	prNumber;
	private BigDecimal	quantity;
	private String		status;
	private String		supplierName;


	public MrAllocationViewBean()
	{
		super();
	}

	public String getCatalogCompanyId()
	{
		return this.catalogCompanyId;
	}

	public String getCatalogId()
	{
		return this.catalogId;
	}

	public String getCatPartNo()
	{
		return this.catPartNo;
	}

	public String getCompanyId()
	{
		return this.companyId;
	}

	public String getCsrName()
	{
		return this.csrName;
	}

	public String getCustomerName()
	{
		return this.customerName;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated()
	{
		return this.dateCreated;
	}

	public Date getDateToDeliver()
	{
		return this.dateToDeliver;
	}

	public String getDedicated()
	{
		return this.dedicated;
	}

	public String getDeleteAfterBuy()
	{
		return this.deleteAfterBuy;
	}

	public BigDecimal getDocNum()
	{
		return this.docNum;
	}

	public String getDocType()
	{
		return this.docType;
	}

	public Date getExpireDatetime()
	{
		return this.expireDatetime;
	}

	public String getHub()
	{
		return this.hub;
	}

	public String getInventoryGroup()
	{
		return this.inventoryGroup;
	}

	public String getItemDesc()
	{
		return this.itemDesc;
	}

	public BigDecimal getItemId()
	{
		return this.itemId;
	}

	public String getLineItem()
	{
		return this.lineItem;
	}

	public String getMfgLot()
	{
		return this.mfgLot;
	}

	/**
	 * @return the mr
	 */
	public String getMr()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.prNumber).append(" - ").append(this.lineItem);
		return sb.toString();
	}

	public BigDecimal getPackingGroupId()
	{
		return this.packingGroupId;
	}

	public BigDecimal getPartGroupNo()
	{
		return this.partGroupNo;
	}

	public BigDecimal getPrNumber()
	{
		return this.prNumber;
	}

	public BigDecimal getQuantity()
	{
		return this.quantity;
	}

	public String getStatus()
	{
		return this.status;
	}

	public String getSupplierName()
	{
		return this.supplierName;
	}

	public Date getTimeStamp()
	{
		return this.dateCreated;
	}

	public void setCatalogCompanyId(String catalogCompanyId)
	{
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId)
	{
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo)
	{
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId)
	{
		this.companyId = companyId;
	}

	public void setCsrName(String csrName)
	{
		this.csrName = csrName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public void setDateToDeliver(Date dateToDeliver)
	{
		this.dateToDeliver = dateToDeliver;
	}

	public void setDedicated(String dedicated)
	{
		this.dedicated = dedicated;
	}

	public void setDeleteAfterBuy(String deleteAfterBuy)
	{
		this.deleteAfterBuy = deleteAfterBuy;
	}

	public void setDocNum(BigDecimal docNum)
	{
		this.docNum = docNum;
	}

	public void setDocType(String docType)
	{
		this.docType = docType;
	}

	public void setExpireDatetime(Date expireDatetime)
	{
		this.expireDatetime = expireDatetime;
	}

	public void setHub(String hub)
	{
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup)
	{
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemDesc(String itemDesc)
	{
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId)
	{
		this.itemId = itemId;
	}

	public void setLineItem(String lineItem)
	{
		this.lineItem = lineItem;
	}

	public void setMfgLot(String mfgLot)
	{
		this.mfgLot = mfgLot;
	}

	public void setPackingGroupId(BigDecimal packingGroupId)
	{
		this.packingGroupId = packingGroupId;
	}

	public void setPartGroupNo(BigDecimal partGroupNo)
	{
		this.partGroupNo = partGroupNo;
	}

	public void setPrNumber(BigDecimal prNumber)
	{
		this.prNumber = prNumber;
	}

	public void setQuantity(BigDecimal quantity)
	{
		this.quantity = quantity;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public void setSupplierName(String supplierName)
	{
		this.supplierName = supplierName;
	}

	public void setTimeStamp(Date timeStamp)
	{
		this.dateCreated = timeStamp;
	}
}
