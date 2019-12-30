package com.tcmis.client.waste.erw;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.util.BarCodeHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.waste.beans.WasteTravelerViewBean;

public class WasteTraveler {

  private String TRAVELER_ID = "";
  private String FACILITY_ID = "";
  private String GENERATION_POINT = "";
  private String VENDOR_PROFILE_ID = "";
  private String VENDOR_NAME = "";
  private String DESCRIPTION = "";
  private String MANAGEMENT_OPTION_DESC = "";
  private String DOT_DESCRIPTION = "";
  private String CONTAINER_ID = "";
  private String BARCODE = "";
  private String WASTE_REQUEST_ID_LINE_ITEM = "";

  public WasteTraveler() {

  }

  public WasteTraveler(WasteTravelerViewBean wasteTravelerViewBean) {
    TRAVELER_ID = StringHandler.emptyIfNull(wasteTravelerViewBean.getTravelerId());
    FACILITY_ID = StringHandler.emptyIfNull(wasteTravelerViewBean.getFacilityId());
    GENERATION_POINT = StringHandler.emptyIfNull(wasteTravelerViewBean.getGenerationPoint());
    VENDOR_PROFILE_ID = StringHandler.emptyIfNull(wasteTravelerViewBean.getVendorProfileId());
    VENDOR_NAME = StringHandler.emptyIfNull(wasteTravelerViewBean.getCompanyName());
    DESCRIPTION = StringHandler.emptyIfNull(wasteTravelerViewBean.getDescription());
    MANAGEMENT_OPTION_DESC = StringHandler.emptyIfNull(wasteTravelerViewBean.getManagementOptionDesc());
    DOT_DESCRIPTION = StringHandler.emptyIfNull(wasteTravelerViewBean.getDotDescription());
    CONTAINER_ID = StringHandler.emptyIfNull(wasteTravelerViewBean.getContainerId());
    String tmp = StringHandler.emptyIfNull(wasteTravelerViewBean.getLpadContainerId());
    if (!StringHandler.isBlankString(tmp)) {
      try {
        BARCODE = BarCodeHandler.Code128c(tmp);
      }catch (Exception e) {
        e.printStackTrace();
        BARCODE = "";
      }
    }
    WASTE_REQUEST_ID_LINE_ITEM = StringHandler.emptyIfNull(wasteTravelerViewBean.getWasteRequestIdLineItem());
  } //end of method

  public String getTravelerId() {
    return TRAVELER_ID;
  }

  public String getFacilityId() {
    return FACILITY_ID;
  }

  public String getGenerationPoint() {
    return GENERATION_POINT;
  }

  public String getVendorProfileId() {
    return VENDOR_PROFILE_ID;
  }

  public String getVendorName() {
    return VENDOR_NAME;
  }

  public String getDescription() {
    return DESCRIPTION;
  }

  public String getManagementOptionDesc() {
    return MANAGEMENT_OPTION_DESC;
  }

  public String getDotDescription() {
    return DOT_DESCRIPTION;
  }

  public String getContainerId() {
    return CONTAINER_ID;
  }

  public String getBarcode() {
    return BARCODE;
  }

  public String getWasteRequestIdLineItem() {
    return WASTE_REQUEST_ID_LINE_ITEM;
  }

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("TRAVELER_ID = getTravelerId");
    v.addElement("FACILITY_ID = getFacilityId");
    v.addElement("GENERATION_POINT = getGenerationPoint");
    v.addElement("VENDOR_PROFILE_ID = getVendorProfileId");
    v.addElement("VENDOR_NAME = getVendorName");
    v.addElement("DESCRIPTION = getDescription");
    v.addElement("MANAGEMENT_OPTION_DESC = getManagementOptionDesc");
    v.addElement("DOT_DESCRIPTION = getDotDescription");
    v.addElement("CONTAINER_ID = getContainerId");
    v.addElement("BARCODE = getBarcode");
    v.addElement("WASTE_REQUEST_ID_LINE_ITEM = getWasteRequestIdLineItem");
    return v;
  }

  public static Vector getVector(Collection in) {
    Vector out = new Vector();
    //if data is empty then create empty label
    if (in.size() == 0) {
      out.addElement(new WasteTraveler());
    }
    Iterator iterator = in.iterator();
    while (iterator.hasNext()) {
      out.addElement(new WasteTraveler((WasteTravelerViewBean) iterator.next()));
    }
    return out;
  }
}