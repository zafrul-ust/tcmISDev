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
import com.tcmis.internal.hub.beans.PackingListDetailViewBean;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;

public class PackingListDetail
{
    String SHIPMENT_ID = "";
    String PR_NUMBER = "";
    String LINE_ITEM = "";
    String MR_LINE = "";
    String RADIAN_PO = "";
    String PO_NUMBER = "";
    String RECEIPT_ID = "";
    String CAT_PART_NO = "";
    String PART_SHORT_NAME = "";
    String QUANTITY_DELIVERED = "";
    String UNIT_OF_SALE = "";
    String DELIVERY_POINT = "";
    String DELIVERY_POINT_DESC = "";
    String PACKAGING = "";
    String MFG_LOT = "";
    String EXPIRE_DATE = "";
    String HAAS_TCM_REF_NO = "";
    String BARCODE_CAT_PART_NO = "";
		String BARCODE_QUANTITY_DELIVERED = "";
		String BARCODE_RECEIPT_ID = "";
		String BARCODE_EXPIRE_DATE = "";
	String QA_STATEMENT = "";
	String INBOUND_CERTIFICATE = "";

	 public PackingListDetail(PackingListDetailViewBean packingListDetailViewBean)
    {
        BARCODE_CAT_PART_NO = "";
		    BARCODE_QUANTITY_DELIVERED = "";
		    BARCODE_RECEIPT_ID = "";
		    BARCODE_EXPIRE_DATE = "";
        SHIPMENT_ID = NumberHandler.emptyIfNull(packingListDetailViewBean.getShipmentId());
        PR_NUMBER = NumberHandler.emptyIfNull(packingListDetailViewBean.getPrNumber());
        LINE_ITEM = StringHandler.emptyIfNull(packingListDetailViewBean.getLineItem());
        MR_LINE = PR_NUMBER+"-"+LINE_ITEM;
        RADIAN_PO = NumberHandler.emptyIfNull(packingListDetailViewBean.getRadianPo());
        PO_NUMBER = StringHandler.emptyIfNull(packingListDetailViewBean.getPoNumber());
        RECEIPT_ID = NumberHandler.emptyIfNull(packingListDetailViewBean.getReceiptId());
        CAT_PART_NO = StringHandler.emptyIfNull(packingListDetailViewBean.getCatPartNo());
        PART_SHORT_NAME = StringHandler.emptyIfNull(packingListDetailViewBean.getPartShortName());
        if (PART_SHORT_NAME.trim().length() == 0)
        {
           PART_SHORT_NAME = StringHandler.emptyIfNull(packingListDetailViewBean.getPartDescription());
        }
        QUANTITY_DELIVERED = NumberHandler.emptyIfNull(packingListDetailViewBean.getQuantityDelivered().multiply(packingListDetailViewBean.getUsageFactor()));
        UNIT_OF_SALE = StringHandler.emptyIfNull(packingListDetailViewBean.getUnitOfSale());
        DELIVERY_POINT = StringHandler.emptyIfNull(packingListDetailViewBean.getDeliveryPoint());
        DELIVERY_POINT_DESC = StringHandler.emptyIfNull(packingListDetailViewBean.getDeliveryPointDesc());
        PACKAGING = StringHandler.emptyIfNull(packingListDetailViewBean.getPackaging());
        MFG_LOT = StringHandler.emptyIfNull(packingListDetailViewBean.getMfgLot());
        EXPIRE_DATE = DateHandler.formatDate(packingListDetailViewBean.getExpireDate(),"dd-MMM-yyyy");
				HAAS_TCM_REF_NO = NumberHandler.emptyIfNull(packingListDetailViewBean.getTcmRefNo());

	      String catPartNo = StringHandler.emptyIfNull(packingListDetailViewBean.getCatPartNo());
     		try {
					BARCODE_CAT_PART_NO = com.tcmis.common.util.BarCodeHandler.Code128b(catPartNo);
				 }
			 catch (Exception ex) {
			 }

     	String quantityDelivered = ""+NumberHandler.emptyIfNull(packingListDetailViewBean.getQuantityDelivered())+"";
			try {
				 BARCODE_QUANTITY_DELIVERED = com.tcmis.common.util.BarCodeHandler.Code128c(quantityDelivered);
				}
			catch (Exception ex) {
			}

    	String receiptId = ""+NumberHandler.emptyIfNull(packingListDetailViewBean.getReceiptId())+"";
		  try {
			 BARCODE_RECEIPT_ID = com.tcmis.common.util.BarCodeHandler.Code128c(receiptId);
		  }
			catch (Exception ex) {
			}

		 try {
			BARCODE_EXPIRE_DATE = com.tcmis.common.util.BarCodeHandler.Code128b(EXPIRE_DATE);
		 }
		 catch (Exception ex) {
 	  	}
		QA_STATEMENT = NumberHandler.emptyIfNull(packingListDetailViewBean.getQaStatement());
		INBOUND_CERTIFICATE = StringHandler.emptyIfNull(packingListDetailViewBean.getInboundCertificate());
	 }

