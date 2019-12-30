package com.tcmis.client.peiprojects.erw;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.client.peiprojects.beans.PeiProjectKeywordBean;
import com.tcmis.client.peiprojects.beans.PeiProjectViewBean;
import com.tcmis.client.peiprojects.beans.VvPeiProjectKeywordBean;
import com.tcmis.client.peiprojects.beans.VvPeiProjectStatusBean;
import com.tcmis.client.peiprojects.beans.VvProjectCategoryBean;
import com.tcmis.client.peiprojects.beans.VvProjectPriorityBean;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2006
 * Company:      Haas TCM Inc.
 * @author       Nawaz Shaik
 * @version
 */

public class PeiProjectData
{
  private String CUSTOMER_CONTACT_ID="";
  private String KEYWORDS="";
  private String PROJECT_MANAGER="";
  private String PROJECT_ID="";
  private String PROJECT_NAME="";
  private String PROJECT_DESC="";
  private String PROJECT_CATEGORY="";
  private String PROJECT_MANAGER_ID="";
  private String FACILITY_ID="";
  private String APPROVED_DATE="";
  private String START_DATE="";
  private String PROJECTED_FINISTED_DATE="";
  private String POJECTED_COST="";
  private String ACTUAL_COST="";
  private String COMMENTS="";
  private String BEST_PRACTICE="";
  private String PROJECT_STATUS="";
  private String ACTUAL_FINISH_DATE="";
  private String PRIORITY="";
  private String CUSTOMER_CONTACT_MANAGER="";
  private String CUSTOMER_CONTACT_EMAIL="";
  private String CUSTOMER_CONTACT_PHONE="";
  private String PROJECT_MANAGER_PHONE="";
  private String PROJECT_MANAGER_EMAIL="";
  private String TOTAL_PROJECTED_PERIOD_SAVINGS="";
  private String TOTAL_ACTUAL_PERIOD_SAVINGS="";
	private String COMPANY_ID="";
	private String PROPOSED_DATE_TO_CLIENT="";
 	private String GAIN_SHARE_DURATION="";

