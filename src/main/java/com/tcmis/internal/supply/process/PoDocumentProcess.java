package com.tcmis.internal.supply.process;

import java.io.File;

import java.util.Collection;
import java.util.Iterator;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

import com.tcmis.internal.supply.beans.PoDocumentInputBean;
import com.tcmis.internal.supply.factory.PoDocumentViewBeanFactory;

/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

 public class PoDocumentProcess
	extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PoDocumentProcess(String client) {
	 super(client);
	}
	
	public PoDocumentProcess(String client,String locale) {
	    super(client,locale);
    }


	public Collection getSearchResult(PoDocumentInputBean
	 poDocumentInputBean) throws BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	PoDocumentViewBeanFactory factory = new PoDocumentViewBeanFactory(
		dbManager);
	
	 SearchCriteria criteria = new SearchCriteria();
	 if(poDocumentInputBean.getRadianPo()!= null)
	 {
	 criteria.addCriterion("radianPo", SearchCriterion.EQUALS,poDocumentInputBean.getRadianPo().toString());
	 }
	
	 return factory.select(criteria);
	}

	public String addNewDocument(PoDocumentInputBean bean) throws
	 BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 PoDocumentViewBeanFactory factory = new
	 PoDocumentViewBeanFactory(dbManager);

	 if (bean.getTheFile() != null && bean.getTheFile().length() > (long) 0) {
		//copy file to server
		File f = bean.getTheFile();
		String fileType = f.getName().substring(f.getName().lastIndexOf("."));
		bean.setFileName(fileType);
		ResourceLibrary resource = new ResourceLibrary("podocument");
		try {
		 File dir = new File(resource.getString("po.documentfile.path"));
		 File file = File.createTempFile("" + bean.getRadianPo() + " -",
			"" + fileType + "", dir);

		 FileHandler.copy(bean.getTheFile(), file);
		 bean.setFileName(file.getName());
		 //file.deleteOnExit();
		}
		catch (Exception e) {
		 BaseException be = new BaseException("Can't put document file on server:" +
			e.getMessage());
		 be.setRootCause(e);
		 throw be;
		}
		String documentFileUrl = resource.getString("po.documentfile.hosturl") +
		 resource.getString("po.documentfile.urlpath") + bean.getFileName();
		//log.info("addNewDocument documentFileUrl     " + documentFileUrl + "");
		bean.setDocumentUrl(documentFileUrl);
		try {
		 factory.insert(bean);
		}
		catch (BaseException ex) {
		 return null;
		}
		return documentFileUrl;
	 }
	 else {
		return null;
	 }
	}

 public int deleteDocument(Collection poDocumentInputBeanCollection) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	PoDocumentViewBeanFactory factory = new PoDocumentViewBeanFactory(
	 dbManager);
	int documentDeleteCount = 0;

	Iterator documentIterator = poDocumentInputBeanCollection.iterator();
	while (documentIterator.hasNext()) {
	 PoDocumentInputBean poDocumentInputBean = (
		PoDocumentInputBean) documentIterator.next();

	 
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("documentId",
		 SearchCriterion.EQUALS,
		 "" + poDocumentInputBean.getDocumentId() + "");

		criteria.addCriterion("radianPo",
		 SearchCriterion.EQUALS,
		 "" + poDocumentInputBean.getRadianPo() + "");
		
		
		String documentUrl = poDocumentInputBean.getDocumentUrl();
		String documentFilePath = documentUrl.substring(documentUrl.lastIndexOf("/"),
		 documentUrl.length());

		if (documentFilePath.length() > 0)
		{
		ResourceLibrary resource = new ResourceLibrary("podocument");
		File documentFile = new File("" + resource.getString("po.documentfile.path") + "" + documentFilePath + "");
		log.info("Deleteing Document " + resource.getString("po.documentfile.path") + "" + documentFilePath + "");
    //log.info("deleteDocument "+documentFile.getName().toString()+"-"+documentFilePath);
		if (documentFile.isFile())
		{
		 documentFile.delete();
		}
		}
		documentDeleteCount = factory.delete(criteria);
    //log.info("deleted files:"+documentDeleteCount);
	 
	}

	return documentDeleteCount;
 }
 }