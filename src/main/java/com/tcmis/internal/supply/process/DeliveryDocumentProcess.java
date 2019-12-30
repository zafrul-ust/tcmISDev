package com.tcmis.internal.supply.process;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.supply.beans.ShipmentDocumentInputBean;
import com.tcmis.internal.supply.factory.ShipmentDocumentViewBeanFactory;
import com.tcmis.internal.supply.factory.VvShipmentDocumentTypeBeanFactory;


public class DeliveryDocumentProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public DeliveryDocumentProcess(String client) {
		super(client);
	}

	public DeliveryDocumentProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getVvShipmentDocumentType() throws
	NoDataException, BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		VvShipmentDocumentTypeBeanFactory vvShipmentDocTypeBeanFactory = new
		VvShipmentDocumentTypeBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		return vvShipmentDocTypeBeanFactory.select(criteria);
	}

	public Collection getSearchResult(ShipmentDocumentInputBean
			shipmentDocInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ShipmentDocumentViewBeanFactory factory = new ShipmentDocumentViewBeanFactory(
				dbManager);

		SearchCriteria criteria = new SearchCriteria();
		if(shipmentDocInputBean.getShipmentId()!= null)
		{
			criteria.addCriterion("shipmentId", SearchCriterion.EQUALS,shipmentDocInputBean.getShipmentId().toString());
		}

		return factory.select(criteria);
	}

	public String addNewDocument(ShipmentDocumentInputBean bean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ShipmentDocumentViewBeanFactory factory = new
		ShipmentDocumentViewBeanFactory(dbManager);
		if (bean.getTheFile() != null && bean.getTheFile().length() > 0) {
			//copy file to server
			File f = bean.getTheFile();
			String fileType = f.getName().substring(f.getName().lastIndexOf("."));
			bean.setFileName(fileType);
			ResourceLibrary resource = new ResourceLibrary("shipmentdocument");
			try {
				File dir = new File(resource.getString("shipment.documentfile.path"));
				File file = File.createTempFile(bean.getShipmentId() + "-", fileType, dir);

				FileHandler.copy(bean.getTheFile(), file);
				bean.setFileName(file.getName());
				//file.deleteOnExit();
			}
			catch (Exception e) {
				log.error("Cannot save document at :" + resource.getString("shipment.documentfile.path"), e);
				return null;
			}
			String documentFileUrl = resource.getString("shipment.documentfile.hosturl") +
			resource.getString("shipment.documentfile.urlpath") + bean.getFileName();
			//log.info("addNewDocument documentFileUrl     " + documentFileUrl + "");
			bean.setDocumentUrl(documentFileUrl);
			try {
				factory.insert(bean);
			}
			catch (Exception e) {
				log.error("Unable to insert bean ", e);
				return null;
			}

			return documentFileUrl;

		}
		else {
			return null;
		}
	}

	public String deliveryConfirm(ShipmentDocumentInputBean bean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		String result="";
		try{
			Collection cin  = new Vector(2);
			if(bean.getShipmentId() != null) {
				cin.add(bean.getShipmentId());
			}
			if(bean.getEnteredBy() != null) {
				cin.add(bean.getEnteredBy());
			}
			Collection cout = new Vector(1);
			cout.add(new Integer(java.sql.Types.VARCHAR));

			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection resultData = procFactory.doProcedure("PKG_DELIVERY_CONFIRMATION.p_confirm_shipment", cin, cout);
			Iterator i11 = resultData.iterator();
			String val = "";
			while (i11.hasNext()) {
				val = (String) i11.next();
			}
			if (val != null && val.length() > 0){

				result=val;
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public int deleteDocument(Collection ShipmentDocInputBeanColl) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ShipmentDocumentViewBeanFactory factory = new ShipmentDocumentViewBeanFactory(
				dbManager);
		int documentDeleteCount = 0;

		Iterator documentIterator = ShipmentDocInputBeanColl.iterator();
		while (documentIterator.hasNext()) {
			ShipmentDocumentInputBean shipmentDocInputBean = (
					ShipmentDocumentInputBean) documentIterator.next();

			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("documentId",
					SearchCriterion.EQUALS,
					"" + shipmentDocInputBean.getDocumentId() + "");

			criteria.addCriterion("shipmentId",
					SearchCriterion.EQUALS,
					"" + shipmentDocInputBean.getShipmentId() + "");


			String documentUrl = shipmentDocInputBean.getDocumentUrl();
			String documentFilePath = documentUrl.substring(documentUrl.lastIndexOf("/"),
					documentUrl.length());

			if (documentFilePath.length() > 0)
			{
				ResourceLibrary resource = new ResourceLibrary("shipmentdocument");
				File documentFile = new File("" + resource.getString("shipment.documentfile.path") + "" + documentFilePath + "");
				log.info("Deleteing Document " + resource.getString("shipment.documentfile.path") + "" + documentFilePath + "");
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