    public PeiProjectData(PeiProjectViewBean peiProjectViewBean,Collection vvKeywordCollection,Collection vvStatusCollection,Collection vvCategoryCollection,Collection vvPriorityCollection)
    {
		 CUSTOMER_CONTACT_ID = StringHandler.emptyIfNull(peiProjectViewBean.
			getCustomerContactId());
		 Collection keywordCollection = peiProjectViewBean.getKeywordsCollection();
		 Iterator keywordIterator = keywordCollection.iterator();
		 String selectedKeyWords = "";
		 int keywordCount = 0;
		 while (keywordIterator.hasNext()) {
			PeiProjectKeywordBean peiProjectKeywordBean = (PeiProjectKeywordBean)
			 keywordIterator.next(); ;

			String selectedKeyWordId = peiProjectKeywordBean.getKeywordId();
			String selectedKeyWordDesc = "";
			Iterator vvKeywordIterator = vvKeywordCollection.iterator();
			while (vvKeywordIterator.hasNext()) {
			 VvPeiProjectKeywordBean vvPeiProjectKeywordBean = (VvPeiProjectKeywordBean)
				vvKeywordIterator.next(); ;

			 if (vvPeiProjectKeywordBean.getKeywordId().equalsIgnoreCase(
				selectedKeyWordId)) {
				selectedKeyWordDesc = vvPeiProjectKeywordBean.getKeywordDesc();
			 }
			}

			if (keywordCount > 0) {
			 selectedKeyWords += ",";
			}
			selectedKeyWords += "" +selectedKeyWordDesc+ "";
			keywordCount++;
		 }
		 KEYWORDS = selectedKeyWords;

	  PROJECT_MANAGER=StringHandler.emptyIfNull(peiProjectViewBean.getProjectManager());
	  PROJECT_ID=StringHandler.emptyIfNull(peiProjectViewBean.getProjectId());
	  PROJECT_NAME=StringHandler.emptyIfNull(peiProjectViewBean.getProjectName());
	  PROJECT_DESC=StringHandler.emptyIfNull(peiProjectViewBean.getProjectDesc());

 	  String selectedCategoryId = peiProjectViewBean.getProjectCategory();
		String selectedCategoryDesc = "";
		Iterator vvCategoryIterator = vvCategoryCollection.iterator();
		while (vvCategoryIterator.hasNext()) {
		 VvProjectCategoryBean vvProjectCategoryBean = (VvProjectCategoryBean)
			vvCategoryIterator.next(); ;

	  if (vvProjectCategoryBean.getProjectCategory().equalsIgnoreCase(
		 selectedCategoryId)) {
		 selectedCategoryDesc = vvProjectCategoryBean.getProjectCategoryDesc();
	  }
	  }
	  PROJECT_CATEGORY=selectedCategoryDesc;

	  PROJECT_MANAGER_ID=StringHandler.emptyIfNull(peiProjectViewBean.getProjectManagerId());
	  FACILITY_ID=StringHandler.emptyIfNull(peiProjectViewBean.getFacilityId());
	  APPROVED_DATE=StringHandler.emptyIfNull(DateHandler.formatDate(peiProjectViewBean.getApprovedDate(),"MM/dd/yyyy"));
	  START_DATE=StringHandler.emptyIfNull(DateHandler.formatDate(peiProjectViewBean.getStartDate(),"MM/dd/yyyy"));
	  PROJECTED_FINISTED_DATE=StringHandler.emptyIfNull(DateHandler.formatDate(peiProjectViewBean.getProjectedFinistedDate(),"MM/dd/yyyy"));
		ACTUAL_FINISH_DATE=StringHandler.emptyIfNull(DateHandler.formatDate(peiProjectViewBean.getActualFinishDate(),"MM/dd/yyyy"));

	  POJECTED_COST=StringHandler.emptyIfNull(peiProjectViewBean.getPojectedCost());
	  ACTUAL_COST=StringHandler.emptyIfNull(peiProjectViewBean.getActualCost());
	  COMMENTS=StringHandler.emptyIfNull(peiProjectViewBean.getComments());
		if (peiProjectViewBean.getBestPractice() !=null && "Y".equalsIgnoreCase(peiProjectViewBean.getBestPractice()))
		{
			 BEST_PRACTICE = "Yes";
		}

   	String selectedStatusId = peiProjectViewBean.getProjectStatus();
		String selectedStatusDesc = "";
		Iterator vvStatusIterator = vvStatusCollection.iterator();
		while (vvStatusIterator.hasNext()) {
		 VvPeiProjectStatusBean vvPeiProjectStatusBean = (VvPeiProjectStatusBean)
			vvStatusIterator.next(); ;

		 if (vvPeiProjectStatusBean.getProjectStatus().equalsIgnoreCase(
			selectedStatusId)) {
			selectedStatusDesc = vvPeiProjectStatusBean.getProjectStatusDesc();
		 }
		}
	  PROJECT_STATUS=selectedStatusDesc;

		String selectedPriorityId = peiProjectViewBean.getPriority();
		String selectedPriorityDesc = "";
		Iterator vvPriorityIterator = vvPriorityCollection.iterator();
		while (vvPriorityIterator.hasNext()) {
		 VvProjectPriorityBean vvProjectPriorityBean = (VvProjectPriorityBean)
			vvPriorityIterator.next(); ;

		 if (vvProjectPriorityBean.getProjectPriority().equalsIgnoreCase(
			selectedPriorityId)) {
			selectedPriorityDesc = vvProjectPriorityBean.getProjectPriorityDesc();
		 }
		}
		PRIORITY=selectedPriorityDesc;

	  CUSTOMER_CONTACT_MANAGER=StringHandler.emptyIfNull(peiProjectViewBean.getCustomerContactManager());
	  CUSTOMER_CONTACT_EMAIL=StringHandler.emptyIfNull(peiProjectViewBean.getCustomerContactEmail());
	  CUSTOMER_CONTACT_PHONE=StringHandler.emptyIfNull(peiProjectViewBean.getCustomerContactPhone());
	  PROJECT_MANAGER_PHONE=StringHandler.emptyIfNull(peiProjectViewBean.getProjectManagerPhone());
	  PROJECT_MANAGER_EMAIL=StringHandler.emptyIfNull(peiProjectViewBean.getProjectManagerEmail());
	  TOTAL_PROJECTED_PERIOD_SAVINGS=StringHandler.emptyIfNull(peiProjectViewBean.getTotalProjectedPeriodSavings());
	  TOTAL_ACTUAL_PERIOD_SAVINGS=StringHandler.emptyIfNull(peiProjectViewBean.getTotalActualPeriodSavings());
		COMPANY_ID=StringHandler.emptyIfNull(peiProjectViewBean.getCompanyId());
		PROPOSED_DATE_TO_CLIENT=StringHandler.emptyIfNull(DateHandler.formatDate(peiProjectViewBean.getProposedDateToClient(),"MM/dd/yyyy"));
		GAIN_SHARE_DURATION=StringHandler.emptyIfNull(peiProjectViewBean.getGainShareDuration());
    }

