package com.tcmis.internal.distribution.process;

import java.io.File;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;



import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.distribution.beans.MrDocumentInputBean;
import com.tcmis.internal.distribution.factory.MrDocumentViewBeanFactory;



/******************************************************************************
 * Process for Receipt Document
 * @version 1.0
 *****************************************************************************/

 public class MrDocumentProcess
	extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public MrDocumentProcess(String client) {
	 super(client);
	}
	
	public MrDocumentProcess(String client,String locale) {
	    super(client,locale);
    }

	/**
	 * @param inventoyGroup
	 * @param itemId
	 * @return A collection of company ids that can be served by this item and inventory group
	 * @throws NoDataException
	 * @throws BaseException
	 * @throws Exception
	 */

	public Collection getSearchResult(MrDocumentInputBean
	 mrDocumentInputBean) throws BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	MrDocumentViewBeanFactory factory = new MrDocumentViewBeanFactory(
		dbManager);
	
	 SearchCriteria criteria = new SearchCriteria();
	 if(mrDocumentInputBean.getPrNumber()!= null)
	 {
	 criteria.addCriterion("prNumber", SearchCriterion.EQUALS,mrDocumentInputBean.getPrNumber().toString());
	 }
	 /*if(poDocumentInputBean.getPoLine()!= null)
	 {
	 criteria.addCriterion("poLine", SearchCriterion.EQUALS, poDocumentInputBean.getPoLine().toString());
	 }*/
	 return factory.select(criteria);
	}

	public String addNewDocument(MrDocumentInputBean bean) throws
	 BaseException {
	 DbManager dbManager = new DbManager(getClient(),getLocale());
	 
	 String result ="";
	 if (bean.getTheFile() != null && bean.getTheFile().length() > (long) 0) {
		//copy file to server
		File f = bean.getTheFile();
		String fileType = f.getName().substring(f.getName().lastIndexOf("."));
		bean.setFileName(fileType);
		ResourceLibrary resource = new ResourceLibrary("mrdocument");
		try {
		 File dir = new File(resource.getString("mr.documentfile.path"));
		 File file = File.createTempFile("" + bean.getPrNumber() + "-",
			"" + fileType + "", dir);

		 FileHandler.copy(bean.getTheFile(), file);
		 bean.setFileName(file.getName());
		 //file.deleteOnExit();
		}
		catch (Exception e) {
		 return null;
		}
		String documentFileUrl = resource.getString("mr.documentfile.hosturl") +
		 resource.getString("mr.documentfile.urlpath") + bean.getFileName();
		//log.info("addNewDocument documentFileUrl     " + documentFileUrl + "");
		bean.setDocumentUrl(documentFileUrl);

		if(bean.isLaunchedFromSupplier())
		{
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			Vector errorMessages = new Vector();
			Collection inArgs = null;
			Collection outArgs = null;
			String errorMsg = "";
			
			try {
				inArgs = new Vector(8);
				inArgs.add(bean.getPrNumber());
				inArgs.add(bean.getDocumentName());
				inArgs.add(bean.getDocumentType());
				inArgs.add(new Timestamp(bean.getDocumentDate().getTime()));
				inArgs.add(bean.getDocumentUrl());
				inArgs.add(bean.getEnteredByName());
				inArgs.add("USGOV");
				inArgs.add(null);
				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				errorMessages = (Vector) factory.doProcedure("pkg_mr_document.p_insert_document", inArgs, outArgs);

				if(errorMessages.size()>0 && errorMessages.get(0) != null)
				      {
				     	 String errorCode = (String) errorMessages.get(0);
				     	 log.info("Error in Procedure p_insert_document. PR Number "+bean.getPrNumber()+", Doc Name "+bean.getDocumentName()+", Doc URL "+bean.getDocumentUrl()+", Error Code "+errorCode+" ");
				     	 errorMessages.add(errorCode);
				      } 
			}
			catch (Exception e) {
				return null;
			}
		
		}
		else
		{
				try {
					MrDocumentViewBeanFactory factory  = new MrDocumentViewBeanFactory(dbManager);
					factory.insert(bean);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
		}
		return documentFileUrl;
	 }
	 else {
		return null;
	 }
	}

 public int deleteDocument(Collection mrDocumentInputBeanCollection) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	MrDocumentViewBeanFactory factory = new MrDocumentViewBeanFactory(
	 dbManager);
	int documentDeleteCount = 0;

	Iterator documentIterator = mrDocumentInputBeanCollection.iterator();
	while (documentIterator.hasNext()) {
	 MrDocumentInputBean mrDocumentInputBean = (
		MrDocumentInputBean) documentIterator.next();

	 	SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("documentId",
		 SearchCriterion.EQUALS,
		 "" + mrDocumentInputBean.getDocumentId() + "");

		criteria.addCriterion("prNumber",
		 SearchCriterion.EQUALS,
		 "" + mrDocumentInputBean.getPrNumber() + "");
		
		
		String documentUrl = mrDocumentInputBean.getDocumentUrl();
		String documentFilePath = documentUrl.substring(documentUrl.lastIndexOf("/"),
		 documentUrl.length());

		if (documentFilePath.length() > 0)
		{
		ResourceLibrary resource = new ResourceLibrary("mrdocument");
		File documentFile = new File("" + resource.getString("mr.documentfile.path") + "" + documentFilePath + "");
		log.info("Deleteing Document " + resource.getString("mr.documentfile.path") + "" + documentFilePath + "");
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