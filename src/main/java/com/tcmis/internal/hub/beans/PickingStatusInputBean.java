package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

@SuppressWarnings("serial")
public class PickingStatusInputBean extends BaseInputBean {

	private Date		filterDate;
	private BigDecimal  labelPrintQty;
	private String      labelPrintType;
	private String		orderBy;
	private Date		pickCreationFromDate;
	private Date		pickCreationToDate;
	private BigDecimal	pickingGroupId;
	private String		pickingState;
	private BigDecimal	pickingUnitId;
	private String		searchText;
	private String		searchType;
	private String		searchWhat;
	private String		dotOverride;
	private String		sourceHub;
	private String		opsEntityId;
	private String		tabletShipmentId;
	
	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public Date getFilterDate() {
		return filterDate;
	}

	public BigDecimal getLabelPrintQty() {
		return labelPrintQty;
	}

	public String getLabelPrintType() {
		return labelPrintType;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Date getPickCreationFromDate() {
		return this.pickCreationFromDate;
	}

	public Date getPickCreationToDate() {
		return this.pickCreationToDate;
	}

	public BigDecimal getPickingGroupId() {
		return pickingGroupId;
	}

	public String getPickingState() {
		return pickingState;
	}

	public BigDecimal getPickingUnitId() {
		return this.pickingUnitId;
	}

	public String getSearchComparisonAndValue() {
		if (isSearchTypeIs()) {
			return " = " + SqlHandler.delimitString(searchText.toLowerCase());
		}
		else if (isSearchTypeContains()) {
			return " like '%" + SqlHandler.validQuery(searchText.toLowerCase()) + "%'";
		}
		else if (isSearchTypeStartsWith()) {
			return " like '" + SqlHandler.validQuery(searchText.toLowerCase()) + "%'";
		}
		else if (isSearchTypeEndsWith()) {
			return " like '%" + SqlHandler.validQuery(searchText.toLowerCase()) + "'";
		}
		return "unknown";
	}

	public String getSearchField() {
		if (isReceiptSearch()) {
			return "i.receipt_id";
		}
		else if (isPicklistSearch()) {
			return "pu.picklist_id";
		}
		else if (isItemSearch()) {
			return "i.item_id";
		}
		else if (isMRSearch()) {
			return "pu.pr_number";
		}
		else if (isCustomerPartNoSearch()) {
			return "rli.fac_part_no";
		}
		else if (isPickingUnitIdSearch()) {
			return "pu.picking_unit_id";
		}
		return "unknown";
	}

	public String getSearchText() {
		return searchText;
	}

	public String getSearchType() {
		return searchType;
	}

	public String getSearchWhat() {
		return searchWhat;
	}

	public String getDotOverride() {
		return this.dotOverride;
	}

	public String getSourceHub() {
		return sourceHub;
	}

	public boolean hasAction() {
		return ! StringHandler.isBlankString(uAction);
	}

	public boolean hasFilterDate() {
		return filterDate != null;
	}

	public boolean hasPickCreationFromDate() {
		return pickCreationFromDate != null;
	}

	public boolean hasPickCreationToDate() {
		return pickCreationToDate != null;
	}

	public boolean hasPickingGroupId() {
		return pickingGroupId != null;
	}
	
	public boolean hasPickingState() {
		return StringUtils.isNotBlank(pickingState);
	}

	public boolean hasSearchText() {
		return StringUtils.isNotBlank(searchText);
	}

	public boolean hasSourceHub() {
		return StringUtils.isNotBlank(sourceHub);
	}

	public boolean isCustomerPartNoOrder() {
		return "facpartno".equalsIgnoreCase(orderBy);
	}

	public boolean isCustomerPartNoSearch() {
		return "facPartNo".equalsIgnoreCase(searchWhat);
	}

	public boolean isDeliveryPointOrder() {
		return "deliverypoint".equalsIgnoreCase(orderBy);
	}

	public boolean isFacilityOrder() {
		return "facility".equalsIgnoreCase(orderBy);
	}

	public boolean isItemOrder() {
		return "item".equalsIgnoreCase(orderBy);
	}

	public boolean isItemSearch() {
		return "itemId".equalsIgnoreCase(searchWhat);
	}

	public boolean isMRSearch() {
		return "prNumber".equalsIgnoreCase(searchWhat);
	}

	public boolean isNeedDateOrder() {
		return "needdate".equalsIgnoreCase(orderBy);
	}

	public boolean isPdocSearch() {
		return "pdoc".equalsIgnoreCase(searchWhat);
	}

	public boolean isPickingStateHold() {
		return "Hold".equals(pickingState);
	}

	public boolean isPickingStateOpen() {
		return "Open".equals(pickingState);
	}

	public boolean isPickingStatePacking() {
		return "Packing".equals(pickingState);
	}

	public boolean isPickingStatePicking() {
		return "Picking".equals(pickingState);
	}

	public boolean isPickingStateQC() {
		return "QC".equals(pickingState);
	}

	public boolean isPickingStateShipping() {
		return "Shipping".equals(pickingState);
	}

	public boolean isPickingUnitOrder() {
		return "pickingunit".equalsIgnoreCase(orderBy) || StringHandler.isBlankString(orderBy);
	}

	public boolean isPickingStateOrder() {
		return "pickingState".equalsIgnoreCase(orderBy) || StringHandler.isBlankString(orderBy);
	}

	public boolean isPicklistOrder() {
		return "picklist".equalsIgnoreCase(orderBy);
	}

	public boolean isPicklistSearch() {
		return "picklistId".equalsIgnoreCase(searchWhat);
	}

	public boolean isPickingUnitIdSearch() {
		return "pickingUnitId".equalsIgnoreCase(searchWhat);
	}

	public boolean isPrintIataDotLabels() {
		return uAction.equals("printIataDotLabels");
	}

	public boolean isPrNumberOrder() {
		return "prnumber".equalsIgnoreCase(orderBy);
	}

	public boolean isReceiptSearch() {
		return "receiptId".equalsIgnoreCase(searchWhat);
	}

	public boolean isSearchTypeContains() {
		return "contains".equalsIgnoreCase(searchType);
	}

	public boolean isSearchTypeEndsWith() {
		return "endswith".equalsIgnoreCase(searchType);
	}

	public boolean isSearchTypeIs() {
		return "is".equalsIgnoreCase(searchType);
	}

	public boolean isSearchTypeStartsWith() {
		return "startswith".equalsIgnoreCase(searchType);
	}

	public boolean isShipToLocationOrder() {
		return "shiptolocation".equalsIgnoreCase(orderBy);
	}

	public boolean isUpdateDotOverride() {
		return "UpdateDotOverride".equals(getuAction());
	}
	
	public boolean isPrintStraightBOL() {
		return "printStraightBOL".equalsIgnoreCase(getuAction());
	}
	
	public boolean isPrintSampleDeliveryLabel() {
		return "printSampleDeliveryLabel".equalsIgnoreCase(getuAction());
	}

	public void setFilterDate(Date filterDate) {
		this.filterDate = filterDate;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setLabelPrintQty(BigDecimal labelPrintQty) {
		this.labelPrintQty = labelPrintQty;
	}

	public void setLabelPrintType(String labelPrintType) {
		this.labelPrintType = labelPrintType;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setPickCreationFromDate(Date pickCreationFromDate) {
		this.pickCreationFromDate = pickCreationFromDate;
	}

	public void setPickCreationToDate(Date pickCreationToDate) {
		this.pickCreationToDate = pickCreationToDate;
	}

	public void setPickingGroupId(BigDecimal pickingGroupId) {
		this.pickingGroupId = pickingGroupId;
	}

	public void setPickingState(String pickingState) {
		this.pickingState = pickingState;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setSearchWhat(String searchWhat) {
		this.searchWhat = searchWhat;
	}
	
	public void setDotOverride(String dotOverride) {
		this.dotOverride = dotOverride;
	}
	
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public String getTabletShipmentId() {
		return tabletShipmentId;
	}

	public void setTabletShipmentId(String tabletShipmentId) {
		this.tabletShipmentId = tabletShipmentId;
	}
}
