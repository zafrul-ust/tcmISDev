package com.tcmis.client.peiprojects.erw;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.client.peiprojects.beans.PeiProjectDocumentBean;
import com.tcmis.client.peiprojects.beans.PeiProjectViewBean;
import com.tcmis.common.util.StringHandler;


/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2006
 * Company:      Haas TCM Inc.
 * @author       Nawaz Shaik
 * @version
 */

public class PeiProjectDocumentsData
{
  private String DOCUMENT_ID="";
  private String DOCUMENT_NAME="";
  /*private String ACTUAL_PERIOD_SAVINGS="";*/
  private String PROJECT_ID="";
  /*private String CURRENCY_ID="";
  private String PERIOD_NAME="";*/

    public PeiProjectDocumentsData(PeiProjectDocumentBean peiProjectDocumentBean)
    {
	  DOCUMENT_ID=StringHandler.emptyIfNull(peiProjectDocumentBean.getDocumentId());
	  DOCUMENT_NAME=StringHandler.emptyIfNull(peiProjectDocumentBean.getDocumentName());
	  /*ACTUAL_PERIOD_SAVINGS=StringHandler.emptyIfNull(peiProjectSavingsBean.getActualPeriodSavings());*/
	  PROJECT_ID=StringHandler.emptyIfNull(peiProjectDocumentBean.getProjectId());
	  /*CURRENCY_ID=StringHandler.emptyIfNull(peiProjectSavingsBean.getCurrencyId());
	  PERIOD_NAME=StringHandler.emptyIfNull(peiProjectSavingsBean.getPeriodName());*/
    }

  public String getDOCUMENT_IDDesc(){return DOCUMENT_ID;}
  public String getDOCUMENT_NAMEDesc(){return DOCUMENT_NAME;}
  //public String getACTUAL_PERIOD_SAVINGSDesc(){return ACTUAL_PERIOD_SAVINGS;}
  public String getPROJECT_IDDesc(){return PROJECT_ID;}
  //public String getCURRENCY_IDDesc(){return CURRENCY_ID;}
  //public String getPERIOD_NAMEDesc(){return PERIOD_NAME;}
  public static Vector getFieldVector()
  {
	Vector v=new Vector();
	v.addElement( "DOCUMENT_ID = getDOCUMENT_IDDesc" );
	v.addElement( "DOCUMENT_NAME = getDOCUMENT_NAMEDesc" );
	//v.addElement( "ACTUAL_PERIOD_SAVINGS = getACTUAL_PERIOD_SAVINGSDesc" );
	v.addElement( "PROJECT_ID = getPROJECT_IDDesc" );
	//v.addElement( "CURRENCY_ID = getCURRENCY_IDDesc" );
	//v.addElement( "PERIOD_NAME = getPERIOD_NAMEDesc" );
	return v;
 }

 public static Vector getVector(Collection peiProjectViewBeanCollection) {
	Vector out = new Vector();
	Iterator mainIterator = peiProjectViewBeanCollection.iterator();
	while (mainIterator.hasNext()) {
	 PeiProjectViewBean peiProjectViewBean = (
		PeiProjectViewBean) mainIterator.next(); ;

	 Collection docuemntCollection = peiProjectViewBean.getDocumentsCollection();
	 if (docuemntCollection.size() == 0) {
		PeiProjectDocumentBean peiProjectDocumentBean = new PeiProjectDocumentBean();

		PeiProjectDocumentsData peiProjectDocumentsData = new
		 PeiProjectDocumentsData(peiProjectDocumentBean);
		out.addElement(peiProjectDocumentsData);
	 }
	 else {
		Iterator documentIterator = docuemntCollection.iterator();
		while (documentIterator.hasNext()) {
		 PeiProjectDocumentBean peiProjectDocumentBean = (
			PeiProjectDocumentBean) documentIterator.next(); ;

		 PeiProjectDocumentsData peiProjectDocumentsData = new
			PeiProjectDocumentsData(peiProjectDocumentBean);
		 out.addElement(peiProjectDocumentsData);
		}
	 }
	}
	return out;
 }
}