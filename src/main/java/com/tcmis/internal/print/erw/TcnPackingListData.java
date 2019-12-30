package com.tcmis.internal.print.erw;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.internal.print.beans.TcnPalletViewBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class TcnPackingListData
{
	private String palletRfId;
	private String palletId;
	private String tcn;
	private String nsn;
	private String qty;
	private String unitOfIssue;

  public TcnPackingListData(TcnPalletViewBean bean) {
    palletRfId = StringHandler.emptyIfNull(bean.getPalletRfid());
	  palletId = StringHandler.emptyIfNull(bean.getPalletId());
    if (palletId.length() == 23)
    {
      palletId = palletId.substring(15,23);
    }
    tcn = StringHandler.emptyIfNull(bean.getTcn());
	  nsn = StringHandler.emptyIfNull(bean.getNsn());
	  qty = NumberHandler.emptyIfNull(bean.getQuantity());
	  unitOfIssue = StringHandler.emptyIfNull(bean.getUnitOfIssue());
  } //end of method


	//getters

public String getPalletRfId() {
	return palletRfId;
}

public String getPalletId() {
	return palletId;
}

public String getTcn() {
	return tcn;
}

public String getNsn() {
	return nsn;
}

public String getQty() {
	return qty;
}

public String getUnitOfIssue() {
	return unitOfIssue;
}

public static Vector getFieldVector() {
  Vector v = new Vector();
  v.addElement("palletRfId= getPalletRfId");
	v.addElement("palletId= getPalletId");
	v.addElement("tcn= getTcn");
	v.addElement("nsn= getNsn");
	v.addElement("qty= getQty");
	v.addElement("unitOfIssue= getUnitOfIssue");
  return v;
 }


public static Vector getVector(Collection in) {
	Vector out = new Vector();
	Iterator iterator = in.iterator();
	while (iterator.hasNext()) {
		out.addElement(new TcnPackingListData((TcnPalletViewBean) iterator.next()));
	}
	return out;
}  
}
