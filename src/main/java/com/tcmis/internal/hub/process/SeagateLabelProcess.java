package com.tcmis.internal.hub.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.InventoryGroupLabelFormatBean;
import com.tcmis.internal.hub.beans.LabelFormatFieldDefinitionBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.beans.SeagateLabelViewBean;

public class SeagateLabelProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public SeagateLabelProcess(String client) {
		super(client);
	}

	public void buildReceiptZpl(Collection labelDataCollection, String labelStock, String printerPath, Collection locationLabelPrinterCollection, String filePath) throws Exception {
		ResourceLibrary resource = new ResourceLibrary("label");
		File dir = new File(resource.getString("label.serverpath"));
		File file = File.createTempFile("labeltxt", ".txt", dir);
		PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));

		DbManager dbManager = new DbManager(getClient());
		//LabelProcess labelProcess = new LabelProcess(getClient());
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());

		Vector distinctInventoryGroupVector = new Vector();
		Iterator mainIterator = labelDataCollection.iterator();
		while (mainIterator.hasNext()) {
			SeagateLabelViewBean seagateLabelViewBean = (SeagateLabelViewBean) mainIterator.next();
			;

			String currentInventoryGroup = seagateLabelViewBean.getInventoryGroup();
			if (!distinctInventoryGroupVector.contains(currentInventoryGroup)) {
				distinctInventoryGroupVector.add(currentInventoryGroup);
			}
		}

		Collection labelFormatCollection = new Vector();
		Collection labelFormatLocaleCollection = new Vector();

		labelFormatCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "container");
		labelFormatLocaleCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "containerLocale");

		String printerResolution = "";
		/*Collection locationLabelPrinterCollection = new Vector();
			LocationLabelPrinterBeanFactory factory =
		 new LocationLabelPrinterBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("printerLocation", SearchCriterion.EQUALS,
		 personnelBean.getPrinterLocation());
			criteria.addCriterion("labelStock", SearchCriterion.EQUALS,
		 labelStock);
			if (printerPath.length() > 0) {
		 criteria.addCriterion("printerPath", SearchCriterion.EQUALS,
		 printerPath);
			}
			locationLabelPrinterCollection = factory.selectDistinct(criteria);*/
		Iterator printerIterator = locationLabelPrinterCollection.iterator();
		while (printerIterator.hasNext()) {
			LocationLabelPrinterBean locationLabelPrinterBean = (LocationLabelPrinterBean) printerIterator.next();
			;

			printerResolution = "" + locationLabelPrinterBean.getPrinterResolutionDpi() + "";
			printerPath = locationLabelPrinterBean.getPrinterPath();
		}

		Collection labelFieldDefinitionCollection = new Vector();
		labelFieldDefinitionCollection = zplDataProcess.getLabelFormatFieldDefinition(labelFormatCollection, printerResolution);

		Collection labelFieldDefinitionLocaleCollection = new Vector();
		labelFieldDefinitionLocaleCollection = zplDataProcess.getLabelFormatFieldDefinition(labelFormatLocaleCollection, printerResolution);

		if (labelFieldDefinitionCollection == null || labelFieldDefinitionCollection.size() == 0) {
			BaseException ex = new BaseException();
			ex.setMessageKey("error.nolabeltemplate.defined");
			throw ex;
		}

		StringBuilder zpl = new StringBuilder(
				com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "container", printerResolution)));

		try {
			mainIterator = labelDataCollection.iterator();
			while (mainIterator.hasNext()) {
				SeagateLabelViewBean seagateLabelViewBean = (SeagateLabelViewBean) mainIterator.next();
				;

				int quantityReceived = seagateLabelViewBean.getQuantityReceived().intValue();

				StringBuilder receiptLabel = new StringBuilder();
				boolean usePqValue = true;

				receiptLabel
				.append(seagateSafetyLabel(seagateLabelViewBean, "" + (quantityReceived) + "", labelFormatCollection, labelFieldDefinitionCollection, labelFormatLocaleCollection, labelFieldDefinitionLocaleCollection,
						usePqValue));

				if (usePqValue) {
					zpl.append(receiptLabel);
				}
				else {
					for (int nooflabels = 0; nooflabels < quantityReceived; nooflabels++) {
						zpl.append(receiptLabel);
					}
				}
				/*
						if ("31".equalsIgnoreCase(labelStock)) {
						 pw.println("^XA");
						 pw.println("^PH");
						 pw.println("^XZ");
						}*/
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
	}

	public StringBuilder seagateSafetyLabel(SeagateLabelViewBean bean, String labelQuantity, Collection labelFormatCollection, Collection labelFieldDefinitionCollection, Collection labelFormatLocaleCollection,
			Collection labelFieldDefinitionLocaleCollection, boolean pqvalues) {
		StringBuilder receiptLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

		boolean twoDBarcode = false;
		if (labelFormatLocaleCollection != null && labelFormatLocaleCollection.size() > 0) {
			twoDBarcode = true;
		}

		String inventoryGroup = bean.getInventoryGroup();
		String labelFormat = "";
		Iterator mainIterator = labelFormatCollection.iterator();
		while (mainIterator.hasNext()) {
			InventoryGroupLabelFormatBean inventoryGroupLabelFormatBean = (InventoryGroupLabelFormatBean) mainIterator.next();
			;

			if (inventoryGroupLabelFormatBean.getInventoryGroup().equalsIgnoreCase(inventoryGroup)) {
				labelFormat = inventoryGroupLabelFormatBean.getLabelFormat();
			}
		}

		receiptLabel.append("^XA").append(lineFeed);
		receiptLabel.append("^XF").append(labelFormat).append("^FS").append(lineFeed);

		Iterator labelFieldIterator = labelFieldDefinitionCollection.iterator();
		while (labelFieldIterator.hasNext()) {
			LabelFormatFieldDefinitionBean labelFormatFieldDefinitionBean = (LabelFormatFieldDefinitionBean) labelFieldIterator.next();
			;

			//log.info("Field Label Format  "+labelFormatFieldDefinitionBean.getLabelFormat()+"  Compare To "+labelFormat+"Strurts"+"");
			if (labelFormatFieldDefinitionBean.getLabelFormat().equalsIgnoreCase(labelFormat + "Struts")) {

				String labelFieldId = labelFormatFieldDefinitionBean.getLabelFieldId();
				String labelFieldContent = labelFormatFieldDefinitionBean.getLabelFieldContent();

				try {
					if ("FN1".equalsIgnoreCase(labelFieldId) && !twoDBarcode) {
						receiptLabel.append("^").append(labelFieldId).append("^FD>;").append(BeanHandler.getField(bean, labelFieldContent)).append("^FS").append(lineFeed);
					}
					else {
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(BeanHandler.getField(bean, labelFieldContent)).append("^FS").append(lineFeed);
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

		if (pqvalues) {
			receiptLabel.append("^PQ").append(labelQuantity).append("").append(lineFeed);
		}
		else {
			receiptLabel.append("^PQ1").append(lineFeed);
		}
		receiptLabel.append("^XZ").append(lineFeed);

		if (labelFormatLocaleCollection != null && labelFormatLocaleCollection.size() > 0) {
			String labelLocaleFormat = "";
			Iterator mainLocaleIterator = labelFormatLocaleCollection.iterator();
			while (mainLocaleIterator.hasNext()) {
				InventoryGroupLabelFormatBean inventoryGroupLabelFormatBean = (InventoryGroupLabelFormatBean) mainLocaleIterator.next();
				;

				if (inventoryGroupLabelFormatBean.getInventoryGroup().equalsIgnoreCase(inventoryGroup)) {
					labelLocaleFormat = inventoryGroupLabelFormatBean.getLabelFormat();
				}
			}

			receiptLabel.append("^XA").append(lineFeed);
			receiptLabel.append("^XF").append(labelLocaleFormat).append("^FS").append(lineFeed);

			Iterator labelFieldLocaleIterator = labelFieldDefinitionLocaleCollection.iterator();
			while (labelFieldLocaleIterator.hasNext()) {
				LabelFormatFieldDefinitionBean labelFormatFieldDefinitionBean = (LabelFormatFieldDefinitionBean) labelFieldLocaleIterator.next();
				;

				//log.info("Field Label Format  "+labelFormatFieldDefinitionBean.getLabelFormat()+"  Compare To "+labelFormat+"Strurts"+"");
				if (labelFormatFieldDefinitionBean.getLabelFormat().equalsIgnoreCase(labelLocaleFormat + "Struts")) {

					String labelFieldId = labelFormatFieldDefinitionBean.getLabelFieldId();
					String labelFieldContent = labelFormatFieldDefinitionBean.getLabelFieldContent();
					try {
						if ("FN1".equalsIgnoreCase(labelFieldId) && !twoDBarcode) {
							receiptLabel.append("^").append(labelFieldId).append("^FD>;").append(BeanHandler.getField(bean, labelFieldContent)).append("^FS").append(lineFeed);
						}
						else {
							receiptLabel.append("^").append(labelFieldId).append("^FD").append(BeanHandler.getField(bean, labelFieldContent)).append("^FS").append(lineFeed);
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

			if (pqvalues) {
				receiptLabel.append("^PQ").append(labelQuantity).append("").append(lineFeed);
			}
			else {
				receiptLabel.append("^PQ1").append(lineFeed);
			}
			receiptLabel.append("^XZ").append(lineFeed);
		}
		return receiptLabel;
	}
}