package com.tcmis.client.report.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.report.beans.MaterialsUsedInputBean;
import com.tcmis.client.report.beans.MaterialsUsedViewBean;
import com.tcmis.client.report.factory.MaterialsUsedViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailViewBean;
/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class MaterialsUsedProcess
	extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public MaterialsUsedProcess(String client) {
	super(client);
	}

 public Collection getSearchResult(MaterialsUsedInputBean bean) throws
	 BaseException {
 DbManager dbManager = new DbManager(getClient());
 MaterialsUsedViewBeanFactory factory =
	 new MaterialsUsedViewBeanFactory(dbManager);

 //SearchCriteria criteria = new SearchCriteria();
 return factory.select(bean);
 }

 public Collection createRelationalObject(Collection
	materialsUsedViewBeanCollection) {
	Collection finalMaterialsUsedViewBeanCollection = new Vector();
	String nextCatalogId = "";
	String nextCatPartNo = "";
	String nextPartGroupNo = "";

	int sameCatPartNoCount = 0;
	Vector collectionVector = new Vector(materialsUsedViewBeanCollection);
	Vector catPartNoVector = new Vector();

	for (int loop = 0; loop < collectionVector.size(); loop++) {
	 MaterialsUsedViewBean currentMaterialsUsedViewBean = (MaterialsUsedViewBean)
		collectionVector.elementAt(loop);

	 String currentCatalogId = "" + currentMaterialsUsedViewBean.getCatalogId() + "";
	 String currentCatPartNo = "" + currentMaterialsUsedViewBean.getCatPartNo() + "";
	 String currentPartGroupNo = com.tcmis.common.util.StringHandler.emptyIfNull(
		 currentMaterialsUsedViewBean.getPartGroupNo());
	 if (! ( (loop + 1) == collectionVector.size())) {
		MaterialsUsedViewBean nextMaterialsUsedViewBean = (MaterialsUsedViewBean)
		 collectionVector.elementAt(loop + 1);

		nextCatalogId = "" + nextMaterialsUsedViewBean.getCatalogId() + "";
		nextCatPartNo = "" + nextMaterialsUsedViewBean.getCatPartNo() + "";
		nextPartGroupNo = com.tcmis.common.util.StringHandler.emptyIfNull(
 	  nextMaterialsUsedViewBean.getPartGroupNo());
	 }
	 else {
		nextCatalogId = "";
		nextCatPartNo = "";
		nextPartGroupNo = "";
	 }

	 boolean sameCatPartNo = false;
	 if (currentCatalogId.equalsIgnoreCase(nextCatalogId) &&
		nextCatPartNo.equalsIgnoreCase(currentCatPartNo) &&
		nextPartGroupNo.equalsIgnoreCase(currentPartGroupNo)) {
		sameCatPartNo = true;
		sameCatPartNoCount++;
	 }

	 MaterialsUsedViewBean materialsUsedViewKitBean = new MaterialsUsedViewBean();
	 materialsUsedViewKitBean.setMaterialDesc(currentMaterialsUsedViewBean.getMaterialDesc());
	 materialsUsedViewKitBean.setPackaging(currentMaterialsUsedViewBean.getPackaging());
	 materialsUsedViewKitBean.setMaterialId(currentMaterialsUsedViewBean.getMaterialId());
	 materialsUsedViewKitBean.setMfgDesc(currentMaterialsUsedViewBean.getMfgDesc());

	 catPartNoVector.add(materialsUsedViewKitBean);

	 if (sameCatPartNo) {

	 }
	 else {
		currentMaterialsUsedViewBean.setKitCollection( (Vector) catPartNoVector.clone());
		catPartNoVector = new Vector();
		currentMaterialsUsedViewBean.setRowSpan(new BigDecimal(sameCatPartNoCount + 1));

		finalMaterialsUsedViewBeanCollection.add(currentMaterialsUsedViewBean);
		sameCatPartNoCount = 0;
	 }
	}

	return finalMaterialsUsedViewBeanCollection;
 }

 public void writeExcelFile(MaterialsUsedInputBean headerBean,Collection searchCollection,String filePath) throws
 BaseException, Exception {

 PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
 pw.println("<html>");

 pw.println("<TABLE BORDER=\"0\">");
 pw.println("<TR>");
 pw.println("<TD COLSPAN=\"2\">");
 pw.println("<B>Materials Used:</B>");
 pw.println("</TD>");

 pw.println("<TD ALIGN=\"RIGHT\">");
 pw.println("<B>Date:</B>");
 pw.println("</TD>");

 pw.println("<TD ALIGN=\"LEFT\">");
 pw.println(""+DateFormat.getInstance().format(new Date())+"");
 pw.println("</TD>");

 pw.println("</TR>");
 pw.println("<TR>");
 pw.println("<TD ALIGN=\"RIGHT\">");
 pw.println("<B>Facility:</B>");
 pw.println("</TD>");

 pw.println("<TD ALIGN=\"LEFT\">");
 pw.println("" + headerBean.getFacilityId() + "");
 pw.println("</TD>");

 pw.println("<TD ALIGN=\"RIGHT\">");
 pw.println("<B>Work Area:</B>");
 pw.println("</TD>");

 pw.println("<TD ALIGN=\"LEFT\">");
 pw.println("" + headerBean.getApplication() + "");
 pw.println("</TD>");

 pw.println("</TR>");
 pw.println("<TR>");

 pw.println("<TD ALIGN=\"RIGHT\">");
 pw.println("<B>Begin:</B>");
 pw.println("</TD>");

 pw.println("<TD ALIGN=\"LEFT\">");
 pw.println("" + com.tcmis.common.util.DateHandler.formatDate(headerBean.
		 getDateDeliveredStart(), "MM/dd/yyyy") + "");
 pw.println("</TD>");

 pw.println("<TD ALIGN=\"RIGHT\">");
 pw.println("<B>End:</B>");
 pw.println("</TD>");

 pw.println("<TD ALIGN=\"LEFT\">");
 pw.println(""+com.tcmis.common.util.DateHandler.formatDate(headerBean.getDateDeliveredEnd(),
	"MM/dd/yyyy") + "");
 pw.println("</TD>");

 pw.println("</TR>");
 pw.println("</TABLE>");
 pw.println("<BR><BR>");
 pw.println("<table border=\"1\">");
 pw.println("<TR>");
 pw.println("<TH width=\"5%\">Part Number</TH>");
 pw.println("<TH width=\"5%\">Material Description</TH>");
 pw.println("<TH width=\"5%\">Manufacturer</TH>");
 pw.println("<TH width=\"5%\">Packaging</TH>");
 pw.println("<TH width=\"5%\">Material ID</TH>");
 pw.println("</TR>");

 //print rows
 Iterator mainIterator = searchCollection.iterator();
 while (mainIterator.hasNext()) {
	pw.println("<TR>");

	MaterialsUsedViewBean
	 materialsUsedViewBean = (
	 MaterialsUsedViewBean) mainIterator.next(); ;

	int mainRowSpan = materialsUsedViewBean.getRowSpan().
	 intValue();
	Collection materialsUsedViewKitBeanCollection = materialsUsedViewBean.getKitCollection();
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	 materialsUsedViewBean.getCatPartNo() + "</TD>");

	int kitCount = 0;
	Iterator kitIterator = materialsUsedViewKitBeanCollection.iterator();
	while (kitIterator.hasNext()) {
	 kitCount++;
	 MaterialsUsedViewBean materialsUsedViewKitBean = (
		MaterialsUsedViewBean) kitIterator.next(); ;

	 if (kitCount > 1 && materialsUsedViewKitBeanCollection.size() > 1) {
		pw.println("<TR>");
	 }

	 pw.println("<TD>" + materialsUsedViewKitBean.getMaterialDesc() + "</TD>");
	 if (kitCount == 1)
	 {
		pw.println("<TD ROWSPAN=" + mainRowSpan + ">" + materialsUsedViewKitBean.getMfgDesc() + "</TD>");
	 }
	 pw.println("<TD>" + materialsUsedViewKitBean.getPackaging() + "</TD>");
	 pw.println("<TD>" + materialsUsedViewKitBean.getMaterialId() + "</TD>");

	 if (kitCount > 1 || materialsUsedViewKitBeanCollection.size() == 1) {
		pw.println("</TR>");
	 }
	}
 }
 pw.println("</html>");
 pw.close();
}
}
