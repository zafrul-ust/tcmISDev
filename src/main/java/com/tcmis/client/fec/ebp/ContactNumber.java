/*
 * ContactNumber.java
 *
 * Created on June 13, 2005, 5:38 PM
 */

package com.tcmis.client.fec.ebp;

import java.io.Serializable;
/**
 *
 * @author  mike.najera
 */
public class ContactNumber implements Serializable {
   
   private String number;   
   private String type;
   
   /** Creates a new instance of ContactNumber */
   public ContactNumber() {
   }
   
   /** Getter for property number.
    * @return Value of property number.
    *
    */
   public String getNumber() {
      return number;
   }
   
   /** Setter for property number.
    * @param number New value of property number.
    *
    */
   public void setNumber(String number) {
      this.number = number;
   }
   
   /** Getter for property type.
    * @return Value of property type.
    *
    */
   public String getType() {
      return type;
   }
   
   /** Setter for property type.
    * @param type New value of property type.
    *
    */
   public void setType(String type) {
      this.type = type;
   }
   
}
