package com.tcmis.supplier.shipping.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.ZplHandler;
import com.tcmis.internal.hub.beans.InventoryGroupLabelFormatBean;
import com.tcmis.internal.hub.beans.LabelFormatFieldDefinitionBean;
import com.tcmis.internal.hub.beans.LabelFormatTemplateFileBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.beans.PickingStatusInputBean;
import com.tcmis.internal.hub.factory.LabelFormatFieldDefinitionBeanFactory;
import com.tcmis.supplier.shipping.beans.SupplierUsgovLabelViewBean;

import radian.tcmis.common.util.SqlHandler;

public class ZplProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static String LINE_FEED = "";
	static {
		LINE_FEED += (char) (13);
		LINE_FEED += (char) (10);
	}

	public ZplProcess(String client) {
		super(client);
	}
	
	/**
	 * TCMISDEV-3646
	 * Changes due to Serial Number requirements for External and Unit Labels can't be implemented in one view or procedure 
	 * If the part does not require Serial Number Tracking: 
	 * 		- Write ZPL from unitData map from SUPPLIER_USGOV_LABEL_VIEW that will print just like it did before these changes
	 * 		- Print is External Label then associated Unit Labels per user selection
	 * Else for Serial Number Tracking:
	 * 		- Write ZPL from boxData map from supplier_usgov_extlabel_view plus Serial Numbers from fx_part_serial_number_list per pallet ID or case ID
	 * 		- Print is External Label then associated Unit Labels per pallet ID or case ID for each user selection
	 * @param labelDataCollection collection of maps per user selection
	 **/

	public void buildLabelZpl(Collection labelDataCollection, String printerPath, Collection locationLabelPrinterCollection, String filePath) throws Exception {

		ResourceLibrary resource = new ResourceLibrary("label");
		File dir = new File(resource.getString("label.serverpath"));
		File file = File.createTempFile("labeltxt", ".txt", dir);
		PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));

		//DbManager dbManager = new DbManager(getClient());
		//LabelProcess labelProcess = new LabelProcess(getClient());
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());

		Vector distinctInventoryGroupVector = new Vector();
		
		Iterator mainIterator = labelDataCollection.iterator();		

		while (mainIterator.hasNext()) {
			Map<String, Vector> rowMap = (Map<String, Vector>)mainIterator.next();
			Vector<SupplierUsgovLabelViewBean> rowUnitDataColl = rowMap.get("unitData");
			for(SupplierUsgovLabelViewBean suppUsgovLabelViewBean : rowUnitDataColl)
			{
				String currentInventoryGroup = suppUsgovLabelViewBean.getInventoryGroup();
	
				//log.info("currentInventoryGroup   "+currentInventoryGroup);
				if (!distinctInventoryGroupVector.contains(currentInventoryGroup)) {
					distinctInventoryGroupVector.add(currentInventoryGroup);
				}
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

		StringBuilder zpl = new StringBuilder(
				com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "usgovbox", printerResolution)));

		try {
			mainIterator = labelDataCollection.iterator();
			
						
			while (mainIterator.hasNext()) {
				Map<String, Vector<SupplierUsgovLabelViewBean>> rowMap = (Map<String, Vector<SupplierUsgovLabelViewBean>>)mainIterator.next();
				Vector<SupplierUsgovLabelViewBean> rowUnitDataColl = rowMap.get("unitData");				
				Vector<SupplierUsgovLabelViewBean> rowBoxDataCollection = rowMap.get("boxData");
				if(rowBoxDataCollection != null)
				{
					int snUnitLabelCount = 0; 
					for(int i = 0; i < rowBoxDataCollection.size(); ++i)
					{
						StringBuilder boxLabel = new StringBuilder();
						StringBuilder unitLabel = null;	
	
						SupplierUsgovLabelViewBean boxUsgovLabelViewBean = rowBoxDataCollection.get(i);
						boxLabel.append(boxLabel(boxUsgovLabelViewBean, labelFormatCollection, labelFieldDefinitionCollection));
						if (boxUsgovLabelViewBean.getSkipUnitLabels() == null)
						{
							unitLabel= new StringBuilder();
							int nextSnCount = boxUsgovLabelViewBean.getNumOfSerialNumber().intValue() + snUnitLabelCount; 
							for(;snUnitLabelCount < nextSnCount;++snUnitLabelCount)
								unitLabel.append(unitLabel(rowUnitDataColl.get(snUnitLabelCount), "1", kitlabelFormatCollection, kitLabelFieldDefinitionCollection, false));
						}
		
						zpl.append(boxLabel);
						if(unitLabel != null)
							zpl.append(unitLabel);
					}
				}
				else
					for(int i = 0; i < rowUnitDataColl.size(); ++i)
					{
						SupplierUsgovLabelViewBean suppUsgovLabelViewBean = rowUnitDataColl.get(i);
						int quantity = suppUsgovLabelViewBean.getQuantity().intValue();	
						StringBuilder boxLabel = new StringBuilder();
						StringBuilder unitLabel = new StringBuilder();						
					
						boxLabel.append(boxLabel(suppUsgovLabelViewBean, labelFormatCollection, labelFieldDefinitionCollection));
		
						/*
						if (shipped as single = 'Y' then part per box =1 and dont SkipUnitLabels then print unit labels
		
						if shipped as single = 'N' and parts per box = 1 and SkipUnitLabels then dont print unit labels
		
						quantity can not be > 1 for shipped as single = 'Y'
		
						if qunaity > 1 and shipped as single = 'N' and SkipUnitLabels then dont print unit labels
						 */
		
						//log.info("getSkipUnitLabels  "+suppUsgovLabelViewBean.getSkipUnitLabels());
						/*if (quantity > 1 || (suppUsgovLabelViewBean.getShippedAsSingle() !=null
						&& !suppUsgovLabelViewBean.getShippedAsSingle().equalsIgnoreCase("Y"))) {
						 */
						if (suppUsgovLabelViewBean.getSkipUnitLabels() == null) {
							unitLabel.append(unitLabel(suppUsgovLabelViewBean, "" + quantity + "", kitlabelFormatCollection, kitLabelFieldDefinitionCollection, true));
						}
		
						zpl.append(boxLabel);
						zpl.append(unitLabel);
					}
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

	public String buildPlacardLabelZpl(Collection labelDataCollection, String printerPath, Collection locationLabelPrinterCollection, String filePath) throws Exception {

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
			SupplierUsgovLabelViewBean suppUsgovLabelViewBean = (SupplierUsgovLabelViewBean) mainIterator.next();
			;

			String currentInventoryGroup = suppUsgovLabelViewBean.getInventoryGroup();

			//log.info("currentInventoryGroup   "+currentInventoryGroup);
			if (!distinctInventoryGroupVector.contains(currentInventoryGroup)) {
				distinctInventoryGroupVector.add(currentInventoryGroup);
			}
		}

		Collection labelFormatCollection = new Vector();
		labelFormatCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, "usgovplacard");

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

		if (labelFieldDefinitionCollection == null || labelFieldDefinitionCollection.size() == 0) {
			BaseException ex = new BaseException();
			ex.setMessageKey("error.nolabeltemplate.defined");
			throw ex;
		}

		StringBuilder zpl = new StringBuilder(
				com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(zplDataProcess.getTemplates(distinctInventoryGroupVector, "usgovplacard", printerResolution)));

		try {
			mainIterator = labelDataCollection.iterator();
			while (mainIterator.hasNext()) {
				SupplierUsgovLabelViewBean suppUsgovLabelViewBean = (SupplierUsgovLabelViewBean) mainIterator.next();
				;

				int quantity = suppUsgovLabelViewBean.getQuantity().intValue();

				StringBuilder placardLabel = new StringBuilder();
				StringBuilder placardBarcodeLabel = new StringBuilder();

				placardLabel.append(placardLabel(suppUsgovLabelViewBean, labelFormatCollection, labelFieldDefinitionCollection));

				placardBarcodeLabel.append(placardBarcodeLabel(suppUsgovLabelViewBean, labelFormatCollection, labelFieldDefinitionCollection));

				zpl.append(placardLabel);
				zpl.append(placardBarcodeLabel);
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

	public StringBuilder placardLabel(SupplierUsgovLabelViewBean bean, Collection labelFormatCollection, Collection labelFieldDefinitionCollection) {
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

		receiptLabel.append("^XA" ).append( lineFeed);
		receiptLabel.append("^XF" ).append( labelFormat ).append( "^FS" ).append( lineFeed);

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
						if (bean.getMfgLot() != null && bean.getMfgLot().length() > 0) {
							receiptLabel.append("^" ).append( labelFieldId ).append( "^FDLOT " ).append( bean.getMfgLot() ).append( "^FS" ).append( lineFeed);
						}
					}
					else if ("FN5".equalsIgnoreCase(labelFieldId)) {
						if (bean.getNsn() != null && bean.getNsn().indexOf("6830") != -1) {
							receiptLabel.append("^" ).append( labelFieldId ).append( "^FDMHM " ).append( bean.getMhmDate() ).append( "^FS" ).append( lineFeed);
						}
					}
					else if ("FN6".equalsIgnoreCase(labelFieldId)) {
						receiptLabel.append("^" ).append( labelFieldId ).append( "^FDCAGE " ).append( bean.getCageCode() ).append( "^FS" ).append( lineFeed);
					}
					else if ("FN8".equalsIgnoreCase(labelFieldId)) {
						if (bean.getGrossWeight() != null) {
							receiptLabel.append("^" ).append( labelFieldId ).append( "^FDWT " ).append( bean.getGrossWeight() ).append( " LB^FS" ).append( lineFeed);
						}
					}
					else if ("FN4".equalsIgnoreCase(labelFieldId)) {
						if (bean.getQuantity() != null) {
							receiptLabel.append("^" ).append( labelFieldId ).append( "^FD" ).append( bean.getQuantity() ).append( " " ).append( bean.getUnitOfSale() ).append( "^FS" ).append( lineFeed);
						}
					}
					else {
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

		/*if (pqvalues) {
		receiptLabel.append("^PQ" + labelQuantity + "" + lineFeed);
		}
		else*/{
			receiptLabel.append("^PQ1" ).append( lineFeed);
		}
		receiptLabel.append("^XZ" ).append( lineFeed);

		return receiptLabel;
	}

	public StringBuilder placardBarcodeLabel(SupplierUsgovLabelViewBean bean, Collection labelFormatCollection, Collection labelFieldDefinitionCollection) {
		StringBuilder receiptLabel = new StringBuilder();
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);

		String inventoryGroup = bean.getInventoryGroup();
		//		String labelFormat = "";
		//		Iterator mainIterator = labelFormatCollection.iterator();
		//		while (mainIterator.hasNext()) {
		//			InventoryGroupLabelFormatBean inventoryGroupLabelFormatBean = (InventoryGroupLabelFormatBean) mainIterator.next();
		//			;
		//
		//			if (inventoryGroupLabelFormatBean.getInventoryGroup().equalsIgnoreCase(inventoryGroup)) {
		//				labelFormat = inventoryGroupLabelFormatBean.getLabelFormat();
		//			}
		//		}

		receiptLabel.append("^XA").append(lineFeed);
		receiptLabel.append("^XFplacardbarcodelabel^FS").append(lineFeed);
		try {
			receiptLabel.append("^FN1^FD").append(bean.getNsn()).append("^FS").append(lineFeed);
			receiptLabel.append("^FN2^FD").append(bean.getCustomerPo()).append("^FS").append(lineFeed);
			receiptLabel.append("^FN3^FD").append(bean.getCageCode()).append("^FS").append(lineFeed);
		}
		catch (SecurityException ex) {
			ex.printStackTrace();
		}
		catch (IllegalArgumentException ex) {
			ex.printStackTrace();
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

	public StringBuilder boxLabel(SupplierUsgovLabelViewBean bean, Collection labelFormatCollection, Collection labelFieldDefinitionCollection) {
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
						receiptLabel.append("^").append(labelFieldId).append("^FD").append(BeanHandler.getField(bean, labelFieldContent)).append("^FS").append(lineFeed);
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
			receiptLabel.append("^PQ1" + lineFeed);
		}
		receiptLabel.append("^XZ" + lineFeed);

		return receiptLabel;
	}

	public StringBuilder unitLabel(SupplierUsgovLabelViewBean bean, String labelQuantity, Collection labelFormatCollection, Collection labelFieldDefinitionCollection, boolean pqvalues) {
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
					receiptKitLabel.append("^").append(labelFieldId).append("^FD").append(BeanHandler.getField(bean, labelFieldContent)).append("^FS").append(lineFeed);
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
	
	public String printGenericLabel(BigDecimal labelPrintQty, String printerPath, Collection<LabelFormatFieldDefinitionBean> fieldDefinitions, LabelFormatTemplateFileBean template, Collection<BaseDataBean> data, Connection conn) throws Exception {
		// Add the ZPL for each label
		StringBuilder zpl = new StringBuilder();
		zpl.append(template.getLabelTemplate()).append(LINE_FEED);
		for (BaseDataBean label : data)
			addLabelZPL(zpl, labelPrintQty, template.getLabelFormat(), fieldDefinitions, label);
		
		//Attempt to use printer
		String printer = new GenericSqlFactory(new DbManager(getClient())).selectSingleValue("select cups_name from printer where printer_path = " + SqlHandler.delimitString(printerPath), conn);
		try {
			PrintHandler.printString(printer, zpl.toString());
			return "";
		} catch (BaseException e) {}
		
		//if cannot use printer, save file
		ResourceLibrary resource = new ResourceLibrary("label");
		File tempDir = new File(resource.getString("label.serverpath"));
		File jnlp = File.createTempFile(template.getLabelFormat() + "jnlp", ".jnlp", tempDir);
		File testFile = File.createTempFile(template.getLabelFormat(), ".txt", tempDir);
		//write to file
		PrintWriter pw = new PrintWriter(new FileOutputStream(testFile.getAbsolutePath()));
		pw.println(zpl.toString());
		pw.close();
		ZplHandler.writeJnlpFileToDisk(jnlp.getAbsolutePath(), testFile.getName(), printer, zpl.toString());
		
		return resource.getString("label.hosturl") + resource.getString("label.urlpath") + jnlp.getName();
	}
	
	private void addLabelZPL(StringBuilder zpl, BigDecimal labelPrintQty, String labelFormat, Collection<LabelFormatFieldDefinitionBean> fieldDefinitions, BaseDataBean bean) throws BaseException {
		// Tell the ZPL printer what template we are using
		zpl.append("^XA").append(LINE_FEED);
		zpl.append("^XF").append(labelFormat).append("^FS").append(LINE_FEED);
		zpl.append(LINE_FEED);
		// Add the data to the label
		for (LabelFormatFieldDefinitionBean field : fieldDefinitions) {
			zpl.append("^").append(field.getLabelFieldId()).append("^FD").append(BeanHandler.getField(bean, field.getLabelFieldContent())).append("^FS")
					.append(LINE_FEED);
		}
		// Add the print quantity and finish the label
		zpl.append("^PQ").append(labelPrintQty).append(LINE_FEED);
		zpl.append("^XZ").append(LINE_FEED);
	}
}