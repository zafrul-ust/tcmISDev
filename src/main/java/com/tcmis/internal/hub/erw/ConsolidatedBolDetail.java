package com.tcmis.internal.hub.erw;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.text.DateFormatSymbols;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import com.tcmis.internal.hub.beans.ConsolidatedBolDetailViewBean;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;

public class ConsolidatedBolDetail
{
    String SHIPMENT_ID = "";
    String HAZARDOUS = "";
    String DOT = "";
    String NET_WEIGHT_LB = "";
    String MR_LINE = "";
    String TOTAL_NET_WEIGHT_LB = "";
    String TOTAL_NET_WEIGHT_FOR_MAJOR_HAZARD_CLASS = "";

    public ConsolidatedBolDetail(ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean, Collection totalNetWeightForMajorHazardClass)
    {
        SHIPMENT_ID = NumberHandler.emptyIfNull(consolidatedBolDetailViewBean.getShipmentId());
        SHIPMENT_ID = SHIPMENT_ID.replace(".","12345");
        HAZARDOUS = StringHandler.emptyIfNull(consolidatedBolDetailViewBean.getHazardous());
        DOT = StringHandler.emptyIfNull(consolidatedBolDetailViewBean.getDot());
        if (DOT.length() > 64) {
         String[] tmpDot = StringHandler.breakingString(DOT,64);
         String tmp = "";
         for (int i = 0; i < tmpDot.length; i++) {
           tmp += tmpDot[i]+"\n";
         }
         DOT = tmp;
        }
        NET_WEIGHT_LB = NumberHandler.emptyIfNull(consolidatedBolDetailViewBean.getNetWeightLb());
        MR_LINE = StringHandler.emptyIfNull(consolidatedBolDetailViewBean.getMrLine());
        if (MR_LINE.length() > 80) {
          String[] tmpMrLine = StringHandler.breakingString(MR_LINE,80);
          String tmp = "";
          for (int i = 0; i < tmpMrLine.length; i++) {
            tmp += tmpMrLine[i]+"\n";
          }
          MR_LINE = tmp;
        }
        TOTAL_NET_WEIGHT_LB = NumberHandler.emptyIfNull(consolidatedBolDetailViewBean.getTotalNetWeightLb());
        TOTAL_NET_WEIGHT_FOR_MAJOR_HAZARD_CLASS = getTotalNetWeightForMajorHazardClass(SHIPMENT_ID,totalNetWeightForMajorHazardClass);
        if (TOTAL_NET_WEIGHT_FOR_MAJOR_HAZARD_CLASS.length() > 150) {
          String[] tmpTotalNetWt = StringHandler.breakingString(TOTAL_NET_WEIGHT_FOR_MAJOR_HAZARD_CLASS,150);
          String tmp = "";
          for (int i = 0; i < tmpTotalNetWt.length; i++) {
            tmp += tmpTotalNetWt[i]+"\n";
          }
          TOTAL_NET_WEIGHT_FOR_MAJOR_HAZARD_CLASS = tmp;
        }
    } //end of method

    public String getTotalNetWeightForMajorHazardClass(String shipmentId, Collection totalNetWeightForMajorHazardClass) {
      String result = "";
      Iterator iterator = totalNetWeightForMajorHazardClass.iterator();
      while (iterator.hasNext()) {
        ConsolidatedBolDetailViewBean consolidatedBolDetailViewBean = (ConsolidatedBolDetailViewBean)iterator.next();
        if (shipmentId.equalsIgnoreCase(consolidatedBolDetailViewBean.getShipmentId().toString())) {
          result += consolidatedBolDetailViewBean.getTotalNetWtForMhc()+" ; ";
        }
      }
      //removing the last " ; "
      return StringHandler.stripLast(result," ; ");
    }

    public String getShipmentID() {
      return SHIPMENT_ID.toUpperCase();
    }

    public String getHazardous()
    {
        return HAZARDOUS.toUpperCase();
    }

    public String getDot()
    {
        return DOT.toUpperCase();
    }

    public String getNetWeightLb()
    {
        return NET_WEIGHT_LB.toUpperCase();
    }

    public String getTotalNetWeightLb()
    {
        return TOTAL_NET_WEIGHT_LB.toUpperCase();
    }

    public String getMrLine()
    {
        return MR_LINE.toUpperCase();
    }

    public String getTotalNetWeightForMajorHazardClass()
    {
        return TOTAL_NET_WEIGHT_FOR_MAJOR_HAZARD_CLASS.toUpperCase();
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector(11);
        v.addElement("SHIPMENT_ID = getShipmentID");
        v.addElement("HAZARDOUS = getHazardous");
        v.addElement("DOT = getDot");
        v.addElement("NET_WEIGHT_LB = getNetWeightLb");
        v.addElement("MR_LINE = getMrLine");
        v.addElement("TOTAL_NET_WEIGHT_LB = getTotalNetWeightLb");
        v.addElement("TOTAL_NET_WEIGHT_FOR_MAJOR_HAZARD_CLASS = getTotalNetWeightForMajorHazardClass");
        return v;
    }
    public static Vector getVector(Collection in, Collection totalNetWeightForMajorHazardClass)
    {
        Vector out = new Vector();
        Iterator iterator = in.iterator();
        while (iterator.hasNext()) {
          out.addElement(new ConsolidatedBolDetail((ConsolidatedBolDetailViewBean)iterator.next(),totalNetWeightForMajorHazardClass));
        }
        return out;
    }

} //end of class
