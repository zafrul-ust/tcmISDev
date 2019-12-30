package com.tcmis.client.report.erw;

import java.text.DateFormatSymbols;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import com.tcmis.client.report.beans.ScaqmdRule109ViewBean;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.DateHandler;

/******************************************************************************
 * This class will link java data and erw template
 * @version 1.0
 *****************************************************************************/
public class ScaqmdRule109 {

  private String keyId;
  private String permitNo;
  private String itemId;
  private String shipmentDate;
  private String facilityGroupId;
  private String catPartNo;
  private String partDescription;
  private String materialId;
  private String revisionDate;
  private String amountUsed;
  private String units;
  private String vocCoatingLbPerGal;
  private String vocMatlLbPerGal;
  private String vocEmissionsLb;
  private String mixingRatio;
  private String vocCompVaporPressureMmhg;
  private String forUseBy;

  public ScaqmdRule109(ScaqmdRule109ViewBean scaqmdRule109ViewBean) {
    if (scaqmdRule109ViewBean.getKeyId() != null) {
      this.keyId = scaqmdRule109ViewBean.getKeyId().toString();
    }else {
      this.keyId = "";
    }
    this.permitNo = scaqmdRule109ViewBean.getPermitNo();
    if (scaqmdRule109ViewBean.getItemId() != null) {
      this.itemId = scaqmdRule109ViewBean.getItemId().toString();
    }else {
      this.itemId = "";
    }
    if (scaqmdRule109ViewBean.getShipmentDate() != null) {
      this.shipmentDate = DateHandler.formatDate(scaqmdRule109ViewBean.getShipmentDate(),"MM/dd/yyyy");
    }else {
      this.shipmentDate = "";
    }
    this.facilityGroupId = scaqmdRule109ViewBean.getFacilityGroupId();
    this.catPartNo = scaqmdRule109ViewBean.getCatPartNo();
    this.partDescription = scaqmdRule109ViewBean.getPartDescription();
    if (scaqmdRule109ViewBean.getMaterialId() != null) {
      this.materialId = scaqmdRule109ViewBean.getMaterialId().toString();
    }else {
      this.materialId = "";
    }
    if (scaqmdRule109ViewBean.getRevisionDate() != null) {
      this.revisionDate = DateHandler.formatDate(scaqmdRule109ViewBean.getRevisionDate(),"MM/dd/yyyy");
    }else {
      this.revisionDate = "";
    }
    if (scaqmdRule109ViewBean.getAmountUsed() != null) {
      this.amountUsed = scaqmdRule109ViewBean.getAmountUsed().toString();
    }else {
      this.amountUsed = "";
    }
    this.units = scaqmdRule109ViewBean.getUnits();
    if (scaqmdRule109ViewBean.getVocCoatingLbPerGal() != null) {
      this.vocCoatingLbPerGal = scaqmdRule109ViewBean.getVocCoatingLbPerGal();
    }else {
      this.vocCoatingLbPerGal = "";
    }
    if (scaqmdRule109ViewBean.getVocMatlLbPerGal() != null) {
      this.vocMatlLbPerGal = scaqmdRule109ViewBean.getVocMatlLbPerGal();
    }else {
      this.vocMatlLbPerGal = "";
    }
    if (scaqmdRule109ViewBean.getVocEmissionsLb() != null) {
      this.vocEmissionsLb = scaqmdRule109ViewBean.getVocEmissionsLb();
    }else {
      this.vocEmissionsLb = "";
    }
    if (scaqmdRule109ViewBean.getMixingRatio() != null) {
      this.mixingRatio = scaqmdRule109ViewBean.getMixingRatio();
    }else {
      this.mixingRatio = "";
    }
    if (scaqmdRule109ViewBean.getVocCompVaporPressureMmhg() != null) {
      this.vocCompVaporPressureMmhg = scaqmdRule109ViewBean.getVocCompVaporPressureMmhg();
    }else {
      this.vocCompVaporPressureMmhg = "";
    }
    this.forUseBy = scaqmdRule109ViewBean.getForUseBy();
  }

  public String getKeyId() {
    return keyId;
  }

  public String getPermitNo() {
    return permitNo;
  }

  public String getItemId() {
    return itemId;
  }

  public String getShipmentDate() {
    return shipmentDate;
  }

  public String getFacilityGroupId() {
    return facilityGroupId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public String getMaterialId() {
    return materialId;
  }

  public String getRevisionDate() {
    return revisionDate;
  }

  public String getAmountUsed() {
    return amountUsed;
  }

  public String getUnits() {
    return units;
  }

  public String getVocCoatingLbPerGal() {
    return vocCoatingLbPerGal;
  }

  public String getVocMatlLbPerGal() {
    return vocMatlLbPerGal;
  }

  public String getVocEmissionsLb() {
    return vocEmissionsLb;
  }

  public String getMixingRatio() {
    return mixingRatio;
  }

  public String getVocCompVaporPressureMmhg() {
    return vocCompVaporPressureMmhg;
  }

  public String getForUseBy() {
    return forUseBy;
  }

  public static Vector getFieldVector() {
    Vector v = new Vector(17);
    v.addElement("keyId = getKeyId");
    v.addElement("permitNo = getPermitNo");
    v.addElement("itemId = getItemId");
    v.addElement("shipmentDate = getShipmentDate");
    v.addElement("facilityGroupId = getFacilityGroupId");
    v.addElement("catPartNo = getCatPartNo");
    v.addElement("partDescription = getPartDescription");
    v.addElement("materialId = getMaterialId");
    v.addElement("revisionDate = getRevisionDate");
    v.addElement("amountUsed = getAmountUsed");
    v.addElement("units = getUnits");
    v.addElement("vocCoatingLbPerGal = getVocCoatingLbPerGal");
    v.addElement("vocMatlLbPerGal = getVocMatlLbPerGal");
    v.addElement("vocEmissionsLb = getVocEmissionsLb");
    v.addElement("mixingRatio = getMixingRatio");
    v.addElement("vocCompVaporPressureMmhg = getVocCompVaporPressureMmhg");
    v.addElement("forUseBy = getForUseBy");
    return v;
  }

  public static Vector getVector(Collection in) {
    Vector out = new Vector();
    Iterator iterator = in.iterator();
    if (in.size() > 0) {
      while (iterator.hasNext()) {
        ScaqmdRule109ViewBean scaqmdRule109ViewBean = (ScaqmdRule109ViewBean) iterator.next();
        out.addElement(new ScaqmdRule109(scaqmdRule109ViewBean));
      }
    }else {
      out.addElement(new ScaqmdRule109(new ScaqmdRule109ViewBean()));
    }
    return out;
  }
}
