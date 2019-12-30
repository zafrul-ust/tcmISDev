package com.tcmis.internal.print.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.InventoryGroupLabelFormatBean;
import com.tcmis.internal.hub.beans.LabelFormatFieldDefinitionBean;
import com.tcmis.internal.hub.beans.LabelFormatTemplateFileBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.factory.LocationLabelPrinterBeanFactory;
import com.tcmis.internal.print.beans.BagLabelViewBean;

public class BagLabelProcess extends BaseProcess {
	
	private static String defaultLabelType = "bag";
	private static String defaultLabelFormat = "defaultbag";

	public BagLabelProcess(String client) {
		super(client);
	}

	public BagLabelProcess(String client, String locale) {
		super(client,locale);
	}	

	public BagLabelProcess(String client, Locale locale) {
		super(client,locale);
	}		
	
	public BagLabelProcess() {
		// Initialize with the client 
		//	that is appropriate for type of report data
		this("TCM_OPS");
	}
	
	public Collection getLabelData(String prNumbers, String lineItems, String picklistIds) throws Exception {
		String[] prNumberArr = prNumbers.split(",");
		String[] lineItemArr = lineItems.split(",");
		String[] picklistIdArr = picklistIds.split(",");
		
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT application, cat_part_no, customer_po_no, delivery_point, line_item, inventory_group, ");
		query.append(" mfg_lot, part_desc, picklist_id, pr_number, quantity, shipping_reference ");
		query.append("FROM bag_label_view ");	
		query.append("WHERE ");
		
		for(int i=0; i<prNumberArr.length; i++){
			if(i > 0)
				query.append(" OR ");
			
			query.append("(pr_number =  " + prNumberArr[i]);		 
			query.append(" AND line_item =  " + lineItemArr[i]);		
			query.append(" AND picklist_id =  " + picklistIdArr[i] + ")");		
		}			

		factory.setBean(new BagLabelViewBean());
		Collection result = factory.selectQuery(query.toString());	

		return result;
	}
	

	@SuppressWarnings("unchecked")
	public String buildBagLabels(Collection labelDataCollection, Collection locationLabelPrinterCollection) throws Exception {		
		ResourceLibrary resource = new ResourceLibrary("label");
		File dir = new File(resource.getString("label.serverpath"));
		File file = File.createTempFile("labeltxt", ".txt", dir);		
		File jnlpDir = new File(resource.getString("label.serverpath"));
		File jnlpFile = File.createTempFile("largelabeljnlp", ".jnlp", jnlpDir);
		
		// stream for outputting ZPL file
		PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());

		// get printer information 
		String printerPath = "";
		String printerResolution = "";

		Iterator printerIterator = locationLabelPrinterCollection.iterator();
		while (printerIterator.hasNext()) {
			LocationLabelPrinterBean locationLabelPrinterBean = (LocationLabelPrinterBean) printerIterator.next();

			printerResolution = "" + locationLabelPrinterBean.getPrinterResolutionDpi() + "";
			printerPath = locationLabelPrinterBean.getPrinterPath();
		}

		// get label format definitions for each inventory group
		Vector distinctInventoryGroupVector = new Vector();
		Iterator mainIterator = labelDataCollection.iterator();
		while (mainIterator.hasNext()) {
			BagLabelViewBean bagLabelViewBean = (BagLabelViewBean) mainIterator.next();

			String currentInventoryGroup = bagLabelViewBean.getInventoryGroup();
			if (!distinctInventoryGroupVector.contains(currentInventoryGroup)) {
				distinctInventoryGroupVector.add(currentInventoryGroup);
			}
		}

		Collection<InventoryGroupLabelFormatBean> labelFormatCollection = zplDataProcess.getLabelFormats(distinctInventoryGroupVector, defaultLabelType);
		Collection<LabelFormatTemplateFileBean> labelFormatTemplateCollection = zplDataProcess.getTemplates(distinctInventoryGroupVector, defaultLabelType, printerResolution);
		
		// add default label
		labelFormatCollection.add(getDefaultLabelFormat());
		
		Collection defaultLabelFormatTemplateCollection = getDefaultLabelFormatTemplate(printerResolution);
		Iterator templateItr = defaultLabelFormatTemplateCollection.iterator();
		if(templateItr.hasNext())
			labelFormatTemplateCollection.add((LabelFormatTemplateFileBean)templateItr.next());
		
		if (labelFormatTemplateCollection == null || labelFormatTemplateCollection.size() == 0) {
			BaseException ex = new BaseException();
			ex.setMessageKey("error.nolabeltemplatefile");
			throw ex;
		}		
		
		// get label format field definitions
		Collection<LabelFormatFieldDefinitionBean> labelFieldDefinitionCollection = zplDataProcess.getLabelFormatFieldDefinition(labelFormatCollection, printerResolution);		
		
		if (labelFieldDefinitionCollection == null || labelFieldDefinitionCollection.size() == 0) {
			BaseException ex = new BaseException();
			ex.setMessageKey("error.nolabeltemplate.defined");
			throw ex;
		}

		StringBuilder zpl = new StringBuilder();

		// print template ZPL code
		zpl.append(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(labelFormatTemplateCollection));

