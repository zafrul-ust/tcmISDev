package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

@SuppressWarnings("serial")
public class OverrideApprovalBean extends BaseInputBean {

	private String uAction;
	private boolean labelOverride;
	private Date labelOverrideDate;
	private BigDecimal labelOverrideApprover;
	private String labelOverrideApproverName;
	private boolean packerOverride;
	private Date packerOverrideDate;
	private BigDecimal packerOverrideApprover;
	private String packerOverrideApproverName;
	private boolean qtyOverride;
	private Date qtyOverrideDate;
	private BigDecimal qtyOverrideApprover;
	private String qtyOverrideApproverName;
	private String overrideNote;
	private BigDecimal pickingUnitId;
	
	private String pickingState;
	private boolean changeRid;
	private BigDecimal toRid;
	private Collection validRids;
//	private BigDecimal[] validRids;
	private boolean packQtyOverride;
	private BigDecimal overrideQty;
// incoming rid and issue id	
	private BigDecimal rid;
	
	private String allowRidOverride;
	private String allowPackerQtyOverride;
	private BigDecimal issueId;

	public String getAllowRidOverride() {
		return allowRidOverride;
	}

	public void setAllowRidOverride(String allowRidOverride) {
		this.allowRidOverride = allowRidOverride;
	}

	public String getAllowPackerQtyOverride() {
		return allowPackerQtyOverride;
	}

	public void setAllowPackerQtyOverride(String allowPackerQtyOverride) {
		this.allowPackerQtyOverride = allowPackerQtyOverride;
	}

	
	public String getPickingState() {
		return pickingState;
	}

	public void setPickingState(String pickingState) {
		this.pickingState = pickingState;
	}

	public BigDecimal getRid() {
		return rid;
	}

	public void setRid(BigDecimal rid) {
		this.rid = rid;
	}

	public boolean isChangeRid() {
		return changeRid;
	}

	public void setChangeRid(boolean changeRid) {
		this.changeRid = changeRid;
	}

	public BigDecimal getToRid() {
		return toRid;
	}

	public void setToRid(BigDecimal toRid) {
		this.toRid = toRid;
	}

	public Collection getValidRids() {
		return validRids;
	}

	public void setValidRids(Collection validRids) {
		this.validRids = validRids;
	}

	public boolean isPackQtyOverride() {
		return packQtyOverride;
	}

	public void setPackQtyOverride(boolean packQtyOverride) {
		this.packQtyOverride = packQtyOverride;
	}

	public BigDecimal getOverrideQty() {
		return overrideQty;
	}

	public void setOverrideQty(BigDecimal overrideQty) {
		this.overrideQty = overrideQty;
	}

	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public boolean isLabelOverride() {
		return labelOverride;
	}

	public void setLabelOverride(boolean labelOverride) {
		this.labelOverride = labelOverride;
	}

	public Date getLabelOverrideDate() {
		return labelOverrideDate;
	}

	public void setLabelOverrideDate(Date labelOverrideDate) {
		this.labelOverrideDate = labelOverrideDate;
	}

	public BigDecimal getLabelOverrideApprover() {
		return labelOverrideApprover;
	}

	public void setLabelOverrideApprover(BigDecimal labelOverrideApprover) {
		this.labelOverrideApprover = labelOverrideApprover;
	}

	public String getLabelOverrideApproverName() {
		return labelOverrideApproverName;
	}

	public void setLabelOverrideApproverName(String labelOverrideApproverName) {
		this.labelOverrideApproverName = labelOverrideApproverName;
	}

	public boolean isPackerOverride() {
		return packerOverride;
	}

	public void setPackerOverride(boolean packerOverride) {
		this.packerOverride = packerOverride;
	}

	public Date getPackerOverrideDate() {
		return packerOverrideDate;
	}

	public void setPackerOverrideDate(Date packerOverrideDate) {
		this.packerOverrideDate = packerOverrideDate;
	}

	public BigDecimal getPackerOverrideApprover() {
		return packerOverrideApprover;
	}

	public void setPackerOverrideApprover(BigDecimal packerOverrideApprover) {
		this.packerOverrideApprover = packerOverrideApprover;
	}

	public String getPackerOverrideApproverName() {
		return packerOverrideApproverName;
	}

	public void setPackerOverrideApproverName(String packerOverrideApproverName) {
		this.packerOverrideApproverName = packerOverrideApproverName;
	}

	public boolean isQtyOverride() {
		return qtyOverride;
	}

	public void setQtyOverride(boolean qtyOverride) {
		this.qtyOverride = qtyOverride;
	}

	public Date getQtyOverrideDate() {
		return qtyOverrideDate;
	}

	public void setQtyOverrideDate(Date qtyOverrideDate) {
		this.qtyOverrideDate = qtyOverrideDate;
	}

	public BigDecimal getQtyOverrideApprover() {
		return qtyOverrideApprover;
	}

	public void setQtyOverrideApprover(BigDecimal qtyOverrideApprover) {
		this.qtyOverrideApprover = qtyOverrideApprover;
	}

	public String getQtyOverrideApproverName() {
		return qtyOverrideApproverName;
	}

	public void setQtyOverrideApproverName(String qtyOverrideApproverName) {
		this.qtyOverrideApproverName = qtyOverrideApproverName;
	}

	public String getOverrideNote() {
		return overrideNote;
	}

	public void setOverrideNote(String overrideNote) {
		this.overrideNote = overrideNote;
	}

	public BigDecimal getPickingUnitId() {
		return pickingUnitId;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}

	public BigDecimal getIssueId() {
		return issueId;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
}
