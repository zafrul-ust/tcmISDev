package com.tcmis.client.waste.erw;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.util.BarCodeHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.waste.beans.WstContCaHazLabelViewBean;

public class HazardousWaste {

  private String NAME = "";
  private String ADDRESS = "";
  private String PHONE = "";
  private String CITY = "";
  private String STATE = "";
  private String ZIP = "";
  private String EPA_ID = "";
  private String MANIFEST = "";
  private String EPA_WASTE_NO = "";
  private String CA_WASTE_NO = "";
  private String ACCUMULATION_START_DATE = "";
  private String CONTENTS = "";
  private String SOLID = "";
  private String LIQUID = "";
  private String FLAMMABLE = "";
  private String TOXIC = "";
  private String CORROSIVE = "";
  private String REACTIVITY = "";
  private String OTHER = "";
  private String OTHER_DETAIL = "";
  private String CONTAINER_ID = "";
  private String BARCODE = "";
  private String DOT = "";

  public HazardousWaste() {

  }

  public HazardousWaste(WstContCaHazLabelViewBean wstContCaHazLabelViewBean) {
    CONTAINER_ID = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getContainerId());
    String tmp = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getLpadContainerId());
    if (!StringHandler.isBlankString(tmp)) {
      try {
        BARCODE = BarCodeHandler.Code128c(tmp);
      }catch (Exception e) {
        e.printStackTrace();
        BARCODE = "";
      }
    }
    NAME = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getGenName());
    ADDRESS = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getGenAddress());
    PHONE = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getGenPhone());
    CITY = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getGenCity());
    STATE = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getGenState());
    ZIP = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getGenZip());
    EPA_ID = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getEpaId());
    MANIFEST = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getManifest());
    EPA_WASTE_NO = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getEpaWasteNo());
    CA_WASTE_NO = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getCaWasteNo());
    ACCUMULATION_START_DATE = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getAccumulationStartDate());
    CONTENTS = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getContentsComposition());
    SOLID = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getPhysicalStateSolid());
    LIQUID = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getPhysicalStateLiquid());
    FLAMMABLE = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getFlammable());
    TOXIC = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getToxic());
    CORROSIVE = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getCorrosive());
    REACTIVITY = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getReactive());
    OTHER = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getHazardOther());
    OTHER_DETAIL = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getHazardOtherDetail());
    DOT = StringHandler.emptyIfNull(wstContCaHazLabelViewBean.getDot());
  } //end of method

  public String getName() {
    return NAME;
  }
  public String getAddress() {
    return ADDRESS;
  }
  public String getPhone() {
    return PHONE;
  }
  public String getCity() {
    return CITY;
  }
  public String getState() {
    return STATE;
  }
  public String getZip() {
    return ZIP;
  }
  public String getEpaId() {
    return EPA_ID;
  }
  public String getManifest() {
    return MANIFEST;
  }
  public String getEpaWasteNo() {
    return EPA_WASTE_NO;
  }
  public String getCaWasteNo() {
    return CA_WASTE_NO;
  }
  public String getAccumulationStartDate() {
    return ACCUMULATION_START_DATE;
  }
  public String getContents() {
    return CONTENTS;
  }
  public String getSolid() {
    return SOLID;
  }
  public String getLiquid() {
    return LIQUID;
  }
  public String getFlammable() {
    return FLAMMABLE;
  }
  public String getToxic() {
    return TOXIC;
  }
  public String getCorrosive() {
    return CORROSIVE;
  }
  public String getReactivity() {
    return REACTIVITY;
  }
  public String getOther() {
    return OTHER;
  }
  public String getOtherDetail() {
    return OTHER_DETAIL;
  }
  public String getContainerId() {
    return CONTAINER_ID;
  }
  public String getBarcode() {
    return BARCODE;
  }
  public String getDot() {
    return DOT;
  }

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("NAME = getName");
    v.addElement("ADDRESS = getAddress");
    v.addElement("PHONE = getPhone");
    v.addElement("CITY = getCity");
    v.addElement("STATE = getState");
    v.addElement("ZIP = getZip");
    v.addElement("EPA_ID = getEpaId");
    v.addElement("MANIFEST = getManifest");
    v.addElement("EPA_WASTE_NO = getEpaWasteNo");
    v.addElement("CA_WASTE_NO = getCaWasteNo");
    v.addElement("ACCUMULATION_START_DATE = getAccumulationStartDate");
    v.addElement("CONTENTS = getContents");
    v.addElement("SOLID = getSolid");
    v.addElement("LIQUID = getLiquid");
    v.addElement("FLAMMABLE = getFlammable");
    v.addElement("TOXIC = getToxic");
    v.addElement("CORROSIVE = getCorrosive");
    v.addElement("REACTIVITY = getReactivity");
    v.addElement("OTHER = getOther");
    v.addElement("OTHER_DETAIL = getOtherDetail");
    v.addElement("CONTAINER_ID = getContainerId");
    v.addElement("BARCODE = getBarcode");
    v.addElement("DOT = getDot");
    return v;
  }

  public static Vector getVector(Collection in) {
    Vector out = new Vector();
    //if data is empty then create empty label
    if (in.size() == 0) {
      out.addElement(new HazardousWaste());
    }
    Iterator iterator = in.iterator();
    while (iterator.hasNext()) {
      out.addElement(new HazardousWaste((WstContCaHazLabelViewBean) iterator.next()));
    }
    return out;
  }
}