package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VvUseApprovalOrderQtyRuleBean <br>
 * @version: 1.0, Feb 14, 2006 <br>
 *****************************************************************************/

public class VvUseApprovalOrderQtyRuleBean
 extends BaseDataBean {

 private String orderQuantityRule;
 private String orderQuantityRuleDesc;

 //constructor
 public VvUseApprovalOrderQtyRuleBean() {
 }

 //setters
 public void setOrderQuantityRule(String orderQuantityRule) {
	this.orderQuantityRule = orderQuantityRule;
 }

 public void setOrderQuantityRuleDesc(String orderQuantityRuleDesc) {
	this.orderQuantityRuleDesc = orderQuantityRuleDesc;
 }

 //getters
 public String getOrderQuantityRule() {
	return orderQuantityRule;
 }

 public String getOrderQuantityRuleDesc() {
	return orderQuantityRuleDesc;
 }
}