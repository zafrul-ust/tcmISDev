package com.tcmis.internal.hub.process;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.PicklistViewBean;
import com.tcmis.internal.hub.erw.PickListData;
import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.internal.hub.factory.PicklistViewBeanFactory;

public class PrintPickListProcess extends BaseProcess {
	private ACJEngine			erw	= null;
	private OutputStream		os	= null;
	private TemplateManager		tm	= null;
	private ACJOutputProcessor	ec	= null;

	Log							log	= LogFactory.getLog(getClass());

	public PrintPickListProcess(String client) {
		super(client);
	}

	public String createPrintablePickList(PicklistViewBean inputBean, BigDecimal personnelId) {
		String url = "";
		Random rand = new Random();
		int tmpReq = rand.nextInt();
		Integer tmpReqNum = new Integer(tmpReq);
		Calendar starTime = Calendar.getInstance();
		try {
			DbManager dbManager = new DbManager(getClient());
			PicklistViewBeanFactory factory = new PicklistViewBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("picklistId", SearchCriterion.EQUALS, inputBean.getPicklistId() + "");

			SortCriteria sortCriteria = null;
			if ((inputBean.getSortBy() != null) && (inputBean.getSortBy().trim().length() > 0)) {
				sortCriteria = new SortCriteria();
				sortCriteria.addCriterion(inputBean.getSortBy());
			}

			Vector printablePickListDataV = (Vector) factory.select(criteria, sortCriteria, inputBean.getPicklistId());

			HashMap m1 = new HashMap();
			HashMap m2 = new HashMap();
			HashMap map = null;
			Integer i1 = null;
			Integer i2 = null;
			String mrLineItemId = null;
			Collection finalprintablePickListData = new Vector();

			PicklistViewBean pbean = null;
			for (int i = 0; i < printablePickListDataV.size(); i++) {
				pbean = (PicklistViewBean) printablePickListDataV.get(i);
				mrLineItemId = pbean.getPrNumber() + pbean.getLineItem() + pbean.getItemId();

				if (m1.get(mrLineItemId) == null) {
					i1 = new Integer(0);
					m1.put(mrLineItemId, i1);
					i2 = new Integer(0);
					m2.put(mrLineItemId, i2);
				}
				i1 = (Integer) m1.get(mrLineItemId);
				i1 = new Integer(i1.intValue() + 1);
				m1.put(mrLineItemId, i1);

				if (pbean.getPicklistQuantity() == null) {
					i2 = (Integer) m2.get(mrLineItemId);
					i2 = new Integer(i2.intValue() + 1);
					m2.put(mrLineItemId, i2);
				}

				i2 = (Integer) m2.get(mrLineItemId);
				log.debug("Count of non picklist options-mrLineItemId " + mrLineItemId + " count- " + i2.intValue());
				if ((i2.intValue() < 5) || (pbean.getPicklistQuantity() != null)) {
					finalprintablePickListData.add(pbean);
				}
			}

			if (finalprintablePickListData.size() == 0) {
				return "";
			}

			erw = new ACJEngine();
			ACJEngine.setDebugMode(true);
			ACJEngine.setX11GfxAvailibility(false);
			erw.setTargetOutputDevice(2);
			ec = new ACJOutputProcessor();

			ResourceLibrary resource = new ResourceLibrary("label");
			String fontmappath = resource.getString("label.font.path");
			String templateName = resource.getString("picklist.template");
			ec.setPathForFontMapFile(fontmappath);

			String templatpath = resource.getString("label.template.path");
			erw.readTemplate("" + templatpath + templateName);

			tm = erw.getTemplateManager();
			tm.setWHEREClause("SEC_00", " PICKLIST.picklistId =" + inputBean.getPicklistId() + "");
			String qryorderby = "";
			String sortBy = StringHandler.emptyIfNull(inputBean.getSortBy());
			if ("FACILITY_ID,APPLICATION".equalsIgnoreCase(sortBy)) qryorderby = "PICKLIST.facilityId,PICKLIST.application";
			else if ("PR_NUMBER,LINE_ITEM".equalsIgnoreCase(sortBy)) {
				qryorderby = "PICKLIST.prNumber,PICKLIST.lineItem";
			}
			else if ("pickupTime,carrierCode,trailerNumber,stopNumber,shipToCity,shipToStateAbbrev,shipToDodaac,consolidationNumber".equalsIgnoreCase(sortBy)) {
				qryorderby = "PICKLIST.pickupTime,PICKLIST.carrierCode,PICKLIST.trailerNumber,PICKLIST.stopNumber,PICKLIST.shipToCity,PICKLIST.shipToStateAbbrev,PICKLIST.shipToDodaac,PICKLIST.consolidationNumber";
			}
			else if ("shipToCity,shipToStateAbbrev,shipToDodaac,pickupTime,carrierCode,consolidationNumber".equalsIgnoreCase(sortBy)) {
				qryorderby = "PICKLIST.shipToCity,PICKLIST.shipToStateAbbrev,PICKLIST.shipToDodaac,PICKLIST.pickupTime,PICKLIST.carrierCode,PICKLIST.consolidationNumber";
			}
			else if ("consolidationNumber".equalsIgnoreCase(sortBy)) {
				qryorderby = "PICKLIST.consolidationNumber";
			}
			else if ("bin".equalsIgnoreCase(sortBy)) {
				qryorderby = "PICKLIST.bin";
			}

			if (qryorderby.trim().length() > 0) {
				tm.setORDERBYClause("SEC_00", qryorderby);
			}

			String tempwritefilepath = resource.getString("label.serverpath");

			AppDataHandler ds = new AppDataHandler();
			RegisterTable[] rt = getData(finalprintablePickListData);
			for (int i = 0; i < rt.length; i++) {
				Vector v1 = rt[i].getData();
				Vector v2 = rt[i].getMethods();
				ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
			}
			erw.setDataSource(ds);

			ec.setReportData(erw.generateReport());
			ec.setPDFProperty("FileName", "" + tempwritefilepath + "PickList" + tmpReqNum.toString() + ".pdf");
			ec.setPDFProperty("ZipCompressed", new Boolean(false));
			ec.generatePDF();

			String tempurlpath = resource.getString("label.hosturl") + resource.getString("label.urlpath");
			url = tempurlpath + "PickList" + tmpReqNum.toString() + ".pdf";
		}
		catch (Exception e) {
			e.printStackTrace();
			url = "";
		}

		Calendar endTime = Calendar.getInstance();
		if (endTime.getTimeInMillis() - starTime.getTimeInMillis() > 200000L) {
			try {
				BulkMailProcess bulkMailProcess = new BulkMailProcess(getClient());

				bulkMailProcess.sendBulkEmail(personnelId, "Picklist PDF URL", "Picklist PDF URL. Click on the URL/Paste in browser to view PDF.\n\n" + url + "", false);
			}
			catch (Exception ex) {
			}

		}

		return url;
	}

	public RegisterTable[] getData(Collection printablePickListData) throws Exception {
		RegisterTable[] rt = new RegisterTable[1];
		try {
			rt[0] = new RegisterTable(PickListData.getVector(printablePickListData), "PICKLIST", PickListData.getFieldVector(), null);
		}
		catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}
		return rt;
	}
}
