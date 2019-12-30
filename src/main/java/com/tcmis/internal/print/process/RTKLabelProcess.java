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
import com.tcmis.internal.print.beans.RTKLabelBean;

public class RTKLabelProcess extends GenericProcess {

	private static String	defaultLabelType	= "rightToKnow";
	private static String	defaultLabelFormat	= "defaultRightToKnow";

	public RTKLabelProcess(String client) {
		super(client);
	}

	public RTKLabelProcess(String client, String locale) {
		super(client, locale);
	}

	public RTKLabelProcess(String client, Locale locale) {
		super(client, locale);
	}

	public RTKLabelProcess() {
		// Initialize with the client
		// that is appropriate for type of report data
		this("TCM_OPS");
	}

	@SuppressWarnings("unchecked")
	public Collection<RTKLabelBean> getLabelData(String itemId) throws BaseException  {
		DbManager dbManager = new DbManager(getClient(), this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);

		StringBuilder query = new StringBuilder();
		query.append("SELECT * ");
		query.append("FROM TABLE (pkg_chemical_list.FX_ITEM_NJ_RIGHT_TO_KNOW (");
		query.append(itemId).append("))");

		factory.setBean(new RTKLabelBean());
		Collection<RTKLabelBean> result = factory.selectQuery(query.toString());

		return result;
	}

	@SuppressWarnings("unchecked")
	public String buildRTKLabels(Collection<RTKLabelBean> labelDataCollection, Collection locationLabelPrinterCollection, int labelQty) throws Exception {
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

		String theZPL = getRTKLabelZPL(labelDataCollection, printerResolution, labelQty);

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

	public String getRTKLabelZPL(Collection<RTKLabelBean> labelDataCollection, String printerResolution, int labelQty) throws BaseException {
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());
		
		// get default label format definition
		// there is only one format and template for this label type regardless of inventory group
		Collection labelFormatTemplateCollection = getDefaultLabelFormatTemplate(printerResolution);

		if (labelFormatTemplateCollection == null || labelFormatTemplateCollection.size() == 0) {
			BaseException ex = new BaseException();
			ex.setMessageKey("error.nolabeltemplatefile");
			throw ex;
		}
		
		// get label format field definitions
		// FN1 through FN10 are reserved for Ingredient/CAS# pairs 
		Collection<InventoryGroupLabelFormatBean> labelFormatCollection = new Vector();
		labelFormatCollection.add(getDefaultLabelFormat());
		Collection<LabelFormatFieldDefinitionBean> labelFieldDefinitionCollection = zplDataProcess.getLabelFormatFieldDefinition(labelFormatCollection, printerResolution);

		StringBuilder zpl = new StringBuilder();

		// print template ZPL code
		zpl.append(com.tcmis.common.util.ZplHandler.printTemplatesNoFiles(labelFormatTemplateCollection));

		try {
			// print defined ZPL variables and matching data
			// one label is printed for each material in a kit
			String curMaterialId = "";
			StringBuilder rtkLabel = new StringBuilder();
			int fieldId = 1;
			
			String lineFeed = "";
			lineFeed += (char) (13);
			lineFeed += (char) (10);
			
			// the data collection is iterated through for ingredients and CAS numbers
			// all other data specified in label_format_field_definition is processed once from the first record for each material
			for (RTKLabelBean rtkLabelBean : labelDataCollection) {
				if(!rtkLabelBean.getMaterialId().toString().equals(curMaterialId)){
					if(!StringHandler.isBlankString(curMaterialId)){
						// end definition of previous label's fields
						rtkLabel.append("^PQ").append(labelQty).append(lineFeed); 
						rtkLabel.append("^XZ").append(lineFeed);

						zpl.append(rtkLabel.toString());
					}
					
					// start field definitions of new label
					curMaterialId = rtkLabelBean.getMaterialId().toString();
					fieldId = 1;
					rtkLabel = new StringBuilder();
					
					rtkLabel.append("^XA").append(lineFeed);
					rtkLabel.append("^XF").append(defaultLabelFormat).append("^FS").append(lineFeed);
					
					for (LabelFormatFieldDefinitionBean labelFormatFieldDefinitionBean : labelFieldDefinitionCollection) {
						rtkLabel.append("^").append(labelFormatFieldDefinitionBean.getLabelFieldId());
						rtkLabel.append("^FD").append(BeanHandler.getField(rtkLabelBean, labelFormatFieldDefinitionBean.getLabelFieldContent())).append("^FS");
						rtkLabel.append(lineFeed);
					}
				}
				
				if(fieldId <= 10) {
					// ingredient
					rtkLabel.append("^").append("FN").append(fieldId);
					rtkLabel.append("^FD").append(rtkLabelBean.getIngredient()).append("^FS");
					rtkLabel.append(lineFeed);
					fieldId++;
					
					// cas number
					if(!rtkLabelBean.getIngredient().toUpperCase().equals("CONTENTS UNKNOWN") && !rtkLabelBean.getIngredient().toUpperCase().equals("CONTENTS PARTIALLY UNKNOWN")){
						rtkLabel.append("^").append("FN").append(fieldId);
						rtkLabel.append("^FD").append(rtkLabelBean.getCasNumber()).append("^FS");
						rtkLabel.append(lineFeed);
					}
					fieldId++;
				}
			}
			
			// make sure the last material id processed is appended
			rtkLabel.append("^PQ").append(labelQty).append(lineFeed); 
			rtkLabel.append("^XZ").append(lineFeed);
			zpl.append(rtkLabel.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
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
