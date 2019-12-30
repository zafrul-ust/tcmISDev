package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ChargeNumberApprovalDetailBean <br>
 * @version: 1.0, Jan 31, 2012 <br>
 *****************************************************************************/


public class ChargeNumberApprovalDetailBean extends BaseDataBean {

	private BigDecimal prNumber;
	private String lineItem;
    private String accountNumber;
    private String accountNumber2;
    private String accountNumber3;
    private String accountNumber4;
    private BigDecimal percentage;
    private String accountSysId;
    private BigDecimal noLevel1ChgAcctApprover;
    private BigDecimal noLevel2ChgAcctApprover;
    private BigDecimal noLevel3ChgAcctApprover;
    private BigDecimal noLevel4ChgAcctApprover;
    private String approveByPrice;
    private BigDecimal acctSysApproverId;
    private String chargeType;
    private String chargeNumber1;
    private String chargeNumber2;
    private String chargeNumber3;
    private String chargeNumber4;
    private String accountApproverName1;
    private String accountApproverName2;
    private String accountApprovalStatus;
    private Date accountApprovalDate;
    private BigDecimal approvalLevel1Limit;
    private String approverLevel1Name1;
    private String level1ApprovalStatus1;
    private Date approvalLevel1ApprDate1;
    private String approverLevel1Name2;
    private String level1ApprovalStatus2;
    private Date approvalLevel1ApprDate2;
    private BigDecimal approvalLevel2Limit;
    private String approverLevel2Name1;
    private String level2ApprovalStatus1;
    private Date approvalLevel2ApprDate1;
    private String approverLevel2Name2;
    private String level2ApprovalStatus2;
    private Date approvalLevel2ApprDate2;
    private BigDecimal approvalLevel3Limit;
    private String approverLevel3Name1;
    private String level3ApprovalStatus1;
    private Date approvalLevel3ApprDate1;
    private String approverLevel3Name2;
    private String level3ApprovalStatus2;
    private Date approvalLevel3ApprDate2;
    private BigDecimal approvalLevel4Limit;
    private String approverLevel4Name1;
    private String level4ApprovalStatus1;
    private Date approvalLevel4ApprDate1;
    private String approverLevel4Name2;
    private String level4ApprovalStatus2;
    private Date approvalLevel4ApprDate2;
    private String approvalLimitCurrencyId;
    private String accountApproverEmail1;
    private String accountApproverPhone1;
    private String accountApproverEmail2;
    private String accountApproverPhone2;
    private String approverLevel1Email1;
    private String approverLevel1Phone1;
    private String approverLevel1Email2;
    private String approverLevel1Phone2;
    private String approverLevel2Email1;
    private String approverLevel2Phone1;
    private String approverLevel2Email2;
    private String approverLevel2Phone2;
    private String approverLevel3Email1;
    private String approverLevel3Phone1;
    private String approverLevel3Email2;
    private String approverLevel3Phone2;
    private String approverLevel4Email1;
    private String approverLevel4Phone1;
    private String approverLevel4Email2;
    private String approverLevel4Phone2;
    private String chargeField1Control;
    private String chargeField2Control;
    private String chargeField3Control;
    private String chargeField4Control;
    private String accountApproverLabel;

    //constructor
	public ChargeNumberApprovalDetailBean() {
	}

    public BigDecimal getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(BigDecimal prNumber) {
        this.prNumber = prNumber;
    }

    public String getLineItem() {
        return lineItem;
    }

    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber2() {
        return accountNumber2;
    }

    public void setAccountNumber2(String accountNumber2) {
        this.accountNumber2 = accountNumber2;
    }

    public String getAccountNumber3() {
        return accountNumber3;
    }

    public void setAccountNumber3(String accountNumber3) {
        this.accountNumber3 = accountNumber3;
    }

    public String getAccountNumber4() {
        return accountNumber4;
    }

