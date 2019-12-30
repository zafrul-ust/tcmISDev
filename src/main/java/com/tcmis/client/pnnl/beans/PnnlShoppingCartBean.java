/*
 * ShoppingCartBean.java
 *
 * Created on August 4, 2009, 4:32 PM
 */
package com.tcmis.client.pnnl.beans;

import com.tcmis.client.seagate.beans.ShoppingCartViewBean;

/**
 *
 * @author  mnajera
 */
public class PnnlShoppingCartBean extends ShoppingCartViewBean {
   
   private String currencyId;
   private String classification;
   
   /** Creates a new instance of ShoppingCartBean */
   public PnnlShoppingCartBean() {
   }
   
   /**
    * Getter for property currencyId.
    * @return Value of property currencyId.
    */
   public java.lang.String getCurrencyId() {
      return currencyId;
   }
   
   /**
    * Setter for property currencyId.
    * @param currencyId New value of property currencyId.
    */
   public void setCurrencyId(java.lang.String currencyId) {
      this.currencyId = currencyId;
   }
   
   /**
    * Getter for property classification.
    * @return Value of property classification.
    */
   public java.lang.String getClassification() {
      return classification;
   }
   
   /**
    * Setter for property classification.
    * @param classification New value of property classification.
    */
   public void setClassification(java.lang.String classification) {
      this.classification = classification;
   }
   
}
