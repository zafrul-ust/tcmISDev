package com.tcmis.internal.print.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.MsdsIndexingBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.ZplHandler;
import com.tcmis.internal.hub.beans.LabelFormatTemplateFileBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.factory.LocationLabelPrinterBeanFactory;
import com.tcmis.internal.print.beans.StaticLabelBean;

/******************************************************************************
 * Process for receiving qc
 * @version 1.0
 *****************************************************************************/
public class StaticLabelProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public StaticLabelProcess(String client) {
		super(client);
	}

	public String buildStaticLabels(String labelFileName,  String printerPath,  int printQuantity) throws BaseException {
		String labelUrl = "";
		ResourceLibrary resource = new ResourceLibrary("label");

		File dir = new File(resource.getString("label.serverpath"));
		try {
			File jnlpFile = File.createTempFile("labeljnlp", ".jnlp", dir);
			File zplFile = File.createTempFile("labeltxt", ".txt", dir);
			String templatePath = resource.getString("label.template.path");

			String zpl = buildStaticLabelZpl(labelFileName, templatePath, zplFile, printQuantity);

			ZplHandler.writeJnlpFileToDisk(jnlpFile.getAbsolutePath(), zplFile.getName(), printerPath, zpl);

			labelUrl = resource.getString("label.hosturl") + resource.getString("label.urlpath") + jnlpFile.getName();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			labelUrl = "";
		}

		return labelUrl;
	}

	public Collection<StaticLabelBean> getStaticLabels() throws BaseException {
		factory.setBean(new StaticLabelBean());
		return factory.select(null, null, "STATIC_LABEL");
	}

	private String buildStaticLabelZpl(String labelFileName, String templatePath, File zplFile,  int printQuantity) throws Exception {
		PrintWriter pw = new PrintWriter(new FileOutputStream(zplFile));
		StringBuilder zpl = new StringBuilder();

		String lineFeed = "" + (char) (13) + (char) (10);

		try {
			LabelFormatTemplateFileBean labelTemplate = getLabelTemplateFromFileName(labelFileName);
			if (labelTemplate != null) {
				zpl.append(labelTemplate.getLabelTemplate()).append(lineFeed);
			}
			zpl.append("^PQ").append(printQuantity).append(lineFeed);
			zpl.append("^XZ").append(lineFeed);

			pw.print(zpl);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			pw.close();
		}
		
		return zpl.toString();
	}
	
	private LabelFormatTemplateFileBean getLabelTemplateFromFileName(String filename) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		factory.setBean(new LabelFormatTemplateFileBean());
		
		StringBuilder query = new StringBuilder();
		query.append("select label_template from label_format_template ");
		query.append("where label_template_filename = '").append(filename).append("'");
		
		Collection<LabelFormatTemplateFileBean> coll = factory.selectQuery(query.toString());
		
		// there should only be one bean in the collection
		LabelFormatTemplateFileBean bean = null;
		if ( ! coll.isEmpty()) {
			bean = coll.iterator().next();
		}
		
		return bean;
	}

	public String getPrinterPath(PersonnelBean personnelBean) throws BaseException {
		LocationLabelPrinterBeanFactory factory = new LocationLabelPrinterBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (personnelBean.getPrinterLocation() !=null) {
			criteria.addCriterion("printerLocation", SearchCriterion.EQUALS,
					personnelBean.getPrinterLocation());
		}

		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());
		Collection<LocationLabelPrinterBean> locationLabelPrinterCollection = factory.selectDistinct(criteria);
		for  (LocationLabelPrinterBean locationLabelPrinterBean : locationLabelPrinterCollection) {
			return locationLabelPrinterBean.getPrinterPath();
		}

		return "";
	}


}
