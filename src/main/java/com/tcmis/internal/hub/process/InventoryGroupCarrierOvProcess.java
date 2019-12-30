package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.hub.beans.InventoryGroupCarrierOvBean;
import com.tcmis.internal.hub.factory.InventoryGroupCarrierOvBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import java.util.Locale;
import java.io.Writer;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriterion;

import java.math.BigDecimal;
import com.tcmis.common.util.ExcelHandler;


   /******************************************************************************
   * Process for InventoryGroupCarrierOv
   * @version 1.0
   *****************************************************************************/
  public class InventoryGroupCarrierOvProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public InventoryGroupCarrierOvProcess(String client) {
  	super(client);
   }
   
   public InventoryGroupCarrierOvProcess(String client,String locale) {
	    super(client,locale);
   }

   public Collection getSearchResult(BigDecimal personnelId) throws BaseException {

  	DbManager dbManager = new DbManager(getClient(),getLocale());
  	InventoryGroupCarrierOvBeanFactory factory = new
  	 InventoryGroupCarrierOvBeanFactory(dbManager);
  	SearchCriteria criteria = new SearchCriteria();
  	 criteria.addCriterion("personnelId",
  		SearchCriterion.EQUALS,
  		personnelId.toString());
  	return factory.selectObject(criteria);
   }

   public ExcelHandler getExcelReport(Collection bean, Locale locale) throws
   Exception {
	   if(log.isDebugEnabled()) {
		   log.debug("locale:" + locale);
		   log.debug("country:" + locale.getCountry());
		   log.debug("language:" + locale.getLanguage());}
	   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	   Collection<InventoryGroupCarrierOvBean> data = bean; 
	   ExcelHandler pw = new ExcelHandler(library);

	   pw.addTable();
//	   write column headers
	   pw.addRow();
	   String[] values = { 
			   "label.companyId",
			   "label.personnelId",
			   "label.inventoryGroup",
			   "label.modeCarrier"
	   };
	   for(String strval: values)
		   pw.addCellKeyBold(strval);

//	   now write data
	   for(InventoryGroupCarrierOvBean member:data) {
		   pw.addRow();
		   String[] contents = {
				   member.getCompanyId().toString(),
				   member.getPersonnelId().toString(),
				   member.getInventoryGroup().toString(),
				   member.getModeCarrier().toString()
		   };
		   for(String strval: contents)
			   pw.addCell(strval);
	   }
	   return pw;
   }

public Collection updateValue(InventoryGroupCarrierOvBean bean)  throws
BaseException {
Collection inArgs = new Vector();
 if( bean.getCompanyId() != null) {
inArgs.add( bean.getCompanyId() );
}
else {
inArgs.add("");
}
if( bean.getPersonnelId() != null) {
inArgs.add( bean.getPersonnelId() );
}
else {
inArgs.add("");
}
if( bean.getInventoryGroup() != null) {
inArgs.add( bean.getInventoryGroup() );
}
else {
inArgs.add("");
}
if( bean.getModeCarrier() != null) {
inArgs.add( bean.getModeCarrier() );
}
else {
inArgs.add("");
}

Vector outArgs = new Vector();
outArgs.add( new Integer(java.sql.Types.VARCHAR) );
DbManager dbManager = new DbManager(getClient(),getLocale());
GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
return procFactory.doProcedure("change_here_update", inArgs,outArgs);
}

}
