/*
 * ReferenceCoded.java
 *
 * Created on July 15, 2005, 10:38 AM
 */

package com.tcmis.client.fec.ebp;

import java.io.Serializable;

/**
 *
 * @author  mike.najera
 */
public class ReferenceCoded implements Serializable {
   
   private String type;
   
   private String value;
   
   public static final String VENDOR_PO = "ZZ_VENDOR_PO";
   public static final String PO_LINE   = "ZZ_VEND_PO_LINE";
   public static final String MATERIAL  = "ZZ_FE_MATERIAL";
   public static final String PACKAGING = "ZZ_FE_MAT_DESC";
   
   /** Creates a new instance of ReferenceCoded */
   public ReferenceCoded() {
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
   
   /** Getter for property value.
    * @return Value of property value.
    *
    */
   public String getValue() {
      return value;
   }
   
   /** Setter for property value.
    * @param value New value of property value.
    *
    */
   public void setValue(String value) {
      this.value = value;
   }
   
}
