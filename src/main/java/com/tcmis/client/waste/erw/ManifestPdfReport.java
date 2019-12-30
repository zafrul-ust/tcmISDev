package com.tcmis.client.waste.erw;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.util.BarCodeHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.waste.beans.WasteManifestViewBean;

public class ManifestPdfReport {

  private String MANIFEST_ID = "";

  public ManifestPdfReport() {

  }

  public ManifestPdfReport(WasteManifestViewBean bean) {
    MANIFEST_ID = StringHandler.emptyIfNull(bean.getManifestId());
  } //end of method

  public String getManifestId() {
    return MANIFEST_ID;
  }

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("MANIFEST_ID = getManifestId");
    return v;
  }

  public static Vector getVector(Collection in) {
    Vector out = new Vector();
    //if data is empty then create empty label
    if (in.size() == 0) {
      out.addElement(new ManifestPdfReport());
    }
    Iterator iterator = in.iterator();
    while (iterator.hasNext()) {
      out.addElement(new ManifestPdfReport((WasteManifestViewBean) iterator.next()));
    }
    return out;
  }
}