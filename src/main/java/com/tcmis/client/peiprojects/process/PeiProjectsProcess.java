package com.tcmis.client.peiprojects.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.peiprojects.beans.PeiProjectDocumentBean;
import com.tcmis.client.peiprojects.beans.PeiProjectInputBean;
import com.tcmis.client.peiprojects.beans.PeiProjectKeywordBean;
import com.tcmis.client.peiprojects.beans.PeiProjectSavingsBean;
import com.tcmis.client.peiprojects.beans.PeiProjectViewBean;
import com.tcmis.client.peiprojects.beans.VvPeiProjectKeywordBean;
import com.tcmis.client.peiprojects.beans.VvPeiProjectStatusBean;
import com.tcmis.client.peiprojects.beans.VvProjectCategoryBean;
import com.tcmis.client.peiprojects.beans.VvProjectPriorityBean;
import com.tcmis.client.peiprojects.factory.FacilitiesForCompanyObjBeanFactory;
import com.tcmis.client.peiprojects.factory.PeiProjectDocumentBeanFactory;
import com.tcmis.client.peiprojects.factory.PeiProjectKeywordBeanFactory;
import com.tcmis.client.peiprojects.factory.PeiProjectSavingsBeanFactory;
import com.tcmis.client.peiprojects.factory.PeiProjectViewBeanFactory;
import com.tcmis.client.peiprojects.factory.VvPeiProjectKeywordBeanFactory;
import com.tcmis.common.admin.factory.FacilityBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.ExcelHandler;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class PeiProjectsProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PeiProjectsProcess (String client) {
    super(client);
  }
  public PeiProjectsProcess(String client,String locale) {
	    super(client,locale);
  }

	/****************************************************************************
	 * Returns a collection of PeiProjectViewBean for the criteria selected
	 * on the web page or for a particular project.
	 *
	 * @param peiProjectInputBean the <code>PeiProjectInputBean</code> has
	 *        the input parameters submited in the from
	 * @throws BaseException if there are problems communicating with the
	 *          database
	 ****************************************************************************/
	public Collection getsearchResult(PeiProjectInputBean peiProjectInputBean) throws
	 BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PeiProjectViewBeanFactory factory =
		new PeiProjectViewBeanFactory(dbManager);

	 SearchCriteria criteria = new SearchCriteria();

	 if (peiProjectInputBean.getProjectId() != null &&
		peiProjectInputBean.getProjectId().intValue() > 0) {
		criteria.addCriterion("projectId",
		 SearchCriterion.EQUALS,
		 ""+peiProjectInputBean.getProjectId()+"");
	 }
	 else
	 {
	 if (peiProjectInputBean.getProjectManagerId() != null &&
		peiProjectInputBean.getProjectManagerId().intValue() !=0	) {
		criteria.addCriterion("projectManagerId",
		 SearchCriterion.EQUALS,
		 ""+peiProjectInputBean.getProjectManagerId()+"");
	 }

	 /*if (peiProjectInputBean.getProjectCategory() != null &&
		!peiProjectInputBean.getProjectCategory().equalsIgnoreCase("ALL")) {
		criteria.addCriterion("projectCategory",
		 SearchCriterion.EQUALS,
		 peiProjectInputBean.getProjectCategory());
	 }

	 if (peiProjectInputBean.getProjectStatus() != null &&
		!peiProjectInputBean.getProjectStatus().equalsIgnoreCase("ALL")) {
		criteria.addCriterion("projectStatus",
		 SearchCriterion.EQUALS,
		 peiProjectInputBean.getProjectStatus());
	 }*/

	 if (peiProjectInputBean.getPriority() != null &&
		!peiProjectInputBean.getPriority().equalsIgnoreCase("ALL")) {
		criteria.addCriterion("priority",
		 SearchCriterion.EQUALS,
		 peiProjectInputBean.getPriority());
	 }

	 if (peiProjectInputBean.getFacilityId() != null &&
		!peiProjectInputBean.getFacilityId().equalsIgnoreCase("ALL") && peiProjectInputBean.getFacilityId().length() > 0) {
		criteria.addCriterion("facilityId",
		 SearchCriterion.EQUALS,
		 peiProjectInputBean.getFacilityId());
	 }

	 if (peiProjectInputBean.getBestPractice() != null &&
		!peiProjectInputBean.getBestPractice().equalsIgnoreCase("ALL")) {
		criteria.addCriterion("bestPractice",
		 SearchCriterion.EQUALS,
		 peiProjectInputBean.getBestPractice());
	 }

	 if (peiProjectInputBean.getSearch() != null &&
		peiProjectInputBean.getSearch().length() > 0) {
		criteria.addCriterion("search",
		 SearchCriterion.EQUALS,
		 peiProjectInputBean.getSearch());
	 }

	 if (peiProjectInputBean.getCompanyId() != null &&
		!peiProjectInputBean.getCompanyId().equalsIgnoreCase("ALL") && peiProjectInputBean.getCompanyId().length() > 0) {
		criteria.addCriterion("companyId",
		 SearchCriterion.EQUALS,
		 peiProjectInputBean.getCompanyId());
	 }

	 }
	 return factory.selectForProjectsList(criteria,peiProjectInputBean);
	}

	/****************************************************************************
	 * Returns a collection of facility beans for a company_id which are not hubs.
	 * @throws BaseException if there are problems communicating with the
	 *          database
	 ****************************************************************************/
	public Collection getFacilityBeanCollection() throws BaseException, Exception {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 FacilityBeanFactory factory = new FacilityBeanFactory(dbManager);
	 SearchCriteria criteria = new SearchCriteria();

	criteria.addCriterion("branchPlant",
	 SearchCriterion.IS,
	 null);

	return factory.select(criteria);
 }

 /****************************************************************************
	* Returns a collection of Keywords stored in the databse fo a particular project.
	*
	* @param peiProjectInputBean the <code>PeiProjectInputBean</code> has
	*        the input parameters submited in the from
	* @throws BaseException if there are problems communicating with the
	*          database
	****************************************************************************/

 public Collection getProjectKeywords(PeiProjectInputBean peiProjectInputBean) throws
	BaseException, Exception {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	PeiProjectKeywordBeanFactory factory = new PeiProjectKeywordBeanFactory(
	 dbManager);
	SearchCriteria criteria = new SearchCriteria();

	criteria.addCriterion("projectId",
	 SearchCriterion.EQUALS,
	 ""+peiProjectInputBean.getProjectId()+"");

	return factory.select(criteria);
 }

 public Collection copyAttributes(PeiProjectInputBean peiProjectInputBean,Collection
	 peiProjectViewBeanCollection) {
	 Collection finalPeiProjectViewBeanCollection = new Vector();
	 Iterator iterator = peiProjectViewBeanCollection.iterator();

	 while (iterator.hasNext()) {
	 PeiProjectViewBean savedPeiProjectViewBean = (PeiProjectViewBean) iterator.next();

	 savedPeiProjectViewBean.setProjectId(peiProjectInputBean.getProjectId());
	 savedPeiProjectViewBean.setCompanyId(peiProjectInputBean.getCompanyId());
	 savedPeiProjectViewBean.setActualCost(peiProjectInputBean.getActualCost());
	 savedPeiProjectViewBean.setActualFinishDate(peiProjectInputBean.getActualFinishDate());
	 savedPeiProjectViewBean.setApprovedDate(peiProjectInputBean.getApprovedDate());
	 savedPeiProjectViewBean.setBestPractice(peiProjectInputBean.getBestPractice());
	 savedPeiProjectViewBean.setComments(peiProjectInputBean.getComments());
	 savedPeiProjectViewBean.setCustomerContactId(peiProjectInputBean.getCustomerContactId());
	 savedPeiProjectViewBean.setFacilityId(peiProjectInputBean.getFacilityId());
	 savedPeiProjectViewBean.setPojectedCost(peiProjectInputBean.getPojectedCost());
	 savedPeiProjectViewBean.setPriority(peiProjectInputBean.getPriority());
	 savedPeiProjectViewBean.setProjectCategory(peiProjectInputBean.getProjectCategory());
	 savedPeiProjectViewBean.setProjectDesc(peiProjectInputBean.getProjectDesc());
	 savedPeiProjectViewBean.setProjectedFinistedDate(peiProjectInputBean.getProjectedFinistedDate());
	 savedPeiProjectViewBean.setProjectManagerId(peiProjectInputBean.getProjectManagerId());
	 savedPeiProjectViewBean.setProjectName(peiProjectInputBean.getProjectName());
	 savedPeiProjectViewBean.setProjectStatus(peiProjectInputBean.getProjectStatus());
	 savedPeiProjectViewBean.setStartDate(peiProjectInputBean.getStartDate());
	 savedPeiProjectViewBean.setInsertOrUpdate(peiProjectInputBean.getInsertOrUpdate());
	 savedPeiProjectViewBean.setProposedDateToClient(peiProjectInputBean.getProposedDateToClient());
	 savedPeiProjectViewBean.setGainShareDuration(peiProjectInputBean.getGainShareDuration());

	 String[] keywordsCollectionSelect = peiProjectInputBean.
		getKeywordsCollectionSelect();
	 Collection keywordsCollection = new Vector();
	 if (keywordsCollectionSelect != null && keywordsCollectionSelect.length > 0) {
		for (int loop = 0; loop < keywordsCollectionSelect.length; loop++) {
		 PeiProjectKeywordBean peiProjectKeywordBean = new PeiProjectKeywordBean();
		 peiProjectKeywordBean.setProjectId(peiProjectInputBean.
			getProjectId());
		 peiProjectKeywordBean.setKeywordId(keywordsCollectionSelect[loop]);
		 peiProjectKeywordBean.setCompanyId(peiProjectInputBean.getCompanyId());
		 keywordsCollection.add(peiProjectKeywordBean);
		}
	 }
	 savedPeiProjectViewBean.setKeywordsCollection(keywordsCollection);

	 savedPeiProjectViewBean.setDocumentsCollection(peiProjectInputBean.getDocumentsCollection());
	 savedPeiProjectViewBean.setSavingsCollection(peiProjectInputBean.getSavingsCollection());

	 finalPeiProjectViewBeanCollection.add(savedPeiProjectViewBean);
	}


	 return  finalPeiProjectViewBeanCollection;
	}

	public String updateProject(Collection peiProjectViewBeanCollection) throws
	 BaseException {
	 String errorMessage = null;
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PeiProjectViewBeanFactory factory = new PeiProjectViewBeanFactory(dbManager);

	 Iterator iterator = peiProjectViewBeanCollection.iterator();
	 while (iterator.hasNext()) {
		PeiProjectViewBean peiProjectViewBean = (PeiProjectViewBean) iterator.next();

   //log.debug("InsertOrUpdate "+peiProjectViewBean.getInsertOrUpdate()+"  Project Id  "+peiProjectViewBean.getProjectId()+"");
	 int updateCount = 0;
	 if (peiProjectViewBean.getInsertOrUpdate() != null &&
		"Insert".equalsIgnoreCase(peiProjectViewBean.getInsertOrUpdate())) {
		updateCount = factory.insert(peiProjectViewBean); //this can not be zero
	 }
	 else
	 {
		updateCount = factory.update(peiProjectViewBean); //this can not be zero
	 }

	 if (updateCount == 0) {
		errorMessage = "Could not update the project";
	 }

		PeiProjectKeywordBeanFactory keywordFactory = new
		 PeiProjectKeywordBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("projectId",
		 SearchCriterion.EQUALS,
		 "" + peiProjectViewBean.getProjectId() + "");
		keywordFactory.delete(criteria);

		Collection keywordBeanCollection = peiProjectViewBean.getKeywordsCollection();
		Iterator keywordIterator = keywordBeanCollection.iterator();
		while (keywordIterator.hasNext()) {
		 PeiProjectKeywordBean peiProjectKeywordBean = (PeiProjectKeywordBean) keywordIterator.next();
		 //log.debug("Project Id  "+peiProjectViewBean.getProjectId()+" KeywordId  "+peiProjectKeywordBean.getKeywordId()+"");
		 int insertKeywordCount = keywordFactory.insert(peiProjectKeywordBean); //this can not be zero
		 if (insertKeywordCount == 0) {
			errorMessage += "Could not update the project keywords.";
		 }
		}

		PeiProjectDocumentBeanFactory documentFactory = new
		 PeiProjectDocumentBeanFactory(dbManager);
		Collection documentBeanCollection = peiProjectViewBean.
		 getDocumentsCollection();

		//If submit as new make copies of the documents

		Iterator documentIterator = documentBeanCollection.iterator();
		while (documentIterator.hasNext()) {
		 PeiProjectDocumentBean peiProjectDocumentBean = (PeiProjectDocumentBean)
			documentIterator.next();
		 //log.debug("Project Id  "+peiProjectViewBean.getProjectId()+" "+peiProjectDocumentBean.getDocumentId()+"");

		 if (peiProjectDocumentBean.getDelete() != null &&
			"Y".equalsIgnoreCase(peiProjectDocumentBean.getDelete())) {
			/*criteria = new SearchCriteria();
					criteria.addCriterion("projectId",
			 SearchCriterion.EQUALS,
			 "" + peiProjectViewBean.getProjectId() + "");
					criteria.addCriterion("documentId",
			 SearchCriterion.EQUALS,
			 "" + peiProjectDocumentBean.getDocumentId() + "");
					int documentDeleteCount = documentFactory.delete(criteria);*/
			ProjectDocumentProcess projectDocumentProcess = new ProjectDocumentProcess(
			 getClient());
			int documentDeleteCount = projectDocumentProcess.deleteDocument(
			 peiProjectDocumentBean);
			if (documentDeleteCount == 0) {
			 errorMessage += "Could not delete Document.";
			}
		 }
		}

		PeiProjectSavingsBeanFactory savingsFactory = new
		 PeiProjectSavingsBeanFactory(dbManager);
		Collection savingsBeanCollection = peiProjectViewBean.getSavingsCollection();
		Iterator savingsIterator = savingsBeanCollection.iterator();
		while (savingsIterator.hasNext()) {
		 PeiProjectSavingsBean peiProjectSavingsBean = (PeiProjectSavingsBean)
			savingsIterator.next();
		 int savingsUpdateCount = 0;
		 if (peiProjectSavingsBean.getLineStatus() != null &&
			"New".equalsIgnoreCase(peiProjectSavingsBean.getLineStatus())) {
			savingsUpdateCount = savingsFactory.insert(peiProjectSavingsBean);
		 }
		 else if (peiProjectViewBean.getInsertOrUpdate() != null &&
		"Insert".equalsIgnoreCase(peiProjectViewBean.getInsertOrUpdate())) {
		  savingsUpdateCount = savingsFactory.insert(peiProjectSavingsBean);
		 }
		 else {
			savingsUpdateCount = savingsFactory.update(peiProjectSavingsBean);
		 }
		 if (savingsUpdateCount == 0) {
			errorMessage += "Could not Update Cost Information.";
		 }
		}
	 }

	 return errorMessage;
	}

	public BigDecimal getNewProjectId() throws BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PeiProjectViewBeanFactory factory = new PeiProjectViewBeanFactory(
		dbManager);

	 return factory.selectProjectId();
	}

	public Collection getNewProjectCollection() throws BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PeiProjectViewBeanFactory factory = new PeiProjectViewBeanFactory(
		dbManager);

	Collection newProjectCollection = new Vector();
	PeiProjectViewBean peiProjectViewBean = new PeiProjectViewBean();
	peiProjectViewBean.setProjectId(factory.selectProjectId());
	peiProjectViewBean.setInsertOrUpdate("Insert");

	VvPeiProjectKeywordBeanFactory baseKeywordFactory = new
	 VvPeiProjectKeywordBeanFactory(dbManager);
	peiProjectViewBean.setBaseKeywordsCollection(baseKeywordFactory.
	 selectKeywordsNotInProject(
	 "" + peiProjectViewBean.getProjectId().intValue() + ""));

	newProjectCollection.add(peiProjectViewBean);
	return newProjectCollection;
 }

 public String buildProjectPdf(PeiProjectInputBean peiProjectInputBean,Collection vvKeywordCollection,Collection vvStatusCollection,Collection vvCategoryCollection,Collection vvPriorityCollection) throws BaseException {
	String pdfUrl = "";

	DbManager dbManager = new DbManager(getClient(),getLocale());
	PeiProjectViewBeanFactory factory =
	 new PeiProjectViewBeanFactory(dbManager);

	SearchCriteria criteria = new SearchCriteria();

	if (peiProjectInputBean.getPrintProjectIdList() != null &&
	 peiProjectInputBean.getPrintProjectIdList().length() > 0) {
	 criteria.addCriterion("projectId",
		SearchCriterion.IN,
		"" + peiProjectInputBean.getPrintProjectIdList() + "");
	}

	/*PeiProjectInputBean peiProjectInputBean = new PeiProjectInputBean();
	//setting a project Id so that the factory also queries the child tables
	//peiProjectInputBean.setProjectId(new BigDecimal("86405"));*/

	Collection peiProjectViewBeanCollection = factory.selectForProjectsList(criteria,peiProjectInputBean);

	PrintPdfProcess printPdfProcess = new PrintPdfProcess(this.getClient());
	try {
	 pdfUrl = printPdfProcess.buildProjectPdf(peiProjectViewBeanCollection,vvKeywordCollection,vvStatusCollection,vvCategoryCollection,vvPriorityCollection);
	}
	catch (Exception ex1) {
	 ex1.printStackTrace();
	 pdfUrl = "";
	}

	return pdfUrl;
 }

 public ExcelHandler writeExcelFile(Collection searchCollection, Collection vvKeywordCollection,Collection vvStatusCollection,Collection vvCategoryCollection,Collection vvPriorityCollection,java.util.Locale locale) throws
		BaseException, Exception {
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  ExcelHandler pw = new ExcelHandler(library);
	  
	  pw.addTable();

//	  write column headers
	  pw.addRow();
     String[] headerkeys = {
    "label.projectcode",
	"label.company",
	"label.facilityid",
	"peiproject.label.projectname",
	"peiproject.label.projectdesc",
	
	"label.category",	
	"peiproject.label.hassprojectmanager",
	"peiproject.label.clientcontact",
	"peiproject.label.bestpractice",
	"label.status",
	
	"label.priority",	
	"peiproject.label.proposeddate",
	"label.ApprovedDate",
	"peiproject.label.gainshareduration",
	"label.startDate",
	
	"peiproject.label.projectedfinishdate",	
	"peiproject.label.actualfinishdate",
	"peiproject.label.projectedcost",
	"peiproject.label.actualcost",
	"peiproject.label.totalprojectedsavings",
	
	"peiproject.label.totalactualsavings",
	"label.keywords"} ;
    
        int[] widths = {11,10,12,15,0,
                        9,14,11,16,9,
                        9,10,12,0,10,
                        12,16,14,12,11,
                        10,40};
        int [] types = {0,0,0,0, ExcelHandler.TYPE_PARAGRAPH,
                        0,0,0,0,0,
                        0,ExcelHandler.TYPE_CALENDAR, ExcelHandler.TYPE_CALENDAR,0, ExcelHandler.TYPE_CALENDAR,
                        ExcelHandler.TYPE_CALENDAR,ExcelHandler.TYPE_CALENDAR,ExcelHandler.TYPE_NUMBER,ExcelHandler.TYPE_NUMBER,ExcelHandler.TYPE_NUMBER,
                        ExcelHandler.TYPE_NUMBER,0} ;
         int [] aligns = {0,0,0,0,0,
                        0,0,0,0,0,
                        0,0,0,0,0,
                        0,0,0,0,0,
                        0,0} ;       
        pw.applyColumnHeader(headerkeys, types, widths, aligns);
	//print rows
	Iterator i11 = searchCollection.iterator();
	while (i11.hasNext()) {
		pw.addRow();
		PeiProjectViewBean peiProjectViewBean = (
			PeiProjectViewBean) i11.next(); ;

		pw.addCell(peiProjectViewBean.getProjectId());
		pw.addCell(peiProjectViewBean.getCompanyId());
		pw.addCell(peiProjectViewBean.getFacilityId());
		pw.addCell(peiProjectViewBean.getProjectName());
		pw.addCell(peiProjectViewBean.getProjectDesc());

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
		pw.addCell(selectedCategoryDesc);

		pw.addCell(peiProjectViewBean.getProjectManager());
 	  pw.addCell(peiProjectViewBean.getCustomerContactManager());

	 if (peiProjectViewBean.getBestPractice() !=null && "Y".equalsIgnoreCase(peiProjectViewBean.getBestPractice()))
	 {
		 pw.addCell("Yes");
	 }
	 else
	 {
		 pw.addCell("");
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
	 pw.addCell(selectedStatusDesc);

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
	 pw.addCell(selectedPriorityDesc);

     pw.addCell(peiProjectViewBean.getProposedDateToClient());
	 pw.addCell(peiProjectViewBean.getApprovedDate());
	 pw.addCell(peiProjectViewBean.getGainShareDuration());
	 pw.addCell(peiProjectViewBean.getStartDate());
	 pw.addCell(peiProjectViewBean.getProjectedFinistedDate());
	 pw.addCell(peiProjectViewBean.getActualFinishDate());
	 pw.addCell(peiProjectViewBean.getPojectedCost());
	 pw.addCell(peiProjectViewBean.getActualCost());
	 pw.addCell(peiProjectViewBean.getTotalProjectedPeriodSavings());
	 pw.addCell(peiProjectViewBean.getTotalActualPeriodSavings());
/*	 pw.addCell(StringHandler.emptyIfNull(peiProjectViewBean.getPojectedCost()));
	 pw.addCell(StringHandler.emptyIfNull(peiProjectViewBean.getActualCost()));
	 pw.addCell(StringHandler.emptyIfNull(peiProjectViewBean.getTotalProjectedPeriodSavings()));
	 pw.addCell(StringHandler.emptyIfNull(peiProjectViewBean.getTotalActualPeriodSavings()));
*/
  
	 Collection keywordCollection = peiProjectViewBean.getKeywordsCollection();
	 if (keywordCollection == null) {
		 pw.addCell("");
		 continue;
	 }
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

		 if (vvPeiProjectKeywordBean.getKeywordId().equalsIgnoreCase(selectedKeyWordId)) {
			selectedKeyWordDesc = vvPeiProjectKeywordBean.getKeywordDesc();
		 }
		}

		if (keywordCount > 0) {
		 selectedKeyWords += ",";
		}
		selectedKeyWords += "" +selectedKeyWordDesc+ "";
		keywordCount++;
	 }   
	 pw.addCell(selectedKeyWords);
	}  
	return pw;
 }

 public Collection getCompanyFacilityData() throws BaseException {
		 //log.debug("Calling getCompanyFacilityData" + getClient());
		 SearchCriteria criteria = new SearchCriteria();
		 /*criteria.addCriterion("personnelId",
													 SearchCriterion.EQUALS,
													 "" + personnelId);*/

		 DbManager dbManager = new DbManager(getClient(),getLocale());
		 FacilitiesForCompanyObjBeanFactory factory = new FacilitiesForCompanyObjBeanFactory(dbManager);
		 return factory.selectObject(criteria);
	 }

 /****************************************************************************
	* Removes the selected Keywords from the base vvkeywrod collection
		*
		* @param peiProjectKeywordBeanCollection the peiProjectKeywordBeanCollection has
		*        the PeiProjectViewBean beans for a projectId
		* @param peiProjectKeywordBeanCollection has the vv values collection for the keywords
		* @throws BaseException if there are problems communicating with the
		*          database
		****************************************************************************/
	 /*public Collection removeSelectedKeywordFromBase(Collection
		peiProjectViewBeanCollection, Collection peiProjectKeywordBeanCollection) throws
		BaseException {
		Iterator iterator = peiProjectViewBeanCollection.iterator();
		while (iterator.hasNext()) {
		 PeiProjectViewBean currentPeiProjectViewBean = (PeiProjectViewBean) iterator.
			next();

		 Collection keywordsCollectionSelect = currentPeiProjectViewBean.
			getKeywordsCollection();
		 Collection finalBaseKeywordsCollectionSelect = new Vector();
		 Iterator baseKeywordsIterator = peiProjectKeywordBeanCollection.iterator();
		 Iterator keywordsIterator = keywordsCollectionSelect.iterator();
		 while (baseKeywordsIterator.hasNext()) {
			VvPeiProjectKeywordBean vvPeiProjectKeywordBean = (VvPeiProjectKeywordBean)
			 baseKeywordsIterator.
			 next();
			boolean keywordSelected = false;
			String currentKeyword = vvPeiProjectKeywordBean.getKeywordId();
			while (keywordsIterator.hasNext()) {
			 PeiProjectKeywordBean peiProjectKeywordBean = (PeiProjectKeywordBean)
				keywordsIterator.
				next();

			 if (currentKeyword.equalsIgnoreCase(peiProjectKeywordBean.getKeywordId())) {
				keywordSelected = true;
				//log.debug("currentKeyword  "+currentKeyword+"");
			 }
			}
			if (!keywordSelected) {
			 finalBaseKeywordsCollectionSelect.add(vvPeiProjectKeywordBean);
			}
		 }
		 currentPeiProjectViewBean.setBaseKeywordsCollection(
			finalBaseKeywordsCollectionSelect);
		}
		return peiProjectViewBeanCollection;
	 }*/
}