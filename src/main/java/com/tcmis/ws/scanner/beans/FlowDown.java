package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.ResourceLibrary;

public class FlowDown extends BaseDataBean {
	private static ResourceLibrary commonResources;
	static {
		commonResources = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
	}

	private String		catalogId;
	private String		companyId;
	private String		content;
	private String		currentVersion;
	private BigDecimal	docNum;
	private String		flowDown;
	private String		flowDownDesc;
	private String		flowDownType;
	private String		inventoryGroup;
	private BigDecimal	itemId;
	private BigDecimal	partId;
	private BigDecimal	poLine;
	private BigDecimal	radianPo;
	private Date		revisionDate;

	// constructor
	public FlowDown() {
	}
	
	public String getCatalogId() {
		return this.catalogId;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public String getContent() {
		return content;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public BigDecimal getDocNum() {
		return this.docNum;
	}

	public String getFlowDown() {
		return flowDown;
	}

	public String getFlowDownDesc() {
		if (StringUtils.isBlank(flowDown) && StringUtils.isNotBlank(flowDownDesc) && flowDownDesc.startsWith("label.")) {
			String localizedDesc = commonResources.getString(flowDownDesc);
			return StringUtils.isNotBlank(localizedDesc) ? localizedDesc : flowDownDesc;
		}
		return flowDownDesc;
	}

	public String getFlowDownType() {
		return flowDownType;
	}

	public String getInventoryGroup() {
		return this.inventoryGroup;
	}

	public BigDecimal getItemId() {
		return this.itemId;
	}

	public JSONObject getJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("companyId", companyId);
		json.put("catalogId", catalogId);
		json.put("content", content);
		json.put("currentVersion", currentVersion);
		json.put("flowDown", flowDown);
		json.put("flowDownDesc", flowDownDesc);
		json.put("flowDownType", flowDownType);
		json.put("revisionDate", revisionDate);
		return json;
	}

	public BigDecimal getPartId() {
		return this.partId;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public boolean hasRadianPo() {
		return radianPo != null;
	}

	public boolean hasPoLine() {
		return poLine != null;
	}
	
	public boolean hasDocNum() {
		return docNum != null;
	}
	
	public boolean isForShipment(InboundShipment shipment) {
		if (shipment.hasRadianPo() && this.hasRadianPo() && shipment.getRadianPo().compareTo(radianPo) == 0) {
			if (shipment.hasLineItem()) {
				if (this.hasPoLine() && shipment.getLineItem().compareTo(poLine) == 0) {
					return true;
				}
			}
			else if (!this.hasPoLine()) {
				return true;
			}
		}
		
		if (shipment.hasDocNum() && this.hasDocNum() && shipment.getDocNum().compareTo(docNum) == 0) {
			return true;
		}
		
		if (shipment.hasInventoryGroup() && shipment.getInventoryGroup().equals(inventoryGroup)) {
			if ("Vendor Qualification".equals(flowDownType)) {
				return true;
			}
			else  {
				// Quality Control flowdown
				if (shipment.getPartId().compareTo(partId) == 0 && shipment.getItemId().compareTo(itemId) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	public void setFlowDown(String flowDown) {
		this.flowDown = flowDown;
	}

	public void setFlowDownDesc(String flowDownDesc) {
		this.flowDownDesc = flowDownDesc;
	}

	public void setFlowDownType(String flowDownType) {
		this.flowDownType = flowDownType;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public BigDecimal getPoLine() {
		return this.poLine;
	}

	public BigDecimal getRadianPo() {
		return this.radianPo;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

}
