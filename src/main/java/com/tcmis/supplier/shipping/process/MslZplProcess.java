package com.tcmis.supplier.shipping.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
import com.tcmis.supplier.shipping.beans.MslBoxViewBean;
import com.tcmis.supplier.shipping.beans.MslPalletViewBean;

public class MslZplProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	
	//value should be changed to 10 later
	private static final int TOTAL_LABELS_IN_A_BATCH = 1;

	public MslZplProcess(String client) {
		super(client);
	}

	public String buildCaseMslZpl(Collection labelDataCollection, String labelType, String sourcePage, String printerPath, Collection locationLabelPrinterCollection, boolean printKitLabels, String filePath) throws Exception {
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
			MslBoxViewBean mslBoxViewBean = (MslBoxViewBean) mainIterator.next();

			String currentInventoryGroup = mslBoxViewBean.getInventoryGroup();

			//log.info("currentInventoryGroup   "+currentInventoryGroup);
			if (!distinctInventoryGroupVector.contains(currentInventoryGroup)) {
				distinctInventoryGroupVector.add(currentInventoryGroup);
			}
		}

		Collection labelFormatCollection = new Vector();
		Collection labelFormatLocaleCollection = new Vector();
		labelFormatCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "usgovcasemsl");
		labelFormatLocaleCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "usgovcasemslLocale");

		String printerResolution = "";
		Iterator printerIterator = locationLabelPrinterCollection.iterator();
		while (printerIterator.hasNext()) {
			LocationLabelPrinterBean locationLabelPrinterBean = (LocationLabelPrinterBean) printerIterator.next();

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

		StringBuilder zpl = new StringBuilder();
		if (labelType.equalsIgnoreCase("PROJECTCODE")) {
			zpl.append(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "projectcode", printerResolution)));
		}
		else {
			zpl.append(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "usgovcasemsl", printerResolution)));
		}

		/*Do not want to send a file path back if no project code labels are printed*/
		int quantityProjectCode = 0;
		try {
			mainIterator = labelDataCollection.iterator();
			while (mainIterator.hasNext()) {
				MslBoxViewBean mslBoxViewBean = (MslBoxViewBean) mainIterator.next();

				int quantity = mslBoxViewBean.getQuantity().intValue();
				StringBuilder boxLabel = new StringBuilder();
				//StringBuilder unitLabel = new StringBuilder();
				if (labelType.equalsIgnoreCase("PROJECTCODE")) {
					if (sourcePage != null && sourcePage.equalsIgnoreCase("POLCHEM") && !StringHandler.isBlankString(mslBoxViewBean.getProjectCode())) {
						quantityProjectCode++;
						boxLabel.append(projectCodeLabel(mslBoxViewBean.getProjectCode(), "1"));
					}
				}
				else {
					boxLabel.append(caseMsl(mslBoxViewBean, "" + (quantity) + "", labelFormatCollection, labelFieldDefinitionCollection, labelFormatLocaleCollection, labelFieldDefinitionLocaleCollection));

					if (sourcePage != null && sourcePage.equalsIgnoreCase("POLCHEM") && !StringHandler.isBlankString(mslBoxViewBean.getFlashpoint())) {
						boxLabel.append(flashPointLabel(mslBoxViewBean.getFlashpoint(), "1"));
					}
				}

				zpl.append(boxLabel);
			}
			pw.print(zpl.toString());
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}
		finally {
			pw.close();
		}

		if (labelType.equalsIgnoreCase("PROJECTCODE") && quantityProjectCode == 0) {
			return "";
		}
		else {
			com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(filePath, file.getName(), printerPath, zpl.toString());
			return fileAbsolutePath;
		}
	}

	public StringBuilder flashPointLabel(String flashPoint, String labelQuantity) {
		StringBuilder projectLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

		projectLabel.append("^XA").append(lineFeed);
		projectLabel.append("^XFflashpointlabel^FS").append(lineFeed);
		projectLabel.append("^FXflash Point^FS").append(lineFeed);
		projectLabel.append("^FN1^FD").append(flashPoint).append("^FS").append(lineFeed);
		projectLabel.append("^PQ").append(labelQuantity).append("").append(lineFeed);
		projectLabel.append("^XZ").append(lineFeed);

		return projectLabel;
	}

	public StringBuilder projectCodeLabel(String projectCode, String labelQuantity) {
		StringBuilder projectLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

		projectLabel.append("^XA").append(lineFeed);
		projectLabel.append("^XFprojectcodelabel^FS").append(lineFeed);
		projectLabel.append("^FXProject code^FS").append(lineFeed);
		projectLabel.append("^FN1^FD").append(projectCode).append("^FS").append(lineFeed);
		projectLabel.append("^PQ").append(labelQuantity).append("").append(lineFeed);
		projectLabel.append("^XZ").append(lineFeed);

		return projectLabel;
	}

	public StringBuilder caseMsl(MslBoxViewBean bean, String labelQuantity, Collection labelFormatCollection, Collection labelFieldDefinitionCollection, Collection labelFormatLocaleCollection, Collection labelFieldDefinitionLocaleCollection) {
		StringBuilder receiptLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

		String inventoryGroup = bean.getInventoryGroup();
		String labelFormat = "";
		Iterator mainIterator = labelFormatCollection.iterator();
		while (mainIterator.hasNext()) {
			InventoryGroupLabelFormatBean inventoryGroupLabelFormatBean = (InventoryGroupLabelFormatBean) mainIterator.next();

			if (inventoryGroupLabelFormatBean.getInventoryGroup().equalsIgnoreCase(inventoryGroup)) {
				labelFormat = inventoryGroupLabelFormatBean.getLabelFormat();
			}
		}

		receiptLabel.append("^XA").append(lineFeed);
		receiptLabel.append("^XF").append(labelFormat).append("^FS").append(lineFeed);

		Iterator labelFieldIterator = labelFieldDefinitionCollection.iterator();
		while (labelFieldIterator.hasNext()) {
			LabelFormatFieldDefinitionBean labelFormatFieldDefinitionBean = (LabelFormatFieldDefinitionBean) labelFieldIterator.next();

			//log.info("Field Label Format  "+labelFormatFieldDefinitionBean.getLabelFormat()+"  Compare To "+labelFormat+"Strurts"+"");
			if (labelFormatFieldDefinitionBean.getLabelFormat().equalsIgnoreCase(labelFormat + "Struts")) {

				String labelFieldId = labelFormatFieldDefinitionBean.getLabelFieldId();
				String labelFieldContent = labelFormatFieldDefinitionBean.getLabelFieldContent();

				try {
					if ("FN3".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getShipFromLine1()).append("\\&").append(bean.getShipFromLine2()).append("\\&").append(
								StringHandler.emptyIfNull(bean.getShipFromLine3())).append("^FS").append(lineFeed);
					}
					else if ("FN13".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getShipViaLine1()).append("\\&").append(bean.getShipViaLine2()).append("\\&").append(StringHandler.emptyIfNull(bean.getShipViaLine3()))
						.append("\\&").append(StringHandler.emptyIfNull(bean.getShipViaLine4())).append("^FS").append(lineFeed);
					}
					else if ("FN18".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getShipToLine1()).append("\\&").append(bean.getShipToLine2()).append("\\&").append(StringHandler.emptyIfNull(bean.getShipToLine3()))
						.append("\\&").append(StringHandler.emptyIfNull(bean.getShipToLine4())).append("^FS").append(lineFeed);
					}
					else if ("FN19".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^").append(labelFieldId).append("^FH^FD").append(caseMsl2dBarcode(bean)).append("^FS").append(lineFeed);
					}
					else if ("FN20".equalsIgnoreCase(labelFieldId)) {
						if (bean.getBoxRfid().length() > 0) {
							receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getBoxId()).append("-").append(bean.getBoxRfid()).append("^FS").append(lineFeed);
						}
						else {
							receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getBoxId()).append("^FS").append(lineFeed);
						}
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
		 receiptLabel.append("^PQ" + labelQuantity + "" + lineFeed);
		}
		else*/{
			receiptLabel.append("^PQ1").append(lineFeed);
		}
		receiptLabel.append("^XZ").append(lineFeed);

		return receiptLabel;
	}

	public StringBuilder caseMsl2dBarcode(MslBoxViewBean caseMslBean) {
		StringBuilder zpl2d = new StringBuilder();
		zpl2d.append("[)>_1E06");
		if (caseMslBean.getTcn() != null && caseMslBean.getTcn().length() > 0) {
			zpl2d.append("_1D").append("JKUSM").append(caseMslBean.getTcn());
		}
		zpl2d.append("_1D").append("3D").append(caseMslBean.getShipDate());
		if (caseMslBean.getTac() != null && caseMslBean.getTac().length() > 0) {
			zpl2d.append("_1D").append("9K").append(caseMslBean.getTac());
		}
		//**TODO** remove null and don't put ).append( if nothing beyond it.
		zpl2d.append("_1D").append("2L").append(caseMslBean.getShipToLine1()).append(").append(").append(caseMslBean.getShipToLine2()).append(").append(").append(StringHandler.emptyIfNull(caseMslBean.getShipToLine3())).append(").append(").append(
				StringHandler.emptyIfNull(caseMslBean.getShipToLine4()));
		zpl2d.append("_1D").append("3L").append(caseMslBean.getShipFromLine1()).append(").append(").append(caseMslBean.getShipFromLine2()).append(").append(").append(StringHandler.emptyIfNull(caseMslBean.getShipFromLine3()));
		if (caseMslBean.getShipFromLine4() != null && caseMslBean.getShipFromLine4().length() > 0) {
			zpl2d.append(").append(").append(caseMslBean.getShipFromLine4());
		}
		zpl2d.append("_1D").append("5L").append(caseMslBean.getShipViaLine1()).append(").append(").append(caseMslBean.getShipViaLine2()).append(").append(").append(StringHandler.emptyIfNull(caseMslBean.getShipViaLine3())).append(").append(")
		.append(StringHandler.emptyIfNull(caseMslBean.getShipViaLine4()));
		if (caseMslBean.getGrossWeight() != null && caseMslBean.getGrossWeight().intValue() > 0) {
			zpl2d.append("_1D").append("2Q").append(caseMslBean.getGrossWeight());
		}
		zpl2d.append("_1D").append("13Q").append(caseMslBean.getBoxNumber()).append("/").append(caseMslBean.getNumberOfBoxes());
		//**TODO** Ocean Carrier Code
		//zpl2d.append("_1D").append("4V").append(caseMslBean.);
		zpl2d.append("_1E").append("07");
		if (caseMslBean.getProjectCode() != null && caseMslBean.getProjectCode().length() > 0) {
			zpl2d.append("_1D").append("03").append(caseMslBean.getProjectCode());
		}
		zpl2d.append("_1D").append("12").append(caseMslBean.getCubicFeet());
		if (caseMslBean.getPortOfEmbarkation() != null && caseMslBean.getPortOfEmbarkation().length() > 0) {
			zpl2d.append("_1D").append("25").append(caseMslBean.getPortOfEmbarkation());
		}
		if (caseMslBean.getPortOfDebarkation() != null && caseMslBean.getPortOfDebarkation().length() > 0) {
			zpl2d.append("_1D").append("26").append(caseMslBean.getPortOfDebarkation());
		}
		zpl2d.append("_1D").append("27").append(caseMslBean.getShipViaDodaac());
		if (caseMslBean.getTransportationPriority() != null && caseMslBean.getTransportationPriority().length() > 0) {
			zpl2d.append("_1D").append("28").append(caseMslBean.getTransportationPriority());
		}
		zpl2d.append("_1D").append("29").append(caseMslBean.getShipFromDodaac());
		//**TODO** Type Service
		//zpl2d.append("_1D").append("48").append(caseMslBean.get);
		if (caseMslBean.getRdd() != null && caseMslBean.getRdd().length() > 0) {
			zpl2d.append("_1D").append("32").append(caseMslBean.getRdd());
		}
		if (caseMslBean.getFmsCaseNum() != null && caseMslBean.getFmsCaseNum().length() > 0) {
			zpl2d.append("_1D").append("67").append(caseMslBean.getFmsCaseNum());
		}
		zpl2d.append("_1E").append("06");
		zpl2d.append("_1D").append("12S").append(caseMslBean.getMilstripCode());
		zpl2d.append("_1D").append("N").append(caseMslBean.getCatPartNo());
		zpl2d.append("_1D").append("7Q").append(caseMslBean.getQuantity()).append(caseMslBean.getUnitOfSale());
		if (caseMslBean.getRic() != null && caseMslBean.getRic().length() > 0) {
			zpl2d.append("_1D").append("V").append(caseMslBean.getRic());
		}
		if (caseMslBean.getUnitPrice() != null && caseMslBean.getUnitPrice().intValue() > 0) {
			zpl2d.append("_1D").append("12Q").append(caseMslBean.getUnitPrice());
		}
		zpl2d.append("_1E");
		zpl2d.append("_04");
		return zpl2d;
	}

	public StringBuilder palletMsl2dBarcode(MslPalletViewBean palletMslBean) {
		StringBuilder zpl2d = new StringBuilder();
		zpl2d.append("[)>_1E06");
		if (palletMslBean.getTcn() != null && palletMslBean.getTcn().length() > 0) {
			zpl2d.append("_1D").append("JKUSM").append(palletMslBean.getTcn());
		}
		zpl2d.append("_1D").append("3D").append(palletMslBean.getShipDate());
		if (palletMslBean.getTac() != null && palletMslBean.getTac().length() > 0) {
			zpl2d.append("_1D").append("9K").append(palletMslBean.getTac());
		}
		/*zpl2d.append("_1D").append("2L").append(palletMslBean.getShipToLine1()).append(palletMslBean.getShipToLine2()).append(palletMslBean.getShipToLine3()).append(palletMslBean.getShipToLine4());
		zpl2d.append("_1D").append("3L").append(palletMslBean.getShipFromLine1()).append(palletMslBean.getShipFromLine2()).append(palletMslBean.getShipFromLine3()).append(palletMslBean.getShipFromLine4());
		zpl2d.append("_1D").append("5L").append(palletMslBean.getShipViaLine1()).append(palletMslBean.getShipViaLine2()).append(palletMslBean.getShipViaLine3()).append(palletMslBean.getShipViaLine4());*/
		if (palletMslBean.getGrossWeight() != null && palletMslBean.getGrossWeight().intValue() > 0) {
			zpl2d.append("_1D").append("2Q").append(palletMslBean.getGrossWeight());
		}
		zpl2d.append("_1D").append("13Q").append(palletMslBean.getPalletNumber()).append("/").append(palletMslBean.getNumberOfPallets());
		//**TODO** Ocean Carrier Code
		//zpl2d.append("_1D").append("4V").append(palletMslBean.);
		zpl2d.append("_1E").append("07");
		if (palletMslBean.getProjectCode() != null && palletMslBean.getProjectCode().length() > 0) {
			zpl2d.append("_1D").append("03").append(palletMslBean.getProjectCode());
		}
		zpl2d.append("_1D").append("12").append(palletMslBean.getCubicFeet());
		if (palletMslBean.getPortOfEmbarkation() != null && palletMslBean.getPortOfEmbarkation().length() > 0) {
			zpl2d.append("_1D").append("25").append(palletMslBean.getPortOfEmbarkation());
		}
		if (palletMslBean.getPortOfDebarkation() != null && palletMslBean.getPortOfDebarkation().length() > 0) {
			zpl2d.append("_1D").append("26").append(palletMslBean.getPortOfDebarkation());
		}
		zpl2d.append("_1D").append("27").append(palletMslBean.getShipViaDodaac());
		if (palletMslBean.getTransportationPriority() != null && palletMslBean.getTransportationPriority().length() > 0) {
			zpl2d.append("_1D").append("28").append(palletMslBean.getTransportationPriority());
		}
		zpl2d.append("_1D").append("29").append(palletMslBean.getShipFromDodaac());
		//**TODO** Type Service
		//zpl2d.append("_1D").append("48").append(palletMslBean.get);
		if (palletMslBean.getRdd() != null && palletMslBean.getRdd().length() > 0) {
			zpl2d.append("_1D").append("32").append(palletMslBean.getRdd());
		}
		if (palletMslBean.getFmsCaseNum() != null && palletMslBean.getFmsCaseNum().length() > 0) {
			zpl2d.append("_1D").append("67").append(palletMslBean.getFmsCaseNum());
		}
		/*zpl2d.append("_1E").append("06");
		//zpl2d.append("_1D").append("12S").append(palletMslBean.);
		zpl2d.append("_1D").append("N").append(palletMslBean.getCatPartNo());
		zpl2d.append("_1D").append("7Q").append(palletMslBean.getQuantity()).append(palletMslBean.getUnitOfSale());
		if (palletMslBean.getRic() != null && palletMslBean.getRic().length() >0)
		{
		zpl2d.append("_1D").append("V").append(palletMslBean.getRic());
		}
		zpl2d.append("_1D").append("12Q").append(palletMslBean.getUnitPrice());*/
		zpl2d.append("_1E");
		zpl2d.append("_04");
		return zpl2d;
	}

	public String buildPalletMslZpl(Collection labelDataCollection, String labelType, String labelFormatType, String sourcePage, String printerPath, Collection locationLabelPrinterCollection, String filePath) throws Exception {

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
			MslPalletViewBean mslPalletViewBean = (MslPalletViewBean) mainIterator.next();

			String currentInventoryGroup = mslPalletViewBean.getInventoryGroup();

			//log.info("currentInventoryGroup   "+currentInventoryGroup);
			if (!distinctInventoryGroupVector.contains(currentInventoryGroup)) {
				distinctInventoryGroupVector.add(currentInventoryGroup);
			}
		}

		Collection labelFormatCollection = new Vector();
		Collection labelFormatLocaleCollection = new Vector();
		labelFormatCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, labelFormatType);
		labelFormatLocaleCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "" + labelFormatType + "Locale");

		String printerResolution = "";
		Iterator printerIterator = locationLabelPrinterCollection.iterator();
		while (printerIterator.hasNext()) {
			LocationLabelPrinterBean locationLabelPrinterBean = (LocationLabelPrinterBean) printerIterator.next();

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

		StringBuilder zpl = new StringBuilder();
		if (labelType.equalsIgnoreCase("PROJECTCODE")) {
			zpl.append(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "projectcode", printerResolution)));
		}
		else {
			zpl.append(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, labelFormatType, printerResolution)));
		}
		/*Do not want to send a file path back if no project code labels are printed*/
		int quantityProjectCode = 0;
		
		try {
			mainIterator = labelDataCollection.iterator();
			while (mainIterator.hasNext()) {
				MslPalletViewBean mslPalletViewBean = (MslPalletViewBean) mainIterator.next();
				StringBuilder boxLabel = new StringBuilder();
				//StringBuilder unitLabel = new StringBuilder();
				String labelQuantity = "1";
				if (sourcePage != null && sourcePage.equalsIgnoreCase("POLCHEM")) {
					labelQuantity = "2";
				}

				if (labelType.equalsIgnoreCase("PROJECTCODE")) {
					if (sourcePage != null && sourcePage.equalsIgnoreCase("POLCHEM") && !StringHandler.isBlankString(mslPalletViewBean.getPalletProjectCode())) {
						boxLabel.append(projectCodeLabel(mslPalletViewBean.getPalletProjectCode(), labelQuantity));
						quantityProjectCode++;
					}
				}
				else {
					boxLabel.append(palletMsl(mslPalletViewBean, labelQuantity, labelFormatCollection, labelFieldDefinitionCollection, labelFormatLocaleCollection, labelFieldDefinitionLocaleCollection));
					if (sourcePage != null && sourcePage.equalsIgnoreCase("POLCHEM") && !StringHandler.isBlankString(mslPalletViewBean.getFlashpointInfo())) {
						boxLabel.append(flashPointLabel(mslPalletViewBean.getFlashpointInfo(), labelQuantity));
					}
				}

				zpl.append(boxLabel);
			}
			pw.print(zpl.toString());
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}
		finally {
			pw.close();
		}
		if (labelType.equalsIgnoreCase("PROJECTCODE") && quantityProjectCode == 0) {
			return "";
		}
		else {
			com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(filePath, file.getName(), printerPath, zpl.toString());
			return fileAbsolutePath;
		}
	}
	
	//split up labels and JNLP files based on TOTAL_LABELS_IN_A_BATCH. 
	public List<String> buildPalletMslZpl(Collection labelDataCollection, String labelType, String labelFormatType, String sourcePage, String printerPath, Collection locationLabelPrinterCollection) throws Exception {
		
		ResourceLibrary resource = new ResourceLibrary("label");
		File dir = new File(resource.getString("label.serverpath"));

		DbManager dbManager = new DbManager(getClient());
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());

		Vector distinctInventoryGroupVector = new Vector();
		Iterator mainIterator = labelDataCollection.iterator();
		while (mainIterator.hasNext()) {
			MslPalletViewBean mslPalletViewBean = (MslPalletViewBean) mainIterator.next();

			String currentInventoryGroup = mslPalletViewBean.getInventoryGroup();

			if (!distinctInventoryGroupVector.contains(currentInventoryGroup)) {
				distinctInventoryGroupVector.add(currentInventoryGroup);
			}
		}

		Collection labelFormatCollection = new Vector();
		Collection labelFormatLocaleCollection = new Vector();
		labelFormatCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, labelFormatType);
		labelFormatLocaleCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "" + labelFormatType + "Locale");

		String printerResolution = "";
		Iterator printerIterator = locationLabelPrinterCollection.iterator();
		while (printerIterator.hasNext()) {
			LocationLabelPrinterBean locationLabelPrinterBean = (LocationLabelPrinterBean) printerIterator.next();

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

//		StringBuilder zpl = new StringBuilder();
		final String zpl;
		StringBuilder jnplZpl = new StringBuilder();
		
		if (labelType.equalsIgnoreCase("PROJECTCODE")) {
			zpl = com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "projectcode", printerResolution));
		}
		else {
			zpl = com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, labelFormatType, printerResolution));
		}
		
		/*Do not want to send a file path back if no project code labels are printed*/
		int quantityProjectCode = 0;
		
		File dirLocation = new File(resource.getString("label.serverpath"));
		//jnlp file Creation
		File jnlpFile = null;
		PrintWriter jnlpPrintWriter = null;
		//label file creation
		File labelFile = null;
		PrintWriter labelPrintWriter = null;
		int labelCount = 0;
		List<String> jnlpFileNames = new ArrayList<String>();
		try {
			mainIterator = labelDataCollection.iterator();
			while (mainIterator.hasNext()) {
				MslPalletViewBean mslPalletViewBean = (MslPalletViewBean) mainIterator.next();
				//create new text file for label and JNLP file
				if(labelCount == 0) {
					jnlpFile = File.createTempFile("labeljnlp", ".jnlp", dir);
					jnplZpl = new StringBuilder();
					jnplZpl.append(zpl);
					labelFile = File.createTempFile("labeltxt", ".txt", dirLocation);
					labelPrintWriter = new PrintWriter(new FileOutputStream(labelFile.getAbsolutePath()));
					
				}
				StringBuilder boxLabel = new StringBuilder();
				//StringBuilder unitLabel = new StringBuilder();
				String labelQuantity = "1";
				if (sourcePage != null && sourcePage.equalsIgnoreCase("POLCHEM")) {
					labelQuantity = "2";
				}

				if (labelType.equalsIgnoreCase("PROJECTCODE")) {
					if (sourcePage != null && sourcePage.equalsIgnoreCase("POLCHEM") && !StringHandler.isBlankString(mslPalletViewBean.getPalletProjectCode())) {
						boxLabel.append(projectCodeLabel(mslPalletViewBean.getPalletProjectCode(), labelQuantity));
						quantityProjectCode++;
						++labelCount;
					}
				}
				else {
					boxLabel.append(palletMsl(mslPalletViewBean, labelQuantity, labelFormatCollection, labelFieldDefinitionCollection, labelFormatLocaleCollection, labelFieldDefinitionLocaleCollection));
					if (sourcePage != null && sourcePage.equalsIgnoreCase("POLCHEM") && !StringHandler.isBlankString(mslPalletViewBean.getFlashpointInfo())) {
						boxLabel.append(flashPointLabel(mslPalletViewBean.getFlashpointInfo(), labelQuantity));
					}
					++labelCount;
				}

				jnplZpl.append(boxLabel);
				labelPrintWriter.print(jnplZpl.toString());
				//save label and JNLP file
				if(labelCount == TOTAL_LABELS_IN_A_BATCH ) {
					com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(jnlpFile.getAbsolutePath(), labelFile.getName(), printerPath, jnplZpl.toString());
					labelPrintWriter.close();
					labelCount = 0;
					jnlpFileNames.add(jnlpFile.getAbsolutePath());
				}
			}
			if(labelCount > 0) {
				com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(jnlpFile.getAbsolutePath(), labelFile.getName(), printerPath, jnplZpl.toString());
				labelPrintWriter.close();
				labelCount = 0;
				jnlpFileNames.add(jnlpFile.getAbsolutePath());
			}
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}
		finally {
			labelPrintWriter.close();
		}
		if (labelType.equalsIgnoreCase("PROJECTCODE") && quantityProjectCode == 0) {
			return null;
		}
		else {
			return jnlpFileNames.size() > 0 ? jnlpFileNames : Collections.emptyList();
		}
	}

	public StringBuilder palletMsl(MslPalletViewBean bean, String labelQuantity, Collection labelFormatCollection, Collection labelFieldDefinitionCollection, Collection labelFormatLocaleCollection, Collection labelFieldDefinitionLocaleCollection) {
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
					if ("FN3".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getShipFromLine1()).append("\\&").append(bean.getShipFromLine2()).append("\\&").append(
								StringHandler.emptyIfNull(bean.getShipFromLine3())).append("^FS").append(lineFeed);
					}
					else if ("FN13".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getShipViaLine1()).append("\\&").append(bean.getShipViaLine2()).append("\\&").append(StringHandler.emptyIfNull(bean.getShipViaLine3()))
						.append("\\&").append(StringHandler.emptyIfNull(bean.getShipViaLine4())).append("^FS").append(lineFeed);
					}
					else if ("FN18".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getShipToLine1()).append("\\&").append(bean.getShipToLine2()).append("\\&").append(StringHandler.emptyIfNull(bean.getShipToLine3()))
						.append("\\&").append(StringHandler.emptyIfNull(bean.getShipToLine4())).append("^FS").append(lineFeed);
					}
					else if ("FN19".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^").append(labelFieldId).append("^FH^FD").append(palletMsl2dBarcode(bean)).append("^FS").append(lineFeed);
					}
					else if ("FN20".equalsIgnoreCase(labelFieldId)) {
						if (bean.getPalletRfid().length() > 0) {
							receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getPalletRfid()).append("^FS").append(lineFeed);
						}
						else {
							receiptLabel.append("^").append(labelFieldId).append("^FD").append(bean.getPalletId()).append("^FS").append(lineFeed);
						}
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

		/*Nawaz 02-27-08 - Printing 2 pallet msl because they want to label on both sides of the pallet.*/
		receiptLabel.append("^PQ").append(labelQuantity).append("").append(lineFeed);
		receiptLabel.append("^XZ").append(lineFeed);

		return receiptLabel;
	}
}