    public String getShipmentID() {
      return SHIPMENT_ID;
    }

    public String getPrNumber()
    {
        return PR_NUMBER;
    }

    public String getLineItem()
    {
        return LINE_ITEM;
    }

    public String getMrLine()
    {
        return MR_LINE;
    }

    public String getRadianPO()
    {
        return RADIAN_PO;
    }

    public String getPoNumber()
    {
        return PO_NUMBER;
    }

    public String getReceiptID()
    {
        return RECEIPT_ID;
    }
    public String getCatPartNo()
    {
        return CAT_PART_NO;
    }
    public String getPartShortName()
    {
        return PART_SHORT_NAME;
    }
    public String getQuantityDelivered()
    {
        return QUANTITY_DELIVERED;
    }
    public String getUnitOfSale()
    {
        return UNIT_OF_SALE;
    }
    public String getDeliveryPoint()
    {
        return DELIVERY_POINT;
    }
    public String getDeliveryPointDesc()
    {
        return DELIVERY_POINT_DESC;
    }
    public String getPackaging()
    {
        return PACKAGING;
    }
    public String getMfgLot()
    {
        return MFG_LOT;
    }
    public String getExpireDate()
    {
        return EXPIRE_DATE;
    }
    public String getHaasTcmRefNo()
    {
        return HAAS_TCM_REF_NO;
    }

  	public String getBARCODE_CAT_PART_NO()
		{
				return BARCODE_CAT_PART_NO;
		}

		public String getBARCODE_QUANTITY_DELIVERED()
		{
				return BARCODE_QUANTITY_DELIVERED;
		}

		public String getBARCODE_RECEIPT_ID()
		{
				return BARCODE_RECEIPT_ID;
		}

		public String getBARCODE_EXPIRE_DATE()
		{
				return BARCODE_EXPIRE_DATE;
		}

	public String getQaStatement() {
		return QA_STATEMENT;
	}
	public String getInboundCertificate() {
		return INBOUND_CERTIFICATE;
	}


	 public static Vector getFieldVector()
    {
        Vector v = new Vector(29);
        v.addElement("SHIPMENT_ID = getShipmentID");
        v.addElement("PR_NUMBER = getPrNumber");
        v.addElement("LINE_ITEM = getLineItem");
        v.addElement("MR_LINE = getMrLine");
        v.addElement("RADIAN_PO = getRadianPO");
        v.addElement("PO_NUMBER = getPoNumber");
        v.addElement("RECEIPT_ID = getReceiptID");
        v.addElement("CAT_PART_NO = getCatPartNo");
        v.addElement("PART_SHORT_NAME = getPartShortName");
        v.addElement("QUANTITY_DELIVERED = getQuantityDelivered");
        v.addElement("UNIT_OF_SALE = getUnitOfSale");
        v.addElement("DELIVERY_POINT = getDeliveryPoint");
        v.addElement("DELIVERY_POINT_DESC = getDeliveryPointDesc");
        v.addElement("PACKAGING = getPackaging");
        v.addElement("MFG_LOT = getMfgLot");
        v.addElement("EXPIRE_DATE = getExpireDate");
        v.addElement("HAAS_TCM_REF_NO = getHaasTcmRefNo");
				v.addElement("BARCODE_CAT_PART_NO = getBARCODE_CAT_PART_NO");
				v.addElement("BARCODE_QUANTITY_DELIVERED = getBARCODE_QUANTITY_DELIVERED");
				v.addElement("BARCODE_RECEIPT_ID = getBARCODE_RECEIPT_ID");
				v.addElement("BARCODE_EXPIRE_DATE = getBARCODE_EXPIRE_DATE");
		  v.add("QA_STATEMENT = getQaStatement");
		  v.add("INBOUND_CERTIFICATE = getInboundCertificate");
		  return v;
    }
    public static Vector getVector(Collection in)
    {
        Vector out = new Vector();
        Iterator iterator = in.iterator();
        while (iterator.hasNext()) {
            out.addElement(new PackingListDetail((PackingListDetailViewBean)iterator.next()));
        }
        return out;
    }
}
