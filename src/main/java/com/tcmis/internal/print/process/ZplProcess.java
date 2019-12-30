package com.tcmis.internal.print.process;

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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.InventoryGroupLabelFormatBean;
import com.tcmis.internal.hub.beans.LabelFormatFieldDefinitionBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.print.beans.UsgovLabelViewBean;

public class ZplProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ZplProcess(String client) {
		super(client);
	}

	public String buildLabelZpl(Collection labelDataCollection, String printerPath, Collection locationLabelPrinterCollection, String filePath, String labelType) throws Exception {

		ResourceLibrary resource = new ResourceLibrary("label");
		File dir = new File(resource.getString("label.serverpath"));
		File file = File.createTempFile("labeltxt", ".txt", dir);
		String fileAbsolutePath = file.getAbsolutePath();
		PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));

		DbManager dbManager = new DbManager(getClient());
		//LabelProcess labelProcess = new LabelProcess(getClient());
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());

		Vector distinctInventoryGroupVector = new Vector();
		Iterator mainIterator = labelDataCollection.iterator();
		while (mainIterator.hasNext()) {
			UsgovLabelViewBean usgovLabelViewBean = (UsgovLabelViewBean) mainIterator.next();
			;

			String currentInventoryGroup = usgovLabelViewBean.getInventoryGroup();

			//log.info("currentInventoryGroup   "+currentInventoryGroup);
			if (!distinctInventoryGroupVector.contains(currentInventoryGroup)) {
				distinctInventoryGroupVector.add(currentInventoryGroup);
			}
		}

		Collection labelFormatCollection = new Vector();
		labelFormatCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "usgovbox");

		String printerResolution = "";
		Iterator printerIterator = locationLabelPrinterCollection.iterator();
		while (printerIterator.hasNext()) {
			LocationLabelPrinterBean locationLabelPrinterBean = (LocationLabelPrinterBean) printerIterator.next();
			;

			printerResolution = "" + locationLabelPrinterBean.getPrinterResolutionDpi() + "";
			printerPath = locationLabelPrinterBean.getPrinterPath();
		}

		Collection labelFieldDefinitionCollection = new Vector();
		labelFieldDefinitionCollection = zplDataProcess.getLabelFormatFieldDefinition(labelFormatCollection, printerResolution);

		Collection kitlabelFormatCollection = new Vector();
		kitlabelFormatCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "usgovunit");

		Collection kitLabelFieldDefinitionCollection = new Vector();
		kitLabelFieldDefinitionCollection = zplDataProcess.getLabelFormatFieldDefinition(kitlabelFormatCollection, printerResolution);

		if (labelFieldDefinitionCollection == null || labelFieldDefinitionCollection.size() == 0) {
			BaseException ex = new BaseException();
			ex.setMessageKey("error.nolabeltemplate.defined");
			throw ex;
		}

		StringBuilder zpl = new StringBuilder();
		if (labelType.equalsIgnoreCase("ProjectCode")) {
			zpl.append(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "projectcode", printerResolution)));
		}
		else {
			zpl.append(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "usgovbox", printerResolution)));
		}
		/*Do not want to send a file path back if no project code or flash point labels are printed*/
		int qtyProjectCodeFlashPt = 0;
		try {
			mainIterator = labelDataCollection.iterator();
			while (mainIterator.hasNext()) {
				UsgovLabelViewBean usgovLabelViewBean = (UsgovLabelViewBean) mainIterator.next();
				;

				StringBuilder boxLabel = new StringBuilder();
				StringBuilder unitLabel = new StringBuilder();

				if (labelType.equalsIgnoreCase("Both") || labelType.equalsIgnoreCase("External")) {
					boxLabel.append(boxLabel(usgovLabelViewBean, labelFormatCollection, labelFieldDefinitionCollection));
					/*     if (!StringHandler.isBlankString(usgovLabelViewBean.getProjectCode()))
					{
					boxLabel.append(projectCodeLabel(usgovLabelViewBean));
					}*/
					if (!StringHandler.isBlankString(usgovLabelViewBean.getFlashpointInfo())) {
						boxLabel.append(flashPointLabel(usgovLabelViewBean));
					}
				}
				else if (labelType.equalsIgnoreCase("ProjectCode")) {
					if (!StringHandler.isBlankString(usgovLabelViewBean.getProjectCode())) {
						boxLabel.append(projectCodeLabel(usgovLabelViewBean));
						qtyProjectCodeFlashPt++;
					}
				}
				else if (labelType.equalsIgnoreCase("FlashPoint")) {
					if (!StringHandler.isBlankString(usgovLabelViewBean.getFlashpointInfo())) {
						boxLabel.append(flashPointLabel(usgovLabelViewBean));
						qtyProjectCodeFlashPt++;
					}
				}
				/*
				if (shipped as single = 'Y' then part per box =1 and dont SkipUnitLabels then print unit labels

				if shipped as single = 'N' and parts per box = 1 and SkipUnitLabels then dont print unit labels

				quantity can not be > 1 for shipped as single = 'Y'

				if qunaity > 1 and shipped as single = 'N' and SkipUnitLabels then dont print unit labels
				 */

				/*if (quantity > 1 || (usgovLabelViewBean.getShippedAsSingle() !=null
				&& !usgovLabelViewBean.getShippedAsSingle().equalsIgnoreCase("Y"))) {
				 */
				if (labelType.equalsIgnoreCase("Both") || labelType.equalsIgnoreCase("Unit")) {
					if(log.isDebugEnabled()) {
						log.debug("getSkipUnitLabels  " + usgovLabelViewBean.getSkipUnitLabels());
					}
					/*Need to print unit labels only if the owner_company_id is <> "USGOV" - we are assuming all VMI will be pre-labeled.
					 * Also if receipt.unit_label_printed = 'Y' we don't need to print a unit label.
					 * */
					if (!usgovLabelViewBean.getUnitLabelPrinted().equalsIgnoreCase("Y")) {
						if (!("USGOV").equalsIgnoreCase(usgovLabelViewBean.getOwnerCompanyId()) && ((usgovLabelViewBean.getSkipUnitLabels() == null || usgovLabelViewBean.getSkipUnitLabels().length() == 0))) {
							int quantity = usgovLabelViewBean.getQuantity().intValue();
							unitLabel.append(unitLabel(usgovLabelViewBean, "" + quantity + "", kitlabelFormatCollection, kitLabelFieldDefinitionCollection, true));
							/*if (!StringHandler.isBlankString(usgovLabelViewBean.getFlashpointInfo()))
							{
							boxLabel.append(flashPointLabel(usgovLabelViewBean));
							}*/
						}
					}
				}

				zpl.append(boxLabel);
				zpl.append(unitLabel);
			}
			pw.print(zpl.toString());
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}
		finally {
			pw.close();
		}
		if ((labelType.equalsIgnoreCase("ProjectCode") || labelType.equalsIgnoreCase("FlashPoint")) && qtyProjectCodeFlashPt == 0) {
			return "";
		}
		else {
			com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(filePath, file.getName(), printerPath, zpl.toString());
			return fileAbsolutePath;
		}
	}

	public StringBuilder projectCodeLabel(UsgovLabelViewBean bean) {
		StringBuilder projectLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

		projectLabel.append("^XA").append(lineFeed);
		projectLabel.append("^XFprojectcodelabel^FS").append(lineFeed);
		projectLabel.append("^FXProject code^FS").append(lineFeed);
		projectLabel.append("^FN1^FD").append(bean.getProjectCode()).append("^FS").append(lineFeed);
		projectLabel.append("^PQ1").append(lineFeed);
		projectLabel.append("^XZ").append(lineFeed);

		return projectLabel;
	}

	public StringBuilder flashPointLabel(UsgovLabelViewBean bean) {
		StringBuilder projectLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

		projectLabel.append("^XA").append(lineFeed);
		projectLabel.append("^XFflashpointlabel^FS").append(lineFeed);
		projectLabel.append("^FXflash Point^FS").append(lineFeed);
		projectLabel.append("^FN1^FD").append(bean.getFlashpointInfo()).append("^FS").append(lineFeed);
		projectLabel.append("^PQ1").append(lineFeed);
		projectLabel.append("^XZ").append(lineFeed);

		return projectLabel;
	}

	public StringBuilder boxLabel(UsgovLabelViewBean bean, Collection labelFormatCollection, Collection labelFieldDefinitionCollection) {
		StringBuilder receiptLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

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
					if ("FN11".equalsIgnoreCase(labelFieldId)) {
						if (bean.getDateOfManufacture() != null && bean.getDateOfManufacture().length() > 0) {
							receiptLabel.append("^").append(labelFieldId).append("^FDMFD ").append(bean.getDateOfManufacture()).append("^FS").append(lineFeed);
						}
					}
					else if ("FN12".equalsIgnoreCase(labelFieldId)) {
						if (bean.getExpireDate() != null && (bean.getExpireDate().length() > 0 && !bean.getExpireDate().equalsIgnoreCase("Indefinite"))) {
							receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getExpireDate()).append("^FS").append(lineFeed);
						}
					}
					/*Printing the box ID for polchem project instead of box_label_id. stripping the - from box_id*/
					else if ("FN13".equalsIgnoreCase(labelFieldId) && bean.getBoxId() != null) {
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(StringHandler.replace(bean.getBoxId(), "-", "")).append("^FS").append(lineFeed);
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

		/*if (pqvalues) {
		receiptLabel.append("^PQ" ).append( labelQuantity ).append( "" ).append( lineFeed);
		}
		else*/{
			receiptLabel.append("^PQ1").append(lineFeed);
		}
		receiptLabel.append("^XZ").append(lineFeed);

		return receiptLabel;
	}

	public StringBuilder unitLabel(UsgovLabelViewBean bean, String labelQuantity, Collection labelFormatCollection, Collection labelFieldDefinitionCollection, boolean pqvalues) {
		StringBuilder receiptKitLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

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

		receiptKitLabel.append("^XA").append(lineFeed);
		receiptKitLabel.append("^XF").append(labelFormat).append("^FS").append(lineFeed);

		Iterator labelFieldIterator = labelFieldDefinitionCollection.iterator();
		while (labelFieldIterator.hasNext()) {
			LabelFormatFieldDefinitionBean labelFormatFieldDefinitionBean = (LabelFormatFieldDefinitionBean) labelFieldIterator.next();
			;
			if (labelFormatFieldDefinitionBean.getLabelFormat().equalsIgnoreCase(labelFormat + "Struts")) {
				String labelFieldId = labelFormatFieldDefinitionBean.getLabelFieldId();
				String labelFieldContent = labelFormatFieldDefinitionBean.getLabelFieldContent();
				try {
					//log.info("labelFieldContent " + labelFieldContent + " Value " +BeanHandler.getField(bean, labelFieldContent) + "");
					if ("FN9".equalsIgnoreCase(labelFieldId)) {
						if (bean.getDateOfManufacture() != null && bean.getDateOfManufacture().length() > 0) {
							receiptKitLabel.append("^").append(labelFieldId).append("^FDMFD ").append(bean.getDateOfManufacture()).append("^FS").append(lineFeed);
						}
					}
					else if ("FN10".equalsIgnoreCase(labelFieldId)) {
						if (bean.getExpireDate() != null && (bean.getExpireDate().length() > 0 && !bean.getExpireDate().equalsIgnoreCase("Indefinite"))) {
							receiptKitLabel.append("^").append(labelFieldId).append("^FD").append(bean.getExpireDate()).append("^FS").append(lineFeed);
						}
					}
					else if ("FN6".equalsIgnoreCase(labelFieldId)) {
						receiptKitLabel.append("^").append(labelFieldId).append("^FD1 ").append(bean.getUnitOfIssue()).append("^FS").append(lineFeed);
					}
					else {
						receiptKitLabel.append("^").append(labelFieldId).append("^FD").append(BeanHandler.getField(bean, labelFieldContent)).append("^FS").append(lineFeed);
					}
				}
				catch (SecurityException ex) {
				}
				catch (BaseException ex) {
					ex.printStackTrace();
				}
			}
		}

		if (pqvalues) {
			receiptKitLabel.append("^PQ").append(labelQuantity).append("").append(lineFeed);
		}
		else {
			receiptKitLabel.append("^PQ1").append(lineFeed);
		}
		receiptKitLabel.append("^XZ").append(lineFeed);

		return receiptKitLabel;
	}
}