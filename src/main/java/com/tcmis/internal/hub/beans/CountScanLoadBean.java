package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.xmlbeans.XmlCalendar;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CountScanLoadBean <br>
 * @version: 1.0, Oct 12, 2006 <br>
 *****************************************************************************/


public class CountScanLoadBean extends BaseDataBean {

	private Date dateCounted;
	private String typeItemOrPn;
	private String inventoryGroup;
	private BigDecimal loadId;
	private String companyId;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private BigDecimal itemId;
	private String countType;
	private BigDecimal uomCountedQuantity;
	private BigDecimal personnelId;
	private Date datetimeLoaded;
	private BigDecimal countId;
	private Date datetimeProcessed;
	private String processStatement;
	private Date scanDate;
	private String loadInventoryGroup;

	//constructor
	public CountScanLoadBean() {
	}

	//setters
	public void setDateCounted(Date dateCounted) {
		this.dateCounted = dateCounted;
	}
	public void setTypeItemOrPn(String typeItemOrPn) {
		this.typeItemOrPn = typeItemOrPn;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setLoadId(BigDecimal loadId) {
		this.loadId = loadId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	public void setUomCountedQuantity(BigDecimal uomCountedQuantity) {
		this.uomCountedQuantity = uomCountedQuantity;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setDatetimeLoaded(Date datetimeLoaded) {
		this.datetimeLoaded = datetimeLoaded;
	}
	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}
	public void setDatetimeProcessed(Date datetimeProcessed) {
		this.datetimeProcessed = datetimeProcessed;
	}
	public void setProcessStatement(String processStatement) {
		this.processStatement = processStatement;
	}
	public void setScanDate(Date scanDate) {
	 this.scanDate = scanDate;
	}
	public void setLoadInventoryGroup(String loadInventoryGroup) {
	 this.loadInventoryGroup = loadInventoryGroup;
	}


	//getters
	public Date getDateCounted() {
		return dateCounted;
	}
	public String getTypeItemOrPn() {
		return typeItemOrPn;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getLoadId() {
		return loadId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getCountType() {
		return countType;
	}
	public BigDecimal getUomCountedQuantity() {
		return uomCountedQuantity;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public Date getDatetimeLoaded() {
		return datetimeLoaded;
	}
	public BigDecimal getCountId() {
		return countId;
	}
	public Date getDatetimeProcessed() {
		return datetimeProcessed;
	}
	public String getProcessStatement() {
		return processStatement;
	}
	public Date getScanDate() {
	 return scanDate;
	}
	public String getLoadInventoryGroup() {
	 return loadInventoryGroup;
	}
// added settier for xml string inputs	
	public void setUomCountedQuantityStr(String s) {
		if( s != null && s.length() !=0 )
			this.uomCountedQuantity = new BigDecimal(s);
	}
	public void setPersonnelIdStr(String s) {
		if( s != null && s.length() !=0 )
			this.personnelId = new BigDecimal(s);
	}
	public void setDateCountedStr(String countDatetime) {
		Date d = null; 
		try {
		XmlCalendar x = new XmlCalendar(countDatetime); 
		d = x.getTime();
		}catch(Exception ex){}
		this.dateCounted = d;
	}
	public void setScanDateStr(String countDatetime) {
		Date d = null; 
		try {
		XmlCalendar x = new XmlCalendar(countDatetime); 
		d = x.getTime();
		}catch(Exception ex){}
		this.scanDate = d;
	}

}