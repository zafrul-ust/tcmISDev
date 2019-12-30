package com.tcmis.client.raytheon.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.client.raytheon.beans.CatPartHazardViewBean;
import com.tcmis.client.raytheon.factory.CatPartHazardViewBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.InventoryGroupLabelFormatBean;
import com.tcmis.internal.hub.beans.LabelFormatFieldDefinitionBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.process.ZplProcess;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class SecondaryLabelProcess
extends BaseProcess {

	public SecondaryLabelProcess(String client) {
		super(client);
	}

	public Collection getLabelData(Collection catPartHazardViewBeanCollection) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient());
		CatPartHazardViewBeanFactory factory = new CatPartHazardViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();
		Iterator iterator = catPartHazardViewBeanCollection.iterator();
		int count = 0;
		Collection catalogIdCollection = new Vector(10);

		while (iterator.hasNext()) {
			CatPartHazardViewBean catPartHazardViewBean = (CatPartHazardViewBean)
			iterator.next();

			if (!catalogIdCollection.contains(catPartHazardViewBean.getCatalogId()))
			{
				catalogIdCollection.add(catPartHazardViewBean.getCatalogId());
			}

			if (count == 0) {
				criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,
						catPartHazardViewBean.getCatPartNo());
			}
			else {
				criteria.addValueToCriterion("catPartNo",
						catPartHazardViewBean.getCatPartNo());
			}
			count++;
		}

		iterator = catalogIdCollection.iterator();
		count = 0;
		while (iterator.hasNext()) {
			String catalogId = (String) iterator.next();
			if (count == 0) {
				criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
			}
			else {
				criteria.addValueToCriterion("catalogId", catalogId);
			}
			count++;
		}

		return factory.select(criteria);
	}

	public String buildSecondaryLabels(PersonnelBean personnelBean,
			LabelInputBean labelInputBean,
			Collection catPartHazardViewBeanCollection) throws BaseException {

		ZplProcess zplProcess = new ZplProcess(this.getClient());
		ResourceLibrary resource = new ResourceLibrary("label");
		String labelUrl = "";

		try {
			File dir = new File(resource.getString("label.serverpath"));
			//File labelZplfile = File.createTempFile("secondarylabel", ".txt", dir);
			File labelJnlpfile = File.createTempFile("secondarylabel", ".jnlp", dir);
			String printerPath = "";

			//LabelProcess labelProcess = new LabelProcess(this.getClient());
			ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
			Collection locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);
			Iterator printerIterator = locationLabelPrinterCollection.iterator();
			printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
			/*while (printerIterator.hasNext()) {
		LocationLabelPrinterBean locationLabelPrinterBean = (
		 LocationLabelPrinterBean) printerIterator.next(); ;
		printerPath = locationLabelPrinterBean.getPrinterPath();
	 }*/

			//writeZplFile(catPartHazardViewBeanCollection,labelInputBean,labelZplfile.getAbsolutePath());
			if ("31".equalsIgnoreCase(labelInputBean.getPaperSize()))
			{
				buildLabelZpl(catPartHazardViewBeanCollection,printerPath,locationLabelPrinterCollection,labelJnlpfile.getAbsolutePath(),"seclabel",labelInputBean.getInventoryGroup());
			}
			else
			{
				buildLabelZpl(catPartHazardViewBeanCollection,printerPath,locationLabelPrinterCollection,labelJnlpfile.getAbsolutePath(),"seclargelabel",labelInputBean.getInventoryGroup());
			}

			//com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(labelJnlpfile.getAbsolutePath(), labelZplfile.getName(), printerPath);

			labelUrl = resource.getString("label.hosturl") +
			resource.getString("label.urlpath") +
			labelJnlpfile.getName();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			labelUrl = "";
		}

		return labelUrl;
	}

	public String buildLabelZpl(Collection labelDataCollection,String printerPath,
			Collection locationLabelPrinterCollection,
			String filePath,String labelType,String inventoryGroup) throws Exception {

		ResourceLibrary resource = new ResourceLibrary("label");
		File dir = new File(resource.getString("label.serverpath"));
		File file = File.createTempFile("secondarylabel", ".txt", dir);
		String fileAbsolutePath = file.getAbsolutePath();
		PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));

		DbManager dbManager = new DbManager(getClient());
		//LabelProcess labelProcess = new LabelProcess(getClient());
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());

		Vector distinctInventoryGroupVector = new Vector();
		distinctInventoryGroupVector.add(inventoryGroup);

		Collection labelFormatCollection = new Vector();
		labelFormatCollection = zplDataProcess.getLabelFormats(
				distinctInventoryGroupVector, labelType);

		String printerResolution = "";
		Iterator printerIterator = locationLabelPrinterCollection.iterator();
		while (printerIterator.hasNext()) {
			LocationLabelPrinterBean locationLabelPrinterBean = (
					LocationLabelPrinterBean) printerIterator.next(); ;

					printerResolution = "" + locationLabelPrinterBean.getPrinterResolutionDpi() +"";
					printerPath = locationLabelPrinterBean.getPrinterPath();
		}

		Collection labelFieldDefinitionCollection = new Vector();
		labelFieldDefinitionCollection = zplDataProcess.getLabelFormatFieldDefinition(
				labelFormatCollection, printerResolution);

		/*    Collection kitlabelFormatCollection = new Vector();
    kitlabelFormatCollection = zplDataProcess.getLabelFormats(
     distinctInventoryGroupVector, "usgovunit");

    Collection kitLabelFieldDefinitionCollection = new Vector();
    kitLabelFieldDefinitionCollection = zplDataProcess.
     getLabelFormatFieldDefinition(kitlabelFormatCollection, printerResolution);*/


		if (labelFieldDefinitionCollection == null ||
				labelFieldDefinitionCollection.size() == 0) {
			BaseException ex = new BaseException();
			ex.setMessageKey("error.nolabeltemplate.defined");
			throw ex;
		}

		StringBuilder zpl = new StringBuilder(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(
				zplDataProcess.getTemplates(distinctInventoryGroupVector, labelType, 
						printerResolution)));

		try {
			Iterator mainIterator = labelDataCollection.iterator();
			while (mainIterator.hasNext()) {
				CatPartHazardViewBean catPartHazardViewBean = (
						CatPartHazardViewBean) mainIterator.next(); ;

						StringBuilder secondaryLabel = new StringBuilder();
						if (catPartHazardViewBean.getLabelQty() !=null && catPartHazardViewBean.getLabelQty().length() > 0)
						{
							secondaryLabel.append(secondaryLabel(catPartHazardViewBean,labelFormatCollection,
									labelFieldDefinitionCollection,inventoryGroup));
						}
						zpl.append(secondaryLabel);
			}
			pw.print(zpl.toString());
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}
		finally {
			pw.close();
		}
		com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(filePath, file.getName(), printerPath, zpl.toString());
		return fileAbsolutePath;
	}

	public StringBuilder secondaryLabel(CatPartHazardViewBean bean,
			Collection labelFormatCollection,
			Collection labelFieldDefinitionCollection,String inventoryGroup) {
		StringBuilder receiptLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

		String labelFormat = "";
		Iterator mainIterator = labelFormatCollection.iterator();
		while (mainIterator.hasNext()) {
			InventoryGroupLabelFormatBean inventoryGroupLabelFormatBean = (
					InventoryGroupLabelFormatBean) mainIterator.next(); ;

					if (inventoryGroupLabelFormatBean.getInventoryGroup().equalsIgnoreCase(
							inventoryGroup)) {
						labelFormat = inventoryGroupLabelFormatBean.getLabelFormat();
					}
		}

		receiptLabel.append("^XA" ).append( lineFeed);
		receiptLabel.append("^XF" ).append( labelFormat ).append( "^FS" ).append( lineFeed);

		Iterator labelFieldIterator = labelFieldDefinitionCollection.iterator();
		while (labelFieldIterator.hasNext()) {
			LabelFormatFieldDefinitionBean labelFormatFieldDefinitionBean = (
					LabelFormatFieldDefinitionBean) labelFieldIterator.next(); ;

					//log.info("Field Label Format  "+labelFormatFieldDefinitionBean.getLabelFormat()+"  Compare To "+labelFormat+"Strurts"+"");
					if (labelFormatFieldDefinitionBean.getLabelFormat().equalsIgnoreCase(
							labelFormat + "Struts")) {

						String labelFieldId = labelFormatFieldDefinitionBean.getLabelFieldId();
						String labelFieldContent = labelFormatFieldDefinitionBean.
						getLabelFieldContent();

						try {
							if ("FN2".equalsIgnoreCase(labelFieldId)) {
								receiptLabel.append("^" ).append( labelFieldId ).append( "^FDP/N: " ).append( bean.getCatPartNo() ).append( "^FS" ).append( lineFeed);
							}
							else
							{
								receiptLabel.append("^" ).append( labelFieldId ).append( "^FD" ).append( BeanHandler.getField(bean, labelFieldContent) ).append( "^FS" ).append( lineFeed);
							}
						}
						catch (SecurityException ex) {
							ex.printStackTrace();
						}
						catch (IllegalArgumentException ex) {
							ex.printStackTrace();
						}
						catch (BaseException ex) {
							ex.printStackTrace();
						}
					}
		}

		receiptLabel.append("^PQ" + bean.getLabelQty() + "" + lineFeed);
		receiptLabel.append("^XZ" + lineFeed);

		return receiptLabel;
	}
	/*
 public void writeZplFile(Collection catPartHazardViewBeanCollection,LabelInputBean labelInputBean,
	String filePath) throws BaseException, Exception {

	PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
	pw.print(loadtemplates("raytheonsecondarylabel.txt"));

	String linefeedd = "";
	linefeedd += (char) (13);
	linefeedd += (char) (10);

	Iterator iterator = catPartHazardViewBeanCollection.iterator();
	while (iterator.hasNext()) {
	 CatPartHazardViewBean catPartHazardViewBean = (CatPartHazardViewBean)
		iterator.next();
	 if (catPartHazardViewBean.getLabelQty() !=null && catPartHazardViewBean.getLabelQty().length() > 0)
	 {
		pw.print("^XA" + linefeedd);
		if ("31".equalsIgnoreCase(labelInputBean.getPaperSize()))
		{
		 pw.print("^XFSECONDARYLABEL^FS" + linefeedd);
		}
		else
		{
		 pw.print("^XFSECONDARYLARGELABEL^FS" + linefeedd);
		}

		pw.print("^FN1^FD" + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getPartShortName()) + "^FS" + linefeedd);
		pw.print("^FN2^FDP/N: " + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getCatPartNo()) + "^FS" + linefeedd);
		pw.print("^FN3^FD" + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getSignalWord()) + "^FS" + linefeedd);
		pw.print("^FN4^FD" + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getTargetOrgan()) + "^FS" + linefeedd);
		pw.print("^FN5^FD" + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getFlammability()) + "^FS" + linefeedd);
		pw.print("^FN6^FD" + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getHealth()) + "^FS" + linefeedd);
		pw.print("^FN7^FD" + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getReactivity()) + "^FS" + linefeedd);
		pw.print("^FN8^FD" + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getSpecificHazard()) + "^FS" + linefeedd);
		pw.print("^PQ" + com.tcmis.common.util.StringHandler.emptyIfNull(catPartHazardViewBean.getLabelQty()) + "" + linefeedd);
		pw.print("^XZ" + linefeedd);
	 }
	}

	pw.close();
 }

 public StringBuilder loadtemplates(String filepath) {
	StringBuilder itmlbltemp = new StringBuilder();
	String linefeedd = "";
	linefeedd += (char) (13);
	linefeedd += (char) (10);

	ResourceLibrary resource = new ResourceLibrary("label");
	String templatpath = resource.getString("label.template.path");

	try {
	 BufferedReader buf = new BufferedReader(new FileReader("" + templatpath +
		filepath + ""));
	 int i = 0;
	 while ( (i = buf.read()) != -1) {
		String temp = buf.readLine();
		itmlbltemp.append("^" + temp + linefeedd);
	 }
	}
	catch (Exception ex) {
	 ex.printStackTrace();
	}

	return itmlbltemp;
 }*/
}