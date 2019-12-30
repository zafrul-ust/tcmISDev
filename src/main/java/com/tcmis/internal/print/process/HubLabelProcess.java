package com.tcmis.internal.print.process;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.DeliveryDocumentViewBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.factory.DeliveryDocumentViewBeanFactory;
import com.tcmis.internal.print.beans.Usgov1348ViewBean;
import com.tcmis.internal.print.beans.UsgovLabelViewBean;
import com.tcmis.internal.print.factory.PackingGroupInstructionViewBeanFactory;
import com.tcmis.internal.print.factory.TcnPalletViewBeanFactory;
import com.tcmis.internal.print.factory.Usgov1348ViewBeanFactory;
import com.tcmis.supplier.shipping.beans.MslBoxViewBean;
import com.tcmis.supplier.shipping.beans.MslPalletViewBean;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;
import com.tcmis.supplier.shipping.beans.SupplierUsgovLabelViewBean;
import com.tcmis.supplier.shipping.process.LabelProcess;

/******************************************************************************
 * Process for receiving qc
 * @version 1.0
 *****************************************************************************/
public class HubLabelProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public HubLabelProcess(String client) {
		super(client);
	}

	public Object[] printDeliveryDocuments(Collection delyDocViewBeanColl,LabelInputBean labelInputBean) throws BaseException,Exception
	{
		Collection printDataCollection = getDeliveryDocuments(delyDocViewBeanColl);

		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("DD1348");
		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);
		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);

		if (printDataCollection.size() ==0)
		{
			String url ="";
			Object[] objs1 = {url,url};
			return objs1;
		}
		else
		{
			DeliveryDocumentViewBean bean = null;
			for (Object obj : printDataCollection) {
				if (obj == null)
					continue;
				bean = (DeliveryDocumentViewBean) obj;

				String filePath = bean.getFileName();
				if (filePath.contains("https://www.tcmis.com"))
				{
					filePath = filePath.substring(21,filePath.length());
					filePath = "/webdata/html/" +filePath;
				}
				else if (filePath.contains("http://www.tcmis.com"))
				{
					filePath = filePath.substring(20,filePath.length());
					filePath = "webdata/html/" +filePath;
				}

				if(log.isDebugEnabled()) {
					log.debug("Doc ULR  "+filePath+"Doc TYPE  "+bean.getDocumentType());
				}
				/*TODO Change this to get the printer path dynamically */
				PrintHandler.print(printerPath, filePath);
			}
		}

		String url ="";
		Object[] resultsObj = {url,url};
		return resultsObj;
	}

	public Collection getDeliveryDocuments(Collection delyDocViewBeanColl) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		DeliveryDocumentViewBeanFactory factory = new	DeliveryDocumentViewBeanFactory(dbManager);

		Collection finalLabelDataCollection = new Vector();

		Iterator iterator = delyDocViewBeanColl.iterator();
		while (iterator.hasNext()) {
			DeliveryDocumentViewBean deliveryDocViewBean = (
					DeliveryDocumentViewBean) iterator.next(); ;

					if (deliveryDocViewBean.getPrintOk() != null &&
							deliveryDocViewBean.getPrintOk().equalsIgnoreCase("Yes")) {

						SearchCriteria criteria = new SearchCriteria();
						criteria.addCriterion("prNumber", SearchCriterion.EQUALS,
								"" + deliveryDocViewBean.getPrNumber() + "");

						criteria.addCriterion("lineItem", SearchCriterion.EQUALS,
								"" + deliveryDocViewBean.getLineItem() + "");

						criteria.addCriterion("picklistId", SearchCriterion.EQUALS,
								"" + deliveryDocViewBean.getPicklistId() + "");

						SortCriteria scriteria = new SortCriteria();
						scriteria.addCriterion("documentType");
						Collection receiptLabelDataColl = new Vector();
						receiptLabelDataColl = factory.select(criteria,scriteria);
						finalLabelDataCollection.addAll(receiptLabelDataColl);
					}
		}
		return finalLabelDataCollection;
	}

	public Collection getTcnPackingListData(LabelInputBean labelInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		TcnPalletViewBeanFactory factory = new TcnPalletViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (!StringHandler.isBlankString(labelInputBean.getCarrierCode())) {
			criteria.addCriterion("carrierCode", SearchCriterion.EQUALS,"" + labelInputBean.getCarrierCode() + "");
		}
		if (!StringHandler.isBlankString(labelInputBean.getConsolidationNumber())) {
			criteria.addCriterion("consolidationNumber", SearchCriterion.EQUALS,"" + labelInputBean.getConsolidationNumber() + "");
		}
		if (!StringHandler.isBlankString(labelInputBean.getPalletId())) {
			criteria.addCriterion("palletId", SearchCriterion.EQUALS,"" + labelInputBean.getPalletId() + "");
		}
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("palletId");
		sortCriteria.addCriterion("tcn");
		sortCriteria.addCriterion("nsn");
		return factory.select(criteria,sortCriteria);
	}

	public Object[] buildTcnPackingList(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		Collection labelDataCollection = getTcnPackingListData(labelInputBean);
		Object[] resultsObj;

		if (labelDataCollection.size() ==0)
		{
			String url ="";
			Object[] objs1 = {url,url};
			return objs1;
		}

		PrintTcnPackingListProcess printPdfProcess = new PrintTcnPackingListProcess(this.getClient());
		resultsObj = printPdfProcess.buildTcnPackingListPdf(labelDataCollection);

		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("DD1348");
		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);
		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);

		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");
		return resultsObj;
	}

	public Collection getPackingGroupInstructionData(String packingGroupId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		PackingGroupInstructionViewBeanFactory factory = new PackingGroupInstructionViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("packingGroupId", SearchCriterion.EQUALS,"" + packingGroupId + "");
		return factory.select(criteria,null);
	}

	public Object[] buildPackingGroupInstruction(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		Collection labelDataCollection = getPackingGroupInstructionData(labelInputBean.getPackingGroupId());
		Object[] objs;

		if (labelDataCollection.size() ==0)
		{
			String url ="";
			Object[] objs1 = {url,url};
			return objs1;
		}

		PrintPackingGroupInstructionProcess printPdfProcess = new PrintPackingGroupInstructionProcess(this.getClient());
		objs = printPdfProcess.buildPackingGroupInstructionPdf(labelDataCollection);

		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("DD1348");
		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);

		if(log.isDebugEnabled()) {
			log.debug(""+objs[0]+"");
		}
		PrintHandler.print(printerPath, ""+objs[0]+"");
		return objs;
	}


	/* public void print(String printerName, String fileName)throws BaseException,Exception
 {
    PrintHandler.print(printerName,fileName);
 }*/

	public Object[] buildUnitBoxLabels(LabelInputBean labelInputBean,String labelType,
			Collection locationLabelPrinterCollection,Collection suppPackSummaryViewBeanCollection
	) throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");

		/*	if (personnelBean.getPrinterLocation() != null &&
	 personnelBean.getPrinterLocation().length() > 0 &&
	 !"811".equalsIgnoreCase(labelInputBean.getPaperSize())) */
		{
			ZplProcess zplProcess = new ZplProcess(this.getClient());
			//Collection labelDataCollection = getLabelData(suppPackSummaryViewBeanCollection);
			Collection labelDataCollection = getLabelData(labelInputBean.getBoxId(),labelInputBean.getSkipKitLabels(),labelInputBean.getFacilityId());

			File dir = new File(resource.getString("label.serverpath"));
			try {
				File file = File.createTempFile("labeljnlp", ".jnlp", dir);
				fileAbsolutePath = zplProcess.buildLabelZpl(labelDataCollection, "",
						locationLabelPrinterCollection,
						file.getAbsolutePath(),labelType);

				labelUrl = resource.getString("label.hosturl") +
				resource.getString("label.urlpath") +
				file.getName();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				labelUrl = "";
			}
		}

		Object[] objs = {fileAbsolutePath,labelUrl};
		return objs;
	}

	public Object[] buildPrintPackingList(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("DD1348");
		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		Object[] resultsObj;

		if (labelInputBean.getFacilityId() != null && labelInputBean.getFacilityId().equalsIgnoreCase("DLA Gases"))
		{
			Collection supplierPackingViewBeanColl = new Vector();
			SupplierPackingViewBean supplierPackingViewBean = new SupplierPackingViewBean();
			if (labelInputBean.getPackingGroupId() !=null && labelInputBean.getPackingGroupId().length()>0)
			{
				supplierPackingViewBean.setPackingGroupId(new BigDecimal(labelInputBean.getPackingGroupId()));
			}
			else
			{
				String url ="";
				Object[] objs1 = {url,url};
				return objs1;
			}
			supplierPackingViewBean.setOk("Ok");
			supplierPackingViewBeanColl.add(supplierPackingViewBean);

			LabelProcess labelProcess = new LabelProcess(this.getClient());
			resultsObj = labelProcess.buildPrintPackingList(supplierPackingViewBeanColl);
		}
		else
		{
			Collection labelDataCollection = getDd1348Data(labelInputBean.getPackingGroupId());
			if (labelDataCollection.size() ==0)
			{
				String url ="";
				Object[] objs1 = {url,url};
				return objs1;
			}

			PrintDd1348Process printPdfProcess = new PrintDd1348Process(this.getClient());
			resultsObj = printPdfProcess.buildDd1348Pdf(labelDataCollection);
		}

		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");
		/*Nawaz 02-27-08 have to print 2 dd1348 so that they can place one in box and one on box*/
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");

		return resultsObj;
	}

	/*This is for Fedex tracking numbers.*/
	public Object[] buildTrackingLabel(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("46");
		labelInputBean.setSourcePage("POLCHEM");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		Collection supplierPackingViewBeanColl = new Vector();
		SupplierPackingViewBean supplierPackingViewBean = new SupplierPackingViewBean();
		supplierPackingViewBean.setPalletId(labelInputBean.getPalletId());
		supplierPackingViewBean.setCarrierCode(labelInputBean.getCarrierCode());
		supplierPackingViewBean.setConsolidationNumber(labelInputBean.getConsolidationNumber());
		supplierPackingViewBean.setOk("Ok");
		supplierPackingViewBeanColl.add(supplierPackingViewBean);

		LabelProcess labelProcess = new LabelProcess(this.getClient());
		Collection<MslPalletViewBean> palletMslDataColl = labelProcess.getPalletMSLData(supplierPackingViewBeanColl);
		Object[] resultsObj = labelProcess.buildCommercialLabel(labelInputBean,locationLabelPrinterCollection,palletMslDataColl,"trackingpallet");
		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");

		return resultsObj;
	}

	public Object[] buildCommercialLabel(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("46");
		labelInputBean.setSourcePage("POLCHEM");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		Collection supplierPackingViewBeanColl = new Vector();
		SupplierPackingViewBean supplierPackingViewBean = new SupplierPackingViewBean();
		supplierPackingViewBean.setPalletId(labelInputBean.getPalletId());
		supplierPackingViewBean.setCarrierCode(labelInputBean.getCarrierCode());
		supplierPackingViewBean.setConsolidationNumber(labelInputBean.getConsolidationNumber());
		supplierPackingViewBean.setOk("Ok");
		supplierPackingViewBeanColl.add(supplierPackingViewBean);

		LabelProcess labelProcess = new LabelProcess(this.getClient());
		Collection<MslPalletViewBean> palletMslDataColl = labelProcess.getPalletMSLData(supplierPackingViewBeanColl);
		Object[] resultsObj = labelProcess.buildCommercialLabel(labelInputBean,locationLabelPrinterCollection,palletMslDataColl,"commercialpallet");
		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");

		boolean printProjectCode = false;
		for(MslPalletViewBean tempBean : palletMslDataColl) {
			if(log.isDebugEnabled()) {
				log.debug("getPalletProjectCode -"+tempBean.getPalletProjectCode()+"-");
			}
			if (tempBean.getPalletProjectCode() !=null && tempBean.getPalletProjectCode().trim().length() > 0)
			{
				printProjectCode =true;
			}
		}

		if (printProjectCode)
		{
			labelInputBean.setPaperSize("33");
			labelInputBean.setLabelType("PROJECTCODE");

			locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

			printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);

			resultsObj = labelProcess.buildPrintPalletMSL(personnelBean,labelInputBean,locationLabelPrinterCollection,supplierPackingViewBeanColl);
			if(log.isDebugEnabled()) {
				log.debug(""+resultsObj[0]+"");
			}
			if (resultsObj[0].toString().length() >0)
			{
				PrintHandler.print(printerPath, ""+resultsObj[0]+"");
			}
		}
		return resultsObj;
	}

	public Object[] buildPalletMslLabel(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("46");
		labelInputBean.setSourcePage("POLCHEM");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		Collection supplierPackingViewBeanColl = new Vector();
		SupplierPackingViewBean supplierPackingViewBean = new SupplierPackingViewBean();
		supplierPackingViewBean.setPalletId(labelInputBean.getPalletId());
		supplierPackingViewBean.setCarrierCode(labelInputBean.getCarrierCode());
		supplierPackingViewBean.setConsolidationNumber(labelInputBean.getConsolidationNumber());
		supplierPackingViewBean.setOk("Ok");
		supplierPackingViewBeanColl.add(supplierPackingViewBean);

		LabelProcess labelProcess = new LabelProcess(this.getClient());
		Collection<MslPalletViewBean> palletMslDataColl = labelProcess.getPalletMSLData(supplierPackingViewBeanColl);
		Object[] resultsObj = labelProcess.buildPrintPalletMSL(labelInputBean,locationLabelPrinterCollection,palletMslDataColl);
		//Object[] resultsObj = labelProcess.buildPrintPalletMSL(personnelBean,labelInputBean,locationLabelPrinterCollection,supplierPackingViewBeanColl);
		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");

		boolean printProjectCode = false;
		for(MslPalletViewBean tempBean : palletMslDataColl) {
			if(log.isDebugEnabled()) {
				log.debug("getPalletProjectCode -"+tempBean.getPalletProjectCode()+"-");
			}
			if (tempBean.getPalletProjectCode() !=null && tempBean.getPalletProjectCode().trim().length() > 0)
			{
				printProjectCode =true;
			}
		}

		if (printProjectCode)
		{
			labelInputBean.setPaperSize("33");
			labelInputBean.setLabelType("PROJECTCODE");
			locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

			printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
			//inputBean.setPrinterName(printerPath);

			resultsObj = labelProcess.buildPrintPalletMSL(labelInputBean,locationLabelPrinterCollection,palletMslDataColl);
			if(log.isDebugEnabled()) {
				log.debug(""+resultsObj[0]+"");
			}
			if (resultsObj[0].toString().length() >0)
			{
				PrintHandler.print(printerPath, ""+resultsObj[0]+"");
			}
		}

		if (labelInputBean.getFacilityId() != null && labelInputBean.getFacilityId().equalsIgnoreCase("DLA Gases"))
		{
			resultsObj = buildPrintPlacardLabels(palletMslDataColl,locationLabelPrinterCollection);
			if(log.isDebugEnabled()) {
				log.debug(""+resultsObj[0]+"");
			}
			if (resultsObj[0].toString().length() >0)
			{
				PrintHandler.print(printerPath, ""+resultsObj[0]+"");
			}
		}
		return resultsObj;
	}

	public Object[] buildPrintPlacardLabels(Collection<MslPalletViewBean> palletMslDataColl,
			Collection locationLabelPrinterCollection) throws BaseException
			{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");

		com.tcmis.supplier.shipping.process.ZplProcess supplierZplProcess = new com.tcmis.supplier.shipping.process.ZplProcess(this.getClient());
		Collection finalLabelDataCollection = new Vector();
		MslPalletViewBean palletBean = null;

		for (Object palletObj : palletMslDataColl) {
			if (palletObj == null)
				continue;
			palletBean = (MslPalletViewBean) palletObj;
			Collection labelDataCollection = getPlacardLabelData(palletBean);
			finalLabelDataCollection.addAll(labelDataCollection);
		}


		File dir = new File(resource.getString("label.serverpath"));
		try {
			File file = File.createTempFile("labeljnlp", ".jnlp", dir);

			fileAbsolutePath = supplierZplProcess.buildPlacardLabelZpl(finalLabelDataCollection,"",
					locationLabelPrinterCollection,
					file.getAbsolutePath());

			labelUrl = resource.getString("label.hosturl") +
			resource.getString("label.urlpath") +
			file.getName();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			labelUrl = "";
		}
		Object[] resultsObj = {fileAbsolutePath,labelUrl};
		return resultsObj;
			}

	public Collection getPlacardLabelData(MslPalletViewBean palletBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SupplierUsgovLabelViewBean());
		Collection finalLabelDataCollection = new Vector();

		String query = "select distinct sum(QUANTITY) QUANTITY,sum(BOX_GROSS_WEIGHT) GROSS_WEIGHT,UNIT_OF_SALE,INVENTORY_GROUP,NSN,"+
		"CUSTOMER_PO,CAGE_CODE,FORMATTED_NSN,FORMATTED_CUSTOMER_PO,UN_NUMBER_AND_PROPER_SHIP_NAME,PART_SHORT_NAME,"+
		"MHM_DATE,MFG_LOT from USGOV_HAAS_GASES_LABEL_VIEW where PALLET_ID = '"+palletBean.getPalletId()+"'"+
		" group by QUANTITY,UNIT_OF_SALE,INVENTORY_GROUP,NSN,CUSTOMER_PO,CAGE_CODE,FORMATTED_NSN,"+
		"FORMATTED_CUSTOMER_PO,UN_NUMBER_AND_PROPER_SHIP_NAME,PART_SHORT_NAME,MHM_DATE,MFG_LOT";

		finalLabelDataCollection =  factory.setBean(new SupplierUsgovLabelViewBean()).selectQuery(query);

		return finalLabelDataCollection;
	}

	public Object[] buildCaseMslLabel(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("46");
		labelInputBean.setSourcePage("POLCHEM");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		Collection supplierPackingViewBeanColl = new Vector();
		SupplierPackingViewBean supplierPackingViewBean = new SupplierPackingViewBean();
		supplierPackingViewBean.setBoxId(labelInputBean.getBoxId());
		if (labelInputBean.getPackingGroupId() !=null && labelInputBean.getPackingGroupId().length()>0)
		{
			supplierPackingViewBean.setPackingGroupId(new BigDecimal(labelInputBean.getPackingGroupId()));
		}
		supplierPackingViewBean.setOk("Ok");
		supplierPackingViewBeanColl.add(supplierPackingViewBean);

		LabelProcess labelProcess = new LabelProcess(this.getClient());
		Collection<MslBoxViewBean> caseMslDataColl = labelProcess.getCaseMSLData(supplierPackingViewBeanColl);
		//Object[] resultsObj = labelProcess.buildPrintCaseMSL(personnelBean,labelInputBean,locationLabelPrinterCollection,supplierPackingViewBeanColl);
		Object[] resultsObj = labelProcess.buildPrintCaseMSL(labelInputBean,locationLabelPrinterCollection,caseMslDataColl);
		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");

		boolean printProjectCode = false;
		for(MslBoxViewBean tempBean : caseMslDataColl) {
			if (tempBean.getProjectCode() !=null && tempBean.getProjectCode().trim().length() > 0)
			{
				printProjectCode =true;
			}
		}

		if (printProjectCode)
		{
			labelInputBean.setPaperSize("33");
			labelInputBean.setLabelType("PROJECTCODE");
			locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

			printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
			//inputBean.setPrinterName(printerPath);

			resultsObj = labelProcess.buildPrintCaseMSL(labelInputBean,locationLabelPrinterCollection,caseMslDataColl);
			if(log.isDebugEnabled()) {
				log.debug(""+resultsObj[0]+"");
			}
			if (resultsObj[0].toString().length() >0)
			{
				PrintHandler.print(printerPath, ""+resultsObj[0]+"");
			}
		}
		return resultsObj;
	}

	public Object[] buildFlashPointLabel(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("33");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());
		labelInputBean.setSkipKitLabels("Yes");

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		HubLabelProcess process = new HubLabelProcess(this.getClient());
		Object[] resultsObj = process.buildUnitBoxLabels(labelInputBean,"FlashPoint",locationLabelPrinterCollection,null);
		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		if (resultsObj[0].toString().length() >0)
		{
			PrintHandler.print(printerPath, ""+resultsObj[0]+"");
		}
		return resultsObj;
	}

	public Object[] buildProjectCodeLabel(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("33");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());
		labelInputBean.setSkipKitLabels("Yes");

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		HubLabelProcess process = new HubLabelProcess(this.getClient());
		Object[] resultsObj = process.buildUnitBoxLabels(labelInputBean,"ProjectCode",locationLabelPrinterCollection,null);
		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		if (resultsObj[0].toString().length() >0)
		{
			PrintHandler.print(printerPath, ""+resultsObj[0]+"");
		}
		return resultsObj;
	}

	public Object[] buildExternalLabel(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("33");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());
		labelInputBean.setSkipKitLabels("Yes");

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		HubLabelProcess process = new HubLabelProcess(this.getClient());
		Object[] resultsObj = process.buildUnitBoxLabels(labelInputBean,"External",locationLabelPrinterCollection,null);
		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");
		return resultsObj;
	}

	public Object[] buildUnitLabel(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("33");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());
		labelInputBean.setSkipKitLabels("");

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		HubLabelProcess process = new HubLabelProcess(this.getClient());
		Object[] resultsObj = process.buildUnitBoxLabels(labelInputBean,"Unit",locationLabelPrinterCollection,null);
		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");
		return resultsObj;
	}

	public Collection getLabelData(String boxId,String skipUnitLabels,String facilityId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new UsgovLabelViewBean());
		Collection finalLabelDataCollection = new Vector();
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("boxId", SearchCriterion.EQUALS,
				"" + boxId + "");

		Collection receiptLabelDataColl = new Vector();
		SortCriteria sort = new SortCriteria();

		if (facilityId.equalsIgnoreCase("DLA Gases"))
		{
			/*TODO use the new label view Diane will create*/
			receiptLabelDataColl =  factory.setBean(new UsgovLabelViewBean()).
			select(criteria,sort, "USGOV_HAAS_GASES_LABEL_VIEW");
		}
		else
		{
			receiptLabelDataColl =  factory.setBean(new UsgovLabelViewBean()).
			select(criteria,sort, "USGOV_LABEL_VIEW");
		}
		Iterator labelDataiterator = receiptLabelDataColl.iterator();
		while (labelDataiterator.hasNext()) {
			UsgovLabelViewBean usgovLabelViewBean = (UsgovLabelViewBean) labelDataiterator.next();
			usgovLabelViewBean.setSkipUnitLabels(skipUnitLabels);
		}
		return receiptLabelDataColl;
	}

	public Collection getDd1348Data(String packingGroupId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		Usgov1348ViewBeanFactory factory = new
		Usgov1348ViewBeanFactory(dbManager);

		Collection finalLabelDataCollection = new Vector();

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("packingGroupId", SearchCriterion.EQUALS,
				"" + packingGroupId + "");
		finalLabelDataCollection = factory.select(criteria,null);
		return finalLabelDataCollection;
	}

	public Object[] buildPrintPackingListGeneric(LabelInputBean labelInputBean,String tableName) throws BaseException,Exception
	{
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		labelInputBean.setPaperSize("DD1348");
		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);

		Collection labelDataCollection = getDd1348DataGeneric(labelInputBean.getPackingGroupId(),tableName);
		Object[] resultsObj;

		if (labelDataCollection.size() ==0)
		{
			String url ="";
			Object[] objs1 = {url,url};
			return objs1;
		}
		PrintDd1348Process printPdfProcess = new PrintDd1348Process(this.getClient());
		resultsObj = printPdfProcess.buildDd1348Pdf(labelDataCollection);

		if(log.isDebugEnabled()) {
			log.debug(""+resultsObj[0]+"");
		}
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");
		/*Nawaz 02-27-08 have to print 2 dd1348 so that they can place one in box and one on box*/
		PrintHandler.print(printerPath, ""+resultsObj[0]+"");

		return resultsObj;
	}

	public Collection<Usgov1348ViewBean> getDd1348DataGeneric(String packingGroupId,String tableName) throws BaseException {
		DbManager dbManager = new DbManager(getClient());

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("packingGroupId", SearchCriterion.EQUALS,
				"" + packingGroupId + "");

		GenericSqlFactory fac = new GenericSqlFactory(dbManager,new Usgov1348ViewBean());
		return fac.select(criteria, null, tableName);
	}

}
