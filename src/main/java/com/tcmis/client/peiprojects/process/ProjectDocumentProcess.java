package com.tcmis.client.peiprojects.process;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.math.BigDecimal;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.peiprojects.beans.ProjectDocumentInputBean;
import com.tcmis.client.peiprojects.beans.PeiProjectDocumentBean;
import com.tcmis.client.peiprojects.factory.PeiProjectDocumentBeanFactory;
import com.tcmis.client.peiprojects.factory.PeiProjectViewBeanFactory;

/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

 public class ProjectDocumentProcess
	extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ProjectDocumentProcess(String client) {
	 super(client);
	}
	
	public ProjectDocumentProcess(String client,String locale) {
	    super(client,locale);
   }

	public Collection getSearchResult(ProjectDocumentInputBean
	 projectDocumentInputBean) throws BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PeiProjectDocumentBeanFactory factory = new PeiProjectDocumentBeanFactory(
		dbManager);

	 SearchCriteria criteria = new SearchCriteria();
	 criteria.addCriterion("projectId", SearchCriterion.EQUALS,
		"" + projectDocumentInputBean.getProjectId() + "");

	 return factory.select(criteria);
	}

	public String addNewDocument(ProjectDocumentInputBean bean) throws
	 BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PeiProjectDocumentBeanFactory factory = new PeiProjectDocumentBeanFactory(dbManager);

	 if (bean.getCompanyId() == null ||
		bean.getCompanyId().length() == 0) {
		if ("TCM_OPS".equalsIgnoreCase(getClient())) {
		 bean.setCompanyId("Radian");
		}
		else {
		 bean.setCompanyId(getClient());
		}
	 }

	 if (bean.getCustomerDisplay() == null || bean.getCustomerDisplay().length() == 0)
	 {
		bean.setCustomerDisplay("Y");
	 }

	 try {
		if (bean.getTheFile() != null && bean.getTheFile().length() > (long) 0) {
		 //copy file to server
		 File f = bean.getTheFile();
		 String fileType = f.getName().substring(f.getName().lastIndexOf("."));
		 bean.setFileName(fileType);
		 ResourceLibrary resource = new ResourceLibrary("projectdocument");
		 try {
			File dir = new File(resource.getString("project.documentfile.path"));
			File file = File.createTempFile("" + bean.getProjectId() + "-",
			 "" + fileType + "", dir);

			FileHandler.copy(bean.getTheFile(), file);
			bean.setFileName(file.getName());
			//file.deleteOnExit();
		 }
		 catch (Exception e) {
			e.printStackTrace();
			BaseException be = new BaseException("Can't put document file on server:" +
			 e.getMessage());
			be.setRootCause(e);
			throw be;
		 }
		 String documentFileUrl = resource.getString("project.documentfile.hosturl") +
			resource.getString("project.documentfile.urlpath") + bean.getFileName();
		 bean.setDocumentUrl(documentFileUrl);

		 PeiProjectDocumentBean peiProjectDocumentBean = new PeiProjectDocumentBean();
		 peiProjectDocumentBean.setProjectId(bean.getProjectId());
		 peiProjectDocumentBean.setCustomerDisplay(bean.getCustomerDisplay());
		 peiProjectDocumentBean.setDocumentId(bean.getDocumentId());
		 peiProjectDocumentBean.setDocumentName(bean.getDocumentName());
		 peiProjectDocumentBean.setUrl(bean.getDocumentUrl());
		 peiProjectDocumentBean.setCompanyId(bean.getCompanyId());

		 try {
			factory.insert(peiProjectDocumentBean);
		 }
		 catch (BaseException ex) {
			ex.printStackTrace();
			BaseException be = new BaseException("Can't put document file on server:" +
			 ex.getMessage());
			be.setRootCause(ex);
			throw be;
		 }
		 return documentFileUrl;
		}
		else {
		 return null;
		}
	 }
	 catch (Exception ex1) {
		BaseException be = new BaseException("Can't put document file on server:" +
			 ex1.getMessage());
			be.setRootCause(ex1);
		ex1.printStackTrace();
		throw be;
	 }
	}

	public BigDecimal getNewDocumentId() throws BaseException {
		 DbManager dbManager = new DbManager(getClient(),getLocale());
		 PeiProjectViewBeanFactory factory = new PeiProjectViewBeanFactory(
			dbManager);

		 return factory.selectProjectId();
		}

	public void copyDocuments(BigDecimal oldProjectId,BigDecimal newProjectId) throws BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PeiProjectDocumentBeanFactory factory = new PeiProjectDocumentBeanFactory(
		dbManager);

	 factory.insertNewProjectDocuments(oldProjectId,newProjectId);
	}

	public int deleteDocument(PeiProjectDocumentBean peiProjectDocumentInputBean) throws
	 BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PeiProjectDocumentBeanFactory factory = new PeiProjectDocumentBeanFactory(
		dbManager);

	 SearchCriteria criteria = new SearchCriteria();
	 criteria.addCriterion("projectId",
		SearchCriterion.EQUALS,
		"" + peiProjectDocumentInputBean.getProjectId() + "");

	 criteria.addCriterion("documentId",
		SearchCriterion.EQUALS,
		"" + peiProjectDocumentInputBean.getDocumentId() + "");

   Collection documentBeanCollection = factory.select(criteria);
	 Iterator documentIterator = documentBeanCollection.iterator();
		 while (documentIterator.hasNext()) {
			PeiProjectDocumentBean peiProjectDocumentBean = (PeiProjectDocumentBean)
			 documentIterator.next();

		  String documentUrl = peiProjectDocumentBean.getUrl();
			String documentFilePath = documentUrl.substring(documentUrl.lastIndexOf("/"),documentUrl.length());

			ResourceLibrary resource = new ResourceLibrary("projectdocument");
			File documentFile = new File(""+resource.getString("project.documentfile.path")+""+documentFilePath+"");
			documentFile.delete();
		 }
	 int documentDeleteCount = factory.delete(criteria);

	 return documentDeleteCount;
	}

 }