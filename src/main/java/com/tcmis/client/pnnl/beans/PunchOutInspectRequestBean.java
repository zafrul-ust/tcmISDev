/*
 * PunchOutSetupInspectBean.java
 *
 * Created on June 25, 2009, 1:24 PM
 */
package com.tcmis.client.pnnl.beans;


import com.tcmis.client.cxml.beans.PunchOutSetupRequestBean;
/**
 *
 * @author  mnajera
 */
public class PunchOutInspectRequestBean extends PunchOutSetupRequestBean {
   
   private String supplierPartId;
   /** Creates a new instance of PunchOutSetupInspectBean */
   public PunchOutInspectRequestBean() {
   }
   
   /**
    * Getter for property supplierPartId.
    * @return Value of property supplierPartId.
    */
   public String getSupplierPartId() {
      return supplierPartId;
   }
   
   /**
    * Setter for property supplierPartId.
    * @param supplierPartId New value of property supplierPartId.
    */
   public void setSupplierPartId(String supplierPartId) {
      this.supplierPartId = supplierPartId;
   }
   
}
