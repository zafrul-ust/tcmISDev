package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class PickReceipt extends BaseDataBean {
	private BigDecimal			pickQuantity;
	private PickValidation	pickValidation;
	private PickValidation	qcValidation;
	private BigDecimal			receiptId;
	private BigDecimal      issueId;
	private static BigDecimal ZERO = new BigDecimal(0);

	public BigDecimal getPickQuantity() {
		return this.pickQuantity;
	}

	public PickValidation getPickValidation() {
		return this.pickValidation;
	}

	public PickValidation getQcValidation() {
		return this.qcValidation;
	}

	public BigDecimal getReceiptId() {
		return this.receiptId;
	}

	public boolean hasPickQuantity() {
		return pickQuantity != null;
	}

	public boolean hasPickValidation() {
		return pickValidation != null;
	}

	public boolean hasQCValidation() {
		return qcValidation != null;
	}

	public boolean hasReceiptId() {
		return receiptId != null;
	}
	
	public boolean hasIssueId() {
		return issueId != null;
	}

	public void setPickQuantity(BigDecimal pickQuantity) {
		this.pickQuantity = pickQuantity;
	}

	public void setPickValidation(PickValidation pickValidation) {
		this.pickValidation = pickValidation;
	}

	public void setQcValidation(PickValidation qcValidation) {
		this.qcValidation = qcValidation;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public boolean isValid() {
		if (hasReceiptId() && hasPickQuantity()) {
			if (isPickQuantityGreaterThanZero()) {
				if (hasPickValidation() && hasQCValidation()) {
					return true;
				}
			}
			else {
				return true;
			}
		}
		return  false;
	}
	
	public boolean isPickQuantityGreaterThanZero () {
		return pickQuantity.compareTo(ZERO) > 0;
	}
	
	public String getInvalidMessage() {
		return "Invalid Receipt Object(" + receiptId + "): must have receiptId, pickQuantity, pickValidation & qcValidation";
	}

	public BigDecimal getIssueId() {
		return issueId;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
}
