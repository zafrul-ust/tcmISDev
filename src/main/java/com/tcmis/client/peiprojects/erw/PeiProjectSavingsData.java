package com.tcmis.client.peiprojects.erw;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.client.peiprojects.beans.PeiProjectViewBean;
import com.tcmis.client.peiprojects.beans.PeiProjectSavingsBean;
import com.tcmis.common.util.StringHandler;


/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2006
 * Company:      Haas TCM Inc.
 * @author       Nawaz Shaik
 * @version
 */

public class PeiProjectSavingsData
{
  //PROJECT_ID, PERIOD_ID, PROJECTED_PERIOD_SAVINGS, ACTUAL_PERIOD_SAVINGS, CURRENCY_ID, PERIOD_NAME

  private String PERIOD_ID="";
  private String PROJECTED_PERIOD_SAVINGS="";
  private String ACTUAL_PERIOD_SAVINGS="";
  private String PROJECT_ID="";
  private String CURRENCY_ID="";
  private String PERIOD_NAME="";

    public PeiProjectSavingsData(PeiProjectSavingsBean peiProjectSavingsBean)
    {
	  PERIOD_ID=StringHandler.emptyIfNull(peiProjectSavingsBean.getPeriodId());
	  PROJECTED_PERIOD_SAVINGS=StringHandler.emptyIfNull(peiProjectSavingsBean.getProjectedPeriodSavings());
	  ACTUAL_PERIOD_SAVINGS=StringHandler.emptyIfNull(peiProjectSavingsBean.getActualPeriodSavings());
	  PROJECT_ID=StringHandler.emptyIfNull(peiProjectSavingsBean.getProjectId());
	  CURRENCY_ID=StringHandler.emptyIfNull(peiProjectSavingsBean.getCurrencyId());
	  PERIOD_NAME=StringHandler.emptyIfNull(peiProjectSavingsBean.getPeriodName());
    }

  public String getPERIOD_IDDesc(){return PERIOD_ID;}
  public String getPROJECTED_PERIOD_SAVINGSDesc(){return PROJECTED_PERIOD_SAVINGS;}
  public String getACTUAL_PERIOD_SAVINGSDesc(){return ACTUAL_PERIOD_SAVINGS;}
  public String getPROJECT_IDDesc(){return PROJECT_ID;}
  public String getCURRENCY_IDDesc(){return CURRENCY_ID;}
  public String getPERIOD_NAMEDesc(){return PERIOD_NAME;}
  public static Vector getFieldVector()
  {
	Vector v=new Vector();
	v.addElement( "PERIOD_ID = getPERIOD_IDDesc" );
	v.addElement( "PROJECTED_PERIOD_SAVINGS = getPROJECTED_PERIOD_SAVINGSDesc" );
	v.addElement( "ACTUAL_PERIOD_SAVINGS = getACTUAL_PERIOD_SAVINGSDesc" );
	v.addElement( "PROJECT_ID = getPROJECT_IDDesc" );
	v.addElement( "CURRENCY_ID = getCURRENCY_IDDesc" );
	v.addElement( "PERIOD_NAME = getPERIOD_NAMEDesc" );
	return v;
 }

 public static Vector getVector(Collection peiProjectViewBeanCollection) {
	Vector out = new Vector();
	Iterator mainIterator = peiProjectViewBeanCollection.iterator();
	while (mainIterator.hasNext()) {
	 PeiProjectViewBean peiProjectViewBean = (
		PeiProjectViewBean) mainIterator.next(); ;

	 Collection savingsCollection = peiProjectViewBean.getSavingsCollection();
	 if (savingsCollection.size() == 0) {
		PeiProjectSavingsBean peiProjectSavingsBean = new PeiProjectSavingsBean();

		PeiProjectSavingsData peiProjectSavingsData = new PeiProjectSavingsData(
		 peiProjectSavingsBean);
		out.addElement(peiProjectSavingsData);
	 }
	 else {

		Iterator savingsIterator = savingsCollection.iterator();
		while (savingsIterator.hasNext()) {
		 PeiProjectSavingsBean peiProjectSavingsBean = (
			PeiProjectSavingsBean) savingsIterator.next(); ;

		 PeiProjectSavingsData peiProjectSavingsData = new PeiProjectSavingsData(
			peiProjectSavingsBean);
		 out.addElement(peiProjectSavingsData);
		}
	 }
	}
	return out;
 }
}