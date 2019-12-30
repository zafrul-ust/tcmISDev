/*
 * Line.java
 *
 * Created on June 13, 2005, 3:33 PM
 */

package com.tcmis.client.fec.ebp;


import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author  mike.najera
 */
public class Line implements Serializable {
   
   private String lineNum;
   private String partNum;
   private String manufactPartNum;
   private String itemDesc;
   private String quantity;
   private String uom;
   private String recipientId;
   private String unitPrice;
   private String currency;
   private String mrNum;
   private String mrLine;
   private String totalValue;
   private String materialId;
   private String packaging;

   private Vector references;
   
   /** Creates a new instance of Line */
   public Line() {
      references = new Vector();
   }

   public void addReferenceCoded(ReferenceCoded reference) {
      references.add(reference);
   }
   
   
   public void parseReferences() {
      ReferenceCoded ref = null;
      Iterator iter = references.iterator();
      while (iter.hasNext()) {
         ref = (ReferenceCoded) iter.next();
         
         if (ref.getType().equals(ReferenceCoded.VENDOR_PO)) {
            setMrNum(ref.getValue());
         } else if (ref.getType().equals(ReferenceCoded.PO_LINE)) {
            setMrLine(ref.getValue());
         } else if (ref.getType().equals(ReferenceCoded.MATERIAL)) {
            setMaterialId(ref.getValue());
         } else if (ref.getType().equals(ReferenceCoded.PACKAGING)) {
            setPackaging(ref.getValue());
         }
           
      }
      
   }
   
   /** Getter for property lineNum.
    * @return Value of property lineNum.
    *
    */
   public String getLineNum() {
      return lineNum;
   }
   
   /** Setter for property lineNum.
    * @param lineNum New value of property lineNum.
    *
    */
   public void setLineNum(String lineNum) {
      this.lineNum = lineNum;
   }
   
   /** Getter for property partNum.
    * @return Value of property partNum.
    *
    */
   public String getPartNum() {
      return partNum;
   }
   
   /** Setter for property partNum.
    * @param partNum New value of property partNum.
    *
    */
   public void setPartNum(String partNum) {
      this.partNum = partNum;
   }
   
   /** Getter for property manufactPartNum.
    * @return Value of property manufactPartNum.
    *
    */
   public String getManufactPartNum() {
      return manufactPartNum;
   }
   
   /** Setter for property manufactPartNum.
    * @param manufactPartNum New value of property manufactPartNum.
    *
    */
   public void setManufactPartNum(String manufactPartNum) {
      this.manufactPartNum = manufactPartNum;
   }
   
   /** Getter for property itemDesc.
    * @return Value of property itemDesc.
    *
    */
   public String getItemDesc() {
      return itemDesc;
   }
   
   /** Setter for property itemDesc.
    * @param itemDesc New value of property itemDesc.
    *
    */
   public void setItemDesc(String itemDesc) {
      this.itemDesc = itemDesc;
   }
   
   /** Getter for property quantity.
    * @return Value of property quantity.
    *
    */
   public String getQuantity() {
      return quantity;
   }
   
   /** Setter for property quantity.
    * @param quantity New value of property quantity.
    *
    */
   public void setQuantity(String quantity) {
      this.quantity = quantity;
   }
   
   /** Getter for property uom.
    * @return Value of property uom.
    *
    */
   public String getUom() {
      return uom;
   }
   
   /** Setter for property uom.
    * @param uom New value of property uom.
    *
    */
   public void setUom(String uom) {
      this.uom = uom;
   }
   
   /** Getter for property recipientId.
    * @return Value of property recipientId.
    *
    */
   public String getRecipientId() {
      return recipientId;
   }
   
   /** Setter for property recipientId.
    * @param recipientId New value of property recipientId.
    *
    */
   public void setRecipientId(String recipientId) {
      this.recipientId = recipientId;
   }
   
   /** Getter for property unitPrice.
    * @return Value of property unitPrice.
    *
    */
   public String getUnitPrice() {
      return unitPrice;
   }
   
   /** Setter for property unitPrice.
    * @param unitPrice New value of property unitPrice.
    *
    */
   public void setUnitPrice(String unitPrice) {
      this.unitPrice = unitPrice;
   }
   
   /** Getter for property currency.
    * @return Value of property currency.
    *
    */
   public String getCurrency() {
      return currency;
   }
   
   /** Setter for property currency.
    * @param currency New value of property currency.
    *
    */
   public void setCurrency(String currency) {
      this.currency = currency;
   }
   
   /** Getter for property totalValue.
    * @return Value of property totalValue.
    *
    */
   public String getTotalValue() {
      return totalValue;
   }
   
   /** Setter for property totalValue.
    * @param totalValue New value of property totalValue.
    *
    */
   public void setTotalValue(String totalValue) {
      this.totalValue = totalValue;
   }      
   
   /** Getter for property mrNum.
    * @return Value of property mrNum.
    *
    */
   public String getMrNum() {
      return mrNum;
   }
   
   /** Setter for property mrNum.
    * @param mrNum New value of property mrNum.
    *
    */
   public void setMrNum(String mrNum) {
      this.mrNum = mrNum;
   }
   
   /** Getter for property mrLine.
    * @return Value of property mrLine.
    *
    */
   public String getMrLine() {
      return mrLine;
   }
   
   /** Setter for property mrLine.
    * @param mrLine New value of property mrLine.
    *
    */
   public void setMrLine(String mrLine) {
      this.mrLine = mrLine;
   }
   
   /**
    * Getter for property materialId.
    * @return Value of property materialId.
    */
   public String getMaterialId() {
      return materialId;
   }
   
   /**
    * Setter for property materialId.
    * @param materialId New value of property materialId.
    */
   public void setMaterialId(String materialId) {
      this.materialId = materialId;
   }
   
   /**
    * Getter for property packaging.
    * @return Value of property packaging.
    */
   public String getPackaging() {
      return packaging;
   }
   
   /**
    * Setter for property packaging.
    * @param packaging New value of property packaging.
    */
   public void setPackaging(String packaging) {
      this.packaging = packaging;
   }
   
}
