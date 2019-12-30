package com.tcmis.internal.hub.beans;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

import org.apache.struts.action.*;

public class ReceivingListForm
    extends ActionForm {

  public ReceivingListForm() {
  }

  private String[] radianPo = null;
  private String[] ok = null;
  private String[] bin = null;
  private String[] lot = null;
  private String supplierShipDate[] = null;
  private String dor[] = null;
  private String dom[] = null;
  private String dos[] = null;
  private String expirationDate[] = null;
  private String quantityReceived[] = null;
  private String duplicate[] = null;

  //getters
  public String[] getRadianPo() {
    return this.radianPo;
  }

  public String[] getOk() {
    return this.ok;
  }

  public String[] getBin() {
    return this.bin;
  }

  public String[] getLot() {
    return this.lot;
  }

  public String getLot(int index) {
    return this.lot[index];
  }

  public String[] getSupplierShipDate() {
    return this.supplierShipDate;
  }

  public String[] getDor() {
    return this.dor;
  }

  public String[] getDom() {
    return this.dom;
  }

  public String[] getDos() {
    return this.dos;
  }

  public String[] getExpirationDate() {
    return this.expirationDate;
  }

  public String[] getQuantityReceived() {
    return this.radianPo;
  }

  public String[] getDuplicate() {
    return this.duplicate;
  }

  //setters
  public void setRadianPo(String[] radianPo) {
    this.radianPo = radianPo;
  }

  public void setOk(String[] ok) {
    this.ok = ok;
  }

  public void setBin(String[] bin) {
    this.bin = bin;
  }

  public void setLot(String[] lot) {
    this.lot = lot;
  }

  public void setLot(int index, String lot) {
    this.lot[index] = lot;
  }

  public void setSupplierShipDate(String[] supplierShipDate) {
    this.supplierShipDate = supplierShipDate;
  }

  public void setDor(String[] dor) {
    this.dor = dor;
  }

  public void setDom(String[] dom) {
    this.dom = dom;
  }

  public void setDos(String[] dos) {
    this.dos = dos;
  }

  public void setExpirationDate(String[] expirationDate) {
    this.expirationDate = expirationDate;
  }

  public void setQuantityReceived(String[] quantityReceived) {
    this.quantityReceived = quantityReceived;
  }

  public void setDuplicate(String[] duplicate) {
    this.duplicate = duplicate;
  }

}