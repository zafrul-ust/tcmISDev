package com.tcmis.client.report.erw;

import java.text.DateFormatSymbols;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import com.tcmis.client.report.beans.ScaqmdMasterListViewBean;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.DateHandler;

/******************************************************************************
 * This class will link java data and erw template
 * @version 1.0
 *****************************************************************************/

public class ScaqmdMasterList {
  private String dateAddedToInventory;
  private String manufacturer;
  private String partDescription;
  private String component;
  private String materialCategory;
  private String productIdMmAndEeNo;
  private String vocCoatingLbPerGal;
  private String vocMatlLbPerGal;
  private String mixingRatio;
  private String vocCompVaporPressureMmhg;
  private String forUseBy;
  private String activityOrSubstrateDesc;
  private String applicableDistrictRule;
  private String categoryComment;
  private String ruleSubcategory;

  public ScaqmdMasterList(ScaqmdMasterListViewBean scaqmdMasterListViewBean) {
    if (scaqmdMasterListViewBean.getDateAddedToInventory() != null) {
      this.dateAddedToInventory = DateHandler.formatDate(scaqmdMasterListViewBean.getDateAddedToInventory(),"MM/dd/yyyy");
    }else {
      this.dateAddedToInventory = "";
    }
    this.manufacturer = scaqmdMasterListViewBean.getManufacturer();
    this.partDescription = scaqmdMasterListViewBean.getPartDescription();
    if (scaqmdMasterListViewBean.getComponent() != null) {
      this.component = scaqmdMasterListViewBean.getComponent().toString();
    }else {
      this.component = "";
    }
    this.materialCategory = scaqmdMasterListViewBean.getMaterialCategory();
    this.productIdMmAndEeNo = scaqmdMasterListViewBean.getProductIdMmAndEeNo();
    if (scaqmdMasterListViewBean.getVocCoatingLbPerGal() != null) {
      this.vocCoatingLbPerGal = scaqmdMasterListViewBean.getVocCoatingLbPerGal();
    }else {
      this.vocCoatingLbPerGal = "";
    }
    if (scaqmdMasterListViewBean.getVocMatlLbPerGal() != null) {
      this.vocMatlLbPerGal = scaqmdMasterListViewBean.getVocMatlLbPerGal();
    }else {
      this.vocMatlLbPerGal = "";
    }
    if (scaqmdMasterListViewBean.getMixingRatio() != null) {
      this.mixingRatio = scaqmdMasterListViewBean.getMixingRatio();
    }else {
      this.mixingRatio = "";
    }
    if (scaqmdMasterListViewBean.getVocCompVaporPressureMmhg() != null) {
      this.vocCompVaporPressureMmhg = scaqmdMasterListViewBean.getVocCompVaporPressureMmhg();
    }else {
      this.vocCompVaporPressureMmhg = "";
    }
    this.forUseBy = scaqmdMasterListViewBean.getForUseBy();
    this.activityOrSubstrateDesc = scaqmdMasterListViewBean.getActivityOrSubstrateDesc();
    this.applicableDistrictRule = scaqmdMasterListViewBean.getApplicableDistrictRule();
    this.categoryComment = scaqmdMasterListViewBean.getCategoryComment();
    this.ruleSubcategory = scaqmdMasterListViewBean.getRuleSubcategory();
  }

  public String getDateAddedToInventory() {
    return dateAddedToInventory;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public String getComponent() {
    return component;
  }

  public String getMaterialCategory() {
    return materialCategory;
  }

  public String getProductIdMmAndEeNo() {
    return productIdMmAndEeNo;
  }

  public String getVocCoatingLbPerGal() {
    return vocCoatingLbPerGal;
  }

  public String getVocMatlLbPerGal() {
    return vocMatlLbPerGal;
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

  public String getActivityOrSubstrateDesc() {
    return activityOrSubstrateDesc;
  }

  public String getApplicableDistrictRule() {
    return applicableDistrictRule;
  }

  public String getCategoryComment() {
    return categoryComment;
  }

  public String getRuleSubcategory() {
    return ruleSubcategory;
  }

  public static Vector getFieldVector() {
    Vector v = new Vector(17);
    v.addElement("dateAddedToInventory = getDateAddedToInventory");
    v.addElement("manufacturer = getManufacturer");
    v.addElement("partDescription = getPartDescription");
    v.addElement("component = getComponent");
    v.addElement("materialCategory = getMaterialCategory");
    v.addElement("productIdMmAndEeNo = getProductIdMmAndEeNo");
    v.addElement("vocCoatingLbPerGal = getVocCoatingLbPerGal");
    v.addElement("vocMatlLbPerGal = getVocMatlLbPerGal");
    v.addElement("mixingRatio = getMixingRatio");
    v.addElement("vocCompVaporPressureMmhg = getVocCompVaporPressureMmhg");
    v.addElement("forUseBy = getForUseBy");
    v.addElement("activityOrSubstrateDesc = getActivityOrSubstrateDesc");
    v.addElement("applicableDistrictRule = getApplicableDistrictRule");
    v.addElement("categoryComment = getCategoryComment");
    v.addElement("ruleSubcategory = getRuleSubcategory");
    return v;
  }

  public static Vector getVector(Collection in) {
    Vector out = new Vector();
    Iterator iterator = in.iterator();
    if (in.size() > 0 ) {
      while (iterator.hasNext()) {
        ScaqmdMasterListViewBean scaqmdMasterListViewBean = (ScaqmdMasterListViewBean) iterator.next();
        out.addElement(new ScaqmdMasterList(scaqmdMasterListViewBean));
      }
    }else {
      out.addElement(new ScaqmdMasterList(new ScaqmdMasterListViewBean()));
    }
    return out;
  }
}
