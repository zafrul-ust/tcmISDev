package com.tcmis.internal.hub.process;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ImageViewerBean;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceiptImageInputBean;
import com.tcmis.internal.hub.beans.ReceivingQcCheckListBean;
import com.tcmis.internal.hub.factory.IgItemCompanyBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptDocumentViewBeanFactory;
import com.tcmis.ws.tablet.beans.TabletInputBean;

/******************************************************************************
 * Process for Receipt Document
 * 
 * @version 1.0
 *****************************************************************************/

public class ReceiptDocumentProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ReceiptDocumentProcess(String client) {
		super(client);
	}

	public ReceiptDocumentProcess(String client, String locale) {
		super(client, locale);
	}

	/**
	 * @param inventoyGroup
	 * @param itemId
	 * @return A collection of company ids that can be served by this item and
	 *         inventory group
	 * @throws NoDataException
	 * @throws BaseException
	 * @throws Exception
	 */
	public Collection getCompanyIdsForInventoryGroupAndItem(String inventoyGroup, BigDecimal receiptId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		ReceiptBeanFactory receiptBeanFactory = new ReceiptBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("receiptId", SearchCriterion.EQUALS, "" + receiptId + "");

		Collection receiptIdCollection = new Vector();
		receiptIdCollection = receiptBeanFactory.select(criteria);
		String itemId = "";
		Iterator iterator = receiptIdCollection.iterator();
		while (iterator.hasNext()) {
			ReceiptBean receiptBean = (ReceiptBean) iterator.next();
			itemId = "" + receiptBean.getItemId() + "";
		}

		if (itemId != null && itemId.trim().length() > 0) {
			IgItemCompanyBeanFactory igItemCompanyBeanFactory = new IgItemCompanyBeanFactory(dbManager);
			criteria = new SearchCriteria();

			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoyGroup);

			criteria.addCriterion("itemId", SearchCriterion.EQUALS, itemId);

			return igItemCompanyBeanFactory.select(criteria);
		}
		else {
			return null;
		}
	}

	public Collection getSearchResult(ReceiptDocumentInputBean receiptDocumentInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReceiptDocumentViewBeanFactory factory = new ReceiptDocumentViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("receiptId", SearchCriterion.EQUALS, "" + receiptDocumentInputBean.getReceiptId() + "");

		return factory.select(criteria);
	}

	public Collection getTableSearchResult(ReceiptDocumentInputBean receiptDocumentInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReceiptDocumentViewBeanFactory factory = new ReceiptDocumentViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("receiptId", SearchCriterion.EQUALS, "" + receiptDocumentInputBean.getReceiptId() + "");

		criteria.addCriterion("documentType", SearchCriterion.NOT_LIKE, "%Picture%", "true");

		return factory.select(criteria);
	}

	public Collection getPOSearchResult(ReceiptDocumentInputBean receiptDocumentInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ReceiptDocumentViewBean());

		SearchCriteria criteria = new SearchCriteria();

		StringBuilder subQuery = new StringBuilder("select receipt_id from receipt where radian_po = ").append(receiptDocumentInputBean.getRadianPo());

		// Searching for order status
		if (receiptDocumentInputBean.getPoLine() != null) {
			subQuery.append(" and po_line = ").append(receiptDocumentInputBean.getPoLine()).append("");
		}

		criteria.addCriterion("receiptId", SearchCriterion.IN, subQuery.toString());

		SortCriteria sort = new SortCriteria();
		sort.addCriterion("receiptId");
		sort.addCriterion("documentDate", "desc");
		return factory.select(criteria, sort, "RECEIPT_DOCUMENT_VIEW");
	}

	public String addNewDocument(ReceiptDocumentInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReceiptDocumentViewBeanFactory factory = new ReceiptDocumentViewBeanFactory(dbManager);
		ResourceLibrary resource = new ResourceLibrary("receiptdocument");

		if (bean.getTheFile() != null && bean.getTheFile().length() > (long) 0) {
			// copy file to server
			File f = bean.getTheFile();
			String fileType = f.getName().substring(f.getName().lastIndexOf("."));
			bean.setFileName(fileType);
			try {
				File dir = new File(resource.getString("receipt.documentfile.path"));
				File file = File.createTempFile("" + bean.getReceiptId() + "-", "" + fileType + "", dir);

				FileHandler.copy(bean.getTheFile(), file);
				bean.setFileName(file.getName());
			}
			catch (Exception e) {
				BaseException be = new BaseException("Can't put document file on server:" + e.getMessage());
				be.setRootCause(e);
				throw be;
			}
			String documentFileUrl = resource.getString("receipt.documentfile.hosturl") + resource.getString("receipt.documentfile.urlpath") + bean.getFileName();
			bean.setDocumentUrl(documentFileUrl);
			factory.insert(bean);
			return documentFileUrl;
		}
		else {
			log.error("Unable to copy empty or non-existent file " + bean.getTheFile().getAbsolutePath() + " to " + resource.getString("receipt.documentfile.path"));
			return null;
		}
	}

	public int deleteDocument(Collection receiptDocumentInputBeanCollection) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = null;
		ReceiptDocumentViewBeanFactory factory = new ReceiptDocumentViewBeanFactory(dbManager);
		int documentDeleteCount = 0;

		Iterator documentIterator = receiptDocumentInputBeanCollection.iterator();
		while (documentIterator.hasNext()) {
			ReceiptDocumentInputBean receiptDocumentInputBean = (ReceiptDocumentInputBean) documentIterator.next();

			if (receiptDocumentInputBean.getOk() != null) {
				SearchCriteria criteria = new SearchCriteria();
				criteria.addCriterion("documentId", SearchCriterion.EQUALS, "" + receiptDocumentInputBean.getDocumentId() + "");

				criteria.addCriterion("receiptId", SearchCriterion.EQUALS, "" + receiptDocumentInputBean.getReceiptId() + "");

				String documentUrl = receiptDocumentInputBean.getDocumentUrl();
				String documentFilePath = documentUrl.substring(documentUrl.lastIndexOf("/"), documentUrl.length());
				try {
					connection = dbManager.getConnection();
					documentDeleteCount = factory.delete(criteria, connection);
					// log.info("deleted files:"+documentDeleteCount);

					if (documentFilePath.length() > 0 && !isSharedDocument(documentUrl, connection)) {
						ResourceLibrary resource = new ResourceLibrary("receiptdocument");
						File documentFile = new File("" + resource.getString("receipt.documentfile.path") + "" + documentFilePath + "");
						log.info("Deleteing Document " + resource.getString("receipt.documentfile.path") + "" + documentFilePath + "");
						// log.info("deleteDocument
						// "+documentFile.getName().toString()+"-"+documentFilePath);
						if (documentFile.isFile()) {
							documentFile.delete();
						}
					}
				}
				finally {
					dbManager.returnConnection(connection);
				}
			}
		}

		return documentDeleteCount;
	}

	private Boolean isSharedDocument(String documentUrl, Connection connection) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select count(*) from receipt_document where document_url like '%").append(documentUrl).append("'");

		return "0".equals(factory.selectSingleValue(query.toString(), connection)) ? false : true;
	}

	public String addNewDatabaseDocumentRecord(ReceiptDocumentInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		ReceiptDocumentViewBeanFactory factory = new ReceiptDocumentViewBeanFactory(dbManager);

		/*
		 * ResourceLibrary resource = new ResourceLibrary("receiptdocument");
		 * String documentFileUrl =
		 * resource.getString("receipt.documentfile.hosturl") +
		 * resource.getString("receipt.documentfile.urlpath") + ;
		 * //log.info("addNewDocument documentFileUrl     " + documentFileUrl +
		 * "");
		 */
		bean.setDocumentUrl(bean.getFileName());

		try {
			factory.insert(bean);
		}
		catch (BaseException ex) {
			return ex.getMessage();
		}
		return "success";
	}

	public Collection getImageSearchResult(ReceiptDocumentInputBean receiptDocumentInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReceiptDocumentViewBeanFactory factory = new ReceiptDocumentViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("receiptId", SearchCriterion.EQUALS, "" + receiptDocumentInputBean.getReceiptId() + "");

		// criteria.addCriterion("documentType", SearchCriterion.IN,
		// "SELECT DOCUMENT_TYPE FROM VV_RECEIPT_DOCUMENT_TYPE");

		criteria.addCriterion("documentType", SearchCriterion.LIKE, "Picture", "true");

		return factory.select(criteria);
	}

	public Collection<ImageViewerBean> getReceiptImageSearchResult(ReceiptImageInputBean input) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new ImageViewerBean());
		StringBuilder query = new StringBuilder(
				"select R.ITEM_ID, RDV.DOCUMENT_URL URL, RDV.RECEIPT_ID, RDV.DOCUMENT_ID, RDV.DOCUMENT_TYPE TEXT, ii.item_image_id from RECEIPT_DOCUMENT_VIEW RDV, RECEIPT R, ITEM_IMAGE ii where R.RECEIPT_ID = RDV.RECEIPT_ID  and  RDV.DOCUMENT_TYPE like 'Picture-%' and II.ITEM_ID(+) = R.ITEM_ID and ii.ITEM_IMAGE_TAG(+) = 'Main'");
		query.append(" AND RDV.RECEIPT_ID = ").append(input.getReceiptId());
		return factory.selectQuery(query.toString());
	}

	public void setItemImage(ReceiptImageInputBean input, PersonnelBean user) {
		try {
			ResourceLibrary library = new ResourceLibrary("tabletConfig");
			String webroot = library.getString("receiptWebRoot");
			if (!webroot.endsWith("/")) {
				webroot += "/";
			}
			String savePath = library.getString("itemImageDirectory");
			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			String relativePath = library.getString("relativeItemImageDirectory");
			if (!relativePath.endsWith("/")) {
				relativePath += "/";
			}

			File receiptImageFile = new File(webroot + input.getFileName());
			File itemImageFile = new File(savePath + input.getItemId() + ".jpg");

			FileUtils.copyFile(receiptImageFile, itemImageFile);

			StringBuilder sql = new StringBuilder();
			if (input.hasItemImageId()) {
				sql.append("update item_image set IMAGE_FILE_NAME = '").append(relativePath).append(input.getItemId()).append(".jpg'");
				sql.append(", UPDATE_USER = ").append(user.getPersonnelId());
				sql.append(", DATE_UPDATED = sysdate");
				sql.append(" where ITEM_IMAGE_ID = ").append(input.getItemImageId());
				sql.append(" and ITEM_IMAGE_TAG = 'Main'");
			}
			else {
				sql.append("insert into ITEM_IMAGE (ITEM_ID, ITEM_IMAGE_TAG, IMAGE_FILE_NAME, INSERT_USER, DATE_INSERTED) values (");
				sql.append(input.getItemId()).append(", ");
				sql.append("'Main', ");
				sql.append("'").append(relativePath).append(input.getItemId()).append(".jpg', ");
				sql.append(user.getPersonnelId()).append(", ");
				sql.append("sysdate)");
			}
			GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
			factory.deleteInsertUpdate(sql.toString());

		}
		catch (Exception e) {
			log.error("Error saving new Image for Item", e);
		}

	}

	public String deleteImage(ReceiptDocumentInputBean receiptDocumentInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReceiptDocumentViewBeanFactory factory = new ReceiptDocumentViewBeanFactory(dbManager);
		int documentDeleteCount = 0;
		String message = "OK";

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("documentId", SearchCriterion.EQUALS, "" + receiptDocumentInputBean.getDocumentId() + "");

		criteria.addCriterion("receiptId", SearchCriterion.EQUALS, "" + receiptDocumentInputBean.getReceiptId() + "");

		String documentUrl = receiptDocumentInputBean.getDocumentUrl();
		String documentFilePath = documentUrl.substring(documentUrl.lastIndexOf("/"), documentUrl.length());

		if (documentFilePath.length() > 0) {
			ResourceLibrary resource = new ResourceLibrary("tabletConfig");
			try {
				File documentFile = new File("" + resource.getString("receiptImageDirectory") + "" + documentFilePath + "");
				log.info("Deleteing Document " + resource.getString("receiptImageDirectory") + "" + documentFilePath + "");
				// log.info("deleteDocument
				// "+documentFile.getName().toString()+"-"+documentFilePath);
				if (documentFile.isFile()) {
					documentFile.delete();
				}
			}
			catch (Exception e) {
				message = "Error deleting image from disk: " + e;
				log.error(message, e);
			}
		}
		try {
			documentDeleteCount = factory.delete(criteria);
		}
		catch (BaseException ex) {
			message = "Error deleing image from disk: " + ex;
			log.error(message, ex);
		}
		// log.info("deleted files:"+documentDeleteCount);

		if (documentDeleteCount == 0) {
			message = "Could not delete Document";
		}

		return message;
	}

	public String addMSDSDocumentRecord(ReceiptDocumentInputBean bean) throws BaseException {
		String result = "success";
		try {
			// query for the material id and latest revision date
			GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
			StringBuilder msdsInfoQuery = new StringBuilder();

			msdsInfoQuery.append("SELECT p.material_id ");
			msdsInfoQuery.append("FROM receipt r, global.part p ");
			msdsInfoQuery.append("WHERE ");
			msdsInfoQuery.append("r.receipt_id = " + bean.getReceiptId().toString());
			msdsInfoQuery.append(" AND r.item_id = p.item_id ");

			String materialId = factory.selectSingleValue(msdsInfoQuery.toString());

			// two separate queries are used as it is possible no msds exists
			// for this material
			msdsInfoQuery = new StringBuilder();

			msdsInfoQuery.append("SELECT TO_CHAR (ms.revision_date, 'dd/MON/yyyy') revision_date ");
			msdsInfoQuery.append("FROM global.msds ms ");
			msdsInfoQuery.append("WHERE ");
			msdsInfoQuery.append("ms.material_id = " + materialId);
			msdsInfoQuery.append(" AND ms.on_line = 'Y' ");
			msdsInfoQuery.append("AND ms.revision_date = fx_msds_most_recent_date (ms.material_id)");

			String latestRevisionDateStr = factory.selectSingleValue(msdsInfoQuery.toString());
			Date latestRevisionDate = null;

			if (!StringHandler.isBlankString(latestRevisionDateStr)) {
				latestRevisionDate = new SimpleDateFormat("dd/MMM/yyyy").parse(latestRevisionDateStr);
			}

			// if the document to be associated is newer than the existing msds,
			// make a copy into the MSDS documents directory
			if (latestRevisionDate == null || latestRevisionDate.compareTo(bean.getMsdsRevisionDate()) < 0) {
				// get the server file save path
				ResourceLibrary resource = new ResourceLibrary("tabletConfig");

				String webroot = resource.getString("receiptWebRoot");
				if (!webroot.endsWith(File.separator)) {
					webroot += File.separator;
				}
				
				String serverUrl = resource.getString("serverURL");
				if (serverUrl.endsWith("/")) {
					serverUrl = StringUtils.removeEnd(serverUrl, "/");
				}

				String relativeMSDSDirectory = resource.getString("relativeMSDSDirectory");
				if (!relativeMSDSDirectory.endsWith(File.separator)) {
					relativeMSDSDirectory += File.separator;
				}

				StringBuilder msdsFileName = new StringBuilder(relativeMSDSDirectory);
				msdsFileName.append(materialId);
				msdsFileName.append("_");
				msdsFileName.append(new SimpleDateFormat("MMddyy").format(bean.getMsdsRevisionDate()));

				// append any file extension from the uploaded File
				int terminator = bean.getFileName().lastIndexOf(".");
				if (terminator > 0) {
					msdsFileName.append(bean.getFileName().substring(terminator));
				}

				// parse document name for file name
				terminator = bean.getFileName().lastIndexOf("/");
				String inboundDocFileName = bean.getFileName().replaceAll("https?://.*?.tcmis.com", "");
				File inboundDocFile = new File(webroot + inboundDocFileName);
				File msdsFile = new File(webroot + msdsFileName.toString());

				// make a copy of document in the msds documents folder
				FileUtils.copyFile(inboundDocFile, msdsFile);

				// insert a record in the msds table
				StringBuilder msdsInsertQuery = new StringBuilder();

				msdsInsertQuery.append("INSERT INTO global.msds (material_id, revision_date, content, UPLOADED_BY_RECEIPT_ID, ON_LINE, inserted_by) VALUES (");
				msdsInsertQuery.append(materialId + ",");
				msdsInsertQuery.append(DateHandler.getOracleToDateFunction(bean.getMsdsRevisionDate()) + ",");
				msdsInsertQuery.append(SqlHandler.delimitString(serverUrl +msdsFileName.toString()) + ",");
				msdsInsertQuery.append(bean.getReceiptId().toString() + ", 'N',");
				msdsInsertQuery.append(bean.getEnteredBy());
				msdsInsertQuery.append(")");

				factory.deleteInsertUpdate(msdsInsertQuery.toString());
			}

		}
		catch (Exception e) {
			log.error("Error saving MSDS ", e);
			result = e.getLocalizedMessage();
		}

		return result;
	}
}