    public void setAccountNumber4(String accountNumber4) {
        this.accountNumber4 = accountNumber4;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getAccountSysId() {
        return accountSysId;
    }

    public void setAccountSysId(String accountSysId) {
        this.accountSysId = accountSysId;
    }

    public BigDecimal getNoLevel1ChgAcctApprover() {
        return noLevel1ChgAcctApprover;
    }

    public void setNoLevel1ChgAcctApprover(BigDecimal noLevel1ChgAcctApprover) {
        this.noLevel1ChgAcctApprover = noLevel1ChgAcctApprover;
    }

    public BigDecimal getNoLevel2ChgAcctApprover() {
        return noLevel2ChgAcctApprover;
    }

    public void setNoLevel2ChgAcctApprover(BigDecimal noLevel2ChgAcctApprover) {
        this.noLevel2ChgAcctApprover = noLevel2ChgAcctApprover;
    }

    public BigDecimal getNoLevel3ChgAcctApprover() {
        return noLevel3ChgAcctApprover;
    }

    public void setNoLevel3ChgAcctApprover(BigDecimal noLevel3ChgAcctApprover) {
        this.noLevel3ChgAcctApprover = noLevel3ChgAcctApprover;
    }

    public BigDecimal getNoLevel4ChgAcctApprover() {
        return noLevel4ChgAcctApprover;
    }

    public void setNoLevel4ChgAcctApprover(BigDecimal noLevel4ChgAcctApprover) {
        this.noLevel4ChgAcctApprover = noLevel4ChgAcctApprover;
    }

    public String getApproveByPrice() {
        return approveByPrice;
    }

    public void setApproveByPrice(String approveByPrice) {
        this.approveByPrice = approveByPrice;
    }

    public BigDecimal getAcctSysApproverId() {
        return acctSysApproverId;
    }

    public void setAcctSysApproverId(BigDecimal acctSysApproverId) {
        this.acctSysApproverId = acctSysApproverId;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChargeNumber1() {
        return chargeNumber1;
    }

    public void setChargeNumber1(String chargeNumber1) {
        this.chargeNumber1 = chargeNumber1;
    }

    public String getChargeNumber2() {
        return chargeNumber2;
    }

    public void setChargeNumber2(String chargeNumber2) {
        this.chargeNumber2 = chargeNumber2;
    }

    public String getChargeNumber3() {
        return chargeNumber3;
    }

    public void setChargeNumber3(String chargeNumber3) {
        this.chargeNumber3 = chargeNumber3;
    }

    public String getChargeNumber4() {
        return chargeNumber4;
    }

    public void setChargeNumber4(String chargeNumber4) {
        this.chargeNumber4 = chargeNumber4;
    }

    public String getAccountApproverName1() {
        return accountApproverName1;
    }

    public void setAccountApproverName1(String accountApproverName1) {
        this.accountApproverName1 = accountApproverName1;
    }

    public String getAccountApproverName2() {
        return accountApproverName2;
    }

    public void setAccountApproverName2(String accountApproverName2) {
        this.accountApproverName2 = accountApproverName2;
    }

    public String getAccountApprovalStatus() {
        return accountApprovalStatus;
    }

    public void setAccountApprovalStatus(String accountApprovalStatus) {
        this.accountApprovalStatus = accountApprovalStatus;
    }

    public Date getAccountApprovalDate() {
        return accountApprovalDate;
    }

    public void setAccountApprovalDate(Date accountApprovalDate) {
        this.accountApprovalDate = accountApprovalDate;
    }

    public BigDecimal getApprovalLevel1Limit() {
        return approvalLevel1Limit;
    }

    public void setApprovalLevel1Limit(BigDecimal approvalLevel1Limit) {
        this.approvalLevel1Limit = approvalLevel1Limit;
    }

    public String getApproverLevel1Name1() {
        return approverLevel1Name1;
    }

    public void setApproverLevel1Name1(String approverLevel1Name1) {
        this.approverLevel1Name1 = approverLevel1Name1;
    }

    public String getLevel1ApprovalStatus1() {
        return level1ApprovalStatus1;
    }

    public void setLevel1ApprovalStatus1(String level1ApprovalStatus1) {
        this.level1ApprovalStatus1 = level1ApprovalStatus1;
    }

    public Date getApprovalLevel1ApprDate1() {
        return approvalLevel1ApprDate1;
    }

    public void setApprovalLevel1ApprDate1(Date approvalLevel1ApprDate1) {
        this.approvalLevel1ApprDate1 = approvalLevel1ApprDate1;
    }

    public String getApproverLevel1Name2() {
        return approverLevel1Name2;
    }

    public void setApproverLevel1Name2(String approverLevel1Name2) {
        this.approverLevel1Name2 = approverLevel1Name2;
    }

    public String getLevel1ApprovalStatus2() {
        return level1ApprovalStatus2;
    }

    public void setLevel1ApprovalStatus2(String level1ApprovalStatus2) {
        this.level1ApprovalStatus2 = level1ApprovalStatus2;
    }

    public Date getApprovalLevel1ApprDate2() {
        return approvalLevel1ApprDate2;
    }

    public void setApprovalLevel1ApprDate2(Date approvalLevel1ApprDate2) {
        this.approvalLevel1ApprDate2 = approvalLevel1ApprDate2;
    }

    public BigDecimal getApprovalLevel2Limit() {
        return approvalLevel2Limit;
    }

    public void setApprovalLevel2Limit(BigDecimal approvalLevel2Limit) {
        this.approvalLevel2Limit = approvalLevel2Limit;
    }

    public String getApproverLevel2Name1() {
        return approverLevel2Name1;
    }

    public void setApproverLevel2Name1(String approverLevel2Name1) {
        this.approverLevel2Name1 = approverLevel2Name1;
    }

    public String getLevel2ApprovalStatus1() {
        return level2ApprovalStatus1;
    }

    public void setLevel2ApprovalStatus1(String level2ApprovalStatus1) {
        this.level2ApprovalStatus1 = level2ApprovalStatus1;
    }

    public Date getApprovalLevel2ApprDate1() {
        return approvalLevel2ApprDate1;
    }

    public void setApprovalLevel2ApprDate1(Date approvalLevel2ApprDate1) {
        this.approvalLevel2ApprDate1 = approvalLevel2ApprDate1;
    }

    public String getApproverLevel2Name2() {
        return approverLevel2Name2;
    }

    public void setApproverLevel2Name2(String approverLevel2Name2) {
        this.approverLevel2Name2 = approverLevel2Name2;
    }

    public String getLevel2ApprovalStatus2() {
        return level2ApprovalStatus2;
    }

    public void setLevel2ApprovalStatus2(String level2ApprovalStatus2) {
        this.level2ApprovalStatus2 = level2ApprovalStatus2;
    }

    public Date getApprovalLevel2ApprDate2() {
        return approvalLevel2ApprDate2;
    }

    public void setApprovalLevel2ApprDate2(Date approvalLevel2ApprDate2) {
        this.approvalLevel2ApprDate2 = approvalLevel2ApprDate2;
    }

    public BigDecimal getApprovalLevel3Limit() {
        return approvalLevel3Limit;
    }

    public void setApprovalLevel3Limit(BigDecimal approvalLevel3Limit) {
        this.approvalLevel3Limit = approvalLevel3Limit;
    }

    public String getApproverLevel3Name1() {
        return approverLevel3Name1;
    }

    public void setApproverLevel3Name1(String approverLevel3Name1) {
        this.approverLevel3Name1 = approverLevel3Name1;
    }

    public String getLevel3ApprovalStatus1() {
        return level3ApprovalStatus1;
    }

    public void setLevel3ApprovalStatus1(String level3ApprovalStatus1) {
        this.level3ApprovalStatus1 = level3ApprovalStatus1;
    }

    public Date getApprovalLevel3ApprDate1() {
        return approvalLevel3ApprDate1;
    }

    public void setApprovalLevel3ApprDate1(Date approvalLevel3ApprDate1) {
        this.approvalLevel3ApprDate1 = approvalLevel3ApprDate1;
    }

    public String getApproverLevel3Name2() {
        return approverLevel3Name2;
    }

    public void setApproverLevel3Name2(String approverLevel3Name2) {
        this.approverLevel3Name2 = approverLevel3Name2;
    }

    public String getLevel3ApprovalStatus2() {
        return level3ApprovalStatus2;
    }

    public void setLevel3ApprovalStatus2(String level3ApprovalStatus2) {
        this.level3ApprovalStatus2 = level3ApprovalStatus2;
    }

    public Date getApprovalLevel3ApprDate2() {
        return approvalLevel3ApprDate2;
    }

    public void setApprovalLevel3ApprDate2(Date approvalLevel3ApprDate2) {
        this.approvalLevel3ApprDate2 = approvalLevel3ApprDate2;
    }

    public BigDecimal getApprovalLevel4Limit() {
        return approvalLevel4Limit;
    }

    public void setApprovalLevel4Limit(BigDecimal approvalLevel4Limit) {
        this.approvalLevel4Limit = approvalLevel4Limit;
    }

    public String getApproverLevel4Name1() {
        return approverLevel4Name1;
    }

    public void setApproverLevel4Name1(String approverLevel4Name1) {
        this.approverLevel4Name1 = approverLevel4Name1;
    }

    public String getLevel4ApprovalStatus1() {
        return level4ApprovalStatus1;
    }

    public void setLevel4ApprovalStatus1(String level4ApprovalStatus1) {
        this.level4ApprovalStatus1 = level4ApprovalStatus1;
    }

    public Date getApprovalLevel4ApprDate1() {
        return approvalLevel4ApprDate1;
    }

    public void setApprovalLevel4ApprDate1(Date approvalLevel4ApprDate1) {
        this.approvalLevel4ApprDate1 = approvalLevel4ApprDate1;
    }

    public String getApproverLevel4Name2() {
        return approverLevel4Name2;
    }

    public void setApproverLevel4Name2(String approverLevel4Name2) {
        this.approverLevel4Name2 = approverLevel4Name2;
    }

    public String getLevel4ApprovalStatus2() {
        return level4ApprovalStatus2;
    }

    public void setLevel4ApprovalStatus2(String level4ApprovalStatus2) {
        this.level4ApprovalStatus2 = level4ApprovalStatus2;
    }

    public Date getApprovalLevel4ApprDate2() {
        return approvalLevel4ApprDate2;
    }

    public void setApprovalLevel4ApprDate2(Date approvalLevel4ApprDate2) {
        this.approvalLevel4ApprDate2 = approvalLevel4ApprDate2;
    }

    public String getApprovalLimitCurrencyId() {
        return approvalLimitCurrencyId;
    }

    public void setApprovalLimitCurrencyId(String approvalLimitCurrencyId) {
        this.approvalLimitCurrencyId = approvalLimitCurrencyId;
    }

    public String getAccountApproverEmail1() {
        return accountApproverEmail1;
    }

    public void setAccountApproverEmail1(String accountApproverEmail1) {
        this.accountApproverEmail1 = accountApproverEmail1;
    }

    public String getAccountApproverPhone1() {
        return accountApproverPhone1;
    }

    public void setAccountApproverPhone1(String accountApproverPhone1) {
        this.accountApproverPhone1 = accountApproverPhone1;
    }

    public String getAccountApproverEmail2() {
        return accountApproverEmail2;
    }

    public void setAccountApproverEmail2(String accountApproverEmail2) {
        this.accountApproverEmail2 = accountApproverEmail2;
    }

    public String getAccountApproverPhone2() {
        return accountApproverPhone2;
    }

    public void setAccountApproverPhone2(String accountApproverPhone2) {
        this.accountApproverPhone2 = accountApproverPhone2;
    }

    public String getApproverLevel1Email1() {
        return approverLevel1Email1;
    }

    public void setApproverLevel1Email1(String approverLevel1Email1) {
        this.approverLevel1Email1 = approverLevel1Email1;
    }

    public String getApproverLevel1Phone1() {
        return approverLevel1Phone1;
    }

    public void setApproverLevel1Phone1(String approverLevel1Phone1) {
        this.approverLevel1Phone1 = approverLevel1Phone1;
    }

    public String getApproverLevel1Email2() {
        return approverLevel1Email2;
    }

    public void setApproverLevel1Email2(String approverLevel1Email2) {
        this.approverLevel1Email2 = approverLevel1Email2;
    }

    public String getApproverLevel1Phone2() {
        return approverLevel1Phone2;
    }

    public void setApproverLevel1Phone2(String approverLevel1Phone2) {
        this.approverLevel1Phone2 = approverLevel1Phone2;
    }

    public String getApproverLevel2Email1() {
        return approverLevel2Email1;
    }

    public void setApproverLevel2Email1(String approverLevel2Email1) {
        this.approverLevel2Email1 = approverLevel2Email1;
    }

    public String getApproverLevel2Phone1() {
        return approverLevel2Phone1;
    }

    public void setApproverLevel2Phone1(String approverLevel2Phone1) {
        this.approverLevel2Phone1 = approverLevel2Phone1;
    }

    public String getApproverLevel2Email2() {
        return approverLevel2Email2;
    }

    public void setApproverLevel2Email2(String approverLevel2Email2) {
        this.approverLevel2Email2 = approverLevel2Email2;
    }

    public String getApproverLevel2Phone2() {
        return approverLevel2Phone2;
    }

    public void setApproverLevel2Phone2(String approverLevel2Phone2) {
        this.approverLevel2Phone2 = approverLevel2Phone2;
    }

    public String getApproverLevel3Email1() {
        return approverLevel3Email1;
    }

    public void setApproverLevel3Email1(String approverLevel3Email1) {
        this.approverLevel3Email1 = approverLevel3Email1;
    }

    public String getApproverLevel3Phone1() {
        return approverLevel3Phone1;
    }

    public void setApproverLevel3Phone1(String approverLevel3Phone1) {
        this.approverLevel3Phone1 = approverLevel3Phone1;
    }

    public String getApproverLevel3Email2() {
        return approverLevel3Email2;
    }

    public void setApproverLevel3Email2(String approverLevel3Email2) {
        this.approverLevel3Email2 = approverLevel3Email2;
    }

    public String getApproverLevel3Phone2() {
        return approverLevel3Phone2;
    }

    public void setApproverLevel3Phone2(String approverLevel3Phone2) {
        this.approverLevel3Phone2 = approverLevel3Phone2;
    }

    public String getApproverLevel4Email1() {
        return approverLevel4Email1;
    }

    public void setApproverLevel4Email1(String approverLevel4Email1) {
        this.approverLevel4Email1 = approverLevel4Email1;
    }

    public String getApproverLevel4Phone1() {
        return approverLevel4Phone1;
    }

    public void setApproverLevel4Phone1(String approverLevel4Phone1) {
        this.approverLevel4Phone1 = approverLevel4Phone1;
    }

    public String getApproverLevel4Email2() {
        return approverLevel4Email2;
    }

    public void setApproverLevel4Email2(String approverLevel4Email2) {
        this.approverLevel4Email2 = approverLevel4Email2;
    }

    public String getApproverLevel4Phone2() {
        return approverLevel4Phone2;
    }

    public void setApproverLevel4Phone2(String approverLevel4Phone2) {
        this.approverLevel4Phone2 = approverLevel4Phone2;
    }

    public String getChargeField1Control() {
        return chargeField1Control;
    }

    public void setChargeField1Control(String chargeField1Control) {
        this.chargeField1Control = chargeField1Control;
    }

    public String getChargeField2Control() {
        return chargeField2Control;
    }

    public void setChargeField2Control(String chargeField2Control) {
        this.chargeField2Control = chargeField2Control;
    }

    public String getChargeField3Control() {
        return chargeField3Control;
    }

    public void setChargeField3Control(String chargeField3Control) {
        this.chargeField3Control = chargeField3Control;
    }

    public String getChargeField4Control() {
        return chargeField4Control;
    }

    public void setChargeField4Control(String chargeField4Control) {
        this.chargeField4Control = chargeField4Control;
    }

    public String getAccountApproverLabel() {
        return accountApproverLabel;
    }

    public void setAccountApproverLabel(String accountApproverLabel) {
        this.accountApproverLabel = accountApproverLabel;
    }
} //end of class