		try {
			mainIterator = labelDataCollection.iterator();
			while (mainIterator.hasNext()) {
				BagLabelViewBean bagLabelViewBean = (BagLabelViewBean) mainIterator.next();

				StringBuilder bagLabel = new StringBuilder();
				String lineFeed = "";
				lineFeed += (char) (13);
				lineFeed += (char) (10);

				// search for this inventory group's label format
				String labelFormat = "";
				Iterator labelFormatIterator = labelFormatCollection.iterator();
				while (labelFormatIterator.hasNext()) {
					InventoryGroupLabelFormatBean inventoryGroupLabelFormatBean = (InventoryGroupLabelFormatBean) labelFormatIterator.next();

					if (inventoryGroupLabelFormatBean.getInventoryGroup().equalsIgnoreCase(bagLabelViewBean.getInventoryGroup())) {
						labelFormat = inventoryGroupLabelFormatBean.getLabelFormat();
					}
				}
				
				// if no label format defined for inventory group, use default bag label format
				if(StringHandler.isBlankString(labelFormat))
					labelFormat = defaultLabelFormat;

				bagLabel.append("^XA").append(lineFeed);
				bagLabel.append("^XF").append(labelFormat).append("^FS").append(lineFeed);

				// print defined ZPL variables and matching data
				Iterator labelFieldIterator = labelFieldDefinitionCollection.iterator();
				while (labelFieldIterator.hasNext()) {
					LabelFormatFieldDefinitionBean labelFormatFieldDefinitionBean = (LabelFormatFieldDefinitionBean) labelFieldIterator.next();

					if (labelFormatFieldDefinitionBean.getLabelFormat().equalsIgnoreCase(labelFormat+"Struts")) {
						String labelFieldId = labelFormatFieldDefinitionBean.getLabelFieldId();
						String labelFieldContent = labelFormatFieldDefinitionBean.getLabelFieldContent();

						try {							
							bagLabel.append("^").append(labelFieldId);
							bagLabel.append("^FD").append(BeanHandler.getField(bagLabelViewBean, labelFieldContent)).append("^FS").append(lineFeed);
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
				
				bagLabel.append("^PQ1").append(lineFeed);  // print 1 copy of each label
				bagLabel.append("^XZ").append(lineFeed);
				
				zpl.append(bagLabel.toString());
			}
			
			pw.print(zpl.toString());
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}
		finally {
			pw.close();
		}
		com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(jnlpFile.getAbsolutePath(), jnlpFile.getName(), printerPath, zpl.toString());
		
		String labelUrl = resource.getString("label.hosturl") +
				resource.getString("label.urlpath") +
				jnlpFile.getName();
		
		return labelUrl;
	}
	
	public Collection getLocationLabelPrinter(PersonnelBean personnelBean, LabelInputBean labelInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		LocationLabelPrinterBeanFactory factory = new LocationLabelPrinterBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (personnelBean.getPrinterLocation() !=null)
		{
			criteria.addCriterion("printerLocation", SearchCriterion.EQUALS, personnelBean.getPrinterLocation());
		}
		else if (labelInputBean.getPrinterLocation() !=null && labelInputBean.getPrinterLocation().length() > 0)
		{
			criteria.addCriterion("printerLocation", SearchCriterion.EQUALS, labelInputBean.getPrinterLocation(), "Y");
		}

		criteria.addCriterion("labelStock", SearchCriterion.EQUALS, labelInputBean.getPaperSize());

		if (labelInputBean.getPrinterPath() != null && labelInputBean.getPrinterPath().length() > 0) {
			criteria.addCriterion("printerPath", SearchCriterion.EQUALS, labelInputBean.getPrinterPath(), "Y");
		}

		return factory.selectDistinct(criteria);
	}
	
	private InventoryGroupLabelFormatBean getDefaultLabelFormat() throws BaseException {
		
		InventoryGroupLabelFormatBean defaultFormat = new InventoryGroupLabelFormatBean();

		defaultFormat.setInventoryGroup("Default");
		defaultFormat.setLabelFormat(defaultLabelFormat);
		defaultFormat.setLabelType(defaultLabelType);
		
		return defaultFormat;
	}
	
	private Collection getDefaultLabelFormatTemplate(String printerResolution) throws BaseException {
/*		DbManager dbManager = new DbManager(getClient());
		Collection locationLabelPrinterCollection = new Vector();
		LabelFormatTemplateFileBeanFactory factory = new LabelFormatTemplateFileBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();
		
		criteria.addCriterion("printerResolutionDpi", SearchCriterion.EQUALS, printerResolution);
		criteria.addCriterion("labelFormat", SearchCriterion.EQUALS, defaultLabelFormat);
		
		return factory.select(criteria);
		*/
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		factory.setBean(new LabelFormatTemplateFileBean());
		
		StringBuilder query = new StringBuilder();
		 query.append("select y.label_template_filename, z.label_template from label_format_template_file y, label_format_template z ");
		 query.append("where y.label_format = '").append(defaultLabelFormat).append("' ");
		 query.append("and y.printer_resolution_dpi = '").append(printerResolution).append("' and y.label_template_filename = z.label_template_filename");
		
		return factory.selectQuery(query.toString());	
	}
}
