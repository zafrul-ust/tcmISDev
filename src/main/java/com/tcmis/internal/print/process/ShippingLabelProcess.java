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
import com.tcmis.common.framework.GenericProcess;
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
import com.tcmis.internal.hub.beans.BillOfLadingViewBean;

public class ShippingLabelProcess extends GenericProcess {

	private static String	defaultLabelType	= "shipping";
	private static String	defaultLabelFormat	= "defaultshipping";

	public ShippingLabelProcess(String client) {
		super(client);
	}

	public ShippingLabelProcess(String client, String locale) {
		super(client, locale);
	}

	public ShippingLabelProcess(String client, Locale locale) {
		super(client, locale);
	}

	public ShippingLabelProcess() {
		// Initialize with the client
		// that is appropriate for type of report data
		this("TCM_OPS");
	}

	@SuppressWarnings("unchecked")
	public Collection<BillOfLadingViewBean> getLabelData(String prNumbers, String lineItems) throws BaseException  {
		String[] prNumberArr = prNumbers.split(",");
		String[] lineItemArr = lineItems.split(",");

		DbManager dbManager = new DbManager(getClient(), this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);

		StringBuilder query = new StringBuilder();
		query.append("SELECT distinct hub_address_line_1, hub_address_line_2, hub_address_line_3, ");
		query.append("hub_city || ' ' || hub_state_abbrev || ' ' || hub_zip hub_address_line_4, hub_country_abbrev, ");
		query.append("ship_to_address_line_1, ship_to_address_line_2, ship_to_address_line_3, ");
		query.append("ship_to_city || ' ' || ship_to_state_abbrev || ' ' || ship_to_zip ship_to_address_line_4, ship_to_country_abbrev, inventory_group ");
		query.append("FROM bill_of_lading_view ");
		query.append("WHERE ");

		for (int i = 0; i < prNumberArr.length; i++) {
			if (i > 0)
				query.append(" OR ");

			query.append("(pr_number =  " + prNumberArr[i]);
			query.append(" AND line_item =  " + lineItemArr[i] + ")");
		}

		factory.setBean(new BillOfLadingViewBean());
		Collection<BillOfLadingViewBean> result = factory.selectQuery(query.toString());

		return result;
	}

	@SuppressWarnings("unchecked")
	public String buildShippingLabels(Collection<BillOfLadingViewBean> labelDataCollection, Collection locationLabelPrinterCollection) throws Exception {
		ResourceLibrary resource = new ResourceLibrary("label");
		File dir = new File(resource.getString("label.serverpath"));
		File file = File.createTempFile("labeltxt", ".txt", dir);
		File jnlpDir = new File(resource.getString("label.serverpath"));
		File jnlpFile = File.createTempFile("largelabeljnlp", ".jnlp", jnlpDir);

		// get printer information
		String printerPath = "";
		String printerResolution = "";

		Iterator printerIterator = locationLabelPrinterCollection.iterator();
		while (printerIterator.hasNext()) {
			LocationLabelPrinterBean locationLabelPrinterBean = (LocationLabelPrinterBean) printerIterator.next();

			printerResolution = "" + locationLabelPrinterBean.getPrinterResolutionDpi() + "";
			printerPath = locationLabelPrinterBean.getPrinterPath();
		}

		String theZPL = getShippingLabelZPL(labelDataCollection, printerResolution);

		// stream for outputting ZPL file
		PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));

		try {
			pw.print(theZPL);
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}
		finally {
			pw.close();
		}
		
		com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(jnlpFile.getAbsolutePath(), jnlpFile.getName(), printerPath, theZPL);

		String labelUrl = resource.getString("label.hosturl") + resource.getString("label.urlpath") + jnlpFile.getName();

		return labelUrl;
	}

	public String getShippingLabelZPL(Collection<BillOfLadingViewBean> labelDataCollection, String printerResolution) throws BaseException {
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());
		
		// get label format definitions for each inventory group
		Vector distinctInventoryGroupVector = new Vector();
		for (BillOfLadingViewBean bolViewBean : labelDataCollection) {
			String currentInventoryGroup = bolViewBean.getInventoryGroup();
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
		if (templateItr.hasNext())
			labelFormatTemplateCollection.add((LabelFormatTemplateFileBean) templateItr.next());

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
			for (BillOfLadingViewBean bolViewBean : labelDataCollection) {
				StringBuilder shippingLabel = new StringBuilder();
				String lineFeed = "";
				lineFeed += (char) (13);
				lineFeed += (char) (10);

				// search for this inventory group's label format
				String labelFormat = "";

				for (InventoryGroupLabelFormatBean inventoryGroupLabelFormatBean : labelFormatCollection) {
					if (inventoryGroupLabelFormatBean.getInventoryGroup().equalsIgnoreCase(bolViewBean.getInventoryGroup())) {
						labelFormat = inventoryGroupLabelFormatBean.getLabelFormat();
					}
				}

				// if no label format defined for inventory group, use default
				// bag label format
				if (StringHandler.isBlankString(labelFormat))
					labelFormat = defaultLabelFormat;

				shippingLabel.append("^XA").append(lineFeed);
				shippingLabel.append("^XF").append(labelFormat).append("^FS").append(lineFeed);

				// print defined ZPL variables and matching data
				for (LabelFormatFieldDefinitionBean labelFormatFieldDefinitionBean : labelFieldDefinitionCollection) {
					if (labelFormatFieldDefinitionBean.getLabelFormat().equalsIgnoreCase(labelFormat + "Struts")) {
						String labelFieldId = labelFormatFieldDefinitionBean.getLabelFieldId();
						String labelFieldContent = labelFormatFieldDefinitionBean.getLabelFieldContent();

							shippingLabel.append("^").append(labelFieldId);
							shippingLabel.append("^FD").append(BeanHandler.getField(bolViewBean, labelFieldContent)).append("^FS").append(lineFeed);
					}
				}

				shippingLabel.append("^PQ1").append(lineFeed); // print 1 copy
																// of each label
				shippingLabel.append("^XZ").append(lineFeed);

				zpl.append(shippingLabel.toString());
			}
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}

		return zpl.toString();

	}
	
	public Collection getLocationLabelPrinter(PersonnelBean personnelBean, LabelInputBean labelInputBean) throws BaseException {
		LocationLabelPrinterBeanFactory factory = new LocationLabelPrinterBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (personnelBean.getPrinterLocation() != null) {
			criteria.addCriterion("printerLocation", SearchCriterion.EQUALS, personnelBean.getPrinterLocation());
		}
		else if (labelInputBean.hasPrinterLocation()) {
			criteria.addCriterion("printerLocation", SearchCriterion.EQUALS, labelInputBean.getPrinterLocation(), "Y");
		}

		criteria.addCriterion("labelStock", SearchCriterion.EQUALS, labelInputBean.getPaperSize());

		if (labelInputBean.hasPrinterPath()) {
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
		StringBuilder query = new StringBuilder();
		query.append("select y.label_template_filename, z.label_template from label_format_template_file y, label_format_template z ");
		query.append("where y.label_format = '").append(defaultLabelFormat).append("' ");
		query.append("and y.printer_resolution_dpi = '").append(printerResolution).append("' and y.label_template_filename = z.label_template_filename");

		factory.setBean(new LabelFormatTemplateFileBean());

		return getSearchResult(query.toString());
	}
}
