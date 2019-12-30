package com.tcmis.internal.hub.beans;

import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Inventory Group Bean <br>
 * @version: 1.0, Nov 5, 2012 <br>
 *****************************************************************************/


public class UserIgCompanyOwnerSegmentBean extends BaseDataBean
{

	private String inventoryGroup;
    private String inventoryGroupName;
    private String companyId;
    private String companyName;
    private String ownerSegmentId;
    private String ownerSegmentDesc;
    private BigDecimal personnelId;
    private String hub;
    private String userGroupId;
    private Collection companyColl;
    private Collection ownerSegmentColl;

    //constructor
	public UserIgCompanyOwnerSegmentBean() {
	}

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public String getInventoryGroupName() {
        return inventoryGroupName;
    }

    public void setInventoryGroupName(String inventoryGroupName) {
        this.inventoryGroupName = inventoryGroupName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOwnerSegmentId() {
        return ownerSegmentId;
    }

    public void setOwnerSegmentId(String ownerSegmentId) {
        this.ownerSegmentId = ownerSegmentId;
    }

    public String getOwnerSegmentDesc() {
        return ownerSegmentDesc;
    }

    public void setOwnerSegmentDesc(String ownerSegmentDesc) {
        this.ownerSegmentDesc = ownerSegmentDesc;
    }

    public BigDecimal getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(BigDecimal personnelId) {
        this.personnelId = personnelId;
    }

    public String getHub() {
        return hub;
    }

    public void setHub(String hub) {
        this.hub = hub;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Collection getCompanyColl() {
        return companyColl;
    }

    public void setCompanyColl(Collection companyColl) {
        this.companyColl = companyColl;
    }

    public Collection getOwnerSegmentColl() {
        return ownerSegmentColl;
    }

    public void setOwnerSegmentColl(Collection ownerSegmentColl) {
        this.ownerSegmentColl = ownerSegmentColl;
    }
}