  public String getCUSTOMER_CONTACT_IDDesc(){return CUSTOMER_CONTACT_ID;}
  public String getKEYWORDSDesc(){return KEYWORDS;}
  public String getPROJECT_MANAGERDesc(){return PROJECT_MANAGER;}
  public String getPROJECT_IDDesc(){return PROJECT_ID;}
  public String getPROJECT_NAMEDesc(){return PROJECT_NAME;}
  public String getPROJECT_DESCDesc(){return PROJECT_DESC;}
  public String getPROJECT_CATEGORYDesc(){return PROJECT_CATEGORY;}
  public String getPROJECT_MANAGER_IDDesc(){return PROJECT_MANAGER_ID;}
  public String getFACILITY_IDDesc(){return FACILITY_ID;}
  public String getAPPROVED_DATEDesc(){return APPROVED_DATE;}
  public String getSTART_DATEDesc(){return START_DATE;}
  public String getPROJECTED_FINISTED_DATEDesc(){return PROJECTED_FINISTED_DATE;}
  public String getPOJECTED_COSTDesc(){return POJECTED_COST;}
  public String getACTUAL_COSTDesc(){return ACTUAL_COST;}
  public String getCOMMENTSDesc(){return COMMENTS;}
  public String getBEST_PRACTICEDesc(){return BEST_PRACTICE;}
  public String getPROJECT_STATUSDesc(){return PROJECT_STATUS;}
  public String getACTUAL_FINISH_DATEDesc(){return ACTUAL_FINISH_DATE;}
  public String getPRIORITYDesc(){return PRIORITY;}
  public String getCUSTOMER_CONTACT_MANAGERDesc(){return CUSTOMER_CONTACT_MANAGER;}
  public String getCUSTOMER_CONTACT_EMAILDesc(){return CUSTOMER_CONTACT_EMAIL;}
  public String getCUSTOMER_CONTACT_PHONEDesc(){return CUSTOMER_CONTACT_PHONE;}
  public String getPROJECT_MANAGER_PHONEDesc(){return PROJECT_MANAGER_PHONE;}
  public String getPROJECT_MANAGER_EMAILDesc(){return PROJECT_MANAGER_EMAIL;}
  public String getTOTAL_PROJECTED_PERIOD_SAVINGSDesc(){return TOTAL_PROJECTED_PERIOD_SAVINGS;}
  public String getTOTAL_ACTUAL_PERIOD_SAVINGSDesc(){return TOTAL_ACTUAL_PERIOD_SAVINGS;}
	public String getCOMPANY_IDDesc(){return COMPANY_ID;}
	public String getPROPOSED_DATE_TO_CLIENTDesc(){return PROPOSED_DATE_TO_CLIENT;}
	public String getGAIN_SHARE_DURATIONDesc(){return GAIN_SHARE_DURATION;}

