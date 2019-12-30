/*
 * OrderSummary.java
 *
 * Created on June 13, 2005, 3:34 PM
 */

package com.tcmis.client.fec.ebp;

import java.io.Serializable;

/**
 *
 * @author  mike.najera
 */
public class OrderSummary implements Serializable {
   
   private String lineCount;
   private String totalAmount;
   
   /** Creates a new instance of OrderSummary */
   public OrderSummary() {
      lineCount = "";
      totalAmount = "";
   }
   
   /** Getter for property lineCount.
    * @return Value of property lineCount.
    *
    */
   public String getLineCount() {
      return lineCount;
   }
   
   /** Setter for property lineCount.
    * @param lineCount New value of property lineCount.
    *
    */
   public void setLineCount(String lineCount) {
      this.lineCount = lineCount;
   }
   
   /** Getter for property totalAmount.
    * @return Value of property totalAmount.
    *
    */
   public String getTotalAmount() {
      return totalAmount;
   }
   
   /** Setter for property totalAmount.
    * @param totalAmount New value of property totalAmount.
    *
    */
   public void setTotalAmount(String totalAmount) {
      this.totalAmount = totalAmount;
   }
   
}
