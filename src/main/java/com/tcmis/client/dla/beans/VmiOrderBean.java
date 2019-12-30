/*
 * VmiOrderBean.java
 *
 * Created on April 23, 2008, 12:49 PM
 */

package com.tcmis.client.dla.beans;

import java.io.Serializable;

/**
 *
 * @author  mnajera
 */
public class VmiOrderBean implements Serializable {
   
   private String companyId;
   private String transactionType;
   private String lineSeq;
   private String changeOrderSeq;
   private String orderQty;
   private String customerPO;
   private String poLine;
   private String unitOfSale;
   
   /** Creates a new instance of VmiOrderBean */
   public VmiOrderBean(String companyId, String transactionType, String customerPO, String poLine, String lineSeq, String changeOrderSeq, String orderQty, String unitOfSale) {
      this.companyId = companyId;
      this.transactionType = transactionType;
      this.customerPO = customerPO;
      this.poLine = poLine;
      this.lineSeq = lineSeq;
      this.changeOrderSeq = changeOrderSeq;
      this.orderQty = orderQty;
      this.unitOfSale = unitOfSale;
   }
   
   /**
    * Getter for property companyId.
    * @return Value of property companyId.
    */
   public java.lang.String getCompanyId() {
      return companyId;
   }
   
   /**
    * Setter for property companyId.
    * @param companyId New value of property companyId.
    */
   public void setCompanyId(java.lang.String companyId) {
      this.companyId = companyId;
   }
   
   /**
    * Getter for property transactionType.
    * @return Value of property transactionType.
    */
   public java.lang.String getTransactionType() {
      return transactionType;
   }
   
   /**
    * Setter for property transactionType.
    * @param transactionType New value of property transactionType.
    */
   public void setTransactionType(java.lang.String transactionType) {
      this.transactionType = transactionType;
   }
   
   /**
    * Getter for property lineSeq.
    * @return Value of property lineSeq.
    */
   public java.lang.String getLineSeq() {
      return lineSeq;
   }
   
   /**
    * Setter for property lineSeq.
    * @param lineSeq New value of property lineSeq.
    */
   public void setLineSeq(java.lang.String lineSeq) {
      this.lineSeq = lineSeq;
   }
   
   /**
    * Getter for property changeOrderSeq.
    * @return Value of property changeOrderSeq.
    */
   public java.lang.String getChangeOrderSeq() {
      return changeOrderSeq;
   }
   
   /**
    * Setter for property changeOrderSeq.
    * @param changeOrderSeq New value of property changeOrderSeq.
    */
   public void setChangeOrderSeq(java.lang.String changeOrderSeq) {
      this.changeOrderSeq = changeOrderSeq;
   }
   
   /**
    * Getter for property orderQty.
    * @return Value of property orderQty.
    */
   public java.lang.String getOrderQty() {
      return orderQty;
   }
   
   /**
    * Setter for property orderQty.
    * @param orderQty New value of property orderQty.
    */
   public void setOrderQty(java.lang.String orderQty) {
      this.orderQty = orderQty;
   }
   
   /**
    * Getter for property customerPO.
    * @return Value of property customerPO.
    */
   public java.lang.String getCustomerPO() {
      return customerPO;
   }
   
   /**
    * Setter for property customerPO.
    * @param customerPO New value of property customerPO.
    */
   public void setCustomerPO(java.lang.String customerPO) {
      this.customerPO = customerPO;
   }
   
   /**
    * Getter for property poLine.
    * @return Value of property poLine.
    */
   public java.lang.String getPoLine() {
      return poLine;
   }
   
   /**
    * Setter for property poLine.
    * @param poLine New value of property poLine.
    */
   public void setPoLine(java.lang.String poLine) {
      this.poLine = poLine;
   }
   
   /**
    * Getter for property unitOfSale.
    * @return Value of property unitOfSale.
    */
   public java.lang.String getUnitOfSale() {
      return unitOfSale;
   }
   
   /**
    * Setter for property unitOfSale.
    * @param unitOfSale New value of property unitOfSale.
    */
   public void setUnitOfSale(java.lang.String unitOfSale) {
      this.unitOfSale = unitOfSale;
   }
   
}
