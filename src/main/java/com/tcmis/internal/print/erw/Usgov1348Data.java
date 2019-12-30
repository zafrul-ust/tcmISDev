package com.tcmis.internal.print.erw;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import IDautomationPDFE.PDF417Encoder;

import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.print.beans.Usgov1348ViewBean;

public class Usgov1348Data {
	private String packingGroupId;
	private String box10;
	private String prNumber;
	private String lineItem;
	private String box2;
	private String box4;
	private String box3;
	private String box27Line1;
	private String priorityRating;
	private String firstRow1348;
	private String box9;
	private String box27Line3;
	private String catalogPrice;
	private String facPartNo;
	private String box15;
	private String box17;
	private String box26Line1;
	private String box26Line2;
	private String box26Line3;
	private String box26Line4;
	private String box26Line5;
	private String box26Line6;
	private String box25;
	private String box24;
	private String box12;
	private String box13;
	private String printedFirstRow;
	private String box1Dollars;
	private String box1Cents;
	private String box5;
	private String box24Barcode;
	private String box25Barcode;
	private String box26Barcode;
	private String box27Barcode;
	private String projectCode;
	private String box28;
	private String box29;
	private String transportationPriority;
	private String fmsCaseNum;
	private String releaseNum;

	public Usgov1348Data(Usgov1348ViewBean bean) {
		packingGroupId = StringHandler.emptyIfNull(bean.getPackingGroupId());
		box10 = NumberHandler.emptyIfNull(bean.getBox10());
		prNumber = NumberHandler.emptyIfNull(bean.getPrNumber());
		lineItem = StringHandler.emptyIfNull(bean.getLineItem());
		box2 = StringHandler.emptyIfNull(bean.getBox2());
		box4 = StringHandler.emptyIfNull(bean.getBox4());
		box3 = StringHandler.emptyIfNull(bean.getBox3());
		box27Line1 = StringHandler.emptyIfNull(bean.getBox27Line1()) + "\n" + StringHandler.emptyIfNull(bean.getBox27Line3());
		priorityRating = StringHandler.emptyIfNull(bean.getPriorityRating());
		firstRow1348 = StringHandler.emptyIfNull(bean.getFirstRow1348());
		box9 = StringHandler.emptyIfNull(bean.getBox9());
		box27Line3 = StringHandler.emptyIfNull(bean.getBox27Line3());
		catalogPrice = NumberHandler.emptyIfNull(bean.getCatalogPrice());
		facPartNo = StringHandler.emptyIfNull(bean.getFacPartNo());
		box15 = StringHandler.emptyIfNull(bean.getBox15());
		box17 = StringHandler.emptyIfNull(bean.getBox17());
		if (bean.getFms() != null && "Y".equalsIgnoreCase(bean.getFms())) {
			box26Line1 = StringHandler.emptyIfNull(bean.getBox26Line2()) + StringHandler.emptyIfNull(bean.getBox26Line3()) + StringHandler.emptyIfNull(bean.getBox26Line4()) + StringHandler.emptyIfNull(bean.getBox26Line6());
			if (bean.getSupplementaryAddress().length() == 6) {
				box26Line1 += bean.getSupplementaryAddress().charAt(0) + bean.getSupplementaryAddress().substring(3, 5);
			}
		}
		else {
			box26Line1 = StringHandler.emptyIfNull(bean.getBox26Line1()) + StringHandler.emptyIfNull(bean.getBox26Line2()) + StringHandler.emptyIfNull(bean.getBox26Line3()) + StringHandler.emptyIfNull(bean.getBox26Line4())
			+ StringHandler.emptyIfNull(bean.getBox26Line5()) + StringHandler.emptyIfNull(bean.getBox26Line6());
		}

		box26Line2 = StringHandler.emptyIfNull(bean.getBox26Line2());
		box26Line3 = StringHandler.emptyIfNull(bean.getBox26Line3());
		box26Line4 = StringHandler.emptyIfNull(bean.getBox26Line4());
		box26Line5 = StringHandler.emptyIfNull(bean.getBox26Line5());
		box26Line6 = StringHandler.emptyIfNull(bean.getBox26Line6());
		box25 = StringHandler.emptyIfNull(bean.getBox25());
		box24 = StringHandler.emptyIfNull(bean.getBox24());
		box12 = NumberHandler.emptyIfNull(bean.getBox12());
		box13 = NumberHandler.emptyIfNull(bean.getBox13());
		printedFirstRow = StringHandler.emptyIfNull(bean.getPrintedFirstRow());
		box1Dollars = StringHandler.emptyIfNull(bean.getBox1Dollars());
		box1Cents = StringHandler.emptyIfNull(bean.getBox1Cents());
		box5 = StringHandler.emptyIfNull(bean.getBox5());
		projectCode = StringHandler.emptyIfNull(bean.getProjectCode());
		box28 = StringHandler.emptyIfNull(bean.getBox28());
		box29 = StringHandler.emptyIfNull(bean.getBox29());
		transportationPriority = StringHandler.emptyIfNull(bean.getTransportationPriority());
		if (transportationPriority.trim().length() > 0) {
			transportationPriority = "TP " + StringHandler.emptyIfNull(bean.getTransportationPriority());
		}
		fmsCaseNum = StringHandler.emptyIfNull(bean.getFmsCaseNum());

		try {
			box24 = box24.substring(0, 14);
			box24Barcode = StringHandler.replace(box24, " ", "=");
			box24Barcode = "*" + box24Barcode + "*";
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			box25Barcode = StringHandler.replace(box25, " ", "=");
			box25Barcode = "*" + box25Barcode + "*";
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			box26Barcode = StringHandler.replace(box26Line1, " ", "=");
			box26Barcode = "*" + box26Barcode + "*";
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		//System.out.println("box24Barcode-"+box24Barcode+"-"+box24Barcode.length()+" -box25Barcode -"+box25Barcode+"-"+box25Barcode.length()+" -box26Barcode-"+box26Barcode+"-"+box26Barcode.length()+"");
		try {
			//box27Barcode = com.tcmis.common.util.BarCodeHandler.Bar128B("" +box26Line1 + "");
			PDF417Encoder pdfe = new PDF417Encoder();
			// This is an example of setting the number of columns (width)
			pdfe.PDFColumns = 11;
			// This is an example of formatting data to the font...
			//System.out.println( pdfe.fontEncode(box26Line1+box26Line1+box26Line1+box26Line1) );
			box27Barcode = pdfe.fontEncode(dd13482dBarcode(bean).toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		releaseNum = StringHandler.emptyIfNull(bean.getReleaseNum());
	} //end of method

	public StringBuilder dd13482dBarcode(Usgov1348ViewBean dataBean) {
		StringBuilder zpl2d = new StringBuilder();
		String pdfGs = "";
		pdfGs += (char) (29);
		String pdfRs = "";
		pdfRs += (char) (30);

		zpl2d.append("[)>" + pdfRs + "06");
		if (dataBean.getBox24() != null && dataBean.getBox24().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "12S" ).append( dataBean.getBox24());
		}
		zpl2d.append(pdfGs ).append( "N" ).append( dataBean.getBox25());
		if (dataBean.getQuantity() != null) {
			zpl2d.append(pdfGs ).append( "7Q" ).append( dataBean.getQuantity() ).append( dataBean.getUnitOfIssue());
		}
		zpl2d.append(pdfGs ).append( "V" ).append( dataBean.getBox26Line1());
		zpl2d.append(pdfGs ).append( "2R" ).append( dataBean.getBox26Line4());
		zpl2d.append(pdfGs ).append( "12Q" ).append( dataBean.getBox26Line6());
		if (dataBean.getTcn() != null && dataBean.getTcn().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "IT" ).append( dataBean.getTcn());
		}
		if (dataBean.getCageCode() != null && dataBean.getCageCode().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "17V" ).append( dataBean.getCageCode());
		}
		if (dataBean.getPartNumber() != null && dataBean.getPartNumber().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "IP" ).append( dataBean.getPartNumber());
		}
		zpl2d.append(pdfRs ).append( "07");
		if (dataBean.getProjectCode() != null && dataBean.getProjectCode().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "03" ).append( dataBean.getProjectCode());
		}
		if (dataBean.getDistributionCode() != null && dataBean.getDistributionCode().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "B6" ).append( dataBean.getDistributionCode());
		}
		if (dataBean.getBox4() != null && dataBean.getBox4().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "27" ).append( dataBean.getBox4());
		}
		if (dataBean.getBox17() != null && dataBean.getBox17().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "38" ).append( dataBean.getBox17());
		}
		if (dataBean.getRequiredDeliveryDate() != null && dataBean.getRequiredDeliveryDate().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "32" ).append( dataBean.getRequiredDeliveryDate());
		}
		if (dataBean.getPriorityRating() != null && dataBean.getPriorityRating().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "B7" ).append( dataBean.getPriorityRating());
		}
		if (dataBean.getTcn() != null && dataBean.getTcn().trim().length() > 0) {
			int tcnLength = dataBean.getTcn().length();
			zpl2d.append(pdfGs ).append( "B8" ).append( dataBean.getTcn().substring(tcnLength - 2, tcnLength - 1));
		}
		if (dataBean.getSupplementaryAddress() != null && dataBean.getSupplementaryAddress().trim().length() > 0) {
			zpl2d.append(pdfGs ).append( "81" ).append( dataBean.getSupplementaryAddress());
		}
		zpl2d.append(pdfRs);
		zpl2d.append((char) (04));
		//System.out.println("box27Barcode-"+zpl2d+"-\n\n"+zpl2d.length());
		return zpl2d;
	}

	//getters
	public String getPackingGroupId() {
		return packingGroupId;
	}

	public String getBox10() {
		return box10;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getBox2() {
		return box2;
	}

	public String getBox4() {
		return box4;
	}

	public String getBox3() {
		return box3;
	}

	public String getBox27Line1() {
		return box27Line1;
	}

	public String getPriorityRating() {
		return priorityRating;
	}

	public String getFirstRow1348() {
		return firstRow1348;
	}

	public String getBox9() {
		return box9;
	}

	public String getBox27Line3() {
		return box27Line3;
	}

	public String getCatalogPrice() {
		return catalogPrice;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public String getBox15() {
		return box15;
	}

	public String getBox17() {
		return box17;
	}

	public String getBox26Line1() {
		return box26Line1;
	}

	public String getBox26Line2() {
		return box26Line2;
	}

	public String getBox26Line3() {
		return box26Line3;
	}

	public String getBox26Line4() {
		return box26Line4;
	}

	public String getBox26Line5() {
		return box26Line5;
	}

	public String getBox26Line6() {
		return box26Line6;
	}

	public String getBox25() {
		return box25;
	}

	public String getBox24() {
		return box24;
	}

	public String getBox12() {
		return box12;
	}

	public String getBox13() {
		return box13;
	}

	public String getPrintedFirstRow() {
		return printedFirstRow;
	}

	public String getBox1Dollars() {
		return box1Dollars;
	}

	public String getBox1Cents() {
		return box1Cents;
	}

	public String getBox5() {
		return box5;
	}

	public String getBox24Barcode() {
		return box24Barcode;
	}

	public String getBox25Barcode() {
		return box25Barcode;
	}

	public String getBox26Barcode() {
		return box26Barcode;
	}

	public String getBox27Barcode() {
		return box27Barcode;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public String getBox28() {
		return box28;
	}

	public String getBox29() {
		return box29;
	}

	public String getTransportationPriority() {
		return transportationPriority;
	}

	public String getFmsCaseNum() {
		return fmsCaseNum;
	}

	public String getReleaseNum() {
		return releaseNum;
	}

	public static Vector getFieldVector() {
		Vector v = new Vector();
		v.addElement("packingGroupId= getPackingGroupId");
		v.addElement("box10= getBox10");
		v.addElement("prNumber= getPrNumber");
		v.addElement("lineItem= getLineItem");
		v.addElement("box2= getBox2");
		v.addElement("box4= getBox4");
		v.addElement("box3= getBox3");
		v.addElement("box27Line1= getBox27Line1");
		v.addElement("priorityRating= getPriorityRating");
		v.addElement("firstRow1348= getFirstRow1348");
		v.addElement("box9= getBox9");
		v.addElement("box27Line3= getBox27Line3");
		v.addElement("catalogPrice= getCatalogPrice");
		v.addElement("facPartNo= getFacPartNo");
		v.addElement("box15= getBox15");
		v.addElement("box17= getBox17");
		v.addElement("box26Line1= getBox26Line1");
		v.addElement("box26Line2= getBox26Line2");
		v.addElement("box26Line3= getBox26Line3");
		v.addElement("box26Line4= getBox26Line4");
		v.addElement("box26Line5= getBox26Line5");
		v.addElement("box26Line6= getBox26Line6");
		v.addElement("box25= getBox25");
		v.addElement("box24= getBox24");
		v.addElement("box12= getBox12");
		v.addElement("box13= getBox13");
		v.addElement("printedFirstRow= getPrintedFirstRow");
		v.addElement("box1Dollars= getBox1Dollars");
		v.addElement("box1Cents= getBox1Cents");
		v.addElement("box5= getBox5");
		v.addElement("box24Barcode= getBox24Barcode");
		v.addElement("box25Barcode= getBox25Barcode");
		v.addElement("box26Barcode= getBox26Barcode");
		v.addElement("box27Barcode= getBox27Barcode");
		v.addElement("projectCode= getProjectCode");
		v.addElement("box28= getBox28");
		v.addElement("box29= getBox29");
		v.addElement("transportationPriority= getTransportationPriority");
		v.addElement("fmsCaseNum= getFmsCaseNum");
		v.addElement("releaseNum= getReleaseNum");
		return v;
	}

	public static Vector getVector(Collection in) {
		Vector out = new Vector();
		Iterator iterator = in.iterator();
		while (iterator.hasNext()) {
			out.addElement(new Usgov1348Data((Usgov1348ViewBean) iterator.next()));
		}
		return out;
	}
}
