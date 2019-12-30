package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class PrintInput extends BaseDataBean {

	private Integer						boxCount;
	private String						id;
	private BigDecimal					personnelId;
	private Integer						pickingUnitId;
	private Integer						printerId;
	private Integer						printQuantity	= 1;
	private String						printType;
	private Collection<QuantityReceipt>	quantityReceipts;
	private BigDecimal					receiptId;
	private boolean						skipKitCaseQty;
	private String						tabletShipmentId;

	// constructor
	public PrintInput() {
	}

	public Integer getBoxCount() {
		return this.boxCount;
	}

	public String getId() {
		return this.id;
	}

	public BigDecimal getPersonnelId() {
		return this.personnelId;
	}

	public Integer getPickingUnitId() {
		return this.pickingUnitId;
	}

	public Integer getPrinterId() {
		return this.printerId;
	}

	public Integer getPrintQuantity() {
		return this.printQuantity;
	}

	public String getPrintType() {
		return this.printType;
	}

	public Collection<QuantityReceipt> getQuantityReceipts() {
		return quantityReceipts;
	}

	public BigDecimal getReceiptId() {
		return this.receiptId;
	}

	public String getTabletShipmentId() {
		return this.tabletShipmentId;
	}

	public boolean hasBoxCount() {
		return boxCount != null;
	}

	public boolean hasPickingUnitId () {
		return pickingUnitId != null;
	}
	
	public boolean hasPrinterId() {
		return printerId != null;
	}
	
	public boolean hasReceiptId () {
		return receiptId != null;
	}

	public boolean hasTabletShipmentId () {
		return StringUtils.isNotBlank(tabletShipmentId);
	}

	public boolean isPrintCerts() {
		return "Certs".equals(printType);
	}

	public boolean isPrintConsolidatedBOL() {
		return "BOL-Consolidated".equals(printType);
	}

	public boolean isPrintContainerLabels() {
		return "ContainerLabels".equals(printType);
	}

	public boolean isPrintDeliveryLabels() {
		return "DeliveryLabels".equals(printType);
	}

	public boolean isPrintDeliveryTicket() {
		return "DeliveryTicket".equals(printType);
	}

	public boolean isPrintDOT() {
		return "DOT".equals(printType);
	}

	public boolean isPrintHoldSheet() {
		return "HoldSheet".equals(printType);
	}

	public boolean isPrintIATA() {
		return "IATA".equals(printType);
	}

	public boolean isPrintLongBOL() {
		return "BOL-Long".equals(printType);
	}

	public boolean isPrintPackingList() {
		return "PackingList".equals(printType);
	}

	public boolean isPrintPVR() {
		return "PVR".equals(printType);
	}

	public boolean isPrintQVR() {
		return "QVR".equals(printType);
	}

	public boolean isPrintShippingLabels() {
		return "ShipmentLabels".equals(printType);
	}

	public boolean isPrintShortBOL() {
		return "BOL-Short".equals(printType);
	}

	public boolean isPrintStraightBOL() {
		return "BOL-Straight".equals(printType);
	}

	public boolean isSkipKitCaseQty() {
		return skipKitCaseQty;
	}
	
	public boolean isValid() {
		if (StringUtils.isBlank(printType)) {
			return false;
		}
		else if ((isPrintStraightBOL() || isPrintPackingList()) && !hasTabletShipmentId()) {
			return false;
		}
		else if (isPrintQVR() && !hasReceiptId()) {
			return false;
		}
		else if (isPrintShippingLabels() && (!hasTabletShipmentId() && !hasPickingUnitId())) {
			return false;			
		}
		else if ((!isPrintStraightBOL() && !isPrintQVR() && !isPrintPackingList() && !isPrintShippingLabels()) && !hasPickingUnitId()) {
			return false;
		}
		return true;
	}

	public void setBoxCount(Integer boxCount) {
		this.boxCount = boxCount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setPickingUnitId(Integer pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public void setPrinterId(Integer printerId) {
		this.printerId = printerId;
	}

	public void setPrintQuantity(Integer printQuantity) {
		this.printQuantity = printQuantity;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public void setQuantityReceipts(Collection<QuantityReceipt> quantityReceipts) {
		this.quantityReceipts = quantityReceipts;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setSkipKitCaseQty(boolean skipKitCaseQty) {
		this.skipKitCaseQty = skipKitCaseQty;
	}

	public void setTabletShipmentId(String tabletShipmentId) {
		this.tabletShipmentId = tabletShipmentId;
	}

}