  public static Vector getFieldVector()
  {
	Vector v=new Vector();
	v.addElement( "CUSTOMER_CONTACT_ID = getCUSTOMER_CONTACT_IDDesc" );
	v.addElement( "KEYWORDS = getKEYWORDSDesc" );
	v.addElement( "PROJECT_MANAGER = getPROJECT_MANAGERDesc" );
	v.addElement( "PROJECT_ID = getPROJECT_IDDesc" );
	v.addElement( "PROJECT_NAME = getPROJECT_NAMEDesc" );
	v.addElement( "PROJECT_DESC = getPROJECT_DESCDesc" );
	v.addElement( "PROJECT_CATEGORY = getPROJECT_CATEGORYDesc" );
	v.addElement( "PROJECT_MANAGER_ID = getPROJECT_MANAGER_IDDesc" );
	v.addElement( "FACILITY_ID = getFACILITY_IDDesc" );
	v.addElement( "APPROVED_DATE = getAPPROVED_DATEDesc" );
	v.addElement( "START_DATE = getSTART_DATEDesc" );
	v.addElement( "PROJECTED_FINISTED_DATE = getPROJECTED_FINISTED_DATEDesc" );
	v.addElement( "POJECTED_COST = getPOJECTED_COSTDesc" );
	v.addElement( "ACTUAL_COST = getACTUAL_COSTDesc" );
	v.addElement( "COMMENTS = getCOMMENTSDesc" );
	v.addElement( "BEST_PRACTICE = getBEST_PRACTICEDesc" );
	v.addElement( "PROJECT_STATUS = getPROJECT_STATUSDesc" );
	v.addElement( "ACTUAL_FINISH_DATE = getACTUAL_FINISH_DATEDesc" );
	v.addElement( "PRIORITY = getPRIORITYDesc" );
	v.addElement( "CUSTOMER_CONTACT_MANAGER = getCUSTOMER_CONTACT_MANAGERDesc" );
	v.addElement( "CUSTOMER_CONTACT_EMAIL = getCUSTOMER_CONTACT_EMAILDesc" );
	v.addElement( "CUSTOMER_CONTACT_PHONE = getCUSTOMER_CONTACT_PHONEDesc" );
	v.addElement( "PROJECT_MANAGER_PHONE = getPROJECT_MANAGER_PHONEDesc" );
	v.addElement( "PROJECT_MANAGER_EMAIL = getPROJECT_MANAGER_EMAILDesc" );
	v.addElement( "TOTAL_PROJECTED_PERIOD_SAVINGS = getTOTAL_PROJECTED_PERIOD_SAVINGSDesc" );
	v.addElement( "TOTAL_ACTUAL_PERIOD_SAVINGS = getTOTAL_ACTUAL_PERIOD_SAVINGSDesc" );
	v.addElement( "COMPANY_ID = getCOMPANY_IDDesc" );
	v.addElement( "PROPOSED_DATE_TO_CLIENT = getPROPOSED_DATE_TO_CLIENTDesc" );
	v.addElement( "GAIN_SHARE_DURATION = getGAIN_SHARE_DURATIONDesc" );

	return v;
  }

	 public static Vector getVector(Collection peiProjectViewBeanCollection,Collection vvKeywordCollection,Collection vvStatusCollection,Collection vvCategoryCollection,Collection vvPriorityCollection) {
		 Vector out = new Vector();
		 if (peiProjectViewBeanCollection.size() == 0)
		 {
				PeiProjectViewBean peiProjectViewBean = new PeiProjectViewBean();

				PeiProjectData peiProjectData = new PeiProjectData(peiProjectViewBean,
				 vvKeywordCollection, vvStatusCollection, vvCategoryCollection,
				 vvPriorityCollection);
				out.addElement(peiProjectData);
	   }
	   else
		 {
				Iterator mainIterator = peiProjectViewBeanCollection.iterator();
				while (mainIterator.hasNext()) {
				 PeiProjectViewBean peiProjectViewBean = (
					PeiProjectViewBean) mainIterator.next(); ;

				 PeiProjectData peiProjectData = new PeiProjectData(peiProjectViewBean,
					vvKeywordCollection, vvStatusCollection, vvCategoryCollection,
					vvPriorityCollection);
				 out.addElement(peiProjectData);
		 }
		 }
		 return out;